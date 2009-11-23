package automenta.spacenet.run.geometry;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoLine3DBasic extends Box {

	public DemoLine3DBasic() {
		super(Color.Invisible);

		add(new Line3D(new Vector3(0,0,0), new Vector3(1,0,0), new DoubleVar(0.1), 5)).color(Color.Red);
		add(new Line3D(new Vector3(0,-1,0), new Vector3(2,-1,0), new DoubleVar(0.1), 5)).color(Color.Green);
		add(new Line3D(new Vector3(-1,-2,0), new Vector3(1,-2,0), new DoubleVar(0.1), 5)).color(Color.Orange);
		add(new Line3D(new Vector3(0,-1.5,0), new Vector3(0,-1.5,1), new DoubleVar(0.1), 5)).color(Color.Purple);
		add(new Line3D(new Vector3(0,1,0), new Vector3(0,2,0), new DoubleVar(0.1), 5)).color(Color.Blue);
		add(new Line3D(new Vector3(0,1,0), new Vector3(1,1,0), new DoubleVar(0.1), 5)).color(Color.Yellow);

	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoLine3DBasic());
	}
}
