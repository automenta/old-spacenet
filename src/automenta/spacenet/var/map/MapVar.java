package automenta.spacenet.var.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastMap;
import javolution.util.FastSet;

import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Variable;


public class MapVar<K,V> implements Map<K,V>, Variable, Disposable {
	//TODO lazy instantiate whenMapChanges list

	private final static Logger logger = Logger.getLogger(MapVar.class);

	private Set<WhenMapEntriesChange<K,V>> mapEntryChanges;
	private Set<WhenMapValuesChange> mapValueChanges;
	
	final private FastMap<K,V> map;

	public MapVar() {
		super();
		map = new FastMap<K,V>().setShared(true);
	}
    
	public MapVar(int size) {
		super();
		map = new FastMap<K,V>(size).setShared(true);
	}
	
	private void ensureWhenMapChangesExists() {
		if (mapEntryChanges == null)
			mapEntryChanges = new FastSet<WhenMapEntriesChange<K,V>>();
	}
	private void ensureWhenMapValuesChangeExists() {
		if (mapValueChanges == null)
			mapValueChanges = new FastSet();
	}

	
	public V put(final K key, final V value) {
		V replaced = map.put(key, value);
				
		if (mapEntryChanges!=null) {
			Entry<K, V> e = newEntry(key, value);
			for (WhenMapEntriesChange<K,V> w : mapEntryChanges) {
				w.afterMapPut(e);
			}
		}
		if (mapValueChanges!=null) {
			for (WhenMapValuesChange w : mapValueChanges) {
				w.afterMapAdds(value);
			}
		}
			
		return replaced;
	}

	private Entry<K, V> newEntry(final K key, final V value) {
		return new Entry<K,V>() {
			@Override public K getKey() {	return key;	}
			@Override public V getValue() {return value;	}
			@Override public V setValue(V value) {	return null;	}				
		};
	}
	
	private Entry<K, V> newEntry(final K key) {
		final V value = get(key);
		return new Entry<K,V>() {
			@Override public K getKey() {	return key;	}
			@Override public V getValue() {	return value;	}
			@Override public V setValue(V value) {	return null;	}			
		};
	}

	
//	public void putAll(Collection<V> values) {
//		if (values.size() == 0)
//			return;
//		
//		ArrayList<Entry<K,V>> entries = new ArrayList(values.size());
//		for (V v : values) {
//			map.put((K)v, v);
//			entries.add(newEntry((K)v,v));
//		}
//		
//		Entry[] a = entries.toArray(new Entry[entries.size()]);
//		
//		if (whenMapChanges!=null) {
//			for (WhenMapEntryChanges<K,V> w : whenMapChanges) {
//				w.afterMapPut(a);
//			}
//		}
//	}
	
	public void putAll(Collection<V> values) {
		if (values.size() == 0)
			return;
		
		for (V v : values) {
			map.put((K)v, v);
		}
		
		
		if (mapEntryChanges!=null) {
			ArrayList<Entry<K,V>> entries = new ArrayList(values.size());
			for (V v : values) {
				entries.add(newEntry((K)v,v));				
			}

			Entry[] a = entries.toArray(new Entry[values.size()]);			
			for (WhenMapEntriesChange<K,V> w : mapEntryChanges) {
				w.afterMapPut(a);
			}
		}

		if (mapValueChanges!=null) {
			Object[] a = values.toArray();
			for (WhenMapValuesChange w : mapValueChanges) {
				w.afterMapAdds(a);
			}
		}

	}

	public void removeAll(Collection<K> keys) {
		if (keys.size() == 0)
			return;
		
		if (mapEntryChanges!=null) {
			ArrayList<Entry<K,V>> entries = new ArrayList(keys.size());
			for (K k : keys) {
				V v = get(k);
				entries.add(newEntry(k, v));				
			}

			Entry[] a = entries.toArray(new Entry[keys.size()]);
			for (WhenMapEntriesChange<K,V> w : mapEntryChanges) {
				w.beforeMapRemoves(a);
			}
		}

		if (mapValueChanges!=null) {
			ArrayList values = new ArrayList(keys.size());
			for (K k : keys) {
				V v = get(k);
				if (v!=null)
					values.add(v);
			}
			for (WhenMapValuesChange w : mapValueChanges) {
				w.beforeMapRemoves(values.toArray());
			}
		}

		
		for (K k : keys) {
			map.remove(k);
		}
			
	}
	
	@Override public void clear() {
		removeAll(map.keySet());
//		for (K k : map.keySet()) {
//			Entry<K,V> e = newEntry(k);
//			for (WhenMapChanges<K,V> w : whenMapChanges) {
//				w.afterMapRemoved(e);
//			}
//		}
//		map.clear();
	}

	
	public void putAll(Map<? extends K, ? extends V> m) {
		logger.error("putAll() not impl yet");
	}

	public V remove(final Object key) {
		
		V value = get(key);
		if (value == null)
			return null;
		
		if (mapEntryChanges!=null) {
			Entry<K,V> e = newEntry((K)key,value);
			for (WhenMapEntriesChange<K,V> w : mapEntryChanges) {
				w.beforeMapRemoves(e);				
			}
		}

		if (mapValueChanges!=null) {
			for (WhenMapValuesChange w : mapValueChanges) {
				w.beforeMapRemoves(value);
			}
		}

		final V removed = map.remove(key);

		return removed;
	}




	protected boolean whenStarted(WhenMapValuesChange w) {
		ensureWhenMapValuesChangeExists();
		if (mapValueChanges.add(w)) {
			if (size() > 0) {
				if (w.willAddPreExistingAfterStart()) {
					w.afterMapAdds(values().toArray());
				}
			}
			return true;
		}
		return false;
	}

	protected boolean whenStarted(WhenMapEntriesChange<K,V> w) {
		ensureWhenMapChangesExists();
		if (mapEntryChanges.add(w)) {
			if (size() > 0) {
				if (w.willAddPreExistingAfterStart()) {
					ArrayList<Entry<K,V>> entries = new ArrayList<Entry<K,V>>(size());
					for (K k : map.keySet()) {
						entries.add(newEntry(k));
					}
					
					Entry[] a = entries.toArray(new Entry[size()]);
					w.afterMapPut(a);	
				}
			}
			return true;
		}
		return false;
	}

	protected boolean whenStopped(WhenMapEntriesChange<K,V> w) {
		if (mapEntryChanges!=null) {
			if (w.willRemoveRemainingBeforeStop()) {
				//TODO implement
			}
			return mapEntryChanges.remove(w);
		}
		return false;
	}

	public boolean whenStopped(WhenMapValuesChange w) {
		if (mapEntryChanges!=null) {
			if (w.willRemoveRemainingBeforeStop()) {
				w.beforeMapRemoves(values().toArray());
			}
			return mapValueChanges.remove(w);
		}
		return false;		
	}

	public static final <K,V> List<K> newKeyList(Map.Entry<K,V>... entries) {
		List<K> v = new LinkedList();
		for (Map.Entry<K,V> e : entries)
			v.add(e.getKey());
		return v;
	}
	public static final <K,V> List<V> newValueList(Map.Entry<K,V>... entries) {
		List<V> v = new LinkedList();
		for (Map.Entry<K,V> e : entries)
			v.add(e.getValue());
		return v;
	}


	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public String toString() {
		return map.toString();
	}

	@Override
	public void dispose() {
		if (mapEntryChanges!=null) {
			mapEntryChanges.clear();
			mapEntryChanges = null;
		}
		if (mapValueChanges!=null) {
			mapValueChanges.clear();
			mapValueChanges = null;
		}
		if (map!=null) {
			map.clear();
		}
		
	}


	
}
