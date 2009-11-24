package automenta.spacenet.space.jme.geom;

import java.util.List;

import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.JmeNode;
import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Line;
import com.jme.scene.shape.Extrusion;

abstract public class ExtrusionNode extends BoxNode {

    Extrusion ext;
    final Vector3f up = new Vector3f(0, 0, 1);

    public ExtrusionNode(Box box) {
        super(box);


    }

//	@Override
//	public int getDefaultRenderQueue() {
//		return Renderer.QUEUE_TRANSPARENT;
//	}
    @Override protected void startJme(JmeNode parent) {
        super.startJme(parent);

        updateExtrusion();

    }

    @Override
    protected void attachBoxGeometry() {
    }

    @Override
    protected void detachBoxGeometry() {
    }

    protected void updateExtrusion() {

        final Line shape = getCrossSection();
        final List<Vector3f> path = getPath();
        if ((shape == null) || (path == null)) {
            clear();
            return;
        }
        if (path.size() < 2) {
            clear();
            return;
        }

        final int segments = getSegments();
        final boolean closed = (path.size() == 2) ? false : getIsClosed();

        if (!closed) {
            //for unknown reason, when the path isn't closed, it needs a duplicate final point, or it won't show
            path.add(path.get(path.size() - 1));
        }


//		Jme.doLater(new Runnable() {
//			@Override public void run() {

        if (ext == null) {
            if (isSpline()) {
                ext = new Extrusion(shape, path, up) {

                    @Override public void updateGeometry(Line shape, List<Vector3f> points, int oldSegments, boolean oldClosed, Vector3f oldUp) {
                        super.updateGeometry(shape, points, segments, closed, up);
                    }

                    @Override public void updateGeometry(Line shape, List<Vector3f> points, int oldSegments, Vector3f oldUp) {
                        super.updateGeometry(shape, points, segments, up);
                    }

                    @Override public void updateGeometry(Line shape, List<Vector3f> path, boolean oldClosed, Vector3f oldUp) {
                        super.updateGeometry(shape, path, closed, up);
                    }

                    @Override public void updateGeometry(Line shape, List<Vector3f> path, Vector3f oldUp) {
                        super.updateGeometry(shape, path, segments, closed, up);
                    }
                };
            } else {
                ext = new Extrusion(shape, path, up);
            }
            
            //ext.setModelBound(new OrientedBoundingBox());
            ext.setModelBound(new BoundingBox());

            attachSpatials(ext);
        } else {
            if (isSpline()) {
                ext.updateGeometry(shape, path, segments, closed, up);
            } else {
                ext.updateGeometry(shape, path, up);
            }
        }

        //ext.updateGeometricState(0.0f, true);

        ext.updateGeometricState(0.0f, true);
        ext.updateModelBound();

        //ext.updateRenderState();
//			}
//		});

    }

    private void clear() {
        if (ext != null) {
            detachSpatials(ext);
            ext = null;
        }
    }

    abstract protected boolean isSpline();

    abstract protected boolean getIsClosed();

    abstract protected int getSegments();

    abstract protected List<Vector3f> getPath();

    abstract protected Line getCrossSection();
}
