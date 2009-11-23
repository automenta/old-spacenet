package automenta.spacenet.test;

import automenta.spacenet.test.*;
import java.util.Arrays;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.picocontainer.injectors.AbstractInjector.AmbiguousComponentResolutionException;
import org.picocontainer.injectors.AbstractInjector.UnsatisfiableDependenciesException;

import automenta.spacenet.Root;
import automenta.spacenet.act.SimultaneousContext;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.StringVar;


/** tests picocontainer */
public class TestBuilder extends TestCase {

	private static final Logger logger = Logger.getLogger(TestBuilder.class);
	
	public static class MockConstructorInjectable {
		IntegerVar i;
		StringVar t;
		
		public MockConstructorInjectable(IntegerVar i, StringVar t) {
			super();
			this.i = i;
			this.t = t;
		}

		public IntegerVar getI() {		return i;		}

		public void setI(IntegerVar i) {	this.i = i;	}

		public StringVar getT() {		return t;	}

		public void setT(StringVar t) {		this.t = t;	}
				
	}
	 
	public void testSingleComponent() {
		Root node = new Root();
		
		//SimultaneousContext is a SINGLE component, and will return the same instance
		SimultaneousContext s1 = node.getThe(SimultaneousContext.class);
		SimultaneousContext s2 = node.getThe(SimultaneousContext.class);
		assertSame(s2, s1);
				
	}
	
	/** test building new instances by constructor injection */
	public void testConstructorInjection() {
		Root node = new Root();

		int initialSize = node.size();
		
		//MockConstructorInjectable is not a SINGLE component, so getThe will return new instances each time it's called
		node.setSome(MockConstructorInjectable.class);

		//parameters to MockConstructorInjectable do not exist yet, so it can not be constructed
		try {
			node.getThe(MockConstructorInjectable.class);
			
			//should not reach here
			assertTrue(false);
		}
		catch (UnsatisfiableDependenciesException e) {
			logger.info(MockConstructorInjectable.class + " depends on: " + e.getUnsatisfiableDependencies());
			logger.info("  unsatisfied dependency type: " + e.getUnsatisfiedDependencyType());
			assertTrue(true);
		}

		node.setThe(new IntegerVar(1));
		node.setThe(new StringVar("a"));

		MockConstructorInjectable t = node.getThe(MockConstructorInjectable.class); {
			assertNotNull(t);
			assertEquals(1, t.getI().i());
			assertEquals("a", t.getT().s());
		}

		
		//TODO test ambiguity by putting > 1 valid possibilities for a constructor parameter
//		node.setThe(new IntegerVar(2));
//		try {
//			MockConstructorInjectable t2 = node.getThe(MockConstructorInjectable.class);
//			assertTrue(false);
//		}
//		catch (AmbiguousComponentResolutionException e) {
//			logger.info(MockConstructorInjectable.class + " unable to determine which of the following parameters to construct with:\n "
//					+ Arrays.asList(e.getAmbiguousComponentKeys()));
//			assertTrue(true);
//		}
		
		
		
		node.stop();
		
	}

}
