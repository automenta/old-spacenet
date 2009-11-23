package automenta.spacenet;


import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.Matrix3f;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector2;
import automenta.spacenet.var.vector.jme.fVector3;





/** centrallized math utilities, as a gathering place for access to an abstracted math implementation */
public class Maths {

    /** A "close to zero" double epsilon value for use*/
    public static final double DBL_EPSILON = 2.220446049250313E-16d;

    /** A "close to zero" float epsilon value for use*/
    public static final float FLT_EPSILON = 1.1920928955078125E-7f;

	public static fVector3 toFloat(Vector3 v) {
		return new fVector3((float)v.x(), ((float)v.y()), ((float)v.z()));
	}
	public static void toFloat(Vector2 d, fVector2 f) {
		f.set((float)d.x(), (float)d.y());		
	}

	public static Vector3 toDouble(fVector3 v) {
		return new Vector3(v.getX(), v.getY(), v.getZ());		
	}
	public static void toDouble(fVector3 src, Vector3 target) {
		target.set(src.getX(), src.getY(), src.getZ());
	}
	public static void toFloat(Vector3 src, fVector3 target) {
		target.set((float)src.x(), (float)src.y(), (float)src.z());		
	}
	public static void toFloat(fVector3 src, Vector3 target) {
		target.set(src.getX(), src.getY(), src.getZ());		
	}
	public static fVector2 toFloat(Vector2 p) {
		return new fVector2((float)p.x(), (float)p.y());
	}
	public static void fromDouble(Vector3 vd, fVector3 vf) {
		vf.set((float)vd.x(), (float)vd.y(), (float)vd.z());		
	}
	public static fVector3 fromDouble(Vector3 d) {
		fVector3 v = new fVector3();
		fromDouble(d, v);
		return v;
	}

	fVector3 left = new fVector3(-1,0,0);

	/** @see: BillboardNode from jme */
	public static void rotateAngleTowardPoint(Vector3 srcPoint, Vector3 srcAngleModified, Vector3 targetPoint) {
		//TODO use MVector3 wrapper
		fVector3 look = new fVector3( (float)(targetPoint.x() - srcPoint.x()), 
										(float)(targetPoint.y() - srcPoint.y()), 
										 (float)(targetPoint.z() - srcPoint.z()));
		

		//	        look.set(camera.getLocation()).subtractLocal(worldTranslation);
		//	        // coopt left for our own purposes.
		//	        // The xzp vector is the projection of the look vector on the xz plane
		fVector3 xzp = new fVector3(look.getX(), 0, look.getZ());

		//	        // check for undefined rotation...
		if (xzp.equals(fVector3.ZERO)) return;
		
		look.normalizeLocal();
		xzp.normalizeLocal();
		double cosp = look.dot(xzp);

		//	        // compute the local orientation matrix for the billboard
		Matrix3f orient = new Matrix3f();
		orient.m00 = xzp.z;
		orient.m01 = xzp.x * -look.y;
		orient.m02 = xzp.x * cosp;
		orient.m10 = 0;
		orient.m11 = cosp;
		orient.m12 = look.y;
		orient.m20 = -xzp.x;
		orient.m21 = xzp.z * -look.y;
		orient.m22 = xzp.z * cosp;

		fQuaternion rot = new fQuaternion();
		double angles[] = new double[3];
		rot.apply(orient);
		rot.toAngles(angles);
		
		
		srcAngleModified.set(angles);

	}

	public static double goldenLesser(double d) {
		return d/1.61803;
	}
	public static double goldenGreater(double d) {
		return d*1.61803;
	}
	public static double getMagnitude(double sx, double sy) {
		return Math.sqrt( sx*sx + sy*sy );
	}

	public static double random(double min, double max) {
		return min + Math.random()*(max-min);
	}

	public static int random(int min, int max) {
		return min + (int)(Math.round(Math.random() * (max - min)));
	}

	public static short random(short min, short max) {
		return (short)(min + (short)(Math.round(Math.random() * (max - min))));
	}
	public static double sin(double x, double freq, double minA, double maxA) {
		double da = maxA - minA;
		return minA + (da) * Math.sin(freq * x)/2.0;
	}
}
