package automenta.spacenet.run.geometry.border;



public class DemoTubeBorderRotation {
	
//	@Override public void run() {
//		final SpaceFifo fifo = new SpaceFifo(this, 15);
//
//		add(new Repeat() {
//
//			@Override
//			public double repeat(double t, double dt) {
//				double x = DoubleVar.random(-5, 5);
//				double y = DoubleVar.random(-5, 5);
//				double w = DoubleVar.random(2, 3);
//				double h = DoubleVar.random(1, 2);
//
//
//				final Rect r = new RectX().color(Color.White.alpha(0.7));
//				r.move(x, y);
//				r.scale(w, h);
//
//				final TubeBorder b = new TubeBorder(r, 0, 0.05);
//				b.add(new ColorSurface(Color.newRandomHSB(1.0, 0.8)));
//				b.move(x, y, 0);
//				b.scale(w, h, 1);
//				//r.add(b);
//
//				fifo.push(b);
//
//				add(new Repeat() {
//					@Override public double repeat(double t, double dt) {
//						b.getOrientation().set(t, t + Math.PI, t/2.0);
//						return 0;
//					}
//				});
//
//				return 0.2;
//			}
//
//		});
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoTubeBorderRotation());
//	}
}
