package automenta.spacenet.space.control.video;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.Vector3;

public class WheelClicksZ implements StartsIn<Scope>, Stops {

	private DoubleVar wheel;
	double velocity;
	private IfDoubleChanges whenRealChanges;
	private Video3D video;
	private Vector3 direction = new Vector3();
	
	public WheelClicksZ(DoubleVar wheel, Video3D video, final double velocity) {
		super();
		this.video = video;
		this.wheel = wheel;
		this.velocity = velocity;
	}

	
	@Override
	public void start(Scope n) {

		whenRealChanges = n.add(new IfDoubleChanges(wheel) {
			@Override 	public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				double delta = next - previous;
				onWheelClick(delta);
			}			
		});

	}
	
	final static double ninety = Math.PI;

	public static double getVisibleRadius(double z, double planeZ, double viewAngle) {
		double halfViewAngleRad = Math.toRadians(viewAngle/2.0);
		return 2.0 * (z - planeZ) * Math.sin(halfViewAngleRad) / Math.sin(ninety - halfViewAngleRad);
	}
	public static double getVisibleHeight(double r, double planeZ, double viewAngle) {
		double halfViewAngleRad = Math.toRadians(viewAngle/2.0);
		return planeZ + (r/2.0) * Math.sin(ninety - halfViewAngleRad) / Math.sin(halfViewAngleRad);
	}
	
//	protected void onWheelClick(double delta) {
//		direction.set(video.getTarget());
//		direction.subtract(video.getPosition());
//		direction.normalize();
//		
//		direction.multiply(delta * velocity);
//	
//		video.getPosition().add(direction);
//		video.getTarget().add(direction);
//	}

	protected void onWheelClick(double delta) {
//		direction.set(video.getTarget());
//		direction.subtract(video.getPosition());
//		direction.normalize();
		
		double oldZ = video.getPosition().z();
		double visRad = getVisibleRadius(oldZ, 0, video.getFocusAngle().d());
		double newVisRad = visRad * (1.0 - delta/120.0/2.5);
		double newZ = getVisibleHeight(newVisRad, 0, video.getFocusAngle().d());
		
		//System.out.println(oldZ + " " + delta + " " + visRad + " " + newVisRad +  " " + newZ);
		
		video.getPosition().setZ(newZ);
		video.getTarget().setZ(0);
	}

	@Override
	public void stop() {
		if (whenRealChanges!=null) {
			whenRealChanges.stop();
			whenRealChanges = null;
		}
	}
	
}
