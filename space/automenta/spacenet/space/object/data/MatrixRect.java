package automenta.spacenet.space.object.data;

import java.util.Map;

import javolution.util.FastMap;
import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.Vector2;


/** displays a sub-matrix of a 2D matrix array of rectangles, useful for grid arrangements and (editable) text rectangles */
public class MatrixRect extends Rect implements Starts {



	DoubleVar cellAspect = new DoubleVar(1.0);


	DoubleVar minX = new DoubleVar(0);
	DoubleVar minY = new DoubleVar(0);
	DoubleVar maxX = new DoubleVar(0);
	DoubleVar maxY = new DoubleVar(0);

	DoubleVar visCX = new DoubleVar(0);
	DoubleVar visCY = new DoubleVar(0);
	DoubleVar visWidth = new DoubleVar(0);
	DoubleVar visHeight = new DoubleVar(0);

	private Map<Integer, Map<Integer,Rect>> cell = new FastMap();

	protected Rect content;

	private Slider xSlider, ySlider;


	private DoubleVar updatePeriod = new DoubleVar(0.02);


	protected boolean needsRefresh = false;


	private IntegerVar numCells = new IntegerVar(0);


	private DoubleVar cellAspectMin = new DoubleVar(1);
	private DoubleVar cellAspectMax = new DoubleVar(1);


	private DoubleVar maxWidth = new DoubleVar(0);
	private DoubleVar maxHeight = new DoubleVar(0);


	private Slider widthSlider;


	private Slider heightSlider;


	protected DoubleVar sliderCX = new DoubleVar(0);
	protected DoubleVar sliderCY = new DoubleVar(0);

	private DoubleVar sliderMaxCX = new DoubleVar(0);
	private DoubleVar sliderMaxCY = new DoubleVar(0);
	private DoubleVar sliderMinCX = new DoubleVar(0);
	private DoubleVar sliderMinCY = new DoubleVar(0);

	private DoubleVar sliderVisWidth = new DoubleVar(0);
	private DoubleVar sliderVisHeight = new DoubleVar(0);
	private DoubleVar sliderMaxWidth = new DoubleVar(0);
	private DoubleVar sliderMaxHeight = new DoubleVar(0);


	private DoubleVar autoAspectScale = new DoubleVar(-1.0);


	private DoubleVar sliderMaxScale = new DoubleVar(5.0);


	private Slider scaleSlider;

	public MatrixRect() {
		super();
	}

	@Override public void start() {
		
		content = add(new Rect());
		content.tangible(false);

		xSlider = add(new Slider(sliderCX, sliderMinCX, sliderMaxCX, new DoubleVar(0.1), SliderType.Horizontal));
		ySlider = add(new Slider(sliderCY, sliderMinCY, sliderMaxCY, new DoubleVar(0.1), SliderType.Vertical));
		xSlider.span(-0.4, -0.45 , 0.4, -0.5);
		ySlider.span(0.45, -0.4 , 0.5, 0.4);

		
		scaleSlider = add(new Slider(sliderVisHeight, new DoubleVar(1), sliderMaxScale, new DoubleVar(0.2), SliderType.Vertical));
		widthSlider = add(new Slider(sliderVisWidth, new DoubleVar(1), sliderMaxWidth, new DoubleVar(0.2), SliderType.Horizontal));
		heightSlider = add(new Slider(sliderVisHeight, new DoubleVar(1), sliderMaxHeight, new DoubleVar(0.2), SliderType.Vertical));

		add(new IfDoubleChanges(getVisCX(), getVisCY(), getVisWidth(), getVisHeight()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				sliderCX.set(getVisCX().d());
				sliderCY.set(getVisCY().d());
				sliderVisWidth.set(getVisWidth().d());
				sliderVisHeight.set(getVisHeight().d());
			}			
		});

