package automenta.spacenet;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javolution.util.FastSet;

import org.apache.commons.collections15.iterators.EmptyIterator;
import org.apache.log4j.Logger;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;

import automenta.spacenet.var.map.MapVar;



/** a hierarchical container=node that can add=start=enable and remove=stop=disable sub-states */
abstract public class Scope implements Contains, Disposable, Builder, Iterable {
	
	//TODO use PicoContainer to call Starts(), StartsIn(...), and Stops() in a similar way to how it calls Startable's start() and stop() methods

	private static final Logger logger = Logger.getLogger(Scope.class);

	final public  MapVar<Object, Object> objects;

	protected Scope parent;

	private Set<Stops> startedObjects;

	boolean started = false;

	private Map<Object, Object> waitingToStart = null;

	public Scope() {
		super();
		objects = new MapVar();
	}
	
	@Override public <X> X add(Object key, X value) {
		Object replaced = null;
		if (getObjects()!=null) {
			replaced = getObjects().get(key);
			if (replaced!=null) {				
				//replace existing
				remove(key);
			}
		}

		getObjects().put(key, value);		
		afterAdd(key, value);
		return value;
	}

	public <X> X add(X value) {
		if (value == null) {
			logger.error(this + " attempted to add null");
			return null;
		}
		add( value, value );
		return value;
	}

	@Override public  <X> void addAll(Collection<X> value) {
		for (X x : value) {
			add(x);
		}
	}

	public <T> T addThe(T t) {
		setThe(t);
		add(t);
		return t;
	}
	public <T> T addThe(Class<? extends T> cl, T t) {
		setThe(cl, t);
		add(cl, t);
		return t;
	}


	/** adds an instance of a class/interface, determined by the implementation specified with setThe(..) or setSome(..) */
	public <T> T addThe(Class<T> baseclass, Class<? extends T> subclass) {
		T t = getThe(baseclass);
		if (t == null) {
			setThe(baseclass, subclass);
			t = getThe(baseclass);			
		}
		if (t!=null) {
			add(baseclass, t);
		}
		return t;
	}

	/** adds an instance of a class/interface, determined by the implementation specified with setThe(..) or setSome(..) */
	public <T> T addThe(Class<T> cl) {
		T t = getThe(cl);
		if (t == null) {
			setThe(cl);
			t = getThe(cl);			
		}
		if (t!=null) {
			add(t);
		}
		return t;
	}
	
	private void afterAdd(Object key, Object value) {
		if (!isStarted()) {
			if (waitingToStart==null)
				waitingToStart = new MapVar();
			
			waitingToStart.put(key, value);
		}
		else
			startAdded(key, value);
	}
	
	private void afterScopeStart() {
		if (waitingToStart!=null) {
			for (Object k : waitingToStart.keySet()) {
				Object v = waitingToStart.get(k);
				startAdded(k, v);
			}
			waitingToStart.clear();
			waitingToStart = null;
		}
	}


	protected void afterScopeStop() {	
		if (waitingToStart!=null) {
			waitingToStart.clear();
			waitingToStart = null;
		}
	}


	private  void attemptStarting(Starts r) {
		if (r instanceof Stops)	
			if (!attemptStartingStops(r))
				return;

		r.start();		

		if (logger.isDebugEnabled())
			logger.debug(this + " started " + r);		
	}
	
	private  void attemptStarting(StartsIn r) {
		if (r instanceof Stops)	
			if (!attemptStartingStops(r))
				return;

		try {
			r.start(this);
			if (logger.isDebugEnabled())
				logger.debug(this + " started " + r);
		}
		catch (Exception e) {
			if (r instanceof Scope) {
				((Scope)r).setStarted(false);	
			}

			logger.warn(this + " unable to start " + r + "(" + e + ")");
			e.printStackTrace();

			if (getStarted()!=null)
				getStarted().remove(r);
		}		
	}
	
	private boolean attemptStartingStops(Stops s) {
		if (getStarted()!=null)
			if (getStarted().contains(s)) {
				//already started
				return false;
			}

		ensureStartedObjectsListInitialized();
		getStarted().add(s);		
		return true;
	}

	private void beforeRemove(Object key, Object value)  {
		
		if (value instanceof Stops) {
			Stops s = (Stops)value;
			stopStoppable(((Stops)value));				
		}

		if (value instanceof Scope) {
			((Scope)value).setParent(null);
		}

	}

	private void beforeScopeStart() {
		
	}

	private void beforeScopeStop() {

		if (getObjects()!=null) {
			Object[] toRemove = getObjects().keySet().toArray();
			for (Object o : toRemove) {
				remove(o);
			}
			getObjects().clear();
		}
		
		if (startedObjects!=null) {			
			startedObjects.clear();
			startedObjects = null;
		}


	}

	public void clear() {
		for (Object o : getKeys())
			remove(o);
	}

	@Override public void dispose() {
		setStarted(false);
				
		parent = null;
	}	

	private void ensureStartedObjectsListInitialized() {
		if (startedObjects==null)
			startedObjects = new FastSet();		
	}

	/** returns inherited objects */
	public MapVar getObjectsInherited() {
		//TODO
		return null;
	}

