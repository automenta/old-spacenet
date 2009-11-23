package automenta.spacenet.test;

import automenta.spacenet.test.*;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections15.IteratorUtils;

import automenta.spacenet.Root;
import automenta.spacenet.var.net.IfNetChanges;
import automenta.spacenet.var.net.Link;
import automenta.spacenet.var.net.Net;
import automenta.spacenet.var.net.Node;
import automenta.spacenet.var.net.memory.MemoryNet;

public class TestNet extends TestCase {

	public void testMemoryNet() {
		MemoryNet<String,String> n = new MemoryNet();
		
		Node<String, String> a = n.addNode("a");
		Node<String, String> b = n.addNode("b");
		Link<String, String> ab = n.addLink("ab", a, b);
		
		assertEquals("a", a.getValue());
		assertEquals("b", b.getValue());
		assertEquals("ab", ab.getValue());

		List<Link<String, String>> aIn = IteratorUtils.toList(a.iterateLinksIn());
		assertEquals(0, aIn.size());
		List<Link<String, String>> aOut = IteratorUtils.toList(a.iterateLinksOut());
		assertEquals(1, aOut.size());
		assertEquals(ab, aOut.get(0));
		
		List<Link<String, String>> links = IteratorUtils.toList(n.iterateLinks());
		assertEquals(1, links.size());


		assertEquals(1, IteratorUtils.toList(n.iterateLinks("a", "ab", "b")).size());
		assertEquals(1, IteratorUtils.toList(n.iterateLinks("a", null, null)).size());
		assertEquals(1, IteratorUtils.toList(n.iterateLinks("a", null, "b")).size());
		assertEquals(1, IteratorUtils.toList(n.iterateLinks(null, "ab", null)).size());
		assertEquals(0, IteratorUtils.toList(n.iterateLinks("b", null, "a")).size());
		
		
		n.removeNode(a);
		
		//removing 'a' should also remove link 'ab'
		links = IteratorUtils.toList(n.iterateLinks());
		assertEquals(0, links.size());

	}
	
	int changeNum = 0;
	
	public void testNetChange() {
		Root r = new Root();
		
		MemoryNet<String,String> n = new MemoryNet();
		
		IfNetChanges<String, String> c = r.add(new IfNetChanges<String, String>(n) {

			@Override public void afterNetChanges(Net<String, String> n,
					Link<String, String>[] linksRemoved,
					Node<String, String>[] nodesRemoved,
					Node<String, String>[] nodesAdded,
					Link<String, String>[] linksAdded) {
	
				if ((changeNum == 0) || (changeNum == 1)) {
					assertTrue(linksRemoved == null);
					assertTrue(nodesRemoved == null);
					assertTrue(linksAdded == null);
					assertTrue(nodesAdded != null);
				}
				else if (changeNum == 2) {
					assertTrue(linksRemoved == null);
					assertTrue(nodesRemoved == null);
					assertTrue(nodesAdded == null);					
					assertTrue(linksAdded != null);
				}
				else if (changeNum == 3) {
					assertTrue(linksRemoved != null);										
				}
				else if (changeNum == 4) {
					assertTrue(nodesRemoved != null);															
				}
				
				changeNum++;
			}		
			
		});
		
		Node<String, String> a = n.addNode("a");
		Node<String, String> b = n.addNode("b");
		Link<String, String> ab = n.addLink("ab", a, b);

		n.removeNode(a);
		
		r.remove(c);
		
	}
}
