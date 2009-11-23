package automenta.spacenet.space.control;

import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;


/** represents a ray originating from a source vector (camera) outwards,
 * interpreting the inverse of the mapping between the 2d projection and 
 * the 3d geometry it projects.  used for input devices such as mice and multi-touch recognition. */
public interface Pointer<Spatial> /*extends Sensor*/  {

	public static enum PointerButton {
		Left, Right, Center
	};

	public MapVar<Object, Toggle> getButtons();
	
	public Vector2 getPixelPosition();
	public BooleanVar getButton(PointerButton b);
	public DoubleVar getWheel();

	public Vector2 getPositionRelativeToScreen();
	
	/** camera that this cursor originates from */
	public Vector3 getSourcePosition();
	
	/** sequence of tangible spatials that are currently intersected by the ray produced by this cursor, 
	    in increasing distance from camera.  spatial -> point of intersection */
	public ObjectVar<? extends Space> getTouchedNode();

	//public Spatial getTouchedSpatial();
	
	/** coordinates relative to center of a spatial along the plane that a rect is oriented upon. null if nothing is touched 
	 * result = (x,y) where (-0.5, -0.5) is lower left corner, and (0.5, 0.5) is upper right
	 * */
	public Vector2 getQuadIntersection(Vector3 a, Vector3 b, Vector3 c, Vector2 result);
	
	/** yields the proportion of the vision consumed by a visible object, which may be zero or > 100% */
	public Vector2 getConsumedVisionProportion(Space s);


	public Vector3 getTouchDirection();

	//public Triangle getTouchedTriangle();

	public Vector3 getTriangleIntersection();

	void forward(double dt);

	public Vector2 getQuadIntersection(HasPosition3 rect, Vector2 uv);



}
