package automenta.spacenet.run.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.net.NetBox;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.dynamic.physics.ConstrainSlider;
import automenta.spacenet.space.dynamic.physics.PhyBox;
import automenta.spacenet.space.dynamic.physics.PhySpace;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.net.Link;
import automenta.spacenet.var.net.Net;
import automenta.spacenet.var.net.Node;
import automenta.spacenet.var.net.ObservableNet;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.string.StringVar;

public class DemoPhysicsNet  {
//	private double decayRate = 0.99;
//
//	protected PhysNetBox pbox;
//
//	public static final double minStrength = 0.1;
//
//	public static class PhysNetBox extends NetBox {
//		private static final Logger logger = Logger.getLogger(PhysNetBox.class);
//
//		double r = 7;
//
//
//		protected Map<Node, PhyBox> nodePhy = new HashMap();
//		protected Map<Node, Box> nodeVis = new HashMap();
//		protected Map<Link, ConstrainSlider> linkCon = new HashMap();
//		protected Map<Link, Space> linkVis = new HashMap();
//		protected Map<Node, DoubleVar> nodeStrengths = new HashMap();
//
//		private final PhySpace physics;
//
//		private PhysNetBox(ObservableNet[] n, PhySpace physics) {
//			super(n);
//			this.physics = physics;
//		}
//
//		@Override protected void whenLinkAdded(Net net, Link link) {
//			PhyBox pa = getNodeBox(link.getFirst());
//			PhyBox pb = getNodeBox(link.getLast());
//
//			//SliderConstraint sj = physics.newSlideJoint(pa, pb, 0.5, 3.5, 0.001);
//			//Generic6DofConstraint sj = physics.newAngleJoint(pa, pb, 0.5, 2.5, 0, 0, 1.0);
//
//			//Constrain6DoF sj = physics.newConstrain6Dof(pa, pb, 0.5, 2.5, 0, 0, 1.0);
//			ConstrainSlider sj = physics.newConstrainSlider(pa, pb, 0, 2.0, 0);
//
//			Line3D line = add(new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.01), 2));
//			line.color(Color.Gray.alpha(0.5));
//			addLink(link, sj, line);
//		}
//
//		@Override protected void whenLinkRemoved(Net net, Link link) {
//			removeLink(link);
//		}
//
//		private void removeLink(Link link) {
//			logger.error("removeLink not impl yet");
////			Space vis = linkVis.get(link);
////			TypedConstraint con = linkCon.get(link);
////			remove(vis);
////			physics.removeConstraint(con);
//		}
//
//		private void removeNode(Node node) {
//			Space vis = nodeVis.get(node);
//			PhyBox p = nodePhy.get(node);
//			remove(vis);
//			physics.removeBox(p);
//		}
//
//		@Override protected void whenNodeAdded(Net net, Node node) {
//
//			double mass = 10.0;
//			double sx = 0.2;
//			double sy = 0.2;
//			double sz = 0.2;
//
//			PhyBox b = physics.newBox(mass);
//			b.getBoundsCenter().set(0,0,0);
//			b.getBoundsScale().set(r, r, r);
//			b.getDrag().set(0.99);
//
//
//			b.scale( sx, sy, sz);
//			b.move(Maths.random(-r, r), Maths.random(-r, r));
//
//			//Box box = add(new Box(b).color(Color.newRandom()));
//
//			Window box = add(new Window(b));
//			box.add(new TextRect(new StringVar(node.toString()), 40, Color.White));
//
//			//box.add(new FaceCamera(0, 0.5));
//
//			addNode(node, b, box);
//		}
//
//		@Override protected void whenNodeRemoved(Net net, Node node) {
//			removeNode(node);
//		}
//
//		protected void addLink(Link link, ConstrainSlider sj, Space visible) {
//			linkCon.put(link, sj);
//			linkVis.put(link, visible);
//		}
//
//		protected void addNode(Node node, final PhyBox b, final Box visibleBox) {
//			nodePhy.put(node, b);
//			nodeVis.put(node, visibleBox);
//			nodeStrengths.put(node, new DoubleVar(minStrength));
//
////			visibleBox.add(new IfDoubleChanges(nodeStrengths.get(node)) {
////				@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
////					b.getSize().normalize().multiply(next*2);
////
////					double o = Math.min(1.0, Math.max(0.0, next));
////					visibleBox.getOpacity().set( o );
////					//visibleBox.getOpacity().set(0.01);
////
////				}
////			});
//
//		}
//
//		public PhyBox getNodeBox(Node n) {
//			return nodePhy.get(n);
//		}
//
//		protected double getStrength(Node n) {
//			DoubleVar s = nodeStrengths.get(n);
//			if (s!=null)
//				return s.get();
//			else
//				return 0.0;
//		}
//
//
//		protected void setStrength(Node n, double d) {
//			DoubleVar s = nodeStrengths.get(n);
//			s.set(d);
//		}
//
//		public Node getRandomNode() {
//			List<Node> nodes = new LinkedList(nodePhy.keySet());
//			if (nodes.size() > 0) {
//				return nodes.get( (int)Maths.random(0, nodes.size() ));
//			}
//			return null;
//		}
//
//		public ConstrainSlider getLinkConstraint(Link l) {
//			return linkCon.get(l);
//		}
//
//	}
//
//
//
//
//
//
//
//
//	@Override public void run() {
//		final ObservableNet net;
//		try {
//			net = add(newNet());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//
//		final PhySpace physics = add(new PhySpace(true));
//		physics.getTimeScale().set(1.0);
//		physics.getUpdatePeriod().set(0);
//
//		pbox = add(new PhysNetBox(new ObservableNet[] { net }, physics));
//
////		//stimulate activation randomly
////		add(new Repeat() {
////			@Override public double repeat(double t, double dt) {
////				return stimulateRandomNode();
////			}
////		});
//
//
//		//decay activation
//		add(new Repeat() {
//
//			@Override public double repeat(double t, double dt) {
//				for (Node n : pbox.nodePhy.keySet()) {
//					double s = pbox.getStrength(n) * decayRate;
//
//					int l = 2;
//					double a = s * l;
//					Iterator<Link> ii = net.iterateLinksInTo(n);
//					while (ii.hasNext()) {
//						a += pbox.getStrength(ii.next().getFirst());
//						l++;
//					}
//					ii = net.iterateLinksOutOf(n);
//					while (ii.hasNext()) {
//						a += pbox.getStrength(ii.next().getLast());
//						l++;
//					}
//
//					a/=l;
//
//					pbox.setStrength(n, Math.max(minStrength, a) );
//				}
//				return 0;
//			}
//		});
//
//		Window control1 = add(new Window());
//		control1.move(-2, -2);
//
//		final Slider lenMin = control1.add(new Slider(0.1, 0.1, 3.0, 0.1, SliderType.Vertical));
//		lenMin.span(-0.5, -0.5, -0.4, 0.5);
//
//		final Slider lenMax = control1.add(new Slider(0.5, 0.1, 3.0, 0.1, SliderType.Vertical));
//		lenMax.span(-0.3, -0.5, -0.2, 0.5);
//
//		final Slider angleWidth = control1.add(new Slider(Math.PI/2.0, 0, Math.PI, 0.1, SliderType.Vertical));
//		angleWidth.span(-0.1, -0.5, 0, 0.5);
//
//		final Slider rot = control1.add(new Slider(Math.PI/2.0, 0, Math.PI, 0.1, SliderType.Vertical));
//		rot.span(0.1, -0.5, 0.2, 0.5);
//
//		final Slider force = control1.add(new Slider(0, 0, 2.0, 0.1, SliderType.Vertical));
//		force.span(0.3, -0.5, 0.4, 0.5);
//
//		add(new IfDoubleChanges(lenMin.getValue(), lenMax.getValue(), angleWidth.getValue(), rot.getValue(), force.getValue()) {
//			@Override public void afterDoubleChanges(DoubleVar doubleVar,  Double previous, Double next) {
//				for (Net n : pbox.getNets()) {
//					Iterator<Link> il = n.iterateLinks();
//					while (il.hasNext()) {
//						Link l = il.next();
//						ConstrainSlider c = pbox.getLinkConstraint(l);
//
//						c.getLengthMin().set(lenMin.getValue().d());
//						c.getLengthMax().set(lenMax.getValue().d());
//
//						c.getForce().set(force.getValue().d() );
//
//						double a = angleWidth.getValue().d();
//						c.getAngleMin().set(-a/2.0);
//						c.getAngleMax().set(a/2.0);
//					}
//
//				}
//
//				pbox.getOrientation().set( rot.getValue().d(), 0, 0);
//			}
//		});
//
//	}
//
//
//
//
//
//	public double stimulateRandomNode() {
//		Node n = pbox.getRandomNode();
//		if (n!=null) {
//			pbox.setStrength(n, 1.0);
//		}
//		return 0.5;
//	}
//
//
//
//
//
//	protected ObservableNet newNet() throws Exception {
//		return new RandomStringNet(1, 0.5);
//	}
//
//
//
//
//
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoPhysicsNet());
//	}
}
