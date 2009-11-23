/**
 * 
 */
package automenta.spacenet.plugin.java.act;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import automenta.spacenet.act.ObjectVarAction;
import automenta.spacenet.act.ActionGenerator;
import automenta.spacenet.var.ObjectVar;

public class StaticMethodsActions implements ActionGenerator {

	double defaultStrength = 0.5;

	private Class cl;

	public StaticMethodsActions(Class c) {
		super();
		this.cl = c;
	}
	
	@Override
	public Iterator<ObjectVarAction> getActions(Object i, ObjectVar o) {
		List<ObjectVarAction> a = new LinkedList();
		
		for (final Method m : cl.getMethods()) {
			if (Modifier.isStatic( m.getModifiers() )) {
				Class<?>[] pt = m.getParameterTypes();
				if (pt.length == 1) {
					if (i.getClass().isAssignableFrom(pt[0])) {
						a.add(new ObjectVarAction() {
							@Override public String getName(Object i) {		return cl.getSimpleName() + "." + m.getName();			}

							@Override public double getStrength(Object i) {	return defaultStrength;		}

							@Override public void run(Object i, ObjectVar o) throws Exception {
								m.invoke(null, i);									
							}
						});
					}
				}
			}
		}
		return a.iterator();
	}
	
}