package automenta.spacenet.var.number;

import automenta.spacenet.var.ObjectVar;

public class BooleanVar extends ObjectVar<Boolean> {

	public BooleanVar(boolean b) {
		super(new Boolean(b));
	}

	public boolean b() {
		if (get()!=null)
			return get().booleanValue();
		return false;
	}

	public void negate() {
		set(!b());
	}

}
