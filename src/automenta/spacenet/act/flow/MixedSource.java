package automenta.spacenet.act.flow;

import automenta.spacenet.Output;
import automenta.spacenet.var.list.ListVar;

/** a mux for sources, which aggregates 1 or more input sources into a combined source */
public interface MixedSource<S> extends Output<S> {
	
	public ListVar<Output<S>> getSources();

}
