package automenta.spacenet.act.flow;

import automenta.spacenet.Input;
import automenta.spacenet.Output;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.number.BooleanVar;

abstract public class InPort<T> implements Input<T>, Starts, Stops {

	private BooleanVar enabled = new BooleanVar(false);

	@Override public boolean in(T o) {
		if (isEnabled().get()) {
			if (acceptsInput(o)) {
				getQueue().push(o);
				return true;
			}
		}
		return false;
	}
	
	@Override public void start() {
		enabled.set(true);
	}
	
	@Override public void stop() {
		enabled.set(false);
	}
	
	public BooleanVar isEnabled() {
		return enabled ;
	}
	
	abstract public boolean acceptsInput(T o);

	/** FIFO queue into which received objects are pushed */
	abstract public FifoVar<T> getQueue();

	/** identifies source of what is presently received, if known. */
	abstract public ObjectVar<Output<T>> getSource();
}
