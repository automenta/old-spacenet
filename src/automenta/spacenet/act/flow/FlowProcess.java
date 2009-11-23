package automenta.spacenet.act.flow;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.list.ListVar;

/** control interface for starting and stopping parts of a data flow */ 
public interface FlowProcess extends Starts, Stops {

	/** a list of startable components that contain the inputs and outputs provided by getInPorts() and getOutPorts() */
	public ListVar<Starts> getComponents();
	
	/** component input ports */
	public ListVar<InPort> getInPorts();
	
	/** component output ports */
	public ListVar<OutPort> getOutPorts();
	
	
}
