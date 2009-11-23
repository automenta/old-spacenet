package automenta.spacenet.run.geometry;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.vector.Vector3;

/** shows several moving boxes, each with a different update interval.  some will appear more 'jumpy' than others */
public class DemoBoxAnimation extends Box implements Starts {


	@Override public void start() {
		double y = 0; 
		for (double interval = 0; interval < 0.5; interval+=0.07) {
			addAnimatingBox(Math.log(1.0 + interval), y);
			y -= 4;
		}
		
	}

	@Override public void stop() {
		
	}
	
	private void addAnimatingBox(final double interval, final double y) {
		final Vector3 p = new Vector3();
		final Vector3 s = new Vector3(1, 1, 1);
		final Vector3 o = new Vector3();
		final Color c = new Color(1.0-interval*2.0, 0.5);
		
		add(new Box(p, s, o).surface(new ColorSurface(c)));	
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				p.set(Math.sin(t) * 6.0, y, -5);
				
				double sDim = 1.0 + Math.cos(t) * 0.5;			
				s.set(sDim, sDim, sDim);
				
				o.set(t, 0, 0);
				
				//c.set(1.0-interval*2.0 * (0.25 + (Math.sin(t)+1.0) * 0.75), 0.5);
				double r = (Math.sin(t*2.0) + 1.0)/4.0;
				double g = 0;
				double b = (Math.cos(t*2.0) + 1.0)/4.0;
				c.set(r, g, b, 0.5);
				
				return interval;
			}
		});		
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoBoxAnimation());
	}
}
