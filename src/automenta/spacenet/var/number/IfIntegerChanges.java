package automenta.spacenet.var.number;

import automenta.spacenet.var.IfChanges;

abstract public class IfIntegerChanges extends IfChanges<Integer> {

	public IfIntegerChanges(IntegerVar... i) {
		super(i);
	}

	@Override public String getChangeTypeString() {
		return "integer";
	}

}
