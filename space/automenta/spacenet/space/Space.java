package automenta.spacenet.space;



import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Scope;
import automenta.spacenet.space.control.Represents;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;

/** a spatial object */
public class Space extends Scope {
	
	private static final Logger logger = Logger.getLogger(Space.class);
	
	public final DoubleVar opacity = new DoubleVar(1.0);

	public final BooleanVar tangible = new BooleanVar(false);

	public final BooleanVar visible = new BooleanVar(true);
		
//	public <X> X addThe(Class<? extends X> c) {
//		X t = getThe(c);
//		if (t!=null)
//			add(t);
//		return t;
//	}


	public DoubleVar getOpacity() {
		return opacity;
	}

	public Space opacity(double d) {
		getOpacity().set(d);
		return this;
	}

	/** whether this node can be 'touched' */
	public BooleanVar getTangible() {
		return tangible ;
	}

	@Override public String toString() {
		return getClass().getSimpleName() + "@" + hashCode();
	}

	public Space addAll(Space... subspaces) {
		for (Space s : subspaces) {
            if (s!=null)
                add(s);
		}
		return this;
	}

	public Object removeAndDispose(Space s) {		
		Object removed = remove(s);
		if (removed!=null) {
			if (removed instanceof Disposable) {
				((Disposable)removed).dispose();
			}
		}
		return removed;
	}

	public Space tangible(boolean t) {
		getTangible().set(t);
		return this;
	}

	public boolean hasParent(Space s) {
		Scope p = getParent();
		while (p!=null) {
			if (p == s) {
				return true;
			}
			p = p.getParent();
		}
		return false;
	}

	
	public void visible(boolean b) {
		getVisible().set(b);
	}
	public BooleanVar getVisible() {
		return visible;
	}

	public static Object getRepresentedObject(Scope s) {
		while (s!=null) {
			if (s instanceof Represents) {
				return ((Represents)s).getObject();
			}
			if (s.getParent() instanceof Scope)
				s = s.getParent();
			else
				return null;
		}
		return null;
	}
	

}

