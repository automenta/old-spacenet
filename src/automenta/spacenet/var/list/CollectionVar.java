package automenta.spacenet.var.list;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import automenta.spacenet.Variable;


abstract public class CollectionVar<L> implements Collection<L>, Variable {
	private static final Logger logger = Logger.getLogger(CollectionVar.class);
	
	protected Set<IfCollectionChanges<L>> whenCollectionChanges;

//	private boolean batching = false;
//	private List<L> batchAdded, batchRemoved;

	public L[] toArray(final Object o) {
		L[] a = (L[]) Array.newInstance(o.getClass(), 1);
		a[0] = (L) o;
		return a;
	}

	public L[] toArray(final Collection c) {		
		return toArray(c, 0);
	}

	public L[] toArray(final Collection c, int size) {
		//return (L[]) c.toArray((L[]) Array.newInstance(c.get(0).getClass(), size));
		return (L[]) c.toArray((L[]) Array.newInstance(c.iterator().next().getClass(), size));
		
	}
	

	protected void notifyAddedCollection(List<L> added) {
		if (whenCollectionChanges!=null) {
//			if (batching) {
//				if (batchAdded == null)
//					batchAdded = new LinkedList();
//				batchAdded.addAll(added);
//			}
//			else {
				L[] addedArray = toArray(added, added.size());
				notifyAdded(addedArray);
//			}
		}
	}

	protected void notifyRemovedCollection(List<L> removed) {
		if (whenCollectionChanges!=null) {
//			if (batching) {
//				if (batchRemoved == null)
//					batchRemoved = new LinkedList();
//				batchRemoved.addAll(removed);
//			}
//			else {
				L[] removedArray = toArray(removed, removed.size());
				notifyRemoved(removedArray);
//			}
		}
	}

//	protected void notifyAdded(L l) {		
//		if (whenListChanges!=null) {
//			for (WhenListChanges<L> w : whenListChanges) {
//				w.afterObjectsAdded(this, l);
//			}
//		}
//	}
//	
//	protected void notifyRemoved(L l) {
//		if (whenListChanges!=null) {
//			for (WhenListChanges<L> w : whenListChanges) {
//				w.afterObjectsRemoved(this, l);
//			}
//		}
//	}

	protected void notifyAdded(L[] addedArray) {		
		if (whenCollectionChanges!=null) {
			for (IfCollectionChanges<L> w : whenCollectionChanges) {
				w.afterObjectsAdded(this, addedArray);
			}
		}
	}
	
	protected void notifyRemoved(L[] removedArray) {
		if (whenCollectionChanges!=null) {
			for (IfCollectionChanges<L> w : whenCollectionChanges) {
				w.afterObjectsRemoved(this, removedArray);
			}
		}
	}
	
	
//	public void startBatching() {
//		batching = true;
//	}
//	public void stopBatching()  {
//		batching = false;
//		if (batchRemoved != null) {
//			if (batchRemoved.size() > 0) {
//				notifyRemovedCollection(batchRemoved);
//				batchRemoved.clear();
//			}
//		}
//		if (batchAdded != null) {
//			if (batchAdded.size() > 0) {
//				notifyAddedCollection(batchAdded);
//				batchAdded.clear();
//			}
//		}
//	}

	public boolean whenStarted(IfCollectionChanges<L> w) {
		if (whenCollectionChanges==null) {
			whenCollectionChanges = new FastSet();
		}
		boolean b = whenCollectionChanges.add(w);
		if (b) {
			if (size() > 0) {
				if (w.willAddPreExistingAfterStart()) {
					//add pre-existing objects
					w.afterObjectsAdded(this, toArray(this, size()));
				}
			}
		}
		return b;
	}
	
	public boolean whenStopped(IfCollectionChanges<L> w) {
		if (whenCollectionChanges==null) {
			return false;
		}

		if (whenCollectionChanges.size() > 0) {
			if (size() > 0) {
//				L[] remaining = toArray(this, size());
//				if (w.willRemoveRemainingBeforeStop()) {
//					//remove remaining objects
//					w.afterObjectsRemoved(this, remaining);
//				}
				//TODO why must this be done sequentially?
				for (Object o : this) {
					w.afterObjectsRemoved(this, toArray(o));
				}
			}
			return whenCollectionChanges.remove(w);
		}
		return false;
	}

}
