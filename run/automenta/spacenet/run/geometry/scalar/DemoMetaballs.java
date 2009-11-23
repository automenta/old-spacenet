package automenta.spacenet.run.geometry.scalar;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.geom3.Sphere;
import automenta.spacenet.var.list.ListVar;

public class DemoMetaballs extends ProcessBox {

	
	@Override public void run() {
		ListVar<Sphere> points = new ListVar();
				
		final MetaballBox mb = add(new MetaballBox(points, 0.2));

		double r = 2;
		for (int i = 0; i < 12; i++) {
			double x = Maths.random(-r, r);
			double y = Maths.random(-r, r);
			double z = Maths.random(-r, r);
			double s = Maths.random(r/12, r/6);
			final Sphere sp = new Sphere(x, y, z, s);
			points.add(sp);
			
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					double dr = Maths.random(-0.05, 0.05);
					double dx = Maths.random(-0.05, 0.05);
					double dy = Maths.random(-0.05, 0.05);
					double dz = Maths.random(-0.05, 0.05);
					sp.getRadius().set(Math.max(0, sp.getRadius().d() + dr));
					sp.getPosition().add(dx, dy, dz);
					return 0.1;
				}
			});

		}

		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				mb.getOrientation().set(t/10.0, t/2.0, 0);
				return 0;
			}
			
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoMetaballs());
	}
}
