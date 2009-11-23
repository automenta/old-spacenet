package automenta.spacenet.space.control.video;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;


public class NumericKeypadMovesEye extends Repeat {

	private DoubleVar movementVelocity;
	private Keyboard keyboard;
	private Video3D video;
	
	private double dFocus = 0.2;

	public NumericKeypadMovesEye(Video3D video, Keyboard keyboard, DoubleVar movementVelocity) {
		super();
		this.video = video;
		this.keyboard = keyboard;
		this.movementVelocity = movementVelocity;
	}


	@Override public double repeat(double t, double dt) {

		double mv = movementVelocity.get() * dt;
		
		double davX = 0; //left-right rotate (roll)
		double davY = 0; //up-down rotate (yaw)
		double davZ = 0; //rotation around center (pitch?)
		double dmv = 0;
		
		if (keyboard.getKey(Keyboard.KEY_NUMPAD4).b()) {
			davX -= mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD6).b()) {
			davX += mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD8).b()) {
			davY += mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD2).b()) {
			davY -= mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD5).b()) {
			dmv += mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD0).b()) {
			dmv -= mv;
		}
		if (keyboard.getKey(Keyboard.KEY_NUMPAD9).b()) {
			video.getFocusAngle().add(dFocus);
		}	
		if (keyboard.getKey(Keyboard.KEY_NUMPAD3).b()) {
			video.getFocusAngle().add(-dFocus);
		}	

		if (keyboard.getKey(Keyboard.KEY_NUMPAD7).b()) {
			davZ += mv;
		}	
		if (keyboard.getKey(Keyboard.KEY_NUMPAD1).b()) {
			davZ -= mv;
		}	

//		if ((davX!=0) || (davY!=0) || (davZ!=0) || (dmv!=0)) {
//			Eye.rotateAndMove(video.getPosition(), video.getTarget(), video.getTilt(), davX, davY, davZ, dmv);
//		}

		double dx = davX;
		double dy = davY;
		double dz = davZ;
		
		if ((davX!=0) || (davY!=0) || (davZ!=0)) {
			video.getPosition().add(dx, dy, dz);
			video.getTarget().add(dx, dy, dz);
		}
		
		return 0;
	}

}
