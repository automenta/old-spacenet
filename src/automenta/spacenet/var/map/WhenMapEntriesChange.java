/**
 * 
 */
package automenta.spacenet.var.map;

import java.util.Map;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;


public abstract class WhenMapEntriesChange<K,V> implements Starts, Stops {
	
	private MapVar<K, V>[] maps;
	private boolean addExisting;

	public WhenMapEntriesChange(MapVar<K,V>... maps) {
		this(true, maps);
	}
	
	public WhenMapEntriesChange(boolean addExisting, MapVar<K,V>... maps) {
		this.addExisting = addExisting;
		this.maps = maps;		
	}

	@Override public void start() {
		for (MapVar<K,V> m : maps)
			m.whenStarted(this);		
	}
	
	@Override public void stop() {
		for (MapVar<K,V> m : maps)
			m.whenStopped(this);
	}
	
	abstract public void afterMapPut(Map.Entry<K, V>... entry);
	abstract public void beforeMapRemoves(Map.Entry<K, V>... entry);

	final public boolean willAddPreExistingAfterStart() { return addExisting; }
	final public boolean willRemoveRemainingBeforeStop() { return false;  }
	
	
	
}