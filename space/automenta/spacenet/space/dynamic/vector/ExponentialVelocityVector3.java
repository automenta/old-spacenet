package automenta.spacenet.space.dynamic.vector;

import automenta.spacenet.var.number.DoubleVar;

/**
 * speed = [0, 1] : 0 = still, 1=moves instantaneously to target
 */
public class ExponentialVelocityVector3 extends ConstantVelocityVector3 {

	public ExponentialVelocityVector3(double x, double y, double z, double speed) {
		super(x, y, z, speed);
	}

	public ExponentialVelocityVector3(double x, double y, double z,	DoubleVar speed) {
		super(x,y,z,speed);
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

	@Override public void forward(double dt) {		
		double s = speed.d();
		
		double cx = (x() * (1.0 - s)) + (target.x() * s); 
		double cy = (y() * (1.0 - s)) + (target.y() * s); 
		double cz = (z() * (1.0 - s)) + (target.z() * s); 
		
		setSuper(cx, cy, cz);
		
	}

}
