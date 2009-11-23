package automenta.spacenet.var.map;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;


/** notifies of map's values that change (not keys).  more efficient than WhenMapEntriesChange, because
 	it also includes keys in each Entry notification */
abstract public class WhenMapValuesChange implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(WhenMapValuesChange.class);
	
	private MapVar[] maps;
	private boolean addExisting;
	public WhenMapValuesChange(MapVar... maps) {
		this(true, maps);
	}
	
	public WhenMapValuesChange(boolean addExisting, MapVar... maps) {
//		for (MapVar m : maps) {
//			if (m == null) {
//				logger.error(this + " watching null map: " + Arrays.asList(maps) );
//			}
//		}
		this.addExisting = addExisting;
		this.maps = maps;		
	}

	@Override public void start() {
		if (maps!=null) {
			for (MapVar m : maps) {
				if (m!=null)
					m.whenStarted(this);
			}
		}
	}
	
	@Override public void stop() {
		if (maps!=null) {
			for (MapVar m : maps) {
				if (m!=null)
					m.whenStopped(this);
			}
			maps = null;
		}
	}
	
	abstract public void afterMapAdds(Object... o);
	abstract public void beforeMapRemoves(Object... o);

	final public boolean willAddPreExistingAfterStart() { return addExisting; }
	final public boolean willRemoveRemainingBeforeStop() { return false; }
	

}
