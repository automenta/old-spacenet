package automenta.spacenet.space.object;

import java.util.Map.Entry;

import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.map.WhenMapEntriesChange;


/** a set of objects associated with a value of (generic) type V.  example: if V is Double, then may represent a 'presence' or 'strength' variable for each object in the space. */
public class ObjectSpace<V> extends Box implements StartsIn<Space>, Stops {

	abstract public static interface ObjectSpaceModel<V> {
		public void ifObjectAdded(ObjectSpace space, Object o, V value);
		public void ifObjectChange(ObjectSpace space, Object o, V oldValue, V newValue);
		public void ifObjectRemoved(ObjectSpace space, Object o);		
	}

	private MapVar<Object, V> obj;
	private ObjectSpaceModel model;
	private WhenMapEntriesChange<Object, V> ifObjChanges;
	
	public ObjectSpace(MapVar<Object,V> objects, ObjectSpaceModel model) {
		super();
		
		this.obj = objects;
		this.model = model;
	}

	@Override public void start(Space c) {
		add(model);
		
		ifObjChanges = add(new WhenMapEntriesChange<Object,V>(getObjects()) {

			@Override public void afterMapPut(Entry<Object, V>... entry) {
				for (Entry<Object,V> e : entry) {
					model.ifObjectAdded(ObjectSpace.this, e.getKey(), e.getValue());					
				}
			}

			@Override public void beforeMapRemoves(Entry<Object, V>... entry) {
				for (Entry<Object,V> e : entry) {
					model.ifObjectRemoved(ObjectSpace.this, e.getKey());					
				}				
			}			
		});
	}
	
	@Override public void stop() {
		remove(ifObjChanges);
		
		for (Object o : getObjects().keySet()) {
			model.ifObjectRemoved(this, o);
		}
		
		remove(model);
	}
	
	@Override
	public MapVar<Object,V> getObjects() { return obj; }
	
	
}
