package automenta.spacenet.space.geom3;

import org.apache.log4j.Logger;

import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.HasSize3;
import automenta.spacenet.space.HasSurface;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.Rect.RectPosition;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;


public class Box extends Space implements HasSize3, HasPosition3, HasOrientation, HasSurface {
	private static final Logger logger = Logger.getLogger(Box.class);
	
	public static enum BoxPosition {
		FrontNW, FrontSW, FrontNE, FrontSE, FrontCenter, FrontS, FrontE, FrontW, FrontN
	};
	
	public final Vector3 position;
	public final Vector3 size;
	public final Vector3 orientation;

	public final Vector3 absoluteSize = new Vector3();
	public final Vector3 absolutePosition = new Vector3();
	public final Vector3 absoluteOrientation = new Vector3();
	
	public final DoubleVar aspectXY = new DoubleVar(0);
	public final ObjectVar<Surface> surface = new ObjectVar<Surface>(null);
	public final Vector3 nextAbsolutePosition = new Vector3();
	public final Vector3 nextAbsoluteSize = new Vector3();
	public final Vector3 nextAbsoluteOrientation = new Vector3();

	public Box() {
		this(new Vector3(), new Vector3(1,1,1), new Vector3());
	}

	public Box(Color surfaceColor) {
		this(new ColorSurface(surfaceColor));		
	}

	public Box(Surface s) {
		this();
		getSurface().set(s);
	}

	public Box(Vector3 position, Vector3 size, Vector3 orientation) {
		super();
		
		if (position == null)		position = new Vector3();
		if (size == null)			size = new Vector3();
		if (orientation == null)	orientation = new Vector3();

		this.position = position;
		this.size = size;
		this.orientation = orientation;
		
		tangible(true);
	}


	public Box(double w, double h, double d) {
		this(new Vector3(), new Vector3(w, h, d), new Vector3());
	}



	public Box(Vector3 pos, Vector3 size) {
		this(pos, size, new Vector3());
	}


	public Box(double x, double y, double z, double w, double h, double d) {
		this(new Vector3(x,y,z), new Vector3(w,h,d), new Vector3());
	}

	public Box(Box b) {
		this(b.getPosition(), b.getSize(), b.getOrientation());
	}

	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getSize() {
		return size;
	}
	
	public Vector3 getOrientation() {
		return orientation;
	}

	public Box scale(double x, double  y, double z) {
		getSize().set(x,y,z);
		return this;
	}
	public Box move(double x, double y, double z) {
		getPosition().set(x,y,z);
		return this;
	}
	public Box moveDelta(double x, double y, double z) {
		getPosition().add(x,y,z);
		return this;
	}

	public Box rotate(double r1, double r2, double r3) {
		getOrientation().set(r1, r2, r3);
		return this;
	}

	@Override public Vector3 getAbsoluteSize() {
		return absoluteSize;
	}

	@Override public Vector3 getAbsolutePosition() {
		return absolutePosition;
	}

	@Override public Vector3 getAbsoluteOrientation() {
		return absoluteOrientation;
	}

	public Box scale(double m) {
		return scale(m,m,m);
	}
	
	public Box move(Vector3 v) {
		getPosition().set(v.x(), v.y(), v.z());
		return this;
	}

	public Box move(double x, double y) {
		return move(x,y,getPosition().z());
	}
	@Deprecated private Box moveUnit(double x, double y) {
		return move(x/2,y/2,getPosition().z());
	}

	
	public Box scale(double x, double y) {
		return scale(x, y, Math.max(x,y));
	}

	public Box size(double x, double y) {
		getAspectXY().set(y / x);
		return scale(x, y);
	}
	

	/**
	 * 
	 * @param pos
	 * @param w     relative width, in range (-1=left, .. +1=right)
	 * @param h     relative height, in range (-1=bottom, .. +1=top)
	 * @return
	 */
	public Space at(BoxPosition pos, double w, double h) {
		size(w, h);

		w/=2;
		h/=2;
		double limX = 0.5 - getPaddingX();
		double limY = 0.5 - getPaddingY();
		if (pos == BoxPosition.FrontNW)			move(-limX + w, limY - h);
		else if (pos == BoxPosition.FrontSW)	move(-limX + w, -limY + h);
		else if (pos == BoxPosition.FrontNE)	move(limX - w, limY - h);
		else if (pos == BoxPosition.FrontSE)	move(limX - w, -limY + h);
		else if (pos == BoxPosition.FrontN)	move(0, limY-h);
		else if (pos == BoxPosition.FrontS)	move(0, -limY+h);
		else if (pos == BoxPosition.FrontE)	move(limX-w, 0);
		else if (pos == BoxPosition.FrontW)	move(-limX+w, 0);
		else if (pos == BoxPosition.FrontCenter)	move(0, 0);
		else {
			logger.warn(this + " positioned at " + pos + " not impl yet");
		}
		
		return this;
	}

