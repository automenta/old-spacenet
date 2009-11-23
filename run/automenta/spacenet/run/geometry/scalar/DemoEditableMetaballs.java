package automenta.spacenet.run.geometry.scalar;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.geom3.Sphere;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoEditableMetaballs extends ProcessBox {

	
	@Override public void run() {
		ListVar<Sphere> points = new ListVar();
		
		Box b = add(new Box());
		b.scale(0.25, 0.25);
		b.move(0, 0, 0.3);
		
		//getThe(Jme.class).getSky().add(SkyUtil.newRainbowSkyBox());
		
		final MetaballBox mb = b.add(new MetaballBox(points, 0.2));
		mb.scale(1,1,0.1);
		mb.move(0,0,-0.2);

		double r = 1;
		for (int i = 0; i < 12; i++) {
			double x = Maths.random(-r, r);
			double y = Maths.random(-r, r);
			double z = 0;
			double s = r/6;
			final Sphere sp = new Sphere(x, y, z, s);
			points.add(sp);
			
			sp.color(Color.newRandom(1.0));
			sp.getRadius().set(Math.max(0, sp.getRadius().d()));
			sp.getPosition().add(x, y, z);

			Window win = b.add(new Window(new Box(sp.getPosition(), sp.getSize(), new Vector3())));
		}

	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoEditableMetaballs());
	}
}
