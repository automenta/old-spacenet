package automenta.spacenet.space.dynamic.physics;

import automenta.spacenet.var.number.DoubleVar;

public class ConstrainSlider {

	DoubleVar angleMin;
	DoubleVar angleMax;
	DoubleVar lengthMin;
	DoubleVar lengthMax;
	DoubleVar force;
	
	public ConstrainSlider(DoubleVar force, DoubleVar lengthMin, DoubleVar lengthMax, DoubleVar angleMin, DoubleVar angleMax) {
		super();
		this.force = force;
		this.lengthMin = lengthMin;
		this.lengthMax = lengthMax;
		this.angleMin = angleMin;
		this.angleMax = angleMax;
	}
	
	public DoubleVar getAngleMin() {
		return angleMin;
	}
	public DoubleVar getAngleMax() {
		return angleMax;
	}
	public DoubleVar getLengthMin() {
		return lengthMin;
	}
	public DoubleVar getLengthMax() {
		return lengthMax;
	}
	public DoubleVar getForce() {
		return force;
	}
	
	
	
}
