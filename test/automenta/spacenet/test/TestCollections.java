package automenta.spacenet.test;

import automenta.spacenet.test.*;
import java.util.Map.Entry;

import junit.framework.TestCase;
import automenta.spacenet.Root;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.map.WhenMapEntriesChange;
import automenta.spacenet.var.map.WhenMapValuesChange;

//@IncompleteFeature("test List and Map addExisting and removeRemaining functionality")
public class TestCollections extends TestCase {

	int listAdded = 0, listRemoved = 0, mapEntryAdded = 0, mapEntryRemoved = 0;
	int mapValueAdded = 0, mapValueRemoved = 0;
	
	public void testList() {
		Root root = new Root();
		
		ListVar l = new ListVar();
		
		IfCollectionChanges whenListChanges = root.add(new IfCollectionChanges(l) {
			@Override public void afterObjectsAdded(CollectionVar  l, Object[] added) {
				listAdded++;
			}
			@Override public void afterObjectsRemoved(CollectionVar  l, Object[] removed) {
				listRemoved++;
			}			
		});
		
		l.add(new Object()); {
			assertEquals(1, listAdded);
			assertEquals(1, l.size());
		}
		
		Object o = new Object();
		l.add(o);
		l.remove(o); {
			assertEquals(1, listRemoved);
		}
		
		root.stop();
		
	}
	
	public void testMap() {
		int initialSize = 4;
		
		Root r = new Root();

		MapVar m = new MapVar();
		
		
		for (int i = 0; i < initialSize; i++) {
			Object o = new Object();
			m.put(o, o);
		}
		
		
		WhenMapEntriesChange whenMapChanges = r.add(new WhenMapEntriesChange(m) {
			@Override public void afterMapPut(Entry... entry) {
				mapEntryAdded+= entry.length;
			}
			@Override public void beforeMapRemoves(Entry... entry) {
				mapEntryRemoved+= entry.length;
			}			
		});
		WhenMapValuesChange whenValueChange = r.add(new WhenMapValuesChange(m) {
			@Override public void afterMapAdds(Object... o) {
				mapValueAdded+=o.length;
			}
			@Override public void beforeMapRemoves(Object... o) {
				mapValueRemoved+=o.length;
			}			
		});
		
		{
			assertEquals(initialSize, mapEntryAdded);
			assertEquals(initialSize, mapValueAdded);
		}

		
		Object k = new Object();
		Object v = new Object();
		m.put(k, v); {
			assertEquals(initialSize + 1, mapEntryAdded);
			assertEquals(initialSize + 1, mapValueAdded);
		}
		m.remove(k); {
			assertEquals(1, mapEntryRemoved);
			assertEquals(1, mapValueRemoved);
		}

		r.remove(whenMapChanges); {
			assertNull(r.get(whenMapChanges));
		}
		r.remove(whenValueChange); {
			assertNull(r.get(whenValueChange));
		}
		
		r.stop();
		
		
	}
}
