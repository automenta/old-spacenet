package automenta.spacenet.act;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import automenta.spacenet.var.list.ListVar;
import java.util.Collection;

public class ActionIndex<I,O> {

	List<ObjectVarAction<? extends I,O>> actions = new LinkedList();
	List<ActionGenerator<? extends I,O>> actionGenerators = new LinkedList();
	
	public void addAction(ObjectVarAction<? extends I,O> a) {
		actions.add(a);
	}
	
	public void addActionGenerator(ActionGenerator<I,O> p) {
		actionGenerators.add(p);
	}
	
//	public ListVar<PossibleAction<I,O>> getPossibleActions(I i, ObjectVar<O> o) {
//		ListVar<PossibleAction<I,O>> l = new ListVar();
//
//		for (ObjectVarAction a : actions) {
//			try {
//				if (a.getStrength(i) > 0) {
//					l.add(new PossibleAction(a, i, o));
//				}
//			}
//			catch (Exception e) {	}
//		}
//
//		for (ActionGenerator p : actionGenerators) {
//			Iterator<ObjectVarAction> m = p.getActions(i, o);
//			while (m.hasNext()) {
//				ObjectVarAction pa = m.next();
//				try {
//					//getName(i) will fail if wrong type.  a possibly faster impl would be to introspect the action's generic type
//					pa.getName(i);
//
//					if (pa.getStrength(i) > 0) {
//						l.add(new PossibleAction(pa, i, o));
//					}
//				}
//				catch (Exception e) { }
//			}
//		}
//
//		Collections.sort(l, new Comparator<PossibleAction>() {
//			@Override public int compare(PossibleAction a, PossibleAction b) {
//				double as = a.getStrength();
//				double bs = b.getStrength();
//				if (as == bs)	return 0;
//				if (as > bs) return -1;
//				else return 1;
//			}
//		});
//
//		return l ;
//	}

    public static ListVar<Action> getPossibleActions(Collection<Action> actions, final Object input) {
		ListVar<Action> l = new ListVar();

		for (Action a : actions) {
			try {
				if (a.getStrength(input) > 0) {
					l.add(a);
				}
			}
			catch (Exception e) {	}
		}

		Collections.sort(l, new Comparator<Action>() {
			@Override public int compare(Action a, Action b) {
				double as = a.getStrength(input);
				double bs = b.getStrength(input);
				if (as == bs)	return 0;
				if (as > bs) return -1;
				else return 1;
			}
		});

		return l ;
	}

//	public ListVar<PossibleAction<I,O>> getPossibleActions(I i) {
//		return getPossibleActions(i, null);
//	}
	
}
