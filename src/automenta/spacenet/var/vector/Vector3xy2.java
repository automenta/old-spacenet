package automenta.spacenet.var.vector;

/** adapts a 3-space vector to 2-space vector */
public class Vector3xy2 extends Vector2 {

	private Vector3 v3;

	public Vector3xy2(Vector3 v3) {
		super();
		this.v3 = v3;
	}

	@Override
	public double x() {
		if (v3!=null)
			return v3.x();
		return 0.0;
	}
	@Override
	public double y() {
		if (v3!=null)
			return v3.y();
		return 0.0;
	}
	
	
}
