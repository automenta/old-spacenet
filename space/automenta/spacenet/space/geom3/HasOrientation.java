package automenta.spacenet.space.geom3;

import automenta.spacenet.var.vector.Vector3;

public interface HasOrientation {
	public Vector3 getOrientation();
	public Vector3 getAbsoluteOrientation();
	
	public Vector3 getNextAbsoluteOrientation();
	public void setNextAbsoluteOrientation(double r1, double r2, double r3);
}
