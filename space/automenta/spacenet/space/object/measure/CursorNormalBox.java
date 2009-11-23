package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;


public class CursorNormalBox extends Box implements Starts {

	private Pointer cursor;
	private Vector3 axisPosition = new Vector3();
	private Vector3 axisSize = new Vector3(1,1,1).multiply(0.5);
	private Vector3 axisOrientation = new Vector3();
	private final static fVector3 up = new fVector3(0,1,0);
	
	public CursorNormalBox(Pointer c) {
		super();
		
		this.cursor = c;
		
	}
	
	fQuaternion q = new fQuaternion();
	float angles[] = new float[3];
	
	@Override public void start() {
		add(new AxisBox(axisPosition, axisSize, axisOrientation));

//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//
//				Triangle tri = cursor.getTouchedTriangle();
//				if (tri!=null) {
//					getPosition().set(cursor.getTriangleIntersection());
//					//MathAction.toDouble( cursor.getTouchedTriangle().getNormal(), intersectNormal );
//
//					q.lookAt(cursor.getTouchedTriangle().getNormal(), up);
//					q.toAngles(angles);
//					axisOrientation.set(angles[0], angles[1], angles[2]);
//				}
//
//
//				return 0;
//			}
//		});
		
	}

    @Override public void stop() {
    }


    
	
	
}
