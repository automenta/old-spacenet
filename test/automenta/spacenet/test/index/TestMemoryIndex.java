package automenta.spacenet.test.index;

import junit.framework.TestCase;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.index.MemoryIndex;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;

public class TestMemoryIndex extends TestCase {

	public void testOneItem() {
		MemoryIndex i = new MemoryIndex();
		
		StringVar x = new StringVar("x");
		
		i.index(x, "name");
		assertEquals(1, i.size());
		
		ListVar<Found> r = i.getResults("name", 10);
		assertEquals(1, r.size());
		assertEquals(x, r.get(0).getObject());
		assertEquals("name", r.get(0).getName());
		assertEquals(null, r.get(0).getDescription());
		
		i.dispose();
	}

	public void testMultipleItems() {
		MemoryIndex i = new MemoryIndex();
		
		String descString = "this is a description.";
		i.index(new DoubleVar(1.0), "a", descString, null, 2.0f);		
		i.index(new DoubleVar(1.0), "b", descString, "key, tag");		

		ListVar<Found> r = i.getResults("description", 10);
		assertTrue(r.size()  == 2);
		assertEquals("a", r.get(0).getName());	//a has boost 2.0
		assertEquals("b", r.get(1).getName());
		
		ListVar<Found> s = i.getResults("tag", 10);
		assertTrue(s.size() > 0);
		assertEquals("b", s.get(0).getName());
		
		i.dispose();
	}

	public void testRemoveItems() {
		//...
	}
}
