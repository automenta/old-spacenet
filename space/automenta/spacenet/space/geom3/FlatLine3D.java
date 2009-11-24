package automenta.spacenet.space.geom3;

import automenta.spacenet.Starts;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;

public class FlatLine3D extends Box implements Starts {

	private final Vector3 a;
	private final Vector3 b;
	private final Vector3 center = new Vector3();
	private final DoubleVar width;
	private final Rect rect;

    private final double[] angles = new double[3];
	private fQuaternion q = new fQuaternion();
	private fVector3 dir = new fVector3();

//	private fVector3 upX = new fVector3(1, 0, 0);
//	private fVector3 upY = new fVector3(0, 1, 0);
//	private fVector3 upZ = new fVector3(0, 0, 1);

	public FlatLine3D(Vector3 a, Vector3 b, DoubleVar width) {
		super();
		
		this.rect = new Rect(0,0,1,1);
		this.a = a;
		this.b = b;
		this.width = width; 
	}

	@Override public void start() {
		add(rect);
		
		add(new IfVector3Changes(getA(), getB()) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				updateFlatLine();
			}			
		});
		
		updateFlatLine();
	}
	
	@Override
	public FlatLine3D surface(Surface s) {
		getRect().surface(s);
		return this;
	}
	
	
	
	protected void updateFlatLine() {
		double length = getB().getDistance(getA());
		
		center.set(getA());
		center.add(getB());
		center.multiply(0.5);

        add(new Box(new Vector3(0,0,0), new Vector3(0.1, 0.1, 0.1))).color(Color.Gray);
        add(new Box(getA().subtractNew(center), new Vector3(0.1, 0.1, 0.1))).color(Color.GrayPlusPlus);
        add(new Box(getB().subtractNew(center), new Vector3(0.1, 0.1, 0.1))).color(Color.GrayMinusMinus);
        

		dir.set(getB().x() - getA().x(), getB().y() - getA().y(), getB().z() - getA().z());

        //double xyRot = Math.atan2(dir.y, dir.x);
        //double yzRot = Math.atan2(dir.z, dir.y);

        double halfPi = Math.PI / 2.0;        

        getPosition().set(center);
		
        rect.size(length, getWidth().d());

        q.lookAt(new fVector3(1,0,0), dir);
        q.toAngles(angles);
        //rect.orient(0, xyRot, 0);
        rect.orient(angles[0], angles[1], angles[2]);

        //System.out.println(dir + ": " + angles[0] + ", " + angles[1] + ", " + angles[2]);

//        angles[0] = 0;
//		angles[1] = Math.atan2(dir.z, dir.x);
        //angles[2] = Math.atan2(dir.y, dir.x);


//        double yaw = Math.atan2(dir.y, dir.x);
//        double pitch = Math.atan2(Math.sqrt((dir.x * dir.x) + (dir.y * dir.y)), dir.z);


		//getRect().orient(halfPi + angles[0], angles[1], halfPi + angles[2]);

        //double halfPi = Math.PI / 2.0;
        //getRect().orient(halfPi + angles[0], angles[1], halfPi + angles[2]);
        
	}

	@Override public void stop() {
		
	}
	
	public Vector3 getA() {
		return a;
	}
	public Vector3 getB() {
		return b;
	}
	public DoubleVar getWidth() {
		return width;
	}
	
	public Rect getRect() {
		return rect;
	}
	
	
}
