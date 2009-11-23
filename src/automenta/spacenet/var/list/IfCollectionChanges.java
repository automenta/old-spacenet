/**
 * 
 */
package automenta.spacenet.var.list;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;

public abstract class IfCollectionChanges<L> implements Starts, Stops {
	
	//TODO support MapVars for situations when values matter, but keys do not
	
	private CollectionVar<L>[] list;
	private boolean addExisting;
	private boolean removeRemaining;

	public IfCollectionChanges(boolean addExisting, boolean removeRemaining, CollectionVar<L>... list) {
		this.addExisting = addExisting;
		this.removeRemaining = removeRemaining;
		this.list = list;
	}

   	public IfCollectionChanges(CollectionVar<L> c) {
		this(true, true, c);
	}

	public IfCollectionChanges(CollectionVar<L>... list) {
		this(true, true, list);
	}
	
	@Override public void start() {
		for (CollectionVar<L> l : list)
			l.whenStarted(this);		
	}
	
	
	abstract public void afterObjectsAdded(CollectionVar list, L[] added);
	abstract public void afterObjectsRemoved(CollectionVar list, L[] removed);
	
	final public boolean willAddPreExistingAfterStart() { return addExisting; }
	final public boolean willRemoveRemainingBeforeStop() { return removeRemaining; }
	
	@Override public void stop() {
		if (list!=null) {
			for (CollectionVar<L> l : list)
				l.whenStopped(this);		
				
			list = null;
		}
	}
	
}