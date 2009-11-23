package automenta.spacenet.space.dynamic.physics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import javolution.util.FastList;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;

import com.bulletphysics.collision.broadphase.AxisSweep3_32;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.SimpleDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.Generic6DofConstraint;
import com.bulletphysics.dynamics.constraintsolver.Point2PointConstraint;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SliderConstraint;
import com.bulletphysics.dynamics.constraintsolver.TypedConstraint;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class PhySpace extends Repeat<Scope> {
	private static final Logger logger = Logger.getLogger(PhySpace.class);
	
	private DefaultCollisionConfiguration collisionConfiguration;
	private CollisionDispatcher dispatcher;
	private SequentialImpulseConstraintSolver solver;
	private AxisSweep3_32 overlappingPairCache;
	private DynamicsWorld space;

	// maximum number of objects
	private static final int MAX_PROXIES = 256;

	// keep the collision shapes, for deletion/cleanup
	private MapVar<PhyBox, RigidBody> boxes = new MapVar();
	private Map<Constrain6DoF, Generic6DofConstraint> constrain6Dof = new HashMap();
	private Map<ConstrainSlider, SliderConstraint> constrainSlider = new HashMap();

	private double[] f3 = new double[3];

	private int subSteps = 10;


	public PhySpace() {
		this(false, 100, 100, 100);
	}

	public PhySpace(boolean allowJoints, double w, double h, double d) {
		super();

		// collision configuration contains default setup for memory, collision setup
		collisionConfiguration = new DefaultCollisionConfiguration();

		// use the default collision dispatcher. For parallel processing you can use a diffent dispatcher (see Extras/BulletMultiThreaded)
		dispatcher = new CollisionDispatcher(collisionConfiguration);

		// the maximum size of the collision world. Make sure objects stay within these boundaries
		// Don't make the world AABB size too large, it will harm simulation quality and performance
		Vector3f worldAabbMin = new Vector3f((float)-w/2.0f, (float)-h/2.0f, (float)-d/2.0f);
		Vector3f worldAabbMax = new Vector3f((float)w/2.0f, (float)h/2.0f, (float)d/2.0f);
		
		overlappingPairCache = new AxisSweep3_32(worldAabbMin,worldAabbMax,MAX_PROXIES);		
			//	overlappingPairCache = new SimpleBroadphase(MAX_PROXIES);

		// the default constraint solver. For parallel processing you can use a different solver (see Extras/BulletMultiThreaded)
		solver = new SequentialImpulseConstraintSolver();		
		
		// TODO: needed for SimpleDynamicsWorld
		//solver.setSolverMode(solver.get getSolverMode() & ~SolverMode.SOLVER_CACHE_FRIENDLY.getMask());
		
		
		if (allowJoints) {
			space = new DiscreteDynamicsWorld(dispatcher, overlappingPairCache, solver, collisionConfiguration);
		}
		else {
			space = new SimpleDynamicsWorld(dispatcher, overlappingPairCache, solver, collisionConfiguration);
		}
	}

	
//	private void addBlocks() {
//		// create a few dynamic rigidbodies
//		// Re-using the same collision is better for memory usage and performance
//
//		CollisionShape boxShape = new BoxShape(new Vector3f(1, 1, 1));
//		//CollisionShape colShape = new SphereShape(1f);
//		shapes.add(boxShape);
//
//		// Create Dynamic Objects
//		Transform startTransform = new Transform();
//		startTransform.setIdentity();
//
//		float mass = 1f;
//
//		// rigidbody is dynamic if and only if mass is non zero, otherwise static
//		boolean isDynamic = true;
//
//		Vector3f localInertia = new Vector3f(0, 0, 0);
//		if (isDynamic) {
//			boxShape.calculateLocalInertia(mass, localInertia);
//		}
//
//		float start_x = START_POS_X - ARRAY_SIZE_X / 2;
//		float start_y = START_POS_Y;
//		float start_z = START_POS_Z - ARRAY_SIZE_Z / 2;
//
//		for (int k = 0; k < ARRAY_SIZE_Y; k++) {
//			for (int i = 0; i < ARRAY_SIZE_X; i++) {
//				for (int j = 0; j < ARRAY_SIZE_Z; j++) {
//					startTransform.origin.set(
//							2f * i + start_x,
//							2f * k + start_y,
//							2f * j + start_z);
//
//					// using motionstate is recommended, it provides interpolation capabilities, and only synchronizes 'active' objects
//					DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
//					RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState, boxShape, localInertia);
//	RigidBody body = new RigidBody(rbInfo);
//
//					space.addRigidBody(body);
//				}
//			}
//		}
//	}

	@Override public double repeat(double t, double dt) {
		for (RigidBody rb : bodiesToRemove) {
			space.removeRigidBody(rb);
		}
		bodiesToRemove.clear();
		
		
		for (RigidBody rb : bodiesToAdd) {
			space.addRigidBody(rb);
		}
		bodiesToAdd.clear();

		
		nodesToPhysics(dt);
		
		if (space != null) {
			float dft = (float)(dt * getTimeScale().d());
			space.stepSimulation(dft, getSubsteps(), dft/4.0f);
			
			//space.stepSimulation((float)(dt * getTimeScale().d()), getSubsteps());
		}
		
		physicsToNodes();

		return getUpdatePeriod().d();
	}


	private int getSubsteps() {
		return subSteps;
	}


	private Vector3f v = new Vector3f();
	private Quat4f q = new Quat4f();
	Transform t = new Transform();
	private DoubleVar timeScale = new DoubleVar(1.0);
	private Vector3 gravity = new Vector3();
	private fQuaternion qu = new fQuaternion();
	private List<RigidBody> bodiesToAdd = new FastList();
	private List<RigidBody> bodiesToRemove = new FastList();
	private DoubleVar updatePeriod = new DoubleVar(0);
	

	private void physicsToNodes() {
		for (PhyBox box : boxes.keySet()) {
			if (!box.isDynamic().b())
				continue;
			
			RigidBody body = boxes.get(box);

			//body.getWorldTransform(t);
			body.getCenterOfMassTransform(t);
			
			toDouble(t.origin, box.getPosition());

//			body.getCollisionShape().getLocalScaling(v);
//			toDouble(v, box.getSize());
			
			t.getRotation(q);
			qu.set(q.x, q.y, q.z, q.w);
			
			qu.toAngles( f3 );
			box.getOrientation().set(f3[0], f3[1], f3[2]);
			

			body.getAngularVelocity(v);
			toDouble(v, box.getAngularVelocity());
			
			body.getLinearVelocity(v);
			toDouble(v, box.getVelocity());
		}
		
	}
	

	private void nodesToPhysics(double dt) {
		toFloat(gravity, v);
		space.setGravity(v);
		
		for (Constrain6DoF c : constrain6Dof.keySet()) {
			Generic6DofConstraint j = constrain6Dof.get(c);
			
			float maxLength = c.getMaxLength().f();
			v.set( -maxLength, -maxLength, -maxLength);		j.setLinearLowerLimit(v);
			v.set( maxLength, maxLength, maxLength);		j.setLinearUpperLimit(v);
			
			toFloat(c.getMinAngle(), v); j.setAngularLowerLimit(v);
			toFloat(c.getMaxAngle(), v); j.setAngularUpperLimit(v);			
		}

		for (ConstrainSlider c : constrainSlider.keySet()) {
			SliderConstraint s = constrainSlider.get(c);

			s.setLowerAngLimit( c.getAngleMin().f() );
			s.setUpperAngLimit( c.getAngleMax().f() );
			
			s.setLowerLinLimit( c.getLengthMin().f() );
			s.setUpperLinLimit( c.getLengthMax().f() );

			s.setMaxLinMotorForce( c.getForce().f() );
			s.setMaxAngMotorForce( c.getForce().f() );
			
			s.setTargetLinMotorVelocity( c.getForce().f() );
		}
		
		for (PhyBox box : boxes.keySet()) {
			RigidBody body = boxes.get(box);

			body.getCenterOfMassTransform(t);
			//body.getInterpolationWorldTransform(t);

			//limit box position
			if (!box.getBoundsScale().isZero()) {
				double minX = box.getBoundsCenter().x() - box.getBoundsScale().x() / 2.0;
				double maxX = box.getBoundsCenter().x() + box.getBoundsScale().x() / 2.0;
				double minY = box.getBoundsCenter().y() - box.getBoundsScale().y() / 2.0;
				double maxY = box.getBoundsCenter().y() + box.getBoundsScale().y() / 2.0;
				double minZ = box.getBoundsCenter().z() - box.getBoundsScale().z() / 2.0;
				double maxZ = box.getBoundsCenter().z() + box.getBoundsScale().z() / 2.0;
				
				double nx = Math.max( Math.min(maxX, box.getPosition().x() ), minX);
				double ny = Math.max( Math.min(maxY, box.getPosition().y() ), minY);
				double nz = Math.max( Math.min(maxZ, box.getPosition().z() ), minZ);
				
				box.getPosition().set(nx, ny, nz);
			}

			toFloat(box.getPosition(), t.origin);


			
			t.getRotation(q);
			
			float r1 = (float) box.getOrientation().x();
			float r2 = (float) box.getOrientation().y();
			float r3 = (float) box.getOrientation().z();
			qu.fromAngles(r1, r2, r3);
			q.set((float)qu.x, (float)qu.y, (float)qu.z, (float)qu.w);
			t.setRotation(q);
			body.setWorldTransform(t);

			toFloat(box.getSize(), v);
			body.getCollisionShape().setLocalScaling(v);

			double dragFactor = (1.0 - (box.getDrag().d() ));

			toFloat(box.getVelocity(), v);	v.scale((float)dragFactor); body.setLinearVelocity(v);
			toFloat(box.getAngularVelocity(), v); v.scale((float)dragFactor); body.setAngularVelocity(v);

			body.activate();
//			v.set(0,0,0); body.setMassProps((float)box.getMass().d(), v);
			
//			v.set(0,0,0);
//			body.getCollisionShape().calculateLocalInertia( (float)box.getMass().d(), v);
		}

		
	}

	public static Vector3f toFloat(Vector3 in, Vector3f out) {
		out.set((float)in.x(), (float)in.y(), (float)in.z());
		return out;
	}

	public static Vector3 toDouble(Vector3f in, Vector3 out) {
		out.set(in.x, in.y, in.z);
		return out;
	}

	public DoubleVar getTimeScale() {
		return timeScale ;
	}

	public DoubleVar getUpdatePeriod() {
		return updatePeriod ;
	}



	public void removeBox(PhyBox b) {
		RigidBody rb = boxes.get(b);
		bodiesToRemove.add(rb);
		boxes.remove(b);		
	}

	public PhyBox newBox(double mass) {
		// create a few dynamic rigidbodies
		// Re-using the same collision is better for memory usage and performance

		CollisionShape boxShape = new BoxShape(new Vector3f(0.5f, 0.5f, 0.5f));
		
		//CollisionShape colShape = new SphereShape(1f);

		// Create Dynamic Objects
		Transform startTransform = new Transform();
		startTransform.setIdentity();

		Vector3f localInertia = new Vector3f(0, 0, 0);
		boxShape.calculateLocalInertia((float)mass, localInertia);

		double x = 0;
		double y = 0;
		double z = 0;
		startTransform.origin.set((float)x, (float)y, (float)z);

		// using motionstate is recommended, it provides interpolation capabilities, and only synchronizes 'active' objects
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
		
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo((float)mass, myMotionState, boxShape, localInertia);
		RigidBody body = new RigidBody(rbInfo);
		
		bodiesToAdd.add(body);

		final PhyBox b = new PhyBox(mass) {
			@Override
			public void dispose() {
				super.dispose();
				removeBox(this);
			}
			
		};
		boxes.put(b, body);

		return b;
	}

	@Override public void stop() {
		super.stop();
		
		if (space!=null) {
			space.destroy();
			space = null;
		}
	}


	public Vector3 getGravity() {
		return gravity ;
	}




	@Deprecated public SliderConstraint newSlideJoint(PhyBox a, PhyBox b, double minLength, double maxLength, double force) {
		Transform localA = new Transform();
		localA.setIdentity();
		
		Transform localB = new Transform();
		localB.setIdentity();
		
		SliderConstraint s = new SliderConstraint(boxes.get(a), boxes.get(b), localA, localB, true);
		
		s.setLowerAngLimit(0);
		s.setUpperAngLimit((float)Math.PI);
		
		s.setLowerLinLimit((float)minLength);
		s.setUpperLinLimit((float)maxLength);
		s.setMaxLinMotorForce((float)force);
		
		//s.setTargetLinMotorVelocity(0.2f);
		space.addConstraint(s);

		return s;
	}

	public void newWeld(PhyBox a, PhyBox b) {
		Vector3f aPivot = new Vector3f();
		Vector3f bPivot = new Vector3f();
		
		Point2PointConstraint pc = new Point2PointConstraint(boxes.get(a), boxes.get(b), aPivot, bPivot);
		
	}
	
	public ConstrainSlider newConstrainSlider(PhyBox a, PhyBox b, double minLength, double maxLength, double force) {
		Transform localA = new Transform();
		localA.setIdentity();
		
		Transform localB = new Transform();
		localB.setIdentity();
		
		ConstrainSlider c = new ConstrainSlider(new DoubleVar(force), new DoubleVar(minLength), new DoubleVar(maxLength), new DoubleVar(0), new DoubleVar(Math.PI));
		SliderConstraint s = new SliderConstraint(boxes.get(a), boxes.get(b), localA, localB, true);
				
		
//		s.setLowerAngLimit((float)-Math.PI);
//		s.setUpperAngLimit((float)Math.PI);
		
		s.setLowerAngLimit(0);
		s.setUpperAngLimit(0);
		
		
		
		space.addConstraint(s);
		constrainSlider.put(c, s);

		return c;
	}

	
	public Constrain6DoF newConstrain6Dof(PhyBox a, PhyBox b, double maxLength, double r1, double r2, double r3) {
		Constrain6DoF c = new Constrain6DoF(new DoubleVar(maxLength), new Vector3(), new Vector3());

		Transform localA = new Transform();
		localA.setIdentity();
		
		Transform localB = new Transform();
		localB.setIdentity();

		Generic6DofConstraint j = new Generic6DofConstraint(boxes.get(a), boxes.get(b), localA, localB, true);

		c.getMaxLength().set(maxLength);

		
		float v = (float)Math.PI*2;

		c.getMinAngle().set( -(v*r1/2.0), -(v*r2/2.0), -(v*r3/2.0) );
		c.getMaxAngle().set( (v*r1/2.0), (v*r2/2.0), (v*r3/2.0) );
		
		constrain6Dof.put(c, j);
		space.addConstraint(j);

		return c;
	}
	
	@Deprecated public Generic6DofConstraint newAngleJoint(PhyBox a, PhyBox b, double minLength, double maxLength, double r1, double r2, double r3) {
		Transform localA = new Transform();
		localA.setIdentity();
		
		Transform localB = new Transform();
		localB.setIdentity();
		
		Generic6DofConstraint j = new Generic6DofConstraint(boxes.get(a), boxes.get(b), localA, localB, true);
		Vector3f linearLower = new Vector3f((float)minLength,0,0);
		j.setLinearLowerLimit(linearLower);
		Vector3f linearUpper = new Vector3f((float)maxLength,0,0);
		j.setLinearUpperLimit(linearUpper);
		
		
		float v = (float)Math.PI*2;

		Vector3f angularLower = new Vector3f(-(float)(v*r1/2.0), -(float)(v*r2/2.0), -(float)(v*r3/2.0));
		j.setAngularLowerLimit(angularLower );
		
		Vector3f angularUpper = new Vector3f((float)(v*r1/2.0), (float)(v*r2/2.0), (float)(v*r3/2.0));
		j.setAngularUpperLimit(angularUpper);
		
		space.addConstraint(j);

		return j;
	}

	public void removeConstraint(TypedConstraint con) {
		space.removeConstraint(con);		
	}


}
