package automenta.spacenet.space.geom3;

import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;


/** 3d line, as cylinder */
public class Line3D extends Curve3D {

	private Vector3 b;
	private Vector3 a;
	private DoubleVar radius;
	private int numEdges;

	public Line3D(Vector3 a, Vector3 b, DoubleVar radius, int numEdges) {
		super(new ListVar<Vector3>(), radius, numEdges);
		this.a = a;
		this.b = b;
		getPoints().add(a);
		getPoints().add(b);
        
		this.radius = radius;
		this.numEdges = numEdges;
	}

	public Line3D(double ax, double ay, double az, double bx, double by, double bz, double radius, int numEdges) {
		this(new Vector3(ax,ay,az), new Vector3(bx, by, bz), new DoubleVar(radius), numEdges);
	}

//	public DoubleVar getRadius() { return radius; }
//	public int getNumEdges() { return numEdges; }
	
	public Vector3 getA() { return a; }
	public Vector3 getB() { return b; }

	public double getLength() {
		return getA().getDistance(getB());
	}

    @Override
    public BooleanVar getClosed() {
        return super.getClosed();
    }
	public boolean isSpline() {
		return false;
	}


}
