package automenta.spacenet.act.flow;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.var.number.BooleanVar;

/** when started in a node, builds dataflow components and binds them to existing objects.
  	when stopped, removes what has been built.  */
abstract public class FlowLayer implements FlowProcess, StartsIn<Scope>, Stops {

	/** when becomes true, all of its components are started.  and vice-versa */
	abstract public BooleanVar isEnabled();
}
