package automenta.spacenet.space.jme.geom;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Cylinder;

/** deprecated, use Curve3DNode */
@Deprecated public class Line3DNode extends BoxNode {
	Line3D line;
	
	private static final Vector3f upDirection = new Vector3f(0,0,1);
	
	protected Spatial lineSpatial;

	//cache of unit-scale cylinder objects.  the only difference between them is the number of segments surrounding.
	private static Map<Integer, Cylinder> cylinders = new HashMap();

	Quaternion rot = new Quaternion();

	private Vector3f direction = new Vector3f();


	public Line3DNode(Line3D line) {
		super(line);
		
		this.line = line;

		lineSpatial = newLineSpatial();
//		lineSpatial.setModelBound(new OrientedBoundingBox());
//		lineSpatial.updateModelBound();
		attachSpatials(lineSpatial);


	}

	@Override
	protected void beforeStartJme(JmeNode parent) {
		super.beforeStartJme(parent);
		
		line.add(new IfVector3Changes(line.getA(), line.getB()) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				updateLineNode();
			}			
		});
		line.add(new IfDoubleChanges(line.getRadius()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				updateLineNode();
			}
		});
		
		updateLineNode();
	}
	
	private double getLength() {
		return line.getA().getDistance(line.getB());		
	}

	public Line3D getLine() {
		return line;
	}


	private Spatial newLineSpatial() {
		TriMesh shape = getCylinder(getLine().getNumEdges().get());
		return new SharedMesh(shape);
	}

	private static Cylinder getCylinder(int numEdges) {
		Cylinder c = cylinders.get(numEdges);
		if (c== null) { 
			c = new Cylinder("line", 2, numEdges,1,1f, false);
			cylinders.put(numEdges, c);
		}
		return c;
	}

	protected synchronized void updateLineNode() {
		
		//getSize().set((float)getLength(), (float)line.getThickness().asDouble(), (float)line.getThickness().asDouble());

		//getOrientation().set(0,0,(float)getThetaXY());

//		if (!exists())
//			return;

		double currentThickness = getLine().getRadius().d();
		
//		center.set((float) ( (line.getA().x() + line.getB().x()) / 2.0) ,
//				(float)( (line.getA().y() + line.getB().y()) / 2.0),
//				(float)( (line.getA().z() + line.getB().z()) / 2.0));
//
		
//		//translation
//		if ((positionChanged) || (shapeChanged)) {
//			lineBox.setLocalTranslation(center);
//
//			positionChanged = false;
//		}

		
		//SIZE
		double length = getLength();		
		//line.getSize().set((float)(currentThickness/length), (float)(currentThickness/length), 1.0f);
		float l = (float) ((float)currentThickness/length);
		//float l = (float)length;
		lineSpatial.getLocalScale().set((float)currentThickness, (float)currentThickness, (float)length);

		//ORIENTATION
		direction.set((float)(line.getB().x() - line.getA().x()),
				(float)(line.getB().y() - line.getA().y()),
				(float)(line.getB().z() - line.getA().z()) );
		direction.normalizeLocal();
		
		
		lineSpatial.getLocalRotation().lookAt(direction, upDirection);

//		Quaternion q = new Quaternion();
//		float[] angles = new float[3];
//		q.lookAt(direction, upDirection);
//		q.toAngles(angles);
//		line.getOrientation().set(angles[0], angles[1], angles[2]);
		
		
		//POSITION
//		line.getPosition().set( 
//				(float)( (line.getA().x() + line.getB().x()) / 2.0) ,
//				(float)( (line.getA().y() + line.getB().y()) / 2.0),
//				(float)( (line.getA().z() + line.getB().z()) / 2.0));
		//lineSpatial.getLocalTranslation().set((float)line.getA().x(), (float)line.getA().y(), (float)line.getA().z());
		lineSpatial.getLocalTranslation().set( 
				(float)( (line.getA().x() + line.getB().x()) / 2.0) ,
				(float)( (line.getA().y() + line.getB().y()) / 2.0),
				(float)( (line.getA().z() + line.getB().z()) / 2.0));

		updateGeometricState(0.0f, true);
		updateModelBound();
		updateRenderState();
	}

	@Override protected void attachBoxGeometry() {	}
	@Override protected void detachBoxGeometry() {	}

}

