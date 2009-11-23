package automenta.spacenet.test;

import automenta.spacenet.test.*;
import java.util.Collection;

import junit.framework.TestCase;
import automenta.spacenet.Root;
import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Simultaneous;

public class TestNode extends TestCase {

	private static final double delayTimeForThreadToStart = 0.5;

	public static class NullNode extends Scope implements StartsIn<Root>, Stops {
		public Scope starter = null;
		public boolean startMethodCalled = false;

		@Override public final void start(final Root c) {
			startMethodCalled = true;
			starter = c;
		}				
		
		@Override public void stop() {
			
		}
		
	}
	
	public static class SomeNode extends Scope {
		
	}
	
	
	public final void testNode() {
		//test starting and stopping a child node
		final Root root = new Root();		int initialSize = root.size();
		NullNode node = root.add(new NullNode());
		{
			assertTrue(node.startMethodCalled);
			assertEquals(root, node.starter);
			assertEquals(root, node.getParent());
			assertEquals(initialSize + 1, root.size());
			assertTrue(root.getStarted().contains(node));
			
			Collection a = root.getValues(Scope.class, false);
			assertEquals(0, a.size());
			
			Collection b = root.getValues(Scope.class, true);
			assertEquals(1, b.size());
			assertTrue(b.contains(node));
			
		}
		root.remove(node);
		{			
			assertFalse(node.isStarted());
			assertNull(root.get(node));
			assertEquals(initialSize, root.size());
			assertFalse(root.getStarted().contains(node));
		}
		root.stop(); assertFalse(root.isStarted());
		assertEquals(0, root.size());

		
	}


	public final void testNodeObjectKeys() {
		Root root;
		//test keys
		NullNode n3;
		root = new Root();	String key = "key";
		n3 = root.add(key, new NullNode());
		assertEquals(n3, root.get(key));
		assertEquals(n3, root.get("key"));
		assertTrue(n3.isStarted());
		root.stop();
				
		assertFalse("stopped automatically when root stopped", n3.isStarted());
	}

	public final void testNodeReplacement() {
		final Root r = new Root();
		
		final NullNode first = new NullNode(); assertFalse(first.isStarted());
		r.add("x", first); assertTrue(first.isStarted());
		final NullNode second = new NullNode();
		r.add("x", second); assertFalse(first.isStarted());
		assertTrue(second.isStarted());
		
	}
	
	public boolean simRun = false;

//	public final void testSimultaneous() {
//		final Root root = new Root();
//
//		final String result = "x";
//
//		Simultaneous s = new Simultaneous() {
//
//			@Override public Object run() throws Exception {
//				simRun = true;
//				return result;
//			}
//
//		};
//		root.add(s);
//
//		//assertFalse(s.getHasStarted().get());
//
//
//
//		Simultaneous.wait(delayTimeForThreadToStart);
//
//		assertTrue(simRun);
//
//		assertEquals(result, s.getResultIfAvailable());
//
//		root.remove(s);
//
//		assertTrue(s.getHasStarted().get());
//		assertTrue(s.getHasStopped().get());
//
//
//		root.stop();
//	}
	
}
