package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.ObjectVar;

public class ClassInfoView implements ObjectView<Object> {

	@Override public String getName(Object i) {
		return "Class Information";
	}

	@Override public double getStrength(Object i) {
		return 0.5;
	}

	@Override public void run(Object i, ObjectVar<Space> o) throws Exception {
		o.set(new TextRect(i.getClass().toString()));
	}

}