	private double getPaddingY() {
		return 0;
	}

	private double getPaddingX() {
		return 0;
	}

	public DoubleVar getAspectXY() {
		return aspectXY;
	}

	public Box aspect(double ax, double ay) {
		getAspectXY().set(ay/ax);
		return this;
	}

	@Override public ObjectVar<Surface> getSurface() {
		return surface ;
	}

	public Box surface(Surface surface) {
		getSurface().set(surface);
		return this;
	}

	public Box color(Color c) {
		return surface(new ColorSurface(c));
	}


	/** warning: may not work for sub-spaces scaled differently from root */
	public void setNextAbsoluteSize(double x, double y, double z) {
		nextAbsoluteSize.set(x,y,z);		
	}

	public void setNextAbsolutePosition(double x, double y, double z) {
		nextAbsolutePosition.set(x,y,z);
	}

	/** not implemented yet (TODO) */
	public void setNextAbsoluteOrientation(double x, double y, double z) {
		nextAbsoluteOrientation.set(x,y,z);
	}
	
	@Override public Vector3 getNextAbsoluteOrientation() {
		return nextAbsoluteOrientation;
	}
	
	@Override public Vector3 getNextAbsolutePosition() {
		return nextAbsolutePosition;
	}
	
//	/** warning: may not work for sub-spaces scaled differently from root */
//	public void setNextAbsolutePosition(Vector3 p) {
//		nextAbsolutePosition = p;
//	}
	
	public Vector3 getAbsolutePosition(double ux, double uy, Vector3 result) {
		Vector3 o = getAbsoluteOrientation();

		fQuaternion q = new fQuaternion();
		fVector3[] axis = new fVector3[3];

		q.fromAngles(o.x(), o.y(), o.z());		
		q.toAxes(axis);


		result.set(axis[0].getX(), axis[0].getY(), axis[0].getZ());
		result.multiply(ux * getAbsoluteSize().x());
		result.add(axis[1].getX(), axis[1].getY(), axis[1].getZ(), uy * getAbsoluteSize().y());
		
		result.add(getAbsolutePosition());
				
		return result;
	}

	public Box span(double ulX, double ulY, double brX, double brY) {
		return span(ulX, ulY, brX, brY, false);
	}
	
	public Box span(double ulX, double ulY, double brX, double brY, boolean aspect) {
		double w = Math.abs(brX - ulX);
		double h = Math.abs(brY - ulY);
		double cx = 0.5 * (ulX + brX);
		double cy = 0.5 * (ulY + brY);
		
		move(cx, cy);
		
		if (aspect)
			size(w, h);
		else
			scale(w, h);
		
		return this;
	}

	public Box moveDZ(double dz) {
		return moveDelta(0,0,dz);
	}


	/* positions something aligned with a specific edge, towards the inside of this box */
	public Box inside(RectPosition n, double w, double h) {
		return inside(n, w, h, 0);		
	}

	/**
	 * 
	 * positions something aligned with a specific edge, towards the inside of this box 
	 * @param pos	anchor point
	 * @param w     relative width, in range (-1=left, .. +1=right)
	 * @param h     relative height, in range (-1=bottom, .. +1=top)
	 * @return
	 */
	public Box inside(RectPosition pos, double w, double h, double dz) {
		scale(w, h);

		w/=2;
		h/=2;
		double limX = 0.5 - getPaddingX();
		double limY = 0.5 - getPaddingY();
		if (pos == RectPosition.NW)			move(-limX + w, limY - h, dz);
		else if (pos == RectPosition.SW)	move(-limX + w, -limY + h, dz);
		else if (pos == RectPosition.NE)	move(limX - w, limY - h, dz);
		else if (pos == RectPosition.SE)	move(limX - w, -limY + h, dz);
		else if (pos == RectPosition.N)	move(0, limY-h, dz);
		else if (pos == RectPosition.S)	move(0, -limY+h, dz);
		else if (pos == RectPosition.E)	move(limX-w, 0, dz);
		else if (pos == RectPosition.W)	move(-limX+w, 0, dz);
		else if (pos == RectPosition.Center)	move(0, 0, dz);
		else {
			logger.warn(this + " positioned at " + pos + " not impl yet");
		}
		
		return this;
	}

	public Vector3 getNextAbsoluteSize() {
		return nextAbsoluteSize;
	}

	public Box orient(double r1, double r2, double r3) {
		getOrientation().set(r1, r2, r3);
		return this;
	}

	@Override public void getAbsoluteNormal(Vector3 normal) {
		Rect.getAbsoluteNormal(getAbsoluteOrientation(), normal);
	}
	

}
