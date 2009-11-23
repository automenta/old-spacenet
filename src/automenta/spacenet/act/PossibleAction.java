package automenta.spacenet.act;

import automenta.spacenet.var.ObjectVar;

public class PossibleAction<I,O> {

	private ObjectVarAction<I, O> action;
	private I input;
	private ObjectVar<O> output;
	
	public PossibleAction(ObjectVarAction<I,O> a, I i, ObjectVar<O> o) {
		super();
	
		this.action = a;
		this.input = i;
		
		if (o == null) {
			o = new ObjectVar<O>();
		}
		
		this.output = o;
		
	}
	
	public PossibleAction(ObjectVarAction<I,O> a, I i) {
		this(a, i, null);
	}

	public String getName() {
		return getAction().getName(getInput());
	}
	
	public double getStrength() {
		return getAction().getStrength(getInput());
	}
	
	public void run() throws Exception {
		getAction().run(getInput(), getOutput());
	}
	
	
	public ObjectVarAction<I,O> getAction() { return action; }
	
	public I getInput() { return input; }
	
	public ObjectVar<O> getOutput() { return output; }

	public O getOutputValue() {
		return getOutput().get();
	}
	
	@Override
	public String toString() {
		return getName() + "<" + getStrength() + ">(" + getInput() + ")" + " -> " + getOutput();
	}
	
}
