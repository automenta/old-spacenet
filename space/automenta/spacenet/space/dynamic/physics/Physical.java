package automenta.spacenet.space.dynamic.physics;

import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;

public interface Physical {

	public DoubleVar getMass();
	public BooleanVar getSolid();
	
//	public Vector3 getPosition();
//	public Vector3 getPositionVelocity();
//	public Vector3 getOrientationVelocity();
//	public Vector3 getAcceleration();

	//collision call-backs
}
