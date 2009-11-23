package automenta.spacenet.space.dynamic.physics;

import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class PhyBox extends Box implements Physical {

	private DoubleVar mass;
	private BooleanVar solid = new BooleanVar(true);
	private Vector3 velocity = new Vector3();
	private Vector3 force = new Vector3();
	private Vector3 angularVelocity = new Vector3();
	private Vector3 torque = new Vector3();
	
	private boolean updateNextPhysicalGeometry = false;
	private boolean automaticPhysicalGeometry = true;
	private BooleanVar dynamic = new BooleanVar(true);
	private DoubleVar drag = new DoubleVar(0.0);
	private Vector3 boundsCenter = new Vector3();
	private Vector3 boundsScale = new Vector3();
	
	
	public PhyBox(double mass) {
		super(Color.Invisible);
		this.mass = new DoubleVar(mass);
	}

	public BooleanVar isDynamic() {
		return dynamic;
	}
	
	@Override public DoubleVar getMass() { return mass; }


	@Override public BooleanVar getSolid() {
		return solid;
	}

	public Vector3 getVelocity() {	return velocity;	}
	public Vector3 getForce() {	return force;	}

	public Vector3 getAngularVelocity() { return angularVelocity; }
	public Vector3 getTorque() { return torque; }

	public void setUpdatePhysicalGeometry(boolean next) {
		updateNextPhysicalGeometry = next;
	}

	public boolean isAutomaticPhysicalGeometry() {
		return automaticPhysicalGeometry;
	}

	public void setAutomaticPhysicalGeometry(boolean automaticPhysicalGeometry) {
		this.automaticPhysicalGeometry = automaticPhysicalGeometry;
	}

	public boolean shouldUpdateNextPhysicalGeometry() {
		return updateNextPhysicalGeometry;
	}

	/** drag of 0 is unaffected; >0 continually lowers the velocity */
	public DoubleVar getDrag() {
		return drag ;
	}

	public Vector3  getBoundsCenter() {
		return boundsCenter;
	}

	/** size of the bounds to which this box is restricted.  if (0,0,0), then unrestricted */
	public Vector3  getBoundsScale() {
		return boundsScale;
	}

}
