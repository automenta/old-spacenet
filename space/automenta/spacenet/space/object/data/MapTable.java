package automenta.spacenet.space.object.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import automenta.spacenet.Starts;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.map.WhenMapEntriesChange;
import automenta.spacenet.var.string.StringVar;

/** displays a map as a 2-column table, where each row corresponding to a key, value pair */
public class MapTable extends Box implements Starts {

	private MapVar map;
	
	int charsPerLine = 60;

	private Map<Object,Space> keyToSpatial = new HashMap();
	
	public MapTable(MapVar map) {
		super();
		
		this.map = map;
	}
	
	@Override public void start() {

		add(new ArrangeColumn(this));
		
		for (Object k : map.keySet()) {
			setProperty(k, map.get(k));
		}
		add(new WhenMapEntriesChange(map) {
			@Override public void afterMapPut(Entry... entry) {
				for (Entry e : entry)
					setProperty(e.getKey(), e.getValue());
			}

			@Override public void beforeMapRemoves(Entry... entry) {
				for (Entry e : entry)
					removeProperty(e.getKey());				
			}			
		});
	}

	protected void setProperty(Object key, Object value) {
		StringVar t = new StringVar(key.toString() + " -> " + value.toString());

		if (keyToSpatial.get(key)!=null) {
			TextRect tr = (TextRect)keyToSpatial.get(key);
			tr.getText().set(t);
		}
		else {
			TextRect tr = new TextRect(t, charsPerLine);
			keyToSpatial.put(key, tr);
			add(tr);			
		}
		
	}

	protected void removeProperty(Object key) {
		Space spatial = keyToSpatial.get(key);
		if (spatial!=null) {
			keyToSpatial.remove(key);
			remove(spatial);
		}
		
	}
	
	@Override public void stop() {
		
	}
	
}
