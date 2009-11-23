package automenta.spacenet.space.dynamic.collection;

import java.util.Map.Entry;

import automenta.spacenet.Maths;
import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.HasSize3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.video2d.HasSize2;
import automenta.spacenet.var.map.WhenMapEntriesChange;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;


public class ArrangeGrid implements StartsIn<Scope>, Stops  {

	public static enum Mode { Column, Grid, Row }
	private double cellPadding;
	protected int cellsTall;
	protected int cellsWide;
	private double marginProp;

	protected Mode mode;;
	
	private int num;
	private Scope node;
	private Space space;
	private Object whenPositionChanges;
	private Object whenSizeChanges;
	private Object whenAttachedChanges;
	private Class<Button> onlySubClassesOf;
	
	
	public ArrangeGrid(Space space) {
		this(space, 0, 0);
	}

	public ArrangeGrid(Space space, double cellPaddingProp, double marginProp) {
		this(space, cellPaddingProp, marginProp, Mode.Grid);
	}
	
	public ArrangeGrid(Space space, double cellPaddingProp, double marginProp, Mode mode) {
		super();

		this.space = space;
		this.mode = mode;
		this.cellPadding = cellPaddingProp;
		this.marginProp = marginProp;
	}

	public ArrangeGrid(Space space, Class onlySubClassesOf) {
		this(space);
		this.onlySubClassesOf = onlySubClassesOf;
	}

	public ArrangeGrid(Space space, Class subClassesOf, double cellPaddingProp, double marginProp, Mode mode) {
		this(space, cellPaddingProp, marginProp, mode);
		this.onlySubClassesOf = subClassesOf;
	}

	public Space getSpace() { return space; }

	public Vector2 getSpaceSize() {
		if (space instanceof HasSize2) {
			return ((HasSize2)space).getSize();
		}
		else /*if (space instanceof HasSize3)*/ {
			Vector3 size3 = ((HasSize3)space).getSize();
			return new Vector2(size3.x(), size3.y());
		}
	}
	
	public Scope getNode() { return node; }
	
	@Override public void stop() {
		if (whenPositionChanges!=null) {
			getNode().remove(whenPositionChanges);
			whenPositionChanges = null;
		}
		if (whenSizeChanges!=null) {
			getNode().remove(whenSizeChanges);
			whenSizeChanges = null;
		}
		if (whenAttachedChanges!=null) {
			getNode().remove(whenAttachedChanges);
			whenAttachedChanges = null;
		}

	}
	
	protected boolean updateDimensions() {
		Vector2 spaceSize = getSpaceSize();

		int nextCellsTall, nextCellsWide;
		
		num = 0;
		for (Object r : getSpace().getObjects().values())
			if (isArrangeable(r))
				num++;

		if (mode == Mode.Row) {
			nextCellsTall = 1;
			nextCellsWide = num;
		}
		else if (mode == Mode.Column) {
			nextCellsTall = num;
			nextCellsWide = 1;			
		}
		else /*if (mode == Mode.Grid)*/ {
			if (num < 1) {
				nextCellsTall = nextCellsWide = 0;
			}
			else if ((spaceSize.x() == 0) || (spaceSize.y() == 0)) {
				nextCellsTall = nextCellsWide = 0;
			}
			else {
		
				if (spaceSize.x() > spaceSize.y()) {
					//wider than tall, limit by width's multiplier
					double m = spaceSize.x() / spaceSize.y();
					nextCellsTall = (int)Math.ceil( Math.sqrt(num) / m);
					nextCellsWide = (int)Math.ceil(  ((double)num) / ((double)nextCellsTall) );
				}
				else {
					//taller than wide
					double m = spaceSize.y() / spaceSize.x();
					nextCellsWide = (int)Math.ceil( Math.sqrt(num) / m);
					nextCellsTall = (int)Math.ceil(  ((double)num) / ((double)nextCellsWide) );
				}
			}
		}
				
		boolean changed = ((nextCellsTall!=cellsTall) || (nextCellsWide!=cellsWide));
		
		cellsTall = nextCellsTall;
		cellsWide = nextCellsWide;
		
		return changed;
	}
	
	public void arrange(Space space) {
		this.space = space;
		arrange();
	}
	
	public void arrange() {
		if (space == null)
			return;
		
		updateDimensions();
		
		if ((cellsWide == 0) || (cellsTall == 0))
			return;
		
		double cellFactor = 1.0 - cellPadding - marginProp/2.0 ; //implements cellpadding

		double cw, ch, x, y;
		double rowStartX;
		if (cellsWide > 1) {
			cw = 1.0 / ((cellsWide));
			rowStartX = -0.5 + (cw * cellFactor)/2.0;
		}
		else {
			cw = 1.0;
			rowStartX = 0;
		}
		
		if (cellsTall > 1) {
			ch = 1.0 / ((cellsTall));
			y = 0.5 - (ch * cellFactor)/2.0;
		}
		else {
			ch = 1.0;
			y = 0;
		}


		int n = 0;

//		rowStartX += marginProp/4.0;
//		y -= marginProp/4.0;

		x = rowStartX;

		for (Object r : getSpace().getObjects().values()) {
			if (isArrangeable(r)) {
				HasPosition3 itsPosition = (HasPosition3)r;

				itsPosition.getPosition().set(x,y,itsPosition.getPosition().z());

				if (r instanceof HasSize2) {
					HasSize2 itsSize = (HasSize2) r; 
					itsSize.getSize().set(cw * cellFactor, ch * cellFactor);
				}
				else {
					HasSize3 itsSize = (HasSize3) r;
					double sx = cw * cellFactor;
					double sy = ch * cellFactor;
					itsSize.getSize().set(sx, sy, Maths.getMagnitude(sx, sy));
				}

				if (num > 1) {
					if ((n+1) % cellsWide == 0) {
						if (cellsWide > 1)
							x = rowStartX;
						if (cellsTall > 1) {
							y -= ch;
						}
					}
					else {
						if (cellsWide > 1) {
							x+= cw;
						}
					}
					n++;
				}

			}			

		}

	}

	private boolean isArrangeable(Object r) {
		if ((r instanceof HasPosition3) && ((r instanceof HasSize2) || (r instanceof HasSize3))) {
			if (onlySubClassesOf!=null) {
				if (onlySubClassesOf.isInstance(r))
					return true;
				else
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void start(Scope node) {
		this.node = node;

		arrange();

		if (space!=null) {

			whenPositionChanges = getNode().add(new IfVector3Changes(((HasPosition3)space).getPosition()) {
				@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
					arrange();				
				}			
			});
		
			if (space instanceof HasSize2) {
				whenSizeChanges = getNode().add(new IfVector2Changes(((HasSize2)space).getSize()) {
					@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
						arrange();
					}			
				});
			}
			else {
				whenSizeChanges = getNode().add(new IfVector3Changes(((HasSize3)space).getSize()) {
					@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
						arrange();
					}
	
				});
				
			}
	
			whenAttachedChanges = getNode().add(new WhenMapEntriesChange(space.getObjects()) {
				@Override public void afterMapPut(Entry... entry) {	arrange();	}
				@Override public void beforeMapRemoves(Entry... entry) {	arrange();	}
			});
		}
		
	}



}
