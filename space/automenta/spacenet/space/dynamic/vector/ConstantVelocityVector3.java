package automenta.spacenet.space.dynamic.vector;

import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class ConstantVelocityVector3 extends Vector3 {

	Vector3 delta;
	Vector3 target;
	protected DoubleVar speed;

	public ConstantVelocityVector3(double x, double y, double z, DoubleVar speed) {
		super(x, y, z);
		super.set(x,y,z);
		this.speed = speed;
	}

	public ConstantVelocityVector3(double x, double y, double z, double speed) {
		this(x, y, z, new DoubleVar(speed));		
	}

	protected void setSuper(double x, double y, double z) {
		super.set(x,y,z);
	}
	
	@Override public Vector3 set(double targetX, double targetY, double targetZ) {
		if (target == null) {
			target = new Vector3(targetX, targetY, targetZ);
		}
		else {
			target.set(targetX, targetY, targetZ);
		}
		return this;
	}

	public void forward(double dt) {		
		double nx, ny, nz;


		if (delta == null) {
			delta = new Vector3();
		}
		delta.set(target.x() - x(),  target.y() - y(), target.z() - z());
		double deltaMag = delta.getMagnitude();
		//delta.multiply(dt * Math.min( deltaMag, speed.asDouble() ));
		if (deltaMag < speed.d() * dt) {
			super.set(target.x(), target.y(), target.z());
		}
		else {
			delta.normalize();
			delta.multiply(dt * speed.d() );
			super.set(
					x() + delta.x(),
					y() + delta.y(),
					z() + delta.z());
		}
		
	}

	public DoubleVar getMaxSpeed() {
		return speed;
	}
}
