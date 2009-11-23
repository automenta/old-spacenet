package automenta.spacenet.var.number;

import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

abstract public class IfDoubleChanges extends IfChanges<Double> {

	public IfDoubleChanges(DoubleVar... r) {
		super(r);
	}
	
	@Override public void afterValueChange(ObjectVar r, Double previous, Double next) {
		afterDoubleChanges((DoubleVar)r, previous, next);
	}
	
	abstract public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next);

	@Override public String getChangeTypeString() {
		return "double";
	}

}
