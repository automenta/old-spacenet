package automenta.spacenet.var.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javolution.util.FastSet;

import org.apache.log4j.Logger;



///@IncompleteFeature("deny addition of duplicate objects")
public class SetVar<O> extends CollectionVar<O> implements Set<O> {
	private static final Logger logger = Logger.getLogger(SetVar.class);
	
	final private Set<O> internal = new FastSet<O>();
	
	@Override public boolean add(O e) {
		boolean b = internal.add(e);
		notifyAdded(toArray(e));
		return b;
	}

	private void notImplemented() {
		logger.error("not implemented");
		Thread.dumpStack();		
	}
	
	@Override
	public boolean addAll(Collection<? extends O> c) {
		notImplemented();
		return false;
	}

	@Override
	public void clear() {		
		notImplemented();		
	}

	@Override
	public boolean contains(Object o) {
		return internal.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return internal.contains(c);
	}

	@Override
	public boolean isEmpty() {
		return internal.isEmpty();
	}

	@Override
	public Iterator<O> iterator() {
		return internal.iterator();
	}

	@Override
	public boolean remove(Object o) {
		boolean b = internal.remove(o);
		notifyRemoved(toArray(o));
		return b;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		notImplemented();
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		notImplemented();
		return false;
	}

	@Override
	public int size() {
		return internal.size();
	}

	@Override
	public Object[] toArray() {
		return internal.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return internal.toArray(a);
	}
	
}
