package automenta.spacenet.space.jme.geom;


import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.number.DoubleVar;

import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.Quaternion;


public class RectNode extends JmeNode {

	final private Rect rect;
	final private Quaternion rotation = new Quaternion();
	private QuadNodeImpl quadNode;

	class QuadNodeImpl extends com.jme.scene.shape.Quad {

		public QuadNodeImpl() {
			super("Q", 1.0f, 1.0f);

			setModelBound(new OrientedBoundingBox());
			updateModelBound();
		}
		
	}
	
	public RectNode(Rect q) {
		super(q);

		this.rect = q;	
		
	}


	@Override protected void setSurface(Surface s) {
		if (isRectSurfaced()) {
			if ((this.quadNode!=null) && (s==null)) {
				detachRectGeometry();
			}
			
			if ((s!=null) && (this.quadNode == null)) {
				attachRectGeometry();
			}


		}

		super.setSurface(s);
	}

	
	protected void attachRectGeometry() {
		if (this.quadNode == null) {
			this.quadNode = new QuadNodeImpl();
			//this.quadNode = new BoxNodeImpl(new Vector3f(-0.5f,-0.5f,-0.01f), new Vector3f(0.5f,0.5f,0.0f));
			attachSpatials(quadNode);
		}
	}
	
	protected void detachRectGeometry() {
		if (quadNode!=null) {
			detachSpatials(quadNode);
			quadNode = null;
		}
	}
	
	public boolean isRectSurfaced() {
		return true;
	}


	public Rect getRect() { return rect; }

	
	



	@Override protected boolean isSizeAdjustingScale() {		return true;	}

	@Override protected boolean isPositionAdjustingTranslation() {	return true;	}

	@Override protected boolean isOrientationAdjustingRotation() {		return true;	}

	
	private DoubleVar getAspectXY() {
		return getRect().getAspectXY();
	}



}
