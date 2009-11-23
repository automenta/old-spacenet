package automenta.spacenet.space.control.video;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class FirstPersonLook extends Repeat {

	private Video3D video;
	private Keyboard keyboard;
	private Pointer pointer;
	private DoubleVar rotationSpeed;
	private int activeKey;
	private boolean isLooking;
	private Vector3 defaultVector = new Vector3(0,0,-1);

	public FirstPersonLook(Video3D video, Keyboard keyboard, Pointer pointer, DoubleVar rotationSpeed, int activeKey) {
		super();
		this.video = video;
		this.keyboard = keyboard;
		this.pointer = pointer;
		this.rotationSpeed = rotationSpeed;
		this.activeKey = activeKey;
	}

	Vector3 rotationOffset = new Vector3();
	Vector3 target = new Vector3();
	
	@Override public double repeat(double t, double dt) {
		if (keyboard.getKey(activeKey).b()) {
			isLooking = true;
			
			lookToward(pointer.getTouchDirection());			
		}
		else {
			if (isLooking) {
				//return to original vector				
				isLooking = false;
				lookToward(defaultVector);
			}
		}
		return 0;
	}

	private void lookToward(Vector3 direction) {
		rotationOffset.set(direction);
		rotationOffset.normalize();
		rotationOffset.cartesianToSphere();

		//System.out.println(rotationOffset);
		
		//1. convert rotationOffset to cartesian vector
		target.set(rotationOffset);
		target.sphereToCartesian();
		target.add(video.getPosition());

		video.getTarget().set(target);		
	}

}
