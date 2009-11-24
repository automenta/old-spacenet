package automenta.spacenet.run.graph.depr;



/**
 * @see http://www.cricketschirping.com/weblog/?p=545
 *
 */
abstract public class DemoForceDirectedNet /*extends ProcessBox*/ {

//	protected PhysNetBox pbox;
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
//
//		Window w = add(new Window());
//		ForceDirectedNetBox fd = w.add(new ForceDirectedNetBox(new ObservableNet[] { net }) {
//				@Override protected Box newBox(Node node) {
//					Window box = new Window();
//					box.scale(0.1, 0.1, 0.1);
//
//					Box panel = box.add(new Box(Color.newRandomHSB(1.0, 1.0).alpha(0.5)));
//					panel.moveDZ(0.1);
//					panel.scale(1,1, 0.05);
//
//					TextRect3 tr = box.add(new TextRect3(new StringVar(node.toString()), 40, Color.White));
//					tr.moveDZ(0.15);
//
//					return box;
//				}
//				@Override protected Space newLine(Link link, Box pa, Box pb) {
//					Line3D l = new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.005), 2);
//					l.color(Color.Gray.alpha(0.5));
//					return l;
//				}
//
//
//		});
//		fd.moveDZ(0.1);
//
//		Window control1 = add(new Window());
//		control1.scale(0.25);
//
//		final Slider repulsion = control1.add(new Slider(fd.getRepulsion(), new DoubleVar(0.0001), new DoubleVar(1.0), 0.1, SliderType.Vertical));
//		repulsion.span(-0.5, -0.5, -0.4, 0.5);
//
//		final Slider stiffness = control1.add(new Slider(fd.getStiffness(), new DoubleVar(0.0001), new DoubleVar(1.0), 0.1, SliderType.Vertical));
//		stiffness.span(-0.3, -0.5, -0.2, 0.5);
//
//		final Slider lengthFactor = control1.add(new Slider(fd.getLengthFactor(), new DoubleVar(0.1), new DoubleVar(5.0), 0.1, SliderType.Vertical));
//		lengthFactor.span(-0.1, -0.5, 0, 0.5);
//
//	}
//
//
//	abstract protected ObservableNet newNet() throws Exception;
//
//	public static class DemoForceDirectedXMLNet extends DemoForceDirectedNet {
//		@Override protected ObservableNet newNet() throws Exception {
//			XMLNet xmlNet = new XMLNet(getClass().getResource("data/test.xml.txt"));
//			return xmlNet;
//		}
//	}
//	public static class DemoForceDirectedMeshNet extends DemoForceDirectedNet {
//		@Override protected ObservableNet newNet() throws Exception {
//			return new MeshNet(5, 5, false);
//		}
//
//		public static void main(String[] args) {
//			new DefaultJmeWindow(new DemoForceDirectedMeshNet());
//		}
//
//	}
//	public static class DemoForceDirectedTorusNet extends DemoForceDirectedNet {
//		@Override protected ObservableNet newNet() throws Exception {
//			return new MeshNet(5, 5, true);
//		}
//	}
//
//	public static void main(String[] args) {
//		new DefaultJmeWindow(new DemoForceDirectedXMLNet());
//	}
}
