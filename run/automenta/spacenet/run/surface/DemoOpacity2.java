package automenta.spacenet.run.surface;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.dynamic.SpaceFifo;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;

public class DemoOpacity2 extends Rect implements Starts, Stops {

	int numRects = 15;
	
	public static class FadeIn extends Repeat<Space> {

		private Space space;

		public FadeIn(Space s) {
			this.space = s;
		}

		@Override
		public double repeat(double t, double dt) {
			double o = getOpacity(t);
			space.getOpacity().set(o);
			if (isFinished(o)) {
				return -1;
			}
			return 0;
		}

		private boolean isFinished(double o) {
			return o >= 1.0;
		}

		private double getOpacity(double t) {
			return t/4.0;
		}
		
	}
	
	public static class FadeOut extends Repeat<Space> {
		private Space space;
		private Runnable whenFinished;

		public FadeOut(Space s, Runnable whenFinished) {
			this.space = s;
			this.whenFinished = whenFinished;
		}

		@Override
		public double repeat(double t, double dt) {
			double o = getOpacity(t);
			space.getOpacity().set(o);
			if (isFinished(o)) {
				if (whenFinished!=null) {
					whenFinished.run();
				}
				return -1;
			}
			return 0;
		}

		private boolean isFinished(double o) {
			return o <= 0;
		}

		private double getOpacity(double t) {
			return 1.0 - t/6.0;
		}
		
	}
	
	@Override public void start() {
		final SpaceFifo<Space> fifo = new SpaceFifo<Space>(this, numRects) {

			@Override public void onIn(Space o) {
				o.getOpacity().set(0);
				super.push(o);
				DemoOpacity2.this.add(new FadeIn(o));
			}

			@Override
			protected <X extends Space> void onOut(final Space o) {
				DemoOpacity2.this.add(new FadeOut(o, new Runnable() {
					@Override public void run() {
						DemoOpacity2.this.remove(o);						
					}					
				}));
			}			
			
		};
		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				getOpacity().set( (Math.pow(Math.sin(10.0 * t), 4.0) + 1.0) * 0.1 + 0.1);
				getOrientation().set(0,0,t/2.0);
				return 0;
			}

		});
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double x = DoubleVar.random(-0.5, 0.5);
				double y = DoubleVar.random(-0.5, 0.5);
				Rect q = new Rect(Color.Orange).move(x, y).scale(0.1);
				fifo.push(q);
			
				return 0.03;
			}			
		});
		
	}
	@Override public void stop() {
		
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoOpacity2().color(Color.White).scale(35));
	}
}
