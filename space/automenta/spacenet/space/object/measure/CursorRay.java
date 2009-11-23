package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

/** a cursor ray representing what a cursor is touching. from sight's position, forward */
public class CursorRay extends Box implements Starts, Stops {

	private double updatePeriod = 0.0;
	private Pointer cursor;
	private Video3D sight;
	private Vector3 rayEndPoint;
	private Line3D line;
	private Vector3 rayStartPoint;

	public CursorRay(Pointer cursor, Video3D sight) {
		super();
		this.cursor = cursor;
		this.sight = sight;
	}
	

	@Override public void start() {
		updateRay();
		line = new Line3D(rayStartPoint, rayEndPoint, new DoubleVar(0.05), 3);
		add(line);
		
		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				updateRay();
				return updatePeriod;
			}
		});
	}
	
	private void updateRay() {

		if (rayEndPoint == null) {
			rayEndPoint = new Vector3();
			rayStartPoint = new Vector3();
		}
				
		rayStartPoint.set(sight.getPosition());
		rayStartPoint.add(cursor.getTouchDirection(), 0.5);
		rayStartPoint.add(0,-0.1,-0.1);
		
		rayEndPoint.set(sight.getPosition());
		rayEndPoint.add(cursor.getTouchDirection(), 100.0);
	}


	@Override public void stop() {
		
	}
	
}
