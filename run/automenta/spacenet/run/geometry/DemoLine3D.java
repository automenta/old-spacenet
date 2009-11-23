package automenta.spacenet.run.geometry;

import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoLine3D extends Box implements Starts {

	@Override public void start() {
		
		double s = 0.5;
		double r = 0.05;
		
		for (double x : new double[] { -1, 0, 1 } ) {
			for (double y : new double[] { -1, 0, 1 } ) {
				for (double z : new double[] { -1, 0, 1 } ) {
					if ((x==0) && (y==0) && (z == 0))
						continue;
					Vector3 a = new Vector3(0, 0, 0);
					Vector3 b = new Vector3(x*s,y*s,z*s);
					add(new Line3D(a, b, new DoubleVar(r), 9).surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5))));
				}
			}			
		}
	}


	@Override public void stop() {
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoLine3D());
	}
}
