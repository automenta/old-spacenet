package automenta.spacenet.run.interact;

import automenta.spacenet.space.geom3.Box;

public class DemoMouseCamera extends Box {

//	@Override protected void whenStarted(Scope superNode) {
//		whenStarted(new DemoSubBoxSpace(0,0,0,1,1,1,Color.Red, 2));
//
//		final Video3D sight = getThe(Video3D.class);
//		final Pointer cursor = getThe(Pointer.class);
//
//		new Repeat(this) {
//			double z = 25;
//
//			@Override public double repeat(double t, double dt) {
//				//System.out.println( cursor.getPositionRelativeToScreen() );
//				//System.out.println( cursor.getButtons() );
//
//
//				if (cursor.getButton(Pointer.PointerButton.Left).get()) {
//					z += dt * 2.0;
//				}
//				else if (cursor.getButton(Pointer.PointerButton.Right).get()){
//					z -= dt * 2.0;
//				}
//
//				sight.getTarget().add(cursor.getTouchDirection());
////				sight.getPosition().set(
////						cursor.getPositionRelativeToScreen().x() / 4.0,
////						cursor.getPositionRelativeToScreen().y() / 4.0, z);
//
//				System.out.println(cursor.getTouchDirection() + " " + sight.getTarget() );
//
//				return 0;
//			}
//		};
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoMouseCamera());
//	}
}
