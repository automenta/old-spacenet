package automenta.spacenet.var.vector;

import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;

@Deprecated public class Vector4 extends ObjectVar<DoubleVar[]> {

	public Vector4(Vector4 copyFrom) {
		this(copyFrom.get());
	}
	
	public Vector4(DoubleVar[] v) {
		this(v[0], v[1], v[2], v[3]);
	}

	public Vector4(DoubleVar a, DoubleVar b, DoubleVar c, DoubleVar d) {
		super();
		set(new DoubleVar[] { a,b,c,d } );
	}

	public Vector4(double a, double b, double c, double d) {
		this(new DoubleVar(a), new DoubleVar(b), new DoubleVar(c), new DoubleVar(d) );
	}


	@Override
	public Vector4 clone() {
		return new Vector4(get()[0], get()[1], get()[2], get()[3]);
	}
}