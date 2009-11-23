package automenta.spacenet.run.interact;




import automenta.spacenet.space.geom3.Box;


/** rotates camera in random directions */
public class DemoLook extends Box {
//	private static final Logger logger = Logger.getLogger(DemoLook.class);
//
//	double rectTime=0.5;
//	int numRects = 100;
//	List<Rect> rects = new LinkedList();
//
//	private Video3D sight;
//
//	@Override protected void whenStarted(Scope superNode) {
//		sight = getThe(Video3D.class);
//		final Pointer cursor = getThe(Pointer.class);
//
////		new RepeatAction(this) {
////			@Override public double repeat(double t, double dt) {
////				//System.out.println(cursor.getTouchDirection() + " " + sight.getDirection());
////
////				//sight.getDirection().set( 5*Math.sin(t), 5*Math.cos(t), -1);
////				if (cursor.getTouchDirection().getMagnitude()!=0)
////					sight.getPosition().addLocal(cursor.getPositionRelativeToScreen().x(),
////							cursor.getPositionRelativeToScreen().y(),
////							0.0);
////
////				if (cursor.getButton(CursorButton.Left).get()) {
////					sight.getPosition().setZ(sight.getPosition().z() + 100.0 * dt);
////					System.out.println(sight.getPosition().z());
////				}
////				if (cursor.getButton(CursorButton.Right).get()) {
////					sight.getPosition().setZ(sight.getPosition().z() - 100.0 * dt);
////					System.out.println(sight.getPosition().z());
////				}
////
////				return 0;
////			}
////		};
//
//
//		GroovyConsole console = (GroovyConsole) whenStarted(new GroovyConsole());
//		console.getBinding().put("sight", sight);
//		console.getBinding().put("cursor", cursor);
//
//
//		for (int i=0; i < numRects; i++) {
//			Rect r = newRect();
//			whenStarted(r);
//			rects.add(r);
//		}
//
//		GLSLSurface grid = DemoGridShader.newGridSurface();
//
//		whenStarted(new Rect(0,0,-10, 30, 30, grid));
//		whenStarted(new Rect(0,0,-20, 60, 60, grid));
//		whenStarted(new Rect(0,0,20, 60, 60, grid));
////
////		new RepeatAction(this) {
////			@Override public double repeat(double t, double dt) {
////				int i = (int)DoubleVar.random(0, numRects);
////				Rect r = rects.get(i);
////
////				System.out.println(r.getPosition()+" " +r.getAbsolutePosition());
////				sight.getDirection().set(r.getAbsolutePosition().subtractNew( sight.getPosition() ) );
////
////
////				return rectTime;
////			}
////		};
//	}
//
//	private Rect newRect() {
//		Vector3 sphereCoordinates = new Vector3(
//				3,
//				DoubleVar.random(-Math.PI, Math.PI),
//				DoubleVar.random(-Math.PI, Math.PI)
//				).cartesianToSphere();
//
//		Spheroid sphere = new Spheroid(16);
//
//		double w = DoubleVar.random(0.3,0.9);
//		double h = DoubleVar.random(0.3,0.6);
//
//		double r1 = DoubleVar.random(-Math.PI,Math.PI);
//		double r2 = DoubleVar.random(-Math.PI,Math.PI);
//		double r3 = DoubleVar.random(-Math.PI,Math.PI);
//
//
//
//
//
//		final Rect r = new Rect(sphereCoordinates, new Vector2(w,h), new Vector3(r1,r2,r3),
//				new ColorSurface(Color.newRandomHSB(0.5, 1.0)));
//
//
//		new Repeat(this) {
//			@Override 	public double repeat(double t, double dt) {
//
//				Vector3 camPoint = sight.getPosition();
//
//				Maths.rotateAngleTowardPoint(r.getPosition(), r.getOrientation(), camPoint);
//
//				return 0;
//			}
//		};
//
//		return r;
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoLook());
//	}
}
