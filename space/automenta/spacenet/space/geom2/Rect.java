package automenta.spacenet.space.geom2;

import org.apache.log4j.Logger;

import automenta.spacenet.Maths;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.HasSurface;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video2d.HasSize2;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;

public class Rect extends Space implements HasPosition3, HasOrientation, HasSize2, HasSurface {

    public static enum RectPosition {

        N, S, E, W, NW, NE, SE, SW, Center
    }
    private static final Logger logger = Logger.getLogger(Rect.class);

    public final Vector3 position;
    public final Vector2 size;
    public final Vector3 orientation;
    public final DoubleVar aspectXY = new DoubleVar(0);
    public final Vector3 absolutePosition = new Vector3();
    public final Vector2 absoluteSize = new Vector2();
    public final Vector3 absoluteOrientation = new Vector3();
    public final Vector3 nextAbsolutePosition = new Vector3();
    public final ObjectVar<Surface> surface = new ObjectVar<Surface>(null);
    public final Vector2 alignment = new Vector2(0, 0);

    public Rect() {
        this(0, 0, 1, 1);
    }

    public Rect(Color c) {
        this(new ColorSurface(c));
    }

    public Rect(double w, double h) {
        this(0, 0, w, h);
    }

    public Rect(double x, double y, double w, double h) {
        this(x, y, 0, w, h);
    }

    public Rect(double x, double y, double z, double w, double h) {
        this(new Vector3(x, y, z), new Vector2(w, h), new Vector3());
    }

    public Rect(Rect r) {
        this(r.getPosition(), r.getSize(), r.getOrientation());
    }

    public Rect(Surface s) {
        this();
        getSurface().set(s);
    }

    public Rect(Vector3 position, Vector2 size, Vector3 orientation) {
        super();
        if (position == null) {
            position = new Vector3();
        }
        if (size == null) {
            size = new Vector2();
        }
        if (orientation == null) {
            orientation = new Vector3();
        }

        this.position = position;
        this.size = size;
        this.orientation = orientation;

        tangible(true);
    }

    public void align(double px, double py) {
        getAlignment().set(px, py);
    }

    public Rect aspect(double x, double y) {
        getAspectXY().set(y / x);
        return this;
    }

    public Rect aspect(double aspect) {
        getAspectXY().set(aspect);
        return this;
    }

    public Rect color(Color c) {
        return surface(new ColorSurface(c));
    }

    @Override public void dispose() {
    }

    /**
     * TODO currently does NOT adjust for global orientation, so only works if remains un-rotated relative to something other than the root space
     * @param targetPosition
     */
    public void facePosition(Vector3 targetPosition) {
        Maths.rotateAngleTowardPoint(getPosition(), getOrientation(), targetPosition);
    }

    public Vector3 getAbsoluteNormal() {
        fVector3 axisStore = new fVector3();
        Vector3 absOrientation = getAbsoluteOrientation();
        float[] angles = new float[]{(float) absOrientation.x(), (float) absOrientation.y(), (float) absOrientation.z()};
        fQuaternion q = new fQuaternion(angles);
        q.toAngleAxis(axisStore);

        //swap X and Z - why is this necessary?
        {
            axisStore.set(axisStore.getZ(), axisStore.getY(), axisStore.getX());
        }

        return Maths.toDouble(axisStore);
    }

    @Override public Vector3 getAbsoluteOrientation() {
        return absoluteOrientation;
    }

    public Vector3 getAbsolutePosition() {
        return absolutePosition;
    }

    @Override public Vector3 getAbsolutePosition(double ux, double uy, Vector3 result) {
        Vector3 o = getAbsoluteOrientation();

        fQuaternion q = new fQuaternion();
        fVector3[] axis = new fVector3[3];

        q.fromAngles((float) o.x(), (float) o.y(), (float) o.z());
        q.toAxes(axis);

        result.set(axis[0].getX(), axis[0].getY(), axis[0].getZ());
        result.multiply(ux * getAbsoluteSize().x());
        result.add(axis[1].getX(), axis[1].getY(), axis[1].getZ(), uy * getAbsoluteSize().y());

        result.add(getAbsolutePosition());

        return result;
    }

    @Override public Vector2 getAbsoluteSize() {
        return absoluteSize;
    }

    public Vector2 getAlignment() {
        return alignment;
    }

    public double getAspect() {
        return getSize().y() / getSize().x();
    }

    public DoubleVar getAspectXY() {
        return aspectXY;
    }

    public void getLocalPosition(Vector3 absolute, Vector2 result) {
    }

    public Vector3 getNextAbsolutePosition() {
        return nextAbsolutePosition;
    }

    public Vector3 getOrientation() {
        return orientation;
    }

    private double getPaddingX() {
        return 0;
    }

