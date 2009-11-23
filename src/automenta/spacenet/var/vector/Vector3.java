package automenta.spacenet.var.vector;

import java.util.List;

import org.apache.commons.collections15.list.FastArrayList;

import automenta.spacenet.Maths;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.jme.Matrix3f;
import automenta.spacenet.var.vector.jme.fVector3;



public class Vector3 extends ObjectVar<Double[]> {

	private List<IfVector3Changes> whenChanges;
	private double x;
	private double y;
	private double z;

	public Vector3(Vector3 copyFrom) {
		this(copyFrom.x(), copyFrom.y(), copyFrom.z());
	}
	
	public Vector3(double x, double y, double z) {
		super();
		set(x, y, z);
	}

	
	public Vector3() {
		this(0,0,0);
	}

	public Vector3(Vector3 a, Vector3 b, double mixAB) {
		this( mixAB * a.x()  + (1.0 - mixAB) * b.x(),  
				mixAB * a.y()  + (1.0 - mixAB) * b.y(),
				mixAB * a.z()  + (1.0 - mixAB) * b.z()
		);
		
	}

	public double x() { return x; }
	public double y() { return y; }
	public double z() { return z; }

	public Vector3 set(double x, double y, double z) {
		if (x == x())
			if (y == y())
				if (z == z())
					return this;
		
		double dx=0, dy=0, dz=0;
		if (whenChanges!=null) {
			dx = x - x();
			dy = y - y();
			dz = z - z();
		}

		this.x = x;
		this.y = y;
		this.z = z;
		
		whenChanged(dx,dy,dz);
		return this;
	}

	private void whenChanged(double dx, double dy, double dz) {
		if (whenChanges!=null) {			
			for (IfVector3Changes w : whenChanges) {
				w.afterVectorChanged(this, dx, dy, dz);
			}
		}		
	}

	public void start(IfVector3Changes w) {
		if (whenChanges==null) {
			whenChanges = newWhenChangesList();
		}
		whenChanges.add(w);
	}
	
	private List<IfVector3Changes> newWhenChangesList() {
		return new FastArrayList<IfVector3Changes>();
	}

	public boolean stop(IfVector3Changes w) {
		if (whenChanges!=null)
			return whenChanges.remove(w);
		return true;
	}

	@Override
	public String toString() {
		return "<" + x() + ", " + y() + ", " + z() + ">";
	}
	
	@Override
	public void dispose() {
		if (whenChanges!=null) {
			for (IfVector3Changes w : whenChanges) {
				w.stop();
			}
		}
		
		super.dispose();
	}

	public Vector3 multiply(double s) {		set(x() * s, y() * s, z() * s);	return this; }
	public Vector3 multiplyNew(double s) {		return new Vector3(x() * s, y() * s, z() * s);	}
	
	public Vector3 normalize() {
		double m = getMagnitude();
		if (m!=0)
			multiply(1.0 / getMagnitude());
		return this;
	}

	public double getMagnitude() {
		return Math.sqrt(getMagnitudeSquared());
	}

	public double getMagnitudeSquared() {
		double X = x();
		double Y = y();
		double Z = z();
		return (X * X) + (Y * Y) + (Z * Z);
	}

	public Vector3 set(Vector3 v) {
		return set(v.x(), v.y(), v.z());
	}
	
	@Override public boolean set(Double[] v) {
		set(v[0], v[1], v[2]);
		return true;
	}
	
	public Vector3 set(double[] v) {
		return set(v[0], v[1], v[2]);
	}
	
	public Vector3 set(float[] v) {
		return set(v[0], v[1], v[2]);
	}

	public Vector3 subtractNew(Vector3 removed) {
		return new Vector3(x() - removed.x(), y() - removed.y(), z() - removed.z()); 
	}

    /**
     * Converts a point from Cartesian coordinates (using positive Y as up) to
     * Spherical and stores the results in the store var. (Radius, Azimuth,
     * Polar)
     */
    public Vector3 toSphericalFromCartesian(Vector3 cartCoords) {
        if (cartCoords.x == 0)
            cartCoords.x = Maths.DBL_EPSILON;
        double sx, sy, sz;
        sx = Math.sqrt((cartCoords.x() * cartCoords.x())
                        + (cartCoords.y() * cartCoords.y())
                        + (cartCoords.z() * cartCoords.z()));
        sy = Math.atan(cartCoords.z() / cartCoords.x());
        if (cartCoords.x() < 0)
            sy += Math.PI;
        sz = Math.asin(cartCoords.y() / sx);
        set(sx, sy, sz);
        return this;
    }

