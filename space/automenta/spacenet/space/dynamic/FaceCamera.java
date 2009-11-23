package automenta.spacenet.space.dynamic;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.vector.Vector3;

public class FaceCamera extends Repeat<Box> {

	private Box box;
	private Video3D video;
	private Vector3 o = new Vector3();
	private double updatePeriod;
	private double restitutionForce;

	public FaceCamera() {
		this(0, 0);
	}
	public FaceCamera(double updatePeriod, double restitutionForce) {
		super();
		this.updatePeriod = updatePeriod;
		this.restitutionForce = restitutionForce;
	}
	
	@Override public void start(Box c) {
		super.start(c);

		this.box = c;
		this.video = c.getThe(Video3D.class);
	}

	@Override public double repeat(double t, double dt) {
		Vector3 camPoint = video.getPosition();
		
		Vector3 or = ((HasOrientation)box).getOrientation();
		o.set(or);
		Maths.rotateAngleTowardPoint(((HasPosition3)box).getPosition(), o, camPoint);
		
		double r = getRestitutionForce();
		
		((HasOrientation)box).getOrientation().set(or, o, r);
		
		return updatePeriod;
	}

	//1.0 = set instantly, 0.0 = set never... ~0.5 = smooth interpolation
	private double getRestitutionForce() {
		return restitutionForce;
	}

}
