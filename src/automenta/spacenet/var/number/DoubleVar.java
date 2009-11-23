package automenta.spacenet.var.number;

import automenta.spacenet.var.ObjectVar;


/**
 * note: using asFloat() and asDouble() is more efficient than get() when doing math
 *
 */
public class DoubleVar extends ObjectVar<Double> {

	public DoubleVar(double d) {
		super();
		set(d);
	}

	@Deprecated public static double random(double min, double max) {
		return min + Math.random()*(max-min);
	}

	public float f() {
		return get().floatValue();
	}

	public double d() {
		return get().doubleValue();
	}

	public void set(double d) {
		set(new Double(d));		
	}

	public DoubleVar add(double dt) {
		set(d() + dt);
		return this;
	}

	public int i() {
		return (int)d();
	}
}
