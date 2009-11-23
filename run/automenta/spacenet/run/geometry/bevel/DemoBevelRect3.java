package automenta.spacenet.run.geometry.bevel;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.BevelRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;

public class DemoBevelRect3 extends ProcessBox {

	@Override public void run() {
		
		DoubleVar border = new DoubleVar(0.2);
		final DoubleVar z1 = new DoubleVar(0.05);
		final DoubleVar z2 = new DoubleVar(0.05);
		final DoubleVar z3 = new DoubleVar(0.05);
		final DoubleVar z4 = new DoubleVar(0.05);
		
		Surface s = new ColorSurface(Color.Gray.alpha(0.5));
		
		add(new BevelRect(s, z1, border).move(-1, 0));	

		add(new BevelRect(s, z2, border).move(0, 0));			
		
		add(new BevelRect(s, z3, border).move(1, 0));	
		
		add(new BevelRect(s, z4, border).move(2, 0));	

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double a = 0;
				double d = 0.2;
				z1.set(d * Math.sin(t / 0.5 + a));	a+=Math.PI/8;
				z2.set(d * Math.sin(t / 0.5 + a));  a+=Math.PI/8;
				z3.set(d * Math.sin(t / 0.5 + a));  a+=Math.PI/8;
				z4.set(d * Math.sin(t / 0.5 + a)); 
				return 0;
			}
		});
	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoBevelRect3().scale(4.0));
	}
}
