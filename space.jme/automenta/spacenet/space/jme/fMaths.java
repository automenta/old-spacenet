package automenta.spacenet.space.jme;

import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.Matrix3f;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

public class fMaths {

	public static Vector3f toFloat(Vector3 v) {
		return new Vector3f((float)v.x(), ((float)v.y()), ((float)v.z()));
	}
	public static void toFloat(Vector2 d, Vector2f f) {
		f.set((float)d.x(), (float)d.y());		
	}

	public static Vector3 toDouble(Vector3f v) {
		return new Vector3(v.getX(), v.getY(), v.getZ());		
	}
	public static void toDouble(Vector3f src, Vector3 target) {
		target.set(src.getX(), src.getY(), src.getZ());
	}
	public static void toFloat(Vector3 src, Vector3f target) {
		target.set((float)src.x(), (float)src.y(), (float)src.z());		
	}
	public static void toFloat(Vector3f src, Vector3 target) {
		target.set(src.getX(), src.getY(), src.getZ());		
	}
	public static Vector2f toFloat(Vector2 p) {
		return new Vector2f((float)p.x(), (float)p.y());
	}
	public static void fromDouble(Vector3 vd, Vector3f vf) {
		vf.set((float)vd.x(), (float)vd.y(), (float)vd.z());		
	}
	public static Vector3f fromDouble(Vector3 d) {
		Vector3f v = new Vector3f();
		fromDouble(d, v);
		return v;
	}

	/** @see: BillboardNode from jme */
	public static void rotateAngleTowardPoint(Vector3f srcPoint, Vector3f srcAngleModified, Vector3f targetPoint) {
		//TODO use MVector3 wrapper
		fVector3 look = new fVector3( (targetPoint.getX() - srcPoint.getX()), 
										(targetPoint.getY() - srcPoint.getY()), 
										 (targetPoint.getZ() - srcPoint.getZ()));
		

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
		
		
		srcAngleModified.set((float)angles[0], (float)angles[1], (float)angles[2]);

	}

}
