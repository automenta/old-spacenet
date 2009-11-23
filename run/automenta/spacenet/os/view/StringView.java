package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.ObjectVar;

public class StringView implements ObjectView<Object> {

	@Override public String getName(Object i) {
		return "Text";
	}

	@Override public double getStrength(Object i) {
		return 0.5;
	}

	@Override public void run(Object i, ObjectVar<Space> o) throws Exception {
		String s = i.toString();
		if (s.length() > getMaxStringLength()) {
			s = s.substring(0, getMaxStringLength());
		}
		
		o.set(new TextRect(s));
	}

	public int getMaxStringLength() { return 32; }
}
