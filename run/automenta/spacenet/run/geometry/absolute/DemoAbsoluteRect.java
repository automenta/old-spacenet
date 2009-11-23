package automenta.spacenet.run.geometry.absolute;


/** tests absolute positions by painting rects (at arbitrary space-tree depths) with an indicator
 * that has been positioned according to that rect's absolute position, not the relative (0,0)
 *
 */
public class DemoAbsoluteRect  {

//	private Box absoluteSpace;
//
//	public class AbsoluteShadowedRect extends RectX implements Starts {
//
//
//		@Override	public void start() {
//			final Rect a = (Rect) new Rect().color(Color.Green).scale(0.2, 0.2);
//
//			absoluteSpace.add(a);
//
//			add(new Repeat() {
//				@Override public double repeat(double t, double dt) {
//					a.setNextAbsolutePosition(new Vector3(AbsoluteShadowedRect.this.getAbsolutePosition()));
//					return 0;
//				}
//			});
//
//		}
//
//	}
//
//
//
//	@Override public void start(Scope c) {
//		super.start(c);
//
//		scale(5);
//
//		absoluteSpace = this;
//
//		final RectX r = (RectX) new AbsoluteShadowedRect().color(Color.Blue.alpha(0.5)).move(0,0,0).scale(1,1);
//		add(r);
//		final RectX s = (RectX) new AbsoluteShadowedRect().color(Color.Red.alpha(0.5)).move(1,1,0).scale(1,1);
//		add(s);
//		final RectX t = (RectX) new AbsoluteShadowedRect().color(Color.Orange.alpha(0.5)).move(1,1,0).scale(0.5, 0.5);
//		r.add(t);
//		final RectX u = (RectX) new AbsoluteShadowedRect().color(Color.Orange.alpha(0.5)).move(1,1,0).scale(0.5, 0.5);
//		t.add(u);
//
//
//		add(new Repeat() {
//
//			@Override public double repeat(double t, double dt) {
//				r.getPosition().set(2*Math.sin(t*0.1), 2*Math.cos(t*0.1), 0);
//
//				r.getOrientation().set(Math.cos(t*0.2), Math.sin(-t*0.4), Math.sin(t*0.6));
//				r.scale(2.0 + Math.sin(t*0.8)*0.5);
//				return 0;
//			}
//		});
//		add(new Repeat() {
//
//			@Override public double repeat(double t, double dt) {
//				s.getPosition().set(Math.random() * 5, Math.random() * 5, 0);
//				return 0.3;
//			}
//		});
//
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoAbsoluteRect());
//	}
}
