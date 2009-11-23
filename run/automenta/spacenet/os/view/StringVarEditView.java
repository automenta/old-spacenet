package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.string.StringVar;

public class StringVarEditView implements ObjectView<StringVar> {

	@Override public String getName(StringVar i) {
		return "Edit Text";
	}

	@Override public double getStrength(StringVar i) {
		return 0.75;
	}

	@Override public void run(StringVar i, ObjectVar<Space> o) throws Exception {
		TextEditRect etr = new TextEditRect(i);
		o.set(etr);
	}

}
