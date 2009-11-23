package automenta.spacenet.space.dynamic;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.var.vector.Vector3;

public class FaceVector extends Repeat {

	private Space spatial;
	private Vector3 vector;
	private Vector3 pTarget = new Vector3();

	public FaceVector(Space c, Vector3 vector) {
		super();
		this.vector = vector;
		this.spatial = c;
	}
	
	@Override public double repeat(double t, double dt) {
		Vector3 pos = ((HasPosition3)spatial).getPosition();
		pTarget.set(pos);
		pTarget.add(vector);
		
		Maths.rotateAngleTowardPoint(((HasPosition3)spatial).getPosition(), ((HasOrientation)spatial).getOrientation(), pTarget);
		return 0;
	}
}

