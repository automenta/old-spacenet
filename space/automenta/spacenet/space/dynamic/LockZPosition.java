package automenta.spacenet.space.dynamic;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.vector.Vector3;

public class LockZPosition extends Repeat<Box> {
	
	private Box box;
	private double constrainedZ;

	public LockZPosition(double z) {
		super();
		this.constrainedZ = z;
	}
	
	@Override public void start(Box b) {
		super.start(b);
		
		this.box = b;
	}
	
	@Override public double repeat(double t, double dt) {
		Vector3 p = box.getPosition();
		
		box.getPosition().set(p.x(), p.y(), constrainedZ);
		
		return 0.0;
	}

}
