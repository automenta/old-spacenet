package automenta.spacenet.act;

import java.util.Iterator;

import automenta.spacenet.var.ObjectVar;

public interface ActionGenerator<I,O> {

	public Iterator<ObjectVarAction<I,O>> getActions(I i, ObjectVar<O> o);
	
}
