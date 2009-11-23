package automenta.spacenet.run.geometry.curve3d;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.geom3.Sketch;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;

public class DemoCurve3D extends ProcessBox {

	@Override public void run() {
		Sketch s = new Sketch();
		s.add(0,0);
		s.add(1,1);
		s.add(-1, 2);
		
		add(new Curve3D(s, new DoubleVar(0.15), 3).color(Color.Orange.alpha(0.5)));
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoCurve3D().scale(5));
	}
}
