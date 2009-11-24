package automenta.spacenet.var.graph.patterns;



import automenta.spacenet.Maths;
import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.var.graph.DiGraph;

public class RandomStringNet extends DiGraph<String,String> implements StartsIn<Scope> {

	int changesPerCycle;
	private double updatePeriod;

	
	public RandomStringNet(int initialNodes, int initialLinks, int changesPerCycle, double updatePeriod) {
		super();
	
		for (int i = 0; i < initialNodes; i++)
			addNode();
		for (int i = 0; i < initialLinks; i++)
			addLink();
		
		this.changesPerCycle = changesPerCycle;
		this.updatePeriod = updatePeriod;
	}

	public void addNode() {
		addVertex(newName());
	}
	
	public boolean addLink() {
		String a = getRandomNode();
		String b = getRandomNode();
		if (a!=null)
			if (b!=null)
				if (a!=b) {
					addEdge(newName(), a, b);
					return true;
				}		
		
		return false;
	}

    
	@Override public void start(Scope c) {
		c.add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				
				for (int i = 0; i < changesPerCycle; i++) {
					double r = Maths.random(0, 4);
					if (r < 1) {
						addNode();
					}
					else if (r < 3) {
						addLink();
					}
					else if (r < 3.7) {
						String x = getRandomNode();
						removeVertex(x);
					}
					else if (r < 4) {
						String x = getRandomLink();
						removeEdge(x);
					}
				}
				
				return updatePeriod;
			}

			
		});
	}

    /** TODO */
	public String getRandomNode() {
//		List<Node<String, String>> nodes = IteratorUtils.toList( iterateNodes() );
//		if (nodes.size() > 0) {
//			return nodes.get((int) Maths.random(0, nodes.size()));
//		}
		return null;
	}

    
    /** TODO */
	protected String getRandomLink() {
//		List<Link<String, String>> links = IteratorUtils.toList( iterateLinks() );
//		if (links.size() > 0) {
//			return links.get((int) Maths.random(0, links.size()));
//		}
		return null;
	}			
	
	@Override public void stop() {
		
	}

	public static String newName() {
		return Integer.toHexString((int) Maths.random(0, 4096));
	}
	
	
}
