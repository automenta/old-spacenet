package automenta.spacenet.space.object.measure;

import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class AxisBox extends Box {

	public AxisBox(Vector3 pos, Vector3 size) {
		this(pos, size, new Vector3());
	}

	public AxisBox(Vector3 pos, Vector3 size, Vector3 orientation) {
		super(pos, size, orientation);
		int edges = 3;
		DoubleVar radius = new DoubleVar(0.1);
		Vector3 center = new Vector3();
		Vector3 x = new Vector3(1,0,0);
		Vector3 y = new Vector3(0,1,0);
		Vector3 z = new Vector3(0,0,1);
		
		double a = 0.5;
		
		add(new Line3D(center, x, radius, edges).surface(new ColorSurface(Color.Red.alpha(a))));
		add(new Line3D(center, y, radius, edges).surface(new ColorSurface(Color.Blue.alpha(a))));
		add(new Line3D(center, z, radius, edges).surface(new ColorSurface(Color.Green.alpha(a))));
		
	}


	
	
}