	/** returns all "local" objects */
	public MapVar getObjects() {
		return objects;
	}

	
	public Object get(Object key) {
		if (objects==null)
			return null;
		Object local = objects.get(key);
		if (local!=null)
			return local;
		if (getBuilder()!=null)
			return getBuilder().getComponent(key);
		return null;
	}

	protected MutablePicoContainer getBuilder() {
		if (getParent()==null)
			return null;
		return getParent().getBuilder(); 
	}

	private Set getKeys() {
		return objects.keySet();
	}




	public Scope getParent() {
		return parent;
	}


	/** gets closest parent which is a subclass of c */
	public <C extends Scope> C getParent(Class<C> c) {
		Scope n = this;
		while (n!=null) {
			n = n.getParent();
			if (c.isAssignableFrom(n.getClass())) {
				return (C)n;
			}
		}
		return null;
	}





	public Set<Stops> getStarted() {
		return startedObjects;
	}

	/** part=component (shorter by two syllables and 5 letters!) */
	public <T> T getThe(Class<T> cl) {
		if (getBuilder()==null)
			return null;
		return getBuilder().getComponent(cl);
	}

	public <C> List<? extends C> getValues(final Class<C> valueClass, final boolean includeSubclasses) {
		List<C> l = new LinkedList();
		for (Object o : objects.values()) {
			if (o.getClass().equals(valueClass))
				l.add((C)o);
			else if (includeSubclasses) {
				if (valueClass.isInstance(o))
					l.add((C)o);
			}
		}
		return l;
	}


	public boolean isStarted() {
		return started ;
	}



	public Iterator<Object> iterateKeys() {
		if (objects == null)
			return EmptyIterator.getInstance();

		return objects.keySet().iterator();
	}

	
	@Override public Iterator iterator() {
		return objects.values().iterator();
	}

	@Override public Object remove(Object key) {
		Object value = get(key);
		if (value!=null) {
			beforeRemove(key, value);
			objects.remove(key);
			return value;			
		}
		if (logger.isDebugEnabled())
			logger.debug(this + " unable to remove " + key + " (non-existing object with that key)");
		return null;
	}

	@Override public  void removeAll(Collection keys) {		
		for (Object r : keys) {
			remove(r);
		}
		
	}

	public void removeAll(Object... removed) {
		for (Object r : removed) {
            if (r!=null)
                remove(r);
        }
	}


	private void setParent(Scope nextParent) {
		Scope currentParent = getParent();

		if ((this.parent!=null) && (nextParent!=null)) {
			if (this.parent!=nextParent) {
				logger.error(this + " unable to move to " + nextParent + " while already in " + this.parent);
				return;
			}
		}

		this.parent = nextParent;

		if ((nextParent!=null) && (currentParent == null)) {
			setStarted(true);
		}
		if ((nextParent == null) && (currentParent != null)) {
			setStarted(false);
		}
	}

	public synchronized <X> X setSome(X component) {
		return setSome(component, component);
	}

	/** specifies something that can be built, associated with a specific key */
	public synchronized <X> X setSome(Object componentKey, X component) {
		getBuilder().addComponent(componentKey, component);
		return component;
	}

	private void setStarted(boolean b) {
		if (started == b) 
			return;

		if (b)
			beforeScopeStart();			
		else
			beforeScopeStop();

		started = b;

		if (b)
			afterScopeStart();
		else
			afterScopeStop();

	}

	/** specifies something that can be built, associated with a specific key */
	public synchronized <X> X setThe(Object componentKey, X component) {
		try {
			getBuilder().as(Characteristics.SINGLE).addComponent(componentKey, component);
		}
		catch (Exception e) {
			//TODO don't warn if existing component is the same as the attempted new component
			logger.warn("existing component previously set - unable to setThe(" + componentKey + ", " + component + "): " + e);
		}
		return component;
	}


	/** specifies that this node's builder can build a component (normally specified with a Class object).
	 * calls picocontainer addComponent() */
	public synchronized <X> X setThe(X component) {
		return setThe(component, component);
	}

	public int size() {
		return objects.size();
	}

	protected void startAdded(Object key, Object value) {
		if (value instanceof Scope) {
			((Scope)value).setParent(this);
		}

		if (value instanceof StartsIn) {
			attemptStarting((StartsIn)value);
		}
		else if (value instanceof Starts) {
			attemptStarting((Starts)value);
		}		

	}

	private void stopStoppable(Stops s) {
		if (getStarted()!=null) {
			if (!getStarted().contains(s)) {
				logger.error(this + " unable to stop " + s + " (it never started here)");
				return;
			}
		}

		s.stop();

		if (getStarted()!=null)
			getStarted().remove(s);

		if (logger.isDebugEnabled())
			logger.debug(this + " stopped " + s);
	}

	public static void printHierarchy(Scope s) {
		Scope n = s, next = s.getParent();
		System.out.print("[ " + s);
		if (next!=null) {
			do {
				n = next;
				next = n.getParent();
				if (next!=null) {
					System.out.print(" -> " + n + " -> ");
				}
				else {
					System.out.print(" -> "  + n);
				}				
			} while (next!=null);
		}
		System.out.println(" ]");
	}
	

}
