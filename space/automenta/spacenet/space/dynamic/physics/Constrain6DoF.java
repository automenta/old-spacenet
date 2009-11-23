package automenta.spacenet.space.dynamic.physics;

import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class Constrain6DoF {

	DoubleVar maxLength;
	
	Vector3 minAngle;
	Vector3 maxAngle;
	
	public Constrain6DoF(DoubleVar maxLength,	Vector3 minAngle, Vector3 maxAngle) {
		super();
		this.maxLength = maxLength;
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}
	
	public Vector3 getMaxAngle() {
		return maxAngle;
	}
	public DoubleVar getMaxLength() {
		return maxLength;
	}
	public Vector3 getMinAngle() {
		return minAngle;
	}
	
	
	
}