    /**
     * Converts a point from Spherical coordinates to Cartesian (using positive
     * Y as up) and stores the results in the store var.  (r, theta, psi)
     */
    public Vector3 toCartesianFromSpherical(Vector3 sphereCoords) {
    	double sx, sy, sz;
    	
        sy = sphereCoords.x() * Math.sin(sphereCoords.z());
        double a = sphereCoords.x() * Math.cos(sphereCoords.z());
        sx = a * Math.cos(sphereCoords.y());
        sz = a * Math.sin(sphereCoords.y());

        set(sx, sy, sz);
        
        return this;
    }


	public Vector3 sphereToCartesian() {
		return toCartesianFromSpherical(this);
	}

	public Vector3 cartesianToSphere() {
		return toSphericalFromCartesian(this);
	}

	
	
	public Vector3 add(Vector3 v) {
		set(x() + v.x(), y() + v.y(), z() + v.z());
		return this;
	}

	public Vector3 add(Vector3 v, double a) {
		set(x() + a* v.x(), y() + a * v.y(), z() + a * v.z());		
		return this;
	}

	public Vector3 setZ(double newZ) {
		set(x(), y(), newZ);
		return this;
	}

	public Vector3 mixLocal(Vector3 v, double d) {
		return set( (1.0 - d) * x() + (d * v.x()), 
			 (1.0 - d) * y() + (d * v.y()),
			 (1.0 - d) * z() + (d * v.z())
		);		
	}

	public void add(double dx, double dy, double dz) {
		set(x() + dx, y() + dy, z() + dz);		
	}

	public void multiply(Vector3 v) {
		set(x() * v.x(), y() * v.y(), z() * v.z());		
	}

	public void setNormalized(double x, double y, double z) {
		set(x, y, z);
		normalize();
	}

	public void setX(double newX) {
		set(newX, y(), z());
	}
	public void setY(double newY) {
		set(x(), newY, z());
	}

	
	public Vector3 asNormalized() {
		Vector3 v = new Vector3(this);
		v.normalize();
		return v;
	}

	public static Vector3 newRandom(double dimMin, double dimMax) {
		return new Vector3(
				DoubleVar.random(dimMin, dimMax),
				DoubleVar.random(dimMin, dimMax),
				DoubleVar.random(dimMin, dimMax)
		);
	}

	public static Vector3 subtract(Vector3 a, Vector3 b) {
		return new Vector3(a.x() - b.x(), a.y() - b.y(), a.z() - b.z());
	}

	public void multiply(double d, double e, double f) {
		set( x() * d, y() * e, z() * f);	
	}

	public Vector3 subtract(Vector3 v) {
		set(x() - v.x(), y() - v.y(), z() - v.z());
		return this;
	}

	public Vector3 subtract(double x, double y, double z) {
		set(x() - x, y() - y, z() - z);
		return this;
	}

	public void add(double x, double y, double z, double m) {
		set(x() + m * x,
				y() + m * y,
					z() + m * z);										
	}

	public Vector3 addNew(double x, double y, double z) {
		return new Vector3(x() + x, y() + y, z() + z);
	}

	public Vector3 addNew(Vector3 v) {
		return addNew(v.x(), v.y(), v.z());
	}

	public double getDistanceSqr(Vector3 v) {
		double dx = x() - v.x();
		double dy = y() - v.y();
		double dz = z() - v.z();

		return dx*dx + dy*dy + dz*dz;
	}

	public double getDistance(Vector3 v) {
		
		return Math.sqrt( getDistanceSqr(v) );
	}

	public Vector3 cartesianToSpherical(double x, double y, double z) {
		set(x,y,z);
		return cartesianToSphere();		
	}

	public Vector3 zero() {
		set(0,0,0);
		return this;
	}

	public boolean isZero() {
		return (x()==0) && (y()==0) && (z()==0);
	}

	public void set(Vector3 a, Vector3 b, double interpolation) {
		double x = (a.x() * interpolation) + (b.x() * (1.0 - interpolation));
		double y = (a.y() * interpolation) + (b.y() * (1.0 - interpolation));
		double z = (a.z() * interpolation) + (b.z() * (1.0 - interpolation));

		set(x, y, z);
	}

	public static void rotateAboutAxis(Vector3 v, Vector3 axis, double angle) {
		fVector3 ax = new fVector3( axis.x(), axis.y(), axis.z() );
		
		fVector3 vv = new fVector3( v.x(), v.y(), v.z() );
		Matrix3f m = new Matrix3f();
		m.fromAngleAxis((float)(angle), ax );
		m.multLocal(vv);
		
		v.set( vv.getX(), vv.getY(), vv.getZ() );

	}

	public double getMaxRadius() {
		return Math.max(Math.abs(x()), Math.max(Math.abs(y()), Math.abs(z())));
	}
	public double getMinRadius() {
		return Math.min(Math.abs(x()), Math.min(Math.abs(y()), Math.abs(z())));
	}





	
	
}


