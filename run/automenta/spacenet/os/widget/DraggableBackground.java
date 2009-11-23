package automenta.spacenet.os.widget;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.DragTarget;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.video.ClickZoomExcepted;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class DraggableBackground extends Rect implements Starts, Stops, Draggable, DragTarget, ClickZoomExcepted {
	private static final Logger logger = Logger.getLogger(DraggableBackground.class);
	
	private Vector3 start;
	private Video3D vid;
	private Vector3 startPos;

	
	public DraggableBackground() {
		super(Color.Invisible);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void startDrag(Pointer p, Drag drag) {
		if (vid == null)
			vid = getThe(Video3D.class);

		start = new Vector3(drag.getRealPositionCurrent());
		startPos = new Vector3(vid.getPosition());
	}

	double getScreenDragThreshold() {
		return 0.004;
	}
	
	@Override
	public void stopDrag(Pointer p, Drag drag) {
		if (drag.getScreenDistance() <= getScreenDragThreshold()) {
			onClicked(drag.getRealPositionCurrent());
		}
	}

	@Override
	public void updateDrag(Pointer p, Drag drag) {
		if (drag.getScreenDistance() > getScreenDragThreshold()) {
			Vector3 current = drag.getRealPositionCurrent();
			
			double cx = startPos.x() - getAmplification() * (current.x() - start.x());
			double cy = startPos.y() - getAmplification() * (current.y() - start.y());
			
			vid.getPosition().set(cx, cy, vid.getPosition().z());
			vid.getTarget().set(cx, cy, vid.getTarget().z());
		}
	}

	public double getAmplification() {
		return 1.5;
	}

	protected void onClicked(Vector3 p) {
		
	}

	@Override public void onDraggableDropped(Pointer p, Draggable d, Vector2 localPosition) {
	}
	
}