    private double getPaddingY() {
        return 0;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    @Override public ObjectVar<Surface> getSurface() {
        return surface;
    }

    public double h() {
        return getSize().y();
    }

    /** positions something aligned with a specific edge, towards the inside of this rect */
    public Rect inside(RectPosition n, double w, double h) {
        return inside(n, w, h, 0);
    }

//	public Rect grid(Class superClass) {
//		stopArrangement();
//		arrangement = add(new ArrangeGrid(this,superClass));
//		return this;
//	}
//
//	protected void stopArrangement() {
//		if (arrangement!=null) {
//			arrangement.stop();
//			arrangement = null;
//		}		
//	}
    /**
     *
     * positions something aligned with a specific edge, towards the inside of this rect
     * @param pos	anchor point
     * @param w     relative width, in range (-1=left, .. +1=right)
     * @param h     relative height, in range (-1=bottom, .. +1=top)
     * @return
     */
    public Rect inside(RectPosition pos, double w, double h, double dz) {
        scale(w, h);

        w /= 2;
        h /= 2;
        double limX = 0.5 - getPaddingX();
        double limY = 0.5 - getPaddingY();
        if (pos == RectPosition.NW) {
            move(-limX + w, limY - h, dz);
        } else if (pos == RectPosition.SW) {
            move(-limX + w, -limY + h, dz);
        } else if (pos == RectPosition.NE) {
            move(limX - w, limY - h, dz);
        } else if (pos == RectPosition.SE) {
            move(limX - w, -limY + h, dz);
        } else if (pos == RectPosition.N) {
            move(0, limY - h, dz);
        } else if (pos == RectPosition.S) {
            move(0, -limY + h, dz);
        } else if (pos == RectPosition.E) {
            move(limX - w, 0, dz);
        } else if (pos == RectPosition.W) {
            move(-limX + w, 0, dz);
        } else if (pos == RectPosition.Center) {
            move(0, 0, dz);
        } else {
            logger.warn(this + " positioned at " + pos + " not impl yet");
        }

        return this;
    }

    public Rect move(double x, double y) {
        getPosition().set(x, y, getPosition().z());
        return this;
    }

    public Rect move(double x, double y, double z) {
        getPosition().set(x, y, z);
        return this;
    }

    public Rect move(Vector3 pos) {
        getPosition().set(pos);
        return this;
    }

    public Rect moveDelta(double dx, double dy, double dz) {
        getPosition().add(dx, dy, dz);
        return this;
    }

    public Rect moveDZ(double dz) {
        return moveDelta(0, 0, dz);
    }

    /** TODO find more appropriate parameter names */
    public Rect orient(double r1, double r2, double r3) {
        getOrientation().set(r1, r2, r3);
        return this;
    }

    public Rect scale(double d) {
        return scale(d, d);
    }

    public Rect scale(double x, double y) {
        getSize().set(x, y);
        return this;
    }

    public void setNextAbsolutePosition(double x, double y, double z) {
        nextAbsolutePosition.set(x, y, z);
    }

    public Rect size(double x, double y) {
        getAspectXY().set(y / x);
        return scale(x, y);
    }

    public Rect span(double ulX, double ulY, double brX, double brY) {
        return span(ulX, ulY, brX, brY, false);
    }

    public Rect span(double ulX, double ulY, double brX, double brY, boolean aspect) {
        double w = Math.abs(brX - ulX);
        double h = Math.abs(brY - ulY);
        double cx = 0.5 * (ulX + brX);
        double cy = 0.5 * (ulY + brY);
        move(cx, cy);

        if (aspect) {
            size(w, h);
        } else {
            scale(w, h);
        }

        return this;
    }

    public Rect surface(Surface s) {
        getSurface().set(s);
        return this;
    }

    public double w() {
        return getSize().x();
    }

    public double x() {
        return getPosition().x();
    }

    public double y() {
        return getPosition().y();
    }

    public double z() {
        return getPosition().z();
    }

    @Override public void getAbsoluteNormal(Vector3 normal) {
        getAbsoluteNormal(getAbsoluteOrientation(), normal);
    }

    public static void getAbsoluteNormal(Vector3 ori, Vector3 normal) {
        fQuaternion q = new fQuaternion();
        fVector3 n = new fVector3(0, 0, 1);
        q.fromAngles(new float[]{(float) ori.x(), (float) ori.y(), (float) ori.z()});
        q.multLocal(n);
        normal.set(n.x, n.y, n.z);
    }

    /** TODO not implemented yet */
    @Override public Vector3 getNextAbsoluteOrientation() {
        // TODO Auto-generated method stub
        return null;
    }

    /** TODO not implemented yet */
    @Override public void setNextAbsoluteOrientation(double r1, double r2, double r3) {
        // TODO Auto-generated method stub
    }

    public void setZ(double newZ) {
        getPosition().setZ(newZ);
    }
}
