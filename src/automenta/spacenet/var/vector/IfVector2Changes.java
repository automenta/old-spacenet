package automenta.spacenet.var.vector;

import java.util.Arrays;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;

abstract public class IfVector2Changes implements Starts, Stops {

	private Vector2[] vectors;

	public IfVector2Changes(Vector2... v) {
		this.vectors = v;
	}

	@Override public void start() {
		for (Vector2 v : vectors)
			v.whenStarted(this);				
	}

	@Override public void stop() {
		for (Vector2 v : vectors)
			v.whenStopped(this);
	}
	

	abstract public void afterVectorChanged(Vector2 v, double dx, double dy);

	@Override public String toString() {
		if (vectors == null) {
			return "noticing [vector2]: null";
		}
		else {
			return "noticing [vector2]: " + Arrays.asList(vectors);
		}
	}
	
}
