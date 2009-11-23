package automenta.spacenet.space.jme.geom;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.vector.Vector3;

import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;


public class BoxNode extends JmeNode  {

	public static final Vector3f unitBoxMin = new Vector3f(-0.5f,-0.5f,-0.5f);
	public static final Vector3f unitBoxMax = new Vector3f(0.5f,0.5f,0.5f);

	protected BoxNodeImpl visibleBox;

	final Quaternion rotation = new Quaternion();

	final protected Box box;
	
	public static class BoxNodeImpl extends com.jme.scene.shape.MultiFaceBox {

		public BoxNodeImpl(Vector3f boxMin, Vector3f boxMax) {
			super("B", boxMin, boxMax);

			//setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
			setModelBound(new OrientedBoundingBox());
			updateModelBound();
		}

		public BoxNodeImpl() {
			this(unitBoxMin, unitBoxMax);

		}

//		@Override public void updateWorldVectors() {
//			Vector3 position = box.getPosition();
//			Vector3 size = box.getSize();
//			Vector3 rot = box.getOrientation();
//
//			getLocalTranslation().set((float)position.x(), (float)position.y(), (float)position.z());
//			getLocalScale().set((float)size.x(), (float)size.y(), (float)size.z());
//
//			rotation.fromAngles((float)rot.x(), (float)rot.y(), (float)rot.z());
//			getLocalRotation().set(rotation);
//
//			super.updateWorldVectors();
//		}
	}

	public BoxNode(Box box) {
		super(box);

		this.box = box;		

		//Jme.setZOrderState(this);	
	}

	@Override protected void setSurface(Surface next) {
		
		if (isBoxSurfaced()) {
			if ((this.visibleBox!=null) && (next==null)) {
				detachBoxGeometry();
			}
			
			if ((next!=null) && (this.visibleBox == null)) {
				attachBoxGeometry();
			}
		}

		super.setSurface(next);

	}

	protected boolean isBoxSurfaced() { return true; }
	




	protected void attachBoxGeometry() {
		if (visibleBox==null) {
			this.visibleBox = new BoxNodeImpl();

			attachSpatials(visibleBox);							

		}
	}
	
	protected void detachBoxGeometry() {
		detachSpatials(visibleBox);
	}

	@Override protected boolean isPositionAdjustingTranslation() {
		return true;
	}
	
	@Override protected boolean isOrientationAdjustingRotation() {
		return true;
	}

	@Override protected boolean isSizeAdjustingScale() {
		return true;
	}


	public Vector3 getSize() { return box.getSize(); }
	public Vector3 getPosition() { return box.getPosition(); }
	public Vector3 getOrientation() {	return box.getOrientation();	}

	public Box getBox() { return box; }

}
