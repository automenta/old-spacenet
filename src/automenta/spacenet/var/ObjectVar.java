package automenta.spacenet.var;

import java.util.Set;

import javolution.util.FastSet;

import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Variable;


/** holds a changeable reference to an object, which may be null.  
 *  observers may be notified when it changes*/
public class ObjectVar<O> implements Variable, Disposable {
	private static final Logger logger = Logger.getLogger(ObjectVar.class);
	
	protected Set<IfChanges> whenVarChanges;

	private O o;

	public ObjectVar(O initialValue) {
		this();
		set(initialValue);
	}
		
	public ObjectVar() {
		super();
	}

//	@Override public int hashCode() {
//		return getID().hashCode();
//	}
//
//	private UUID getID() {
//		if (this.id == null) {
//			this.id = UUID.randomUUID();
//		}
//
//		return id;
//	}

	protected IfChanges whenStarted(IfChanges r) {
		if (whenVarChanges == null) {			
			whenVarChanges = newWhenVarChangesList();
		}
		
		//synchronized (whenVarChanges) {
			this.whenVarChanges.add(r);
		//}
		return r;
	}
	
	private Set<IfChanges> newWhenVarChangesList() {
		return new FastSet();
	}

	protected IfChanges whenStopped(IfChanges r) {
		if (whenVarChanges == null) {
			return null;
		}
		boolean removed;
		//synchronized (whenVarChanges) {
			removed = this.whenVarChanges.remove(r);
		//}
		if (removed) {
			return r;
		}
		return null;	
	}

	/**
	 * 
	 * @param next
	 * @return true if a new value is set, false if unchanged (same as previous value)
	 */
	public boolean set(final O next) {
		
		O previous = get();

		//don't send changes for exact object
		if (previous == next) {
			if (next!=null) {
				return false;
			}
		}
		
		//don't send changes for '.equals'
		if (previous!=null) {
			if (previous.equals(next)) {
				return false;
			}
		}

		this.o = next;

		if (previous!=next) {
			if (whenVarChanges!=null) {
				//synchronized (whenVarChanges) {
					for (IfChanges r : whenVarChanges) {
						r.afterValueChange(this, previous, next);
					}
				//}
			}
		}

		return true;
	}

	public O get() {
		return o;
	}

//	/** 
//	 * 
//	 * @param numChangesAgo > 0
//	 */
//	public O get(int numChangesAgo) {
//		return null;	
//	}
//	public O get(TimePoint when) {
//		return null;
//	}

	@Override public String toString() {
		if (get() == null) {
			return "[null]@" + Integer.toHexString(hashCode());
		}
		return "[" + get().toString() + "]@" + Integer.toHexString(hashCode());
	}

	@Override public void dispose() {
		
		if (whenVarChanges!=null) {
			//synchronized (whenVarChanges) {
				for (IfChanges w : whenVarChanges) {
					w.stop();
				}
				whenVarChanges.clear();
	
				whenVarChanges = null;
			//}
			o = null;
//			id = null;
		}
		
				
	}

	public boolean is(O o) {
		return get() == o;
	}
}
