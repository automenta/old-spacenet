/**
 * 
 */
package automenta.spacenet.plugin.java.act;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import automenta.spacenet.act.ObjectVarAction;
import automenta.spacenet.act.ActionGenerator;
import automenta.spacenet.var.ObjectVar;

public class MethodActions implements ActionGenerator {

	double defaultStrength = 0.5;
	
	@Override public Iterator<ObjectVarAction> getActions(Object i, ObjectVar o) {
		List<ObjectVarAction> a = new LinkedList();
		
		final Class c = i.getClass();
		for (final Method m : c.getMethods()) {
			if (isExcluded(m))
				continue;
			
			if (m.getParameterTypes().length == 0) {
				a.add(new ObjectVarAction() {
					@Override public String getName(Object i) { 	return c.getSimpleName() + "." + m.getName();			}

					@Override public double getStrength(Object i) { 	return defaultStrength;		}

					@Override public void run(Object i, ObjectVar o) throws Exception {
						Object p = m.invoke(i);
						o.set(p);
					}						
				});
			}
		}
		
		return a.iterator();
	}

	private boolean isExcluded(Method m) {
		if (m.getDeclaringClass().equals(Object.class)) {
			if (m.getName().equals("notify"))
				return true;
			if (m.getName().equals("notifyAll"))
				return true;
			if (m.getName().equals("wait"))
				return true;
			
		}
		return false;
	}
	
}