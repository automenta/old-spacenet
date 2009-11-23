package automenta.spacenet.space.control;

import automenta.spacenet.var.number.BooleanVar;

/** a real button or switch: mouse or keyboard, that can be pressed or released. named
 * 'Toggle' so as not to be confused with widget 'buttons' */
public class Toggle extends BooleanVar {
	
	private int change;
	private String name;

	public Toggle(boolean b) {
		super(b);
	}


	public Toggle(String name) {
		this(false);
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "=" + b();
	}
	

	public void clear() {
		change = 0;
	}
	
	public int getChange() {
		return change;
	}
	
	@Override
	public boolean set(Boolean next) {
		if (b() && !next.booleanValue()) {
			change = -1;
		}
		else if (!b() && next.booleanValue()) {
			change = +1;
		}
		return super.set(next);
	}
}
