package automenta.spacenet.space.object.measure;

import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class VectorBox extends Box {

	private Vector3 vector;

	public VectorBox(Vector3 vector) {
		super();
		
		this.vector = vector;
		
		DoubleVar radius = new DoubleVar(0.35);
		int edges = 4;
		Vector3 center = new Vector3();
		add(new Line3D(center, vector, radius, edges));
		add(new ColorSurface(Color.White));
	}
	
	public Vector3 getVector() { return this.vector; }
}
