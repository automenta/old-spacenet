package automenta.spacenet.os.widget;

/** ObjectCanvas holding, primarily, a Physics-backed NetBox */
abstract public class PhysicsNetCanvas  {

//	private AtomNet net = new AtomNet();
//
//	private NetBox<Atom, Atom> netBox;
//
//	private Map<Atom, PhyBox> atomBox = new HashMap();
//
//	double h = 8;
//	double w = 16;
//	double d = 2;
//	private PhySpace phy;
//
//	protected void addPhy(Node<Atom, Atom> node, PhyBox lb, Space ps) {
//		atomBox.put(node.getValue(), lb);
//		add(ps);
//	}
//
//	protected PhyBox getPhy(Atom value) {
//		return atomBox.get(value);
//	}
//
//
//	@Override public void start(OSSpace os) {
//		super.start(os);
//
//		tangible(false);
//
//		phy = add(new PhySpace(allowJoints(), w*2, h*2, d*2));
//		phy.getUpdatePeriod().set(0.02);
//		phy.getTimeScale().set(0.5);
//
//		netBox = add(new NetBox<Atom,Atom>(getNet()));
//		netBox.addBehavior(this);
//	}
//
//
//	@Override public void whenLinkAdded(Net<Atom, Atom> net, Link<Atom, Atom> link) {
//		PhyBox pa = getPhy(link.getFirst().getValue());
//		PhyBox pb = getPhy(link.getLast().getValue());
//
//		newLinkSpace(net, link, pa, pb);
////		//SliderConstraint sj = physics.newSlideJoint(pa, pb, 0.5, 3.5, 0.001);
////		//Generic6DofConstraint sj = physics.newAngleJoint(pa, pb, 0.5, 2.5, 0, 0, 1.0);
////
////		//Constrain6DoF sj = physics.newConstrain6Dof(pa, pb, 0.5, 2.5, 0, 0, 1.0);
////
//////		PhyBox paR = phy.newBox(1.0);
//////		PhysicsNetCanvas.this.add(new Box(paR).color(Color.Orange));
//////		paR.scale(0.1);
//////		PhyBox pbR = phy.newBox(1.0);
//////		PhysicsNetCanvas.this.add(new Box(pbR).color(Color.Orange));
//////		pbR.scale(0.1);
////
////		//phy.newWeld(pa, paR);
//////		phy.newConstrain6Dof(pa, paR, 0.2, 0.5, 0, 0, 3.14159);
//////		phy.newConstrain6Dof(pb, pbR, 0.2, 0.5, 0, 0, 3.14159);
//////		phy.newConstrainSlider(paR, pbR, 1.5, 3.0, 1.0);
////		double p = Math.PI;
////		phy.newConstrain6Dof(pa, pb, 2.0, p , 0, 0);
////
////		//					double p = 3.14159;
////		//					return phy.newConstrain6Dof(pa, pb, 0, 3, 0, 0, 2 * p);
////
////
////		Line3D line = add(new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.05), 4));
////		line.getPosition().set(0,0,-0.5);
////		line.color(Color.Orange.alpha(1.0));
////		line.tangible(false);
////		PhysicsNetCanvas.this.add(line);
////		//addLink(link, sj, line);
////
//	}
//
//
//	public PhySpace getPhysics() {
//		return phy;
//	}
//
//	@Override public void whenLinkRemoved(Net<Atom, Atom> net, Link<Atom, Atom> link) {
//	}
//
//	@Override public void whenNodeAdded(Net<Atom, Atom> net, Node<Atom, Atom> node) {
//		PhyBox lb = newPhysicsBox(false);
//		Space ps = newPhysicsSpace(node, lb);
//		if (ps!=null) {
//			addPhy(node, lb, ps);
//		}
//		else {
//			removePhysicsBox(lb);
//		}
//
//	}
//
//
//	abstract protected void newLinkSpace(Net<Atom, Atom> net2, Link<Atom, Atom> link, PhyBox pa, PhyBox pb);
//	abstract protected Space newPhysicsSpace(Node<Atom, Atom> node, PhyBox b);
//
//
//	@Override public void whenNodeRemoved(Net<Atom, Atom> net, Node<Atom, Atom> node) {
//	}
//
//
//	protected boolean allowJoints() {
//		return true;
//	}
//
//
//
//	//	phy = add(new PhySpace(false, w*2, h*2, 0));
//	//	phy.getUpdatePeriod().set(0.02);
//	//	phy.getTimeScale().set(0.3);
//
//
//	public void removePhysicsBox(PhyBox b) {
//		phy.removeBox(b);
//	}
//
//	//TODO un-hack this
//	public PhyBox newPhysicsBox() {
//		return newPhysicsBox(true);
//	}
//
//	public PhyBox newPhysicsBox(boolean faceForward) {
//		final PhyBox b = phy.newBox(2.0);
//		b.getDrag().set(0.5);
//		//b.getBoundsScale().set(w, h, 0.1);
//
//		//		final Window w = add(new Window(b) {
//		//
//		//			@Override protected void startDrag(Pointer c, Drag drag) {
//		//				super.startDrag(c, drag);
//		//				b.isDynamic().set(false);
//		//			}
//		//
//		//			@Override protected void stopDrag(Pointer c, Drag drag) {
//		//				super.stopDrag(c, drag);
//		//				b.getVelocity().set(0,0,0);
//		//				b.isDynamic().set(true);
//		//			}
//		//		});
//
//		final double restitutionFactor = 0.99;
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				b.getPosition().setZ(b.getPosition().z() * restitutionFactor);
//
//				//b.getPosition().setZ(0);
//
//				/*if (!w.getBeingDragged().isTrue())*/ {
//					//b.getOrientation().set(1.00,0,0);
//					b.getOrientation().multiply(restitutionFactor, restitutionFactor, 1.0);
//					b.getAngularVelocity().multiply(restitutionFactor, restitutionFactor, restitutionFactor);
//					//				}
//					return 0.0;
//				}
//			}});
//
//		return b;
//	}
//
//	public AtomNet getNet() {
//		return net;
//	}
}
