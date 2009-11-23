package automenta.spacenet.space.object.data;

import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;

abstract public class ListSpace<O> extends Box implements StartsIn<Space>, Stops {

	private ListVar<O> list;
	private Space space;
	private IfCollectionChanges<O> whenSetChanges;

	public ListSpace(ListVar<O> set) {
		super();
		this.list = set;
		
	}
	
	@Override public void start(Space space) {
		this.space = space;
		
		whenSetChanges = add(new IfCollectionChanges<O>(getList()) {

			 
			@Override
			public void afterObjectsAdded(CollectionVar  list, O[] added) {
				for (O o : added)
					whenObjectAdded(o);				
			}

			@Override
			public void afterObjectsRemoved(CollectionVar  list, O[] removed) {
				for (O o : removed)
					whenObjectRemoved(o);				
			}
			
		});
	}

	@Override public void stop() {
		if (whenSetChanges!=null) {
			for (O o : getList()) {
				whenObjectRemoved(o);
			}
			//getSpace().whenStopped(whenSetChanges);
			whenSetChanges = null;
		}
	}

	abstract protected void whenObjectRemoved(O o);
	abstract protected void whenObjectAdded(O o);

	
	public ListVar<O> getList() { return list; }

	public Space getSpace() { return space; }
	
}
