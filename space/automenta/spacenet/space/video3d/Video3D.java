package automenta.spacenet.space.video3d;

import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** camera = point-of-view = eye = experience of seeing
 * 
 *  determines mapping of 3D space to 2D visual plane, as seen by an operator
 */
public interface Video3D  {

	/** size in pixels of the display */
	public Vector2 getPixelSize();
	
	/** position where the camera is located */
	public Vector3 getPosition();
	
	/** position that the camera is looking at */
	public Vector3 getTarget();
	
	/** in degrees */
	public DoubleVar getFocusAngle();		//TODO use radians

	public DoubleVar getZNear();
	public DoubleVar getZFar();

	/** up vector */
	public Vector3 getUp();

	public Color getBackgroundColor();

}
