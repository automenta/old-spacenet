package automenta.spacenet.run.physics;

public class DemoJoints {

//	private PhySpace phy;
//
//	@Override public void run() {
//		phy = add(new PhySpace(true));
//
//		phy.getUpdatePeriod().set(0);
//		phy.getTimeScale().set(0.5);
//
//		int numNodes = 20;
//		int numJoints = 20;
//
//		double r = 4;
//		List<PhyBox> nodes = new LinkedList();
//		for (int i = 0; i < numNodes; i++) {
//			double x = Maths.random(-r, r);
//			double y = Maths.random(-r, r);
//			double z = Maths.random(-r, r);
//			nodes.add( addBox(x, y, z) );
//		}
//
//		int a, b;
//		for (int i = 0; i < numJoints; i++) {
//			a = (int)(Math.random() * numNodes);
//
//			b = -1;
//			do {
//				b = (int)(Math.random() * numNodes);
//			} while (a == b);
//
//			System.out.println(a + " " + b);
//
//			if (Math.random() < 0.5)
//				addSlideJoint(nodes.get(a), nodes.get(b));
//			else
//				addAngleJoint(nodes.get(a), nodes.get(b));
//		}
//
////		addSlideJoint(a, b);
////		addAngleJoint(c, b);
////		//addSlideJoint(c, b);
////		addSlideJoint(d, c);
//
//
//	}
//
//	private void addAngleJoint(PhyBox a, PhyBox b) {
//		phy.newAngleJoint(a, b);
//		add(new Line3D(a.getPosition(), b.getPosition(), new DoubleVar(0.07), 3));
//	}
//
//	private void addSlideJoint(PhyBox a, PhyBox b) {
//		final SliderConstraint joint = phy.newSlideJoint(a, b);
//		Line3D curve = add(new Line3D(a.getPosition(), b.getPosition(), new DoubleVar(0.03), 3));
//	}
//
//	private PhyBox addBox(double x, double y, double z) {
//		PhyBox a = phy.newBox(1.0);
//		a.scale(0.5);
//		Box w = add(new Window(a).move(x, y, z));
//		w.add(new FaceCamera());
//		w.add(new LockZPosition(0));
//
//		return a;
//	}
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoJoints());
//	}
}
