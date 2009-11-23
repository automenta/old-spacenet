package automenta.spacenet.space.video3d;

import automenta.spacenet.var.vector.Vector3;

public class DirectCameraVideo3D extends AbstractVideo3D {

	@Override
	protected Vector3 newTarget() {
		return new Vector3(0,0,0);
	}

	@Override
	protected Vector3 newPosition() {
		return new Vector3(0,0,25);
	}

	@Override
	protected void init() {		
		
	}

}
