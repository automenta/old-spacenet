/**
 * 
 */
package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.space.control.rect.depr.RectDragging;
import automenta.spacenet.space.dynamic.SpaceFifo;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** displays drag-start, being-dragged (with points separated by time period specified in the 
drag handler),  and drag-end points in different colors, with positioners and rulers */
public class CursorDrawingRect extends Rect implements Starts, Stops {

	private Pointer cursor;

	final double indicatorSize = 0.02;
	int maxIndicators = 300;
	double distanceThreshold = 0.02;

	private SpaceFifo fifo;
	
	private HasPosition3 lastBox = null;

	private Video3D sight;
	
	public CursorDrawingRect(Vector3 pos, Vector2 size, Vector3 rot) {
		super(pos, size, rot);
	}

	
	protected void addIndicator(HasPosition3 b) {
		if (lastBox!=null)
			if (lastBox.getPosition().getDistance(b.getPosition()) < distanceThreshold)
				return;
		
		fifo.push(b);
		if (lastBox!=null) {
			fifo.push(new Line3D(b.getPosition(), lastBox.getPosition(), new DoubleVar(0.01), 3)); 
		}
		lastBox = b;
		
	}
	
	@Override public void start() {
		color(Color.Gray.alpha(0.95));
		
		fifo = new SpaceFifo(this, maxIndicators);
		
		this.cursor = getThe(Pointer.class);
		this.sight = getThe(Video3D.class);
		
		add("drag", new RectDragging(this, cursor, PointerButton.Left) {
			@Override protected void whenDragStarts(Vector2 dragStart) {
				//System.out.println("start: " + dragStart);
				addIndicator(
						new Box().color(Color.Blue).scale(indicatorSize).move(dragStart.x(), dragStart.y(), 0)
				);
			}

			@Override protected void whenDragStops(Vector2 dragStop) {
				//System.out.println("stop: " + dragStop);				
				addIndicator(						
						new Box().color(Color.Red).scale(indicatorSize).move(dragStop.x(), dragStop.y(), 0)
				);
				lastBox = null;
			}

			private Vector3 pos = new Vector3();
			
			@Override protected void whenDragging(Vector2 dragCurrent) {
				//System.out.println("drag: " + dragCurrent);
				pos.set(dragCurrent.x(), dragCurrent.y(), 0);
				addIndicator(						
						new Box().color(Color.Green.alpha(0.5)).move(pos).scale(indicatorSize/2.0)
				);
				
				//look at center of rect
				//sight.getTarget().set(getAbsolutePosition());
			}			
		});
	}	


	@Override public void stop() {
		
		remove("drag");		
	}
}