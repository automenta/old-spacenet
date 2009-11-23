package automenta.spacenet.space.geom3;

import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.vector.Vector3;



public class Curve3D extends Box {

	private ListVar<Vector3> points;
	private IntegerVar numEdges;
	private DoubleVar radius;
	private BooleanVar closed = new BooleanVar(false);
	private IntegerVar segmentation = new IntegerVar(2);

	public Curve3D(DoubleVar radius, int numEdges, Vector3... points) {
		this(new ListVar<Vector3>(points), radius, numEdges);
	}

	public Curve3D(ListVar<Vector3> points, DoubleVar radius, int numEdges) {
		super(new Vector3(), new Vector3(1,1,1), new Vector3());
		
		this.points = points;
		this.radius = radius;
		this.numEdges = new IntegerVar(numEdges);
	}

	public Curve3D(ListVar<Vector3> points, DoubleVar radius, int numEdges, int numSegments) {
		this(points, radius, numEdges);
		segmentation.set(numSegments);
	}

	public Curve3D(ListVar<Vector3> points, DoubleVar radius, int numEdges, int numSegments, boolean closed) {
		this(points, radius, numEdges, numSegments);
		this.closed.set(closed);
	}

	public Curve3D(double radius, int numEdges) {
		this(new ListVar<Vector3>(), new DoubleVar(radius), numEdges);
	}

	public ListVar<Vector3> getPoints() {
		return points;
	}

	public IntegerVar getNumEdges() {
		return numEdges;
	}

	public DoubleVar getRadius() {
		return radius;
	}

	public BooleanVar getClosed() {
		return closed;
	}

	/** how many extra segments to generate for each curve point (along its spline) */
	public IntegerVar getSegmentation() {
		return segmentation ;
	}

//	public Curve3D setCurveSurface(Surface s) {
//		add(s);
//		return this;
//	}

	public boolean isSpline() {
		return true;
	}



}
