package automenta.spacenet.var.number;

import automenta.spacenet.var.ObjectVar;


public class IntegerVar extends ObjectVar<Integer> {

	
	public IntegerVar(int i) {
		super();
		set(i);
	}

	@Override public String toString() {
		return get().toString();
	}

	/** the integer value */
	public int i() {
		return get().intValue();
	}

	public void add(int i) {
		set(i() + i);		
	}

	public void set(IntegerVar i) {		
		set(i.i());
	}

	/** the value, cast to double */
	public double d() {
		return i();
	}

}
