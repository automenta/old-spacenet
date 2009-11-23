package automenta.spacenet.run.surface;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;

public class DemoOpacity extends ProcessBox  {

	@Override public void run() {
		
		final Box a = new Box(Color.Orange);
		add(a);
		
		Box b = a.add( new Box(Color.Purple).move(0.5,0).scale(0.5) );
		b.opacity(0.5);

		final Box c = b.add( new Box(Color.Blue).move(0.5,0).scale(0.5) );
		c.opacity(0.5);

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				a.opacity((Math.cos(t) + 1.0) / 2.0);
				c.opacity(0.5);
				return 0;
			}			
		});
				
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoOpacity().scale(5.0));
	}
}
