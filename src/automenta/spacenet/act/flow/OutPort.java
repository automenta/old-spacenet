package automenta.spacenet.act.flow;

import org.apache.log4j.Logger;

import automenta.spacenet.Input;
import automenta.spacenet.Output;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.BooleanVar;


abstract public class OutPort<O> implements Output<O>, Starts, Stops {
	private static final Logger logger = Logger.getLogger(OutPort.class);
	
	/** zero or more targets to which objects will be sent */
	abstract public ListVar<Input<O>> getTargets();
	
	abstract public FifoVar<O> getQueue();

	/** specifies whether outgoing objects should be queued, if no targets are attached */
	public BooleanVar queueWhileWithoutTargets() {
		return null;
	}
	
	
	public boolean out(O o) {
		if (getTargets().size() == 0) {
			if (queueWhileWithoutTargets().get()) {
				getQueue().push(o);
				return true;
			}
			else {
				logger.warn(this + " dropped outgoing object " + o);
				return false;
			}
		}
		else {
			for (Input<O> t : getTargets()) {
				boolean accepted = t.in(o);
				if (!accepted) {
					logger.warn(this + " has target " + t + " which did not accept object " + o );
				}
			}
			return true;
		}
	}
	

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
