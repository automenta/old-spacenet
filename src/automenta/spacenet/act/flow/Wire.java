package automenta.spacenet.act.flow;

import automenta.spacenet.Input;
import automenta.spacenet.Output;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.ObjectVar;

/** a connection=wire=pipe which passes data from a source to a target */
abstract public class Wire<S extends Output, T extends Input> implements Starts, Stops {
	
	public ObjectVar<InPort> getInput() {
		return null;
	}
	
	public ObjectVar<OutPort> getOutput() {
		return null;
	}

	//TODO: element delay parameter
	//TODO: max flow rate parameter
	//TODO: enabled
}
