package automenta.spacenet.space.control;

import automenta.spacenet.var.time.TimePoint;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** describes an instance of a pointer drag.  created each new drag, and updated as the drag progresses. */
abstract public class Drag {

	public static enum DragState { Started, Dragging, Stopped };
	
	private DragState state = DragState.Started;
	
	TimePoint timeStart = new TimePoint();

	Vector2 pixelStart = new Vector2();
	Vector2 pixelEnd = new Vector2();
	
	private Vector3 meshIntersectStart = new Vector3();
	private Vector3 realPositionCurrent = new Vector3();


	private Vector2 pixelCurrent = new Vector2();

	private Vector2 pixelStop = new Vector2();

	private TimePoint timeStop = new TimePoint();

	private Draggable dragged;
	
	public Drag(Draggable dragged) {
		super();
		this.dragged = dragged;
	}

	public Draggable getDraggable() { return dragged; }
	
	public Vector3 getMeshIntersectStart() {	return meshIntersectStart;	}

	public Vector3 getRealPositionCurrent() {	return realPositionCurrent;	}
	
	public void setState(DragState ds) { state = ds; }

	public Vector2 getPixelStart() {	return pixelStart;	}
	public Vector2 getPixelCurrent() {	return pixelCurrent;	}
	public Vector2 getPixelStop() {	return pixelStop;	}

	public TimePoint getTimeStart() {	return timeStart;	}
	public TimePoint getTimeStop() {	return timeStop;	}

	public double getScreenDistance() {
		return getPixelStop().getDistance(getPixelStart());
	}
	
}
