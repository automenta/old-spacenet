package automenta.spacenet.space.geom3;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.Vector3;

/** a sphere whose scale is managed by a radius number */
public class Sphere extends Spheroid implements Starts, Stops {

	private DoubleVar radius;



	public Sphere(Vector3 position, double radius) {
		super(position, new Vector3(radius, radius, radius), new Vector3());
		this.radius = new DoubleVar(radius);
	}
	
	public Sphere(double x, double y, double z, double r) {
		this(new Vector3(x, y, z), r);
	}

	public Sphere() {
		this(0,0,0,1);
	}

	@Override public void start() {
		add(new IfDoubleChanges(radius) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				double r = radius.d();
				getSize().set(r, r, r);
			}			
		});
	}
	
	@Override public void stop() {
		
	}
	
	
	
	public DoubleVar getRadius() {
		double r = Math.max(getSize().x(), Math.max(getSize().y(), getSize().z()));
		radius.set(r);
		return radius ;
	}
	
}
