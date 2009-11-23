package automenta.spacenet.test.rdf;

import java.util.List;

import junit.framework.TestCase;
import automenta.spacenet.plugin.rdf.RDFLink;
import automenta.spacenet.plugin.rdf.RDFModel;
import automenta.spacenet.plugin.rdf.RDFNet;
import automenta.spacenet.plugin.rdf.RDFNode;

public class TestRDFNet extends TestCase {
//
//	public void testRDFModel() throws Exception {
//		RDFModel r = new RDFModel();
//		r.addFile(getClass().getResource("data/Semantic_Web.rdf"), "http://local");
//
//		System.out.println( r.toN3() );
//
//		List<Statement> s = r.getStatements(null, null, null);
//		assertTrue(s.size() > 5);
//
//		r.clear();
//
//		assertTrue( r.getStatements(null, null, null).size() < s.size() );
//	}
//
//	public void testRDFNet() throws Exception {
//		RDFNet r = new RDFNet();
//		r.addFile(getClass().getResource("data/Semantic_Web.rdf"), "http://local");
//
//		List<RDFNode> nodes = RDFModel.toList( r.iterateNodes() );
//		assertTrue(nodes.size() > 5);
//		List<RDFLink> links = RDFModel.toList( r.iterateLinks() );
//		assertTrue(links.size() > 5);
//
//		System.out.println("Links: " );
//		for (RDFLink l : links) {
//			System.out.println("  " + l);
//		}
//
//		System.out.println("Nodes: " );
//		for (RDFNode n : nodes) {
//			String label = n.getLabel();
//			if (label.equals("")) {
//				System.out.println(n);
//			}
//			else {
//				System.out.println(">  " + label + " : " + n.getComment());
//			}
//
//		}
//
//	}
//	
}
