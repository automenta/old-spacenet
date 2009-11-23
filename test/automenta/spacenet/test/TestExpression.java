package automenta.spacenet.test;

import junit.framework.TestCase;

public class TestExpression extends TestCase {

//	public void testExpression() {
//		Vector2 v = new Vector2(1.0, 1.0);
//
//		MVELExpression e = new MVELExpression(v, "x() + y()"); {
//			assertEquals(2.0, e.evaluate());
//			assertEquals(2.0, e.evaluate()); //test twice
//		}
//
//		DoubleVar z = new DoubleVar(4.0);
//		MVELExpression f = new MVELExpression(v, "isInside(0.0,0.0,z.get(),z.get())", "z", z); {
//			assertEquals(true, f.evaluate());
//			z.set(0.5);
//			assertEquals(false, f.evaluate());
//		}
//	}
//
//	int expChange1 = 0;
//	private Object expChangeCurrent;
//
//	public void testExpressionChange() {
//		Root node = new Root();
//
//		Vector2 v = new Vector2(1.0, 1.0);
//		DoubleVar z = new DoubleVar(1.0);
//
//		Stops w = node.startOld(new DynamicExpression(v, "isInside(0.0, 0.0, z.get(), z.get())", "z", z) {
//			@Override public void afterExpressionChanges(Object previousValue, Object currentValue) {
//				//System.out.println(previousValue + " -> " + currentValue);
//				expChange1++;
//				expChangeCurrent = currentValue;
//			}
//		});
//
//		z.set(0.5); {
//			assertEquals(v.isInside(0,0,0.5,0.5), expChangeCurrent);
//		}
//
//		z.set(2.0); {
//			assertEquals(true, expChangeCurrent);
//		}
//
//	}
//
	
}
