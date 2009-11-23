package automenta.spacenet.run.geometry;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;

public class DemoAspectAndAlignment extends ProcessBox {

	public class AspectAnimation extends Rect implements Starts {
		private double ax;
		private double ay;
		private double px;
		private double py;

		public AspectAnimation(double ax, double ay, double px, double py) {
			super();
			this.ax = ax;
			this.ay = ay;
			this.px = px;
			this.py = py;
		}
		
		@Override public void start() {
			final Rect rect = add( new Rect().color(Color.Blue.alpha(0.3)).move(0,0) );
			rect.aspect(ax, ay);
			rect.align(px, py);
			
			final Rect rect2 = add( new Rect(rect.getPosition(), rect.getSize(), rect.getOrientation()).color(Color.Red.alpha(0.3)) );
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					t/=5;
					rect.getSize().set(Math.cos(t), Math.sin(t));
					return 0;
				}
			});
		}
	
		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Override public void run() {
		add(new AspectAnimation(1,2, 0,0).move(-1, 0));
		add(new AspectAnimation(1,1, 0,0).move(0, 0));
		add(new AspectAnimation(2,1, 0,0).move(1, 0));

		add(new AspectAnimation(1,2, 0,1).move(-1, -1));
		add(new AspectAnimation(1,1, 0,1).move(0, -1));
		add(new AspectAnimation(2,1, 0,1).move(1, -1));

		add(new AspectAnimation(1,2, 1,0).move(-1, 1));
		add(new AspectAnimation(1,1, 1,0).move(0, 1));
		add(new AspectAnimation(2,1, 1,0).move(1, 1));

	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoAspectAndAlignment().size(5,5));
	}
}
