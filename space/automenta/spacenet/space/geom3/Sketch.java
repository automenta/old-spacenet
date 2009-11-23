package automenta.spacenet.space.geom3;

import automenta.spacenet.space.Space;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.vector.Vector3;

public class Sketch extends ListVar<Vector3> {

	private ObjectVar<Space> startNode = new ObjectVar();
	private ObjectVar<Space> stopNode = new ObjectVar();

	public void add(double x, double y, double z) {
		add(new Vector3(x, y, z));		
	}
	
	public void add(double x, double y) {
		add(x, y, 0);
	}

	public ObjectVar<Space> getStartNode() {
		return startNode;
	}
	public ObjectVar<Space> getStopNode() {
		return stopNode;
	}

}
