package automenta.spacenet.var.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import automenta.spacenet.Variable;


public class ListVar<L> extends CollectionVar<L> implements Variable, List<L>  {
	private static final Logger logger = Logger.getLogger(ListVar.class);
	
	private List<L> internal;
	
	static public <X> ListVar<X> newLinkedList(Class<? extends X> c) {
		return new ListVar<X>() {
			@Override
			protected List<X> newInternalList() {
				return new LinkedList<X>();
			}			
		};		
	}
	
	public ListVar() {
		super();
		internal = newInternalList();
	}
	
	protected List<L> newInternalList() {
		return new FastList<L>();
	}

	public ListVar(L... initialContents) {
		this();
		for (L l : initialContents)
			add(l);
	}


	public void addFirst(L e) {
		if (internal instanceof FastList)	((FastList)internal).addFirst(e);
		else if (internal instanceof LinkedList) ((LinkedList)internal).addFirst(e);
		notifyAdded(toArray(e));
	}
	
	
	public void addLast(L e) { 
		if (internal instanceof FastList)	((FastList)internal).addLast(e);
		else if (internal instanceof LinkedList) ((LinkedList)internal).addLast(e);
		notifyAdded(toArray(e));		
	}
	
	@Override public boolean add(L e) {
		boolean b = internal.add(e);
		if (b) {
			notifyAdded(toArray(e));
		}
		return b;
	}
	
	@Override public boolean addAll(Collection<? extends L> c) {
		//TODO optimize by adding them all, not one-by-one
		for (L l : c) {
			if (!add(l)) {
				return false;
			}
		}
		return true;
	}
	
	public void add(int index, L element) {
		internal.add(index, element);
		notifyAdded(toArray(element));
	}
	
	public boolean addAll(int index, Collection<? extends L> c) {
		//TODO optimize by adding them all, not one-by-one
		for (L l : c) {
			if (!add(l)) {
				return false;
			}
		}
		return true;
	}

	public L remove(int index) {
		L l = internal.remove(index);
		notifyRemoved(toArray(l));
		return l;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean b = internal.remove(o);
		if (b) {
			notifyRemoved(toArray(o));
		}
		return b;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		//TODO optimize
		for (Object o : c) {
			if (!remove(o)) {
				return false;
			}
		}
		return true;
	}
	
	public L removeFirst() {
		L l = null;
		if (internal instanceof FastList)	l = ((FastList<L>)internal).removeFirst();
		else if (internal instanceof LinkedList) l = ((LinkedList<L>)internal).removeFirst();
		
		if (l!=null) {
			notifyRemoved(toArray(l));
		}
		return l;
	}
	
	public L removeLast() {
		L l = null;
		if (internal instanceof FastList)	l = ((FastList<L>)internal).removeLast();
		else if (internal instanceof LinkedList) l = ((LinkedList<L>)internal).removeLast();
		
		if (l!=null) {
			notifyRemoved(toArray(l));
		}
		return l;
	}
	

//	@Override public void whenStopped(Net<Object, Object> net) {
//		Containing c = netContainments.get(net);
//		if (c!=null) {
//			for (L l: this) {
//				if (l instanceof ListVar)
//					((ListVar) l).whenStarted(net);
//			}
//
//			net.stop(c);
//		}
//	}
	
//	@Override
//	public void whenStarted(Net<Object, Object> net) {
//		Containing c = new Containing(this);
//		if (netContainments == null) {
//			netContainments = new HashMap();
//		}
//		netContainments.put(net, c);
//		net.startOld(c);
//		for (L l: this) {
//			if (l instanceof ListVar)
//				((ListVar) l).whenStarted(net);
//		}		
//	}

	@Override
	public void clear() {
		if (size() > 0) {
			//ArrayList remaining = new ArrayList(this);
			//notifyRemovedCollection(remaining);

//			List<L> l = new LinkedList();
//			for (L o : this) {
//				l.add(o);
//			}
//			internal.clear();
//			notifyRemovedCollection(l);
			
//			notifyRemoved(toArray(this, size()));
//			internal.clear();
			
			//TODO why must this be done sequentially?  something lock

			ArrayList<L> thisCopy = new ArrayList<L>(this);
			
//			internal.clear();
//			for (L o : thisCopy) {
//				notifyRemoved(toArray(o));				
//			}			
			for (L o : thisCopy) {
				remove(o);
			}			
			
		}
	}
	
	@Override
	public boolean contains(Object o) {
		return internal.contains(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		return internal.containsAll(c);
	}
	@Override
	public boolean isEmpty() {
		return internal.isEmpty();
	}
	@Override
	public Iterator<L> iterator() {
		return internal.iterator();
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		return internal.retainAll(c);
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
	@Override
	public L get(int index) {
		return internal.get(index);
	}
	@Override
	public int indexOf(Object o) {
		return internal.indexOf(o);
	}
	@Override
	public int lastIndexOf(Object o) {
		return internal.lastIndexOf(o);
	}
	@Override
	public ListIterator<L> listIterator() {
		return internal.listIterator();
	}
	@Override
	public ListIterator<L> listIterator(int index) {
		return internal.listIterator(index);
	}
	
	@Override
	public L set(int index, L element) {
		logger.error("set(index,object) not supported yet");
		return null;
	}
	
	@Override
	public List<L> subList(int fromIndex, int toIndex) {	
		return internal.subList(fromIndex, toIndex);
	}

	@Override
	public String toString() {
		return internal.toString();
	}

	public L getFirst() {
		if (size() > 0)
			return get(0);
		return null;
	}

	public L getLast() {
		if (size() > 0)
			return get(size()-1);
		return null;
	}
	
	
	
}

