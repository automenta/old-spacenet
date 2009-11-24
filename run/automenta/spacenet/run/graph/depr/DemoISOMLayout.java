package automenta.spacenet.run.graph.depr;



public class DemoISOMLayout  {


//	double width = 16;
//	double height = 16;
//
//	private int epoch;
//	private int radius;
//	private double speed;
//
//	private List<Node> queue = new LinkedList();
//
//	Map<Node, ISOMNodeData> isomNodeData = new HashMap();
//
//	public DemoISOMLayout(Net<String,String> n) {
//		super(n);
//	}
//
//	@Override public void run() {
//		super.run();
//
//		initialize();
//
//		add(new Repeat() {
//
//			@Override public double repeat(double t, double dt) {
//				step();
//				return 0.01;
//			}
//
//		});
//
//	}
//
//	public void initialize() {
//
//		epoch = 1;
//
//		radius = 5;
//
//		speed = 0.01;
//
//
//		//temperature = 0.03;
//		//initialJumpRadius = 100;
//		//jumpRadius = initialJumpRadius;
//
//		//delay = 100;
//	}
//
//
//	/**
//	 * Advances the current positions of the graph elements.
//	 */
//	public void step() {
//		adjust();
//		updateParameters();
//	}
//
//	private synchronized void adjust() {
//		//Generate random position in graph space
//		Vector3 tempXYD = new Vector3(Maths.random(-width/2.0, width/2.0), Maths.random(-height/2.0, height/2.0), 0);
//
//
//		//Get closest node to random position
//		Node winner = netBox.getClosestNode(tempXYD);
//
//		Iterator<Node<String, String>> ni = net.iterateNodes();
//		while (ni.hasNext()) {
//			Node n = ni.next();
//            ISOMNodeData ivd = getISOMNodeData(n);
//            ivd.distance = 0;
//            ivd.visited = false;
//		}
//
//		if (winner!=null)
//			adjustVertex(winner, tempXYD);
//	}
//
//	private synchronized void updateParameters() {
//		epoch++;
//		//double factor = Math.exp(-1 * coolingFactor * (1.0 * 0.5));
//		//adaption = Math.max(minAdaption, factor * initialAdaption);
//		//jumpRadius = (int) factor * jumpRadius;
//		//temperature = factor * temperature;
////		if ((radius > minRadius) && (epoch % radiusConstantTime == 0)) {
////			radius--;
////		}
//	}
//
//	private synchronized void adjustVertex(Node n, Vector3 p) {
//		queue.clear();
//		ISOMNodeData ivd = getISOMNodeData(n);
//		ivd.distance = 0;
//		ivd.visited = true;
//		queue.add(n);
//
//		Node current;
//
//		while (!queue.isEmpty()) {
//			current = queue.remove(0);
//			ISOMNodeData currData = getISOMNodeData(current);
//			Vector3 nodePosition = netBox.getNodePosition(current);
//
//			double dx = p.x() - nodePosition.x();
//			double dy = p.y() - nodePosition.y();
//			double factor = speed / Math.pow(2, currData.distance);
//
//			nodePosition.set(nodePosition.x()+(factor*dx), nodePosition.y()+(factor*dy), 0);
//
//			if (currData.distance < radius) {
//				updateNodeLinkDistance(currData, net.iterateLinksInTo(current));
//				updateNodeLinkDistance(currData, net.iterateLinksOutOf(current));
//			}
//		}
//	}
//
//	private void updateNodeLinkDistance(ISOMNodeData currData, Iterator<Link<String, String>> s) {
//		while (s.hasNext()) {
//			Link l = s.next();
//			Node child = l.getFirst();
//			ISOMNodeData childData = getISOMNodeData(child);
//			if (childData != null && !childData.visited) {
//				childData.visited = true;
//				childData.distance = currData.distance + 1;
//				queue.add(child);
//			}
//		}
//	}
//
//	protected ISOMNodeData getISOMNodeData(Node v) {
//		ISOMNodeData d = isomNodeData.get(v);
//		if (d == null) {
//			d = new ISOMNodeData();
//			isomNodeData.put(v, d);
//		}
//		return d;
//	}
//
//	/**
//	 * This one is an incremental visualization.
//	 * @return <code>true</code> is the layout algorithm is incremental, <code>false</code> otherwise
//	 */
//	public boolean isIncremental() {
//		return true;
//	}
//
//	protected static class ISOMNodeData {
//		int distance;
//		boolean visited;
//
//		protected ISOMNodeData() {
//			distance = 0;
//			visited = false;
//		}
//	}
//
//	/**
//	 * Resets the layout iteration count to 0, which allows the layout algorithm to
//	 * continue updating vertex positions.
//	 */
//	public void reset() {
//		epoch = 0;
//	}
//
//
//	public static void main(String[] args) {
//		//RandomStringNet n = new RandomStringNet();
//		Net n = new RandomTreeNet(4, 4);
//		System.out.println(n);
//		new DefaultJmeWindow(new DemoISOMLayout(n));
//	}
}
