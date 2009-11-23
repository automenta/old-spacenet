package automenta.spacenet.test;

import automenta.spacenet.test.*;
import junit.framework.TestCase;
import automenta.spacenet.Root;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** tests watching both an entire vector3 and one of its scalar components */
public class TestVector3 extends TestCase {

	protected int numV3Changes=0, numV2Changes=0;
	protected double lastChangeX;
	protected double lastChangeY;
	protected double lastChangeZ;

	public void testVector3() {
		Root root = new Root();
		
		Vector3 v = new Vector3(0,0,0);

		numV3Changes = 0;
		IfVector3Changes whenVectorChanges = new IfVector3Changes(v) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				numV3Changes++;
				lastChangeX = dx;
				lastChangeY = dy;
				lastChangeZ = dz;
			}			
		};
		root.add(whenVectorChanges); {
			assertTrue(root.getStarted().contains(whenVectorChanges));
		}

		v.set(1,1,1); {
			assertEquals(1, numV3Changes);
			assertEquals(1.0, lastChangeX);
			assertEquals(1.0, lastChangeY);
			assertEquals(1.0, lastChangeZ);
		}
		
		root.remove(whenVectorChanges);
		
		v.set(0,0,0); {
			//now that the watch is removed, it should not be updated
			assertEquals(1, numV3Changes);
		}
		
		root.stop();
		
		assertFalse(root.getStarted().contains(whenVectorChanges));
	}

	public void testVector3StartedLast() {
		
		Vector3 v = new Vector3(0,0,0);
			
		numV3Changes = 0;
		IfVector3Changes whenVectorChanges = new IfVector3Changes(v) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				numV3Changes++;
				lastChangeX = dx;
				lastChangeY = dy;
				lastChangeZ = dz;
			}			
		};
		
		Root root = new Root();		

		root.add(whenVectorChanges); {
			assertTrue(root.getStarted().contains(whenVectorChanges));
		}

		v.set(1,1,1); {
			assertEquals(1, numV3Changes);
		}
						
		root.stop();
		
	}

	
	public void testVector2ExpressionChange() {
		Root root = new Root();
		
		Vector2 v = new Vector2(0,0);
				
		IfVector2Changes whenVectorChanges = new IfVector2Changes(v) {
			
			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
				numV2Changes++;
				lastChangeX = dx;
				lastChangeY = dy;
			}			
		};
		root.add(whenVectorChanges); {
			assertTrue(root.getStarted().contains(whenVectorChanges));
		}

		v.set(1,1); {
			assertEquals(1, numV2Changes);
			assertEquals(1.0, lastChangeX);
			assertEquals(1.0, lastChangeY);
		}
		
		root.remove(whenVectorChanges);
		
		v.set(0,0); {
			//now that the watch is removed, it should not be updated
			assertEquals(1, numV2Changes);
		}
		
		root.stop();
				
	}

}
