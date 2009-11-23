/**
 * 
 */
package automenta.spacenet.space.video3d;

import automenta.spacenet.Scope;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

abstract public class AbstractVideo3D extends Scope implements Video3D, Starts, Stops {
	
	private Vector3 direction;
	private Vector3 position;
	private final DoubleVar zFar = new DoubleVar(50.0);
	private final DoubleVar zNear = new DoubleVar(0.05);
	private final DoubleVar focusAngle = new DoubleVar(45.0);

	private IfVector3Changes v3Watch;
	private IfVector2Changes v2Watch;
	private Vector2 pixelSize = new Vector2(1,1);
	protected Vector3 up = new Vector3();
	private Color backgroundColor;

	abstract protected void init();
	abstract protected Vector3 newPosition();
	abstract protected Vector3 newTarget();

	@Override
	public String toString() {
		return "Sight3d (" + getClass().getSimpleName() + ")";
	}
	
	@Override public void start() {
		init();

		backgroundColor = new Color(Color.Black);
		
		direction = newTarget();
		position = newPosition();	
		
		v3Watch = add(new IfVector3Changes(direction, position) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy,	double dz) {
				//...
			}
			@Override public String toString() { return "noticing changes in direction or position"; }
		});
//		v2Watch = (WhenVector2Changes)add(new WhenVector2Changes(getPixelSize()) {				
//			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
//				//TODO resize the window
//			}
//		});		
	}

	@Override public void stop() {
		
	}

	
	@Override
	public Vector3 getTarget() {
		return direction;
	}

	@Override
	public Vector2 getPixelSize() {
		return pixelSize ;
	}

	@Override
	public Vector3 getPosition() {
		return position;
	}

	@Override public DoubleVar getFocusAngle() {
		return focusAngle;
	}

	@Override
	public DoubleVar getZFar() {
		return zFar;
	}

	@Override	public DoubleVar getZNear() {
		return zNear;
	}
	
	@Override	public Vector3 getUp() {
		return up ;
	}
	
	
	@Override public Color getBackgroundColor() {
		return backgroundColor;
	}
	public static Vector3 getDirection(Video3D video) {
		return new Vector3(video.getTarget().x() - video.getPosition().x(), 
				video.getTarget().y() - video.getPosition().y(),
				video.getTarget().z() - video.getPosition().z());
	}
	
}