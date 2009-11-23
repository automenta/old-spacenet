package automenta.spacenet.var.vector;

import java.util.List;

import org.apache.commons.collections15.list.FastArrayList;

import automenta.spacenet.var.ObjectVar;




//@IncompleteFeature("does not detect changes in getX() individual dimension components")
public class Vector2 extends ObjectVar {

	private List<IfVector2Changes> whenChanges;
	private double x;
	private double y;

	public Vector2(Vector2 copyFrom) {
		this(copyFrom.x(), copyFrom.y());
	}

	public Vector2(double x, double y) {
		super();
		set(x, y);
	}

	public Vector2() {
		this(0,0);
	}

	public double x() { return x; }
	public double y() { return y; }
	
	public double getMagnitude() {
		return Math.sqrt(getMagnitudeSquared());
	}
	public double getMagnitudeSquared() {
		double x = x();
		double y = y();
		return ( x*x + y*y );
	}

	public void set(double x, double y) {
		double dx = x - x();
		double dy = y - y();
		
		if ((dx == 0) && (dy == 0))
			return;
		
		this.x = x;
		this.y = y;
		
		if (whenChanges!=null) {
			for (IfVector2Changes w : whenChanges) {
				w.afterVectorChanged(this, dx, dy);
			}
		}
	}
	
	public boolean isInside(double x1, double y1, double x2, double y2) {
		double x = x();
		double y = y();
		return (x >= x1) && (x <= x2) && (y >= y1) && (y <= y2);
	}
	
	@Override public String toString() {
		return "<" + x() + ", " + y() + ">";
	}

	public void whenStarted(IfVector2Changes w) {
		if (this.whenChanges == null) {			
			whenChanges = newWhenChangesList();			
		}
		whenChanges.add(w);
	}
	
	private List<IfVector2Changes> newWhenChangesList() {
		return new FastArrayList<IfVector2Changes>();
	}

	public void whenStopped(IfVector2Changes w) {
		if (this.whenChanges == null) {
			return;
		}
		whenChanges.remove(w);
	}

	public double getAspect() {
		return y() / x();
	}

	public Vector2 normalizeNew() {
		Vector2 v = new Vector2(this);
		v.normalize();
		return v;
	}

	private void normalize() {
		multiply(1.0/getMagnitude());
	}

	public void multiply(double d) {
		set(x() * d, y() * d);
	}

	public void set(Vector2 v) {
		set(v.x(), v.y());		
	}

	public void subtract(Vector2 s) {
		set(x() - s.x(), y() - s.y());		
	}

	public Vector2 multiplyNew(double d) {
		return new Vector2(x() * d , y() * d);
	}

	public Vector2 add(double dx, double dy) {
		set(x() + dx, y() + dy);
		return this;		
	}

	public Vector2  setX(double newX) {
		set(newX, y());
		return this;
	}
	public Vector2  setY(double newY) {
		set(x(), newY);
		return this;
	}

	public double getDistance(Vector2 a) {
		double xs = (x() - a.x());
		double ys = (y() - a.y());
		return Math.sqrt( xs*xs + ys*ys );
	}

	public double getMaxRadius() {
		return Math.max(Math.abs(x()), Math.abs(y()));
	}
	public double getMinRadius() {
		return Math.min(Math.abs(x()), Math.abs(y()));
	}

	public double getAverageRadius() {
		return (getMinRadius() + getMaxRadius())/2.0;
	}
	
}
