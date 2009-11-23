package automenta.spacenet.test;

import automenta.spacenet.test.*;
import junit.framework.TestCase;
import automenta.spacenet.Root;
import automenta.spacenet.var.number.DoubleVar;


public class TestBuildSome extends TestCase {

	public static class B extends DoubleVar {
		public B() {
			super(1.0);
		}		
	}
	public static class C extends DoubleVar {
		public C() {
			super(1.0);
		}		
	}
	

	/** demonstrate diff between setThe(..) and setSome(..) */
	public void testBuildSome() {
		Root r = new Root();
		
		r.setSome(C.class);
		{
			C c1 = r.addThe(C.class);
			C c2 = r.addThe(C.class);		
			assertTrue(c1!=c2);
			assertTrue(c1.get().equals(c2.get()));
		}
		
		r.setThe(B.class);
		{
			B b1 = r.addThe(B.class);
			B b2 = r.addThe(B.class);
			assertTrue(b1 == b2);
		}
		
		
	}
	
}
