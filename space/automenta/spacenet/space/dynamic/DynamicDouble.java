package automenta.spacenet.space.dynamic;

import automenta.spacenet.var.number.DoubleVar;

public class DynamicDouble extends DoubleVar {

	double target = 0;
	private double speed;
	
	public DynamicDouble(double d, double speed) {
		super(d);
		super.set(d);
		this.speed = speed;
	}

	@Override public void set(double d) {
		target = d;
	}
	
	public void forward(double dt) {
		double s = speed;
		
		double cx = (d() * (1.0 - s)) + (target * s);
		
		super.set(cx);
		
	}
	
	@Override public DoubleVar add(double dt) {
		set(target + dt);
		return this;
	}


}
