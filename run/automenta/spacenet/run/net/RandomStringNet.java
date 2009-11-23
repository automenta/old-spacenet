package automenta.spacenet.run.net;

import java.util.List;

import org.apache.commons.collections15.IteratorUtils;

import automenta.spacenet.Maths;
import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.var.net.Link;
import automenta.spacenet.var.net.Node;
import automenta.spacenet.var.net.memory.MemoryNet;

public class RandomStringNet extends MemoryNet<String,String> implements StartsIn<Scope>, Stops {

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
		addNode(newName());
	}
	
	public boolean addLink() {
		Node<String,String> a = getRandomNode();
		Node<String,String> b = getRandomNode();
		if (a!=null)
			if (b!=null)
				if (a!=b) {
					addLink(newName(), a, b);
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
						Node<String,String> x = getRandomNode();
						removeNode(x);					
					}
					else if (r < 4) {
						Link<String,String> x = getRandomLink();
						removeLink(x);										
					}
				}
				
				return updatePeriod;
			}

			
		});
	}
	
	protected Node<String,String> getRandomNode() {
		List<Node<String, String>> nodes = IteratorUtils.toList( iterateNodes() );
		if (nodes.size() > 0) {
			return nodes.get((int) Maths.random(0, nodes.size()));
		}
		return null;
	}
	protected Link<String, String> getRandomLink() {
		List<Link<String, String>> links = IteratorUtils.toList( iterateLinks() );
		if (links.size() > 0) {
			return links.get((int) Maths.random(0, links.size()));
		}
		return null;
	}			
	
	@Override public void stop() {
		
	}

	public static String newName() {
		return Integer.toHexString((int) Maths.random(0, 4096));
	}
	
	
}
