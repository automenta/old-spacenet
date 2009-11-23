package automenta.spacenet.test;

import automenta.spacenet.test.*;
import junit.framework.TestCase;
import automenta.spacenet.Root;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.IfIntegerChanges;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.StringVar;

public class TestVar extends TestCase {

	private boolean intChanged1 = false;
	protected int objChanged2 = 0;

	
	public void testMultipleValueChanges() {
		Root node = new Root();
		
		IntegerVar i = new IntegerVar(1);
		StringVar t = new StringVar("text");
		
		IfChanges ifChanges = node.add(new IfChanges(i, t) {
			@Override public void afterValueChange(ObjectVar o, Object previous, Object next) {
				objChanged2++;												
			}
		}); {
			assertEquals(1, node.getStarted().size());
		}

		i.set(2);
		t.set("abc"); {		
			assertEquals(2, objChanged2);
		}
		
		node.remove(ifChanges);
		
		i.set(3); {
			assertEquals(2, objChanged2);
		}
	
		assertEquals(0, node.getStarted().size());
		
		
	}
	
	public void testIntValueChange() {
		Root node = new Root();
		
		int initialSize = node.size();
		
		IntegerVar i = new IntegerVar(1); 
		node.add(i); {			
			assertEquals(1 + initialSize, node.size());
			assertEquals(1, node.getValues(IntegerVar.class, false).size());
		}
		
		IfIntegerChanges whenIntChanges = new IfIntegerChanges(i) {			
			@Override public void afterValueChange(ObjectVar o, Integer previous, Integer next) {
				assertEquals(2, (int)next);
				assertEquals(1, (int)previous);
				intChanged1 = true;				
			}
		};
		node.add(whenIntChanges);
		
		i.set(2); {
			assertEquals(2, (int)i.get());
			assertTrue(intChanged1);	
			intChanged1 = false;	//reset
		}
		
		i.set(2); {
			assertFalse(intChanged1);
		}
		
		node.remove(whenIntChanges);
		
		i.set(3); {
			assertFalse(intChanged1);
		}
				

		node.remove(i); {
			assertNull(node.get(i));
		}
		
		
	}
	
	
//	public void testVector2Change() {
//		
//	}
}
