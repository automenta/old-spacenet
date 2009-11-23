package automenta.spacenet.run.interact;



public class DemoClickable /*extends Box implements Starts*/ {
//	private static final Logger logger = Logger.getLogger(DemoClickable.class);
//
//	static VectorFont font = new VectorFont("Arial", Color.White, 0.01 );
//
//	int num = 25;
//
//	public static class ClickableRect extends Box implements Starts {
//
//		private String normalString;
//		private String pressedString;
//		private StringVar msg;
//		boolean pressed = false;
//
//		public ClickableRect(Physics physics, String normalString, String pressedString) {
//			super();
//
//			this.normalString = normalString;
//			this.pressedString = pressedString;
//		}
//
//
//		@Override public void start() {
//
//			msg = new StringVar();
//			//add(new TextRect(msg, font, 12).scale(0.5,0.5).move(0,0,1));
//			add(new TextRect(msg, font, 12).scale(1.0,1.0).move(0,0,1));
//
//			setNormal();
//
//			final Pointer cursor = getThe(Pointer.class);
//
//			add(new IfButtonPress(ClickableRect.this, cursor, PointerButton.Left) {
//				@Override protected void whenPressed(int repeatClicks) {
//					setPressed();
//				}
//
//				@Override
//				protected void whenReleased() {
//					setNormal();
//				}
//			});
//
//		}
//
//		@Override public void stop() {
//
//		}
//
//		protected void setPressed() {
//			msg.set(pressedString);
//		}
//
//		private void setNormal() {
//			msg.set(normalString);
//		}
//	}
//
//
//	@Override public void start() {
//
//		final Video3D video = getThe(Video3D.class);
//
//		Physics p = new Physics();
//		add(p);
//
//
//		for (int i = 0; i < num; i++) {
//			double w = DoubleVar.random(0.2, 1.4);
//			double h = Maths.goldenLesser(w);
//
//			double d = 6;
//
//			double x = DoubleVar.random(-1,1)*d;
//			double y = DoubleVar.random(-1,1)*d;
//			double z = DoubleVar.random(-1,1)*d;
//
//			final Box b = new ClickableRect(p, Integer.toString(i) + "/" + Integer.toString(num), "Pressed!").color(Color.newRandom().alpha(0.8)).scale(w, h, 1).move(x,y,z);
//			add(b);
//
//			add(new Repeat() {
//				@Override public double repeat(double t, double dt) {
//					Vector3 camPoint = video.getPosition();
//					Maths.rotateAngleTowardPoint(((HasPosition3)b).getPosition(), ((HasOrientation)b).getOrientation(), camPoint);
//					return 0;
//				}
//
//			});
//
//
//
//		}
//
//	}
//
//	@Override public void stop() {
//
//	}
//
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoClickable());
//	}
}
