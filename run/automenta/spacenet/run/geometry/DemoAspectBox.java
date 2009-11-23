package automenta.spacenet.run.geometry;

import automenta.spacenet.space.Space;

public class DemoAspectBox extends Space {

//	public class AspectAnimation extends Rect implements Starts {
//		private double ax;
//		private double ay;
//
//		public AspectAnimation(double ax, double ay) {
//			super();
//			this.ax = ax;
//			this.ay = ay;
//		}
//
//		@Override public void start() {
//			final Box box = add( new BoxX().color(Color.Blue.alpha(0.5)).move(0,0) );
//			box.aspect(ax, ay);
//			final Box box2 = add( new BoxX(box.getPosition(), box.getSize(), box.getOrientation()).color(Color.Red.alpha(0.2)) );
//			repeat(new Repeat() {
//				@Override public double repeat(double t, double dt) {
//					t/=5;
//					System.out.println(t);
//					box.getSize().set(Math.cos(t), Math.sin(t), 1);
//					return 0;
//				}
//			});
//		}
//
//
//	}
//
//	@Override public void run() {
//		add(new AspectAnimation(1,2).move(-1, 0));
//		add(new AspectAnimation(1,1).move(0, 0));
//		add(new AspectAnimation(2,1).move(1, 0));
//	}
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoAspectBox().size(50,50, 1));
//	}
}
