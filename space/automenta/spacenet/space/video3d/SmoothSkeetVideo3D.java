package automenta.spacenet.space.video3d;

import automenta.spacenet.var.number.DoubleVar;

/**
 * adds wobble to sight, so that the camera points in the direction
 * in which its moving.  when it's finished moving, both the sight's
 * position and target will have finished moving, meaning the line of sight will be still.
 */
public class SmoothSkeetVideo3D extends SmoothCameraVideo3D {

	/**
	 * 
	 * @param speed			base speed
	 * @param skeetFactor	0=no skeet, 1=position never moves, but values close to 0 are most useful
	 */
	public SmoothSkeetVideo3D(double speed, double skeetFactor) {
		super(new DoubleVar(speed), new DoubleVar(speed * (1.0 - skeetFactor)));
	}

}
