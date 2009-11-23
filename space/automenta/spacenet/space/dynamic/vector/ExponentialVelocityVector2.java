package automenta.spacenet.space.dynamic.vector;

import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

/**
 * speed = [0, 1] : 0 = still, 1=moves instantaneously to target
 */
public class ExponentialVelocityVector2 extends Vector2 {

	private DoubleVar speed;

	Vector2 target;

	public ExponentialVelocityVector2(double x, double y, double speed) {
		super(x, y);
		target = new Vector2(x, y);
		this.speed = new DoubleVar(speed);
	}

//	@Override public void forward(double dt) {		
//		double nx, ny, nz;
//
//
//		if (delta == null) {
//			delta = new Vector3();
//		}
//		delta.set(target.x() - x(),  target.y() - y(), target.z() - z());
//		double deltaMag = delta.getMagnitude();
//		delta.multiply(dt * Math.min( deltaMag, speed.asDouble() ));
//		
////		if (deltaMag < speed.asDouble() * dt) {
////			setSuper(target.x(), target.y(), target.z());
////		}
////		else {
////			delta.normalize();
////			delta.multiply(dt * speed.asDouble() );
//			setSuper(
//					x() + delta.x(),
//					y() + delta.y(),
//					z() + delta.z());
////		}
//		
//	}

	@Override public void set(double x, double y) {
		if (target!=null)
			target.set(x, y);
	}

	public void forward(double dt) {		
		double s = speed.d();
		
		double cx = (x() * (1.0 - s)) + (target.x() * s); 
		double cy = (y() * (1.0 - s)) + (target.y() * s); 
		
		super.set(cx, cy);
		
	}

}