		add(new IfDoubleChanges(sliderCX, sliderCY, sliderVisWidth, sliderVisHeight) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				needsRefresh = true;
			}			
		});
		add(new IfVector2Changes(getAbsoluteSize()) {
			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
				double autoAspectScale = getAutoAspectScale().d();
				if (autoAspectScale!=-1) {
					needsRefresh = true;
				}
			}			
		});
		

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				updateMatrix();
				return getUpdatePeriod().get();
			}			
		});
		
		needsRefresh = true;
		updateMatrix();
	}

	protected void updateMatrix() {
		if (needsRefresh) {
			layout();
		}
	}

	private DoubleVar getMaxWidth() {
		return maxWidth;
	}
	private DoubleVar getMaxHeight() {
		return maxHeight ;
	}


	protected DoubleVar getUpdatePeriod() {
		return updatePeriod ;
	}

	@Override public void stop() {	}

	public void removeAll() {
		synchronized (cell) {

			getNumCells().set(0);
			cell.clear();

            if (content!=null)
                content.clear();

			needsRefresh = true;
		}

	}

	public void put(int x, int y, Rect r) {
		synchronized (cell) {

			Map<Integer, Rect> row = cell.get(y);
			if (row == null) {
				row =new FastMap<Integer,Rect>(); 
				cell.put(y, row);
			}

			if (row.get(x)!=null) {
				Rect removed = row.remove(x);
				if (removed == r)
					return;
				content.remove(removed);
			}
			else {
				getNumCells().add(1);
			}

			row.put(x, r);

			if (getNumCells().i() == 1) {
				minX.set(x);
				maxX.set(x);
				minY.set(y);
				maxY.set(y);
			}
			else {
				if (x < minX.i()) minX.set(x);
				if (x > maxX.i()) maxX.set(x);
				if (y < minY.i()) minY.set(y);
				if (y > maxY.i()) maxY.set(y);
			}


			content.add(r);

			needsRefresh = true;
		}
	}

	public Rect get(int x, int y) {
		try {
			return cell.get(y).get(x);
		}
		catch (Exception e) {
			return null;
		}
	}


	protected void layout() {
		synchronized (cell) {
			
			maxWidth.set(getMaxX().i() - getMinX().i());
			maxHeight.set(getMaxY().i() - getMinY().i());

			
			double d = Math.ceil(sliderVisHeight.d());
			d = Math.min( d, getMaxY().d() - getMinY().d() );
			d = Math.max(1, d);
			visHeight.set(d);

			d = Math.ceil(sliderVisWidth.d());
			d = Math.min( d, getMaxX().d() - getMinX().d() );
			d = Math.max(1, d);
			visWidth.set(d);

			double autoAspectScale = getAutoAspectScale().d();
			if (autoAspectScale!=-1) {
				getAutoAspectScale().set( scaleSlider.getValue().d() );
				double sx = getAbsoluteSize().x();
				double sy = getAbsoluteSize().y();
				double sa = sy / sx;
				
				
				visWidth.set( (1.0 / sa) * autoAspectScale);
				visHeight.set(  sa * autoAspectScale );
				
				scaleSlider.span(-0.5, 0.25, -0.45, -0.25);
				scaleSlider.visible(true);
				
				widthSlider.visible(false);
				heightSlider.visible(false);
			}
			else {
				widthSlider.span(-0.25, 0.5, 0.25, 0.45);
				heightSlider.span(-0.5, 0.25, -0.45, -0.25);
				
				widthSlider.visible(true);
				heightSlider.visible(true);
				
				scaleSlider.visible(false);
			}
			

			double v = sliderCX.d();
			v = Math.max(getMinX().d(), v);
			v = Math.min(getMaxX().d(), v);
			visCX.set(v);

			v = sliderCY.d();
			v = Math.max(getMinY().d(), v);
			v = Math.min(getMaxY().d(), v);
			visCY.set(v);



			double dx = 1 + maxX.d() - minX.d();
			double dy = 1 + maxY.d() - minY.d();



			if (getNumCells().i() == 0) {
				needsRefresh = false;
				return;			
			}

			content.scale(0.9, 0.9);

			double height = 0;
			double width = 0;

			double cellAspect = (getCellAspectMax().d() + getCellAspectMin().d()) / 2.0;

			//invert to match slider's output
			double vy = getMaxY().d() * ( 1.0 - ( getVisCY().d() / (getMaxY().d())));
			
			int startY = (int)Math.floor(vy - getVisHeight().d()/2.0);
			int stopY = (int)Math.ceil(vy + getVisHeight().d()/2.0);
			int startX = (int)Math.floor(getVisCX().d() - getVisWidth().d()/2.0);
			int stopX = (int)Math.ceil(getVisCX().d() + getVisWidth().d()/2.0);

			if (startX < getMinX().i()) {
				startX = getMinX().i();
				stopX = getMinX().i() + getVisWidth().i(); 
			}
			if (stopX > getMaxX().i()) {
				stopX = getMaxX().i();
				startX = getMaxX().i() - getVisWidth().i(); 				
			}
			if (startY < getMinY().i()) {
				startY = getMinY().i();
				stopY = getMinY().i() + getVisHeight().i(); 
			}
			if (stopY > getMaxY().i()) {
				stopY = getMaxY().i();
				startY = getMaxY().i() - getVisHeight().i(); 				
			}

			startX = Math.max( startX, getMinX().i() );			
			stopX = Math.min( getMaxX().i(), stopX );
			
			startY = Math.max( startY, getMinY().i() );
			stopY = Math.min( getMaxY().i(), stopY );
			
			//calculate width and height to normalize
			for (int y = startY; y <= stopY; y++) {
				double yScale = cellAspect;

				Map<Integer, Rect> row = cell.get(y);

				double w = 0;
				if (row!=null) {
					for (int x = startX; x <= stopX; x++) {
						double xScale = 1.0 / cellAspect;					
						w += xScale;
					}
				}
				if (w > width)
					width = w;

				height += yScale;
			}

			double px;
			double py = -0.5;

			for (int y = getMinY().i(); y <= getMaxY().i(); y++) {
				px = -0.5;
				double yScale = cellAspect/height;
				Map<Integer, Rect> row = cell.get(y);

				if (row!=null) {
					for (int x = getMinX().i(); x <= getMaxX().i(); x++) {
						Rect r = row.get(x);
						if (r!=null) {
							r.visible(false);
						}						
					}
				}
			}

			for (int y = stopY; y >= startY; y--) {
				px = -0.5;
				double yScale = cellAspect/height;
				Map<Integer, Rect> row = cell.get(y);

				if (row!=null) {
					for (int x = startX; x <= stopX; x++) {
						double xScale = 1.0 / cellAspect / width;
						Rect r = row.get(x);
						if (r!=null) {
							r.getPosition().set(px + xScale/2.0, py + yScale/2.0, 0);						
							r.getSize().set(xScale, yScale);
							r.visible(true);
						}
						px += xScale;
					}
				}
				py += yScale;
			}

//			sliderCX.set(visCX.d());
//			sliderCY.set(visCY.d());
			
			sliderMaxWidth.set(getMaxWidth().d());
			sliderMaxHeight.set(getMaxHeight().d());
			
			xSlider.getKnobLength().set( Math.min(1.0, getVisWidth().d() / getMaxWidth().d()) );
			ySlider.getKnobLength().set( Math.min(1.0, getVisHeight().d() / getMaxHeight().d()) );
			
			sliderMinCX.set(getMinX().d() );
			sliderMaxCX.set(getMaxX().d() );

			sliderMinCY.set(getMinY().d() );
			sliderMaxCY.set(getMaxY().d() );

			//System.out.println(getVisWidth().d() + ", " + getVisHeight().d() + " : " + startX + ".." + stopX + "," + startY + ".." + stopY);
			

//			System.out.println(visWidth.d() + ": " + sliderMinCX.d() + " < " +sliderCX.d() + " < " + sliderMaxCX.d());
//			System.out.println(visHeight.d() + ": " + sliderMinCY.d() + " < " +sliderCY.d() + " < " + sliderMaxCY.d());
//			System.out.println(sliderMaxWidth.d() + " , " + sliderMaxHeight.d());

			needsRefresh = false;
		}

	}


	/** if =-1, auto aspect is disabled */
	public DoubleVar getAutoAspectScale() {
		return autoAspectScale ;
	}

	public IntegerVar getNumCells() {
		return numCells ;
	}

	/** maximum cell X coordinate */	
	public DoubleVar getMaxX() {	return maxX;	}

	/** maximum cell Y coordinate */	
	public DoubleVar getMaxY() {	return maxY;	}

	/** minimum cell X coordinate */	
	public DoubleVar getMinX() {	return minX;	}

	/** minimum cell Y coordinate */	
	public DoubleVar getMinY() {	return minY;	}

	/** center X of visible cells */
	public DoubleVar getVisCX() {
		return visCX;	
	}

	/** center Y of visible cells */
	public DoubleVar getVisCY() {	
		return visCY;	
	}

	/** number of visible cells tall */
	public DoubleVar getVisHeight() {
		return visHeight;	
	}

	/** number of visible cells wide */
	public DoubleVar getVisWidth() {	
		return visWidth;	
	}

	public DoubleVar getCellAspectMin() {
		return cellAspectMin;
	}
	public DoubleVar getCellAspectMax() {
		return cellAspectMax;
	}

	//	protected double getXScale(double x) {
	//	double dx = Math.abs( x - getCx().d() );
	//	double dw = getWidth().d()/2.0;
	//	if (dx < dw) {
	//		return 1.0;
	//	}
	//	double sx = 1.0 / (dx - dw + 1.0);
	//	if (sx < getVisibleThreshold()) {
	//		return 0.0;
	//	}
	//	return sx;
	//}
	//
	//protected double getYScale(double y) {
	//	double dy = Math.abs( y - getCy().d() );
	//	double dh = getHeight().d()/2.0;
	//	if (dy < dh) {
	//		return 1.0;
	//	}
	//	double sy = 1.0 / (dy - dh + 1.0);
	//	if (sy < getVisibleThreshold()) {
	//		return 0.0;
	//	}
	//	return sy;
	//}
	//
	//public double getVisibleThreshold() {
	//	return 0.05;
	//}

	public Rect getContent() {
		return content;
	}

}
