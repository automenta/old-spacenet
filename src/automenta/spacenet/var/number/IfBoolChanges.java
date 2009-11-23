package automenta.spacenet.var.number;

import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

abstract public class IfBoolChanges extends IfChanges<Boolean> {

	public IfBoolChanges(BooleanVar observed) {
		super(observed);
	}

    public IfBoolChanges(BooleanVar... observed) {
		super(observed);
	}

	@Override public void afterValueChange(ObjectVar o, Boolean previous, Boolean next) {
		afterValueChanged((BooleanVar)o, next.booleanValue());
	}
	
	abstract public void afterValueChanged(BooleanVar b, boolean nextValue);

	@Override public String getChangeTypeString() {
		return "boolean";
	}

}
