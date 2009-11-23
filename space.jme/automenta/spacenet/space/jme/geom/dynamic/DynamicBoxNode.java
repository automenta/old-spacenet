package automenta.spacenet.space.jme.geom.dynamic;


public class DynamicBoxNode /*extends BoxNode*/ {
//	private static final Logger logger = Logger.getLogger(DynamicBoxNode.class);
//
//	private PhyBox dynamicBox;
//	private PhysicsSpace jmePhysics;
//	private DynamicPhysicsNode dynamicNode;
//	float[] angles = new float[3];
//
//	private Vector3f linearVelocityF = new Vector3f();
//	private Vector3f tempV = new Vector3f();
//
//	private PhysicsUpdateCallback physicsCallback;
//
//	private PhyBox box;
//
//
//	public DynamicBoxNode(PhyBox box, final PhysicsSpace jmePhysics, final DynamicPhysicsNode dynamicNode) {
//		super(box);
//
//		this.box = box;
//
//		this.dynamicNode = dynamicNode;
//		this.dynamicBox = box;
//		this.jmePhysics = jmePhysics;
//
//		boxToPhysics(0);
//		initPhysics();
//
//	}
//
//
//
//	private void initPhysics() {
//		physicsCallback = new PhysicsUpdateCallback() {
//			@Override public void afterStep(PhysicsSpace space, float time) {
//				physicsToBox(time);
//			}
//
//			@Override public void beforeStep(PhysicsSpace space, float time) {
//				boxToPhysics(time);
//			}
//		};
//		jmePhysics.addToUpdateCallbacks(physicsCallback);
//
//	}
//
//	private void physicsToBox(float time) {
//
//		//physics -> box
//		Maths.toDouble(dynamicNode.getLocalTranslation(), getDynamicBox().getPosition() );
//		Maths.toDouble(dynamicNode.getLocalScale(), getDynamicBox().getSize() );
//
//		dynamicNode.getLocalRotation().toAngles(angles);
//		getDynamicBox().getOrientation().set(angles);
//
//		Maths.toDouble( dynamicNode.getLinearVelocity(tempV), getDynamicBox().getVelocity() );
//		Maths.toDouble( dynamicNode.getAngularVelocity(tempV), getDynamicBox().getAngularVelocity() );
//		Maths.toDouble( dynamicNode.getForce(tempV), getDynamicBox().getForce() );
//		Maths.toDouble( dynamicNode.getTorque(tempV), getDynamicBox().getTorque() );
//	}
//
//	protected void boxToPhysics(float dt) {
//
//		if (dynamicBox.shouldUpdateNextPhysicalGeometry()) {
//			dynamicBox.setUpdatePhysicalGeometry(false);
//
//			dynamicNode.updateWorldBound();
//
////			if (getWorldBound().getVolume() > 0) {
////				dynamicNode.generatePhysicsGeometry();
////			}
////			else {
////				return;
////			}
//
//		}
//
//
//		Vector3 size = getDynamicBox().getSize();
//		Maths.fromDouble(size, dynamicNode.getLocalScale() );
//
//		//box -> physics
//		Maths.fromDouble(getDynamicBox().getPosition(), dynamicNode.getLocalTranslation() );
//
//		Vector3 r = getDynamicBox().getOrientation();
//		angles[0] = (float) r.x();
//		angles[1] = (float) r.y();
//		angles[2] = (float) r.z();
//		dynamicNode.getLocalRotation().fromAngles(angles);
//
//		Maths.fromDouble( getDynamicBox().getVelocity(), linearVelocityF);
//		dynamicNode.setLinearVelocity(linearVelocityF );
//
//
//		Maths.fromDouble( getDynamicBox().getForce(), tempV);
//		dynamicNode.addForce(tempV);
//
//		//TODO:
//		//Maths.fromDouble( getDynamicBox().getTorque(), dynamicNode.getTorque(null) );
//		//Maths.fromDouble( getDynamicBox().getAngularVelocity(), dynamicNode.getAngularVelocity(null) );
//
//
//		dynamicNode.updateModelBound();
//
//		try {
//			dynamicNode.updateGeometricState(dt, true);
//		}
//		catch (Exception e) {
//			logger.error(e);
//		}
//
//		float mass = getDynamicBox().getMass().f();
//		if (dynamicNode.getMass()!=mass)
//			dynamicNode.setMass( mass );
//
//	}
//
////	@Override public int attachChild(Spatial child) {
////		int i = super.attachChild(child);
////		if (dynamicBox.isAutomaticPhysicalGeometry()) {
////			dynamicBox.setUpdatePhysicalGeometry(true);
////		}
////		return i;
////	}
////	@Override public int detachChild(Spatial child) {
////		int i = super.detachChild(child);
////		if (dynamicBox.isAutomaticPhysicalGeometry()) {
////			dynamicBox.setUpdatePhysicalGeometry(true);
////		}
////		return i;
////	}
//
//
////	//handle thru physics
////	@Override protected boolean isOrientationAdjustingRotation() {	return false;	}
////	@Override protected boolean isPositionAdjustingTranslation() {	return false; 	}
////	@Override protected boolean isSizeAdjustingScale() {	return false;	}
//
//	public PhyBox getDynamicBox() { return dynamicBox; }
//
//	public PhySpace getPhysics() { return getDynamicBox().getPhysics(); }
//	public DoubleVar getMass() { return getDynamicBox().getMass(); }
//	public BooleanVar getSolid() { return getDynamicBox().getSolid(); }
//
//
//
//	public DynamicPhysicsNode getPhysicsNode() {
//		return dynamicNode;
//	}
	
}
