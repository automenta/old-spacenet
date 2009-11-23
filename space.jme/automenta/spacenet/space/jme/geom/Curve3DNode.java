package automenta.spacenet.space.jme.geom;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.jme.fMaths;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.number.IfIntegerChanges;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.Line;
import com.jme.util.geom.BufferUtils;


public class Curve3DNode extends ExtrusionNode {

	//TODO: instead of updateExtrusion whenever a point changes, set a flag that updateExtrusion on updateGeometry.  this is more efficient because it prevents calling updateExtrusion multiple times per frame
	
	private Curve3D curve;

	private Map<Vector3, IfVector3Changes> vectorWatches = new HashMap();

	protected boolean curveChanged;

	public Curve3DNode(Curve3D s) {
		super(s);
		this.curve = s;
	}

	@Override
	protected void afterStopJme(JmeNode parent) {
		for (IfVector3Changes v : vectorWatches.values()) {
			getCurve().remove(v);
		}
		vectorWatches.clear();

		super.afterStopJme(parent);				
	}
	
	@Override protected void startJme(JmeNode parent) {
		super.startJme(parent);

		getCurve().add(new IfIntegerChanges(curve.getSegmentation(), curve.getNumEdges()) {
			@Override public void afterValueChange(ObjectVar o, Integer previous, Integer next) {
				requireUpdateGeometry();
			}			
		});
		getCurve().add(new IfDoubleChanges(curve.getRadius()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar,  Double previous, Double next) {
				requireUpdateGeometry();
			}			
		});
		getCurve().add(new IfCollectionChanges<Vector3>(curve.getPoints()) {
 			@Override public synchronized void afterObjectsAdded(CollectionVar list, Vector3[] added) {				
				for (Vector3 v : added) {
					IfVector3Changes w = getCurve().add(new IfVector3Changes(v) {
						@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
							requireUpdateGeometry();
						}			
					});
					vectorWatches.put(v, w);
				}
				requireUpdateGeometry();
			}

			@Override public synchronized void afterObjectsRemoved(CollectionVar list, Vector3[] removed) {
				for (Vector3 v : removed) {
					IfVector3Changes w = vectorWatches.remove(v);
					if (w != null) {
						getCurve().remove(w);
					}
				}
				requireUpdateGeometry();
			}
			
		});
		

		
	}

	@Override public void updateGeometricState(float time, boolean initiator) {
		updateExtrusion();

		super.updateGeometricState(time, initiator);
	}

	@Override protected Line getCrossSection() {
		Line l = new Line();
		l.appendCircle(getCurve().getRadius().f(), 0, 0, getCurve().getNumEdges().get(), false);
		return l;
	}

    public static Line newArc(float radius, float angleStart, float angleEnd, float x, float y, int segments, boolean insideOut) {
    	Line line = new Line();
    	
        int requiredFloats = segments * 2 * 3;
        FloatBuffer verts = BufferUtils.ensureLargeEnough(line.getVertexBuffer(),
                requiredFloats);
        line.setVertexBuffer(verts);
        FloatBuffer normals = BufferUtils.ensureLargeEnough(line.getNormalBuffer(),
                requiredFloats);
        line.setNormalBuffer(normals);
        float angle = angleStart;
        float step = (angleEnd-angleStart) / segments;
        for (int i = 0; i < segments; i++) {
            float dx = FastMath.cos(insideOut ? -angle : angle) * radius;
            float dy = FastMath.sin(insideOut ? -angle : angle) * radius;
            if (i > 0) {
                verts.put(dx + x).put(dy + y).put(0);
                normals.put(dx).put(dy).put(0);
            }
            verts.put(dx + x).put(dy + y).put(0);
            normals.put(dx).put(dy).put(0);
            angle += step;
        }
//        verts.put(radius + x).put(y).put(0);
//        normals.put(radius).put(0).put(0);
        line.generateIndices();
        
        return line;
    }

	@Override protected List<Vector3f> getPath() {
		if (curve.getPoints().size() == 0)
			return null;
		List<Vector3f> l = new LinkedList();
		for (Vector3 v : curve.getPoints()) {
			if (v!=null)
				l.add(fMaths.toFloat(v));
		}
		return l;
	}


	public Curve3D getCurve() { return curve; }

	@Override
	protected boolean getIsClosed() {
		return getCurve().getClosed().get();
	}

	@Override
	protected int getSegments() {
		return getCurve().getSegmentation().get();
	}
	
	@Override
	protected boolean isSpline() {
		return getCurve().isSpline();
	}
}
