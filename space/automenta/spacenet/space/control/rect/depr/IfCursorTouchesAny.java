package automenta.spacenet.space.control.rect.depr;

import java.util.Set;

import javolution.util.FastSet;
import automenta.spacenet.Scope;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

@Deprecated abstract public class IfCursorTouchesAny extends IfChanges<Scope>  {

	private Set<Scope> nodeSet = new FastSet();
	private Pointer cursor;

	public IfCursorTouchesAny(Pointer c, Scope... nodes) {
		super(c.getTouchedNode());
		this.cursor = c;
		for (Scope n : nodes)
			nodeSet.add(n);
		
	}

	@Override public void afterValueChange(ObjectVar o, Scope previous, Scope next) {
		if (previous!=null) {
			if (nodeSet.contains(previous)) {
				whenTouchChanges(previous, false, cursor);
			}
		}
		if (next!=null) {
			if (nodeSet.contains(next)) {
				whenTouchChanges(previous, true, cursor);
			}
		}
	}

	abstract protected void whenTouchChanges(Scope node, boolean touched, Pointer c);
	
}
