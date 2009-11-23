/**
 * 
 */
package automenta.spacenet.space.control.rect.depr;

import automenta.spacenet.Scope;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.Vector2;

@Deprecated abstract public class RectDragging extends IfButtonPress {
	boolean dragging = false;
	Vector2 dragStart = new Vector2(), dragCurrent = new Vector2(), dragStop = new Vector2();
	private Rect rect;
	private IfVector2Changes cursorWatch;

	public RectDragging(Rect rect, Pointer cursor, PointerButton cursorButton) {
		super(rect, cursor, cursorButton);
		this.rect = rect;
	}

	@Override protected void whenPressed(int repeatClicks) {
		if (!dragging) {
			//startDragWatch();

			dragging = true;

			getPointer().getQuadIntersection(rect, dragStart);

			dragStop.set(dragCurrent);
			dragStop.set(dragStart);

			whenDragStarts(dragStart);

		}

	}

	@Override public void start(Scope n) {

		cursorWatch = n.add(new IfVector2Changes(getPointer().getPixelPosition()) {
			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
				if (dragging) {
					getPointer().getQuadIntersection(rect, dragCurrent);
					dragStop.set(dragCurrent);

					whenDragging(dragCurrent);
				}
			}

		});

		super.start(n);
		
	}

	abstract protected void whenDragging(Vector2 dragCurrent);			

	abstract protected void whenDragStops(Vector2 dragStop2);

	abstract protected void whenDragStarts(Vector2 dragStart);

	@Override protected void whenReleased() {
		if (dragging) {
			getPointer().getQuadIntersection(rect, dragStop);
			whenDragStops(dragStop);
			dragging = false;
		}
	}

	public Rect getRect() { return rect; }
	
}