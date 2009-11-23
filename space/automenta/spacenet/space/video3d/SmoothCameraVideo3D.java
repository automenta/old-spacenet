package automenta.spacenet.space.video3d;

import automenta.spacenet.space.dynamic.vector.DynamicVectorSet;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class SmoothCameraVideo3D extends AbstractVideo3D {

	private DynamicVectorSet vectorSet;
	private DoubleVar positionSpeed;
	private DoubleVar targetSpeed;

	public SmoothCameraVideo3D(DoubleVar positionSpeed, DoubleVar targetSpeed) {
		super();
		this.positionSpeed = positionSpeed;
		this.targetSpeed = targetSpeed;
	}
	
	@Override
	protected void init() {
		vectorSet = new DynamicVectorSet(0);
		add(vectorSet);
		
		up = vectorSet.newVector3(0, 1, 0, 0.1);
	}

	@Override
	protected Vector3 newTarget() {
		return vectorSet.newVector3(0,0,0, targetSpeed);
	}

	@Override
	protected Vector3 newPosition() {
		return vectorSet.newVector3(0,0,20, positionSpeed);
	}

}
