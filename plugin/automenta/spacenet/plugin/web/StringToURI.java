package automenta.spacenet.plugin.web;

import automenta.spacenet.UURI;
import automenta.spacenet.act.ObjectVarAction;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.string.StringVar;

public class StringToURI implements ObjectVarAction<StringVar, UURI> {

	@Override
	public String getName(StringVar i) {
		return "as URI";
	}

	@Override
	public double getStrength(StringVar i) {
		if ((i.s().startsWith("http:") || (i.s().startsWith("https:")))) {
			return 0.9;
		}
		if (i.s().startsWith("www.")) {
			return 0.9;
		}
		return 0.1;
	}

	@Override
	public void run(StringVar i, ObjectVar<UURI> o) throws Exception {
		o.set(new UURI(i.s()));
	}

}
