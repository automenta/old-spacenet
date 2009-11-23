package automenta.spacenet.var.string;

import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

abstract public class IfStringChanges extends IfChanges<String> {

	public IfStringChanges(StringVar... text) {
		super(text);
	}

	abstract public void afterTextChanged(StringVar t, String previous, String current);
	
	@Override
	public void afterValueChange(ObjectVar o, String previous, String next) {
		afterTextChanged((StringVar)o, previous, next);
	}

	@Override public String getChangeTypeString() {
		return "string";
	}

}
