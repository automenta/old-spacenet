package automenta.spacenet.space.geom3;

import automenta.spacenet.var.vector.Vector3;

public class Spheroid extends Box {

	public Spheroid(Vector3 position, Vector3 size, Vector3 orientation) {
		super(position, size, orientation);
	}

	public Spheroid(double radius) {
		this(new Vector3(), new Vector3(radius, radius, radius), new Vector3());
	}

}
