package automenta.spacenet.var.vector;

import java.util.Arrays;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;

abstract public class IfVector3Changes implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(IfVector3Changes.class);
	
	private Vector3[] vectors;

	public IfVector3Changes(Vector3... v) {
		super();
		if (v == null) {
			logger.error("vectors = null");
		}
		this.vectors = v;
	}

	@Override public void start() {
		for (Vector3 x : vectors) {
			x.start(this);
		}
	}

	@Override public void stop() {
		if (vectors!=null) {
			for (Vector3 v : vectors) {
				if (v!=null)
					v.stop(this);
			}
			vectors = null;
		}
	}
	

	abstract public void afterVectorChanged(Vector3 v, double dx, double dy, double dz);

	@Override public String toString() {
		if (vectors == null) {
			return "noticing [vector3]: null";			
		}
		else {
			return "noticing [vector3]: " + Arrays.asList(vectors);
		}
	}

}
