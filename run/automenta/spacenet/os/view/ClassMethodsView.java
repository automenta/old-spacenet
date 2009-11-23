package automenta.spacenet.os.view;

import java.lang.reflect.Method;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.ObjectVar;

public class ClassMethodsView implements ObjectView<Object> {

	@Override public String getName(Object i) {
		return "Class Methods";
	}

	@Override public double getStrength(Object i) {
		return 0.5;
	}

	@Override public void run(Object i, ObjectVar<Space> o) throws Exception {
		String t = "";
		for (Method m : i.getClass().getMethods()) {
			t += m.getName() + "\n";
		}
		o.set(new TextRect(t));
	}

}
