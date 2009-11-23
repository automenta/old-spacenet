/**
 * 
 */
package automenta.spacenet.var;


import java.util.Arrays;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;


abstract public class IfChanges<C> implements StartsIn<Scope>, Stops {
	private static final Logger logger = Logger.getLogger(IfChanges.class);

	private ObjectVar[] observed;

	public IfChanges(ObjectVar... observed) {
		super();
		
		this.observed = observed;		
	}

	@Override public void start(Scope c) {
		for (ObjectVar o : observed)
			o.whenStarted(this);								
	}	


	@Override public void stop() {
		if (observed!=null) {
			for (ObjectVar o : observed)
				o.whenStopped(this);
			observed = null;
		}
	}
	
	abstract public void afterValueChange(ObjectVar o, C previous, C next);
	
	
	@Override
	public String toString() {
		if (observed == null) {
			return "noticing [" + getChangeTypeString() + "]: null";			
		}
		else {
			return "noticing [" + getChangeTypeString() + "]: " + Arrays.asList(observed);
		}
	}

	public String getChangeTypeString() {
		return "object";
	}
	
}