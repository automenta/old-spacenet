package automenta.spacenet.space.jme.video;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.UURI;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.fMaths;
import automenta.spacenet.space.jme.video.control.JmeKeyboard;
import automenta.spacenet.space.jme.video.filter.RenderFlow;
import automenta.spacenet.space.jme.video.filter.RenderNode;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.IfColorChanges;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

import com.jme.bounding.BoundingBox;
import com.jme.input.joystick.DummyJoystickInput;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.renderer.AbstractCamera;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.renderer.lwjgl.LWJGLRenderer;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.Spatial.LightCombineMode;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.BlendState.TestFunction;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.scene.state.RenderState.StateType;
import com.jme.scene.state.lwjgl.LWJGLZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.lwjgl.LWJGLDisplaySystem;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.lwjgl.LWJGLTimer;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.math.TriangulationVertex;
import com.jmex.font3d.math.Triangulator;

/** an interface to a particular Jme audiovisual interaction */ 
abstract public class Jme extends Scope implements StartsIn<Spacetime>, Stops {
	private static final Logger logger = Logger.getLogger(Jme.class);


	static {
		java.util.logging.Logger.getLogger(Scope.class.getName()).setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.jmex.physics").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.jme.system.PropertiesGameSettings").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.jme.scene").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.jme.renderer.lwjgl").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.jme.app").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("com.acarter").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger(Triangulator.class.getName()).setLevel(Level.OFF);
		java.util.logging.Logger.getLogger(TriangulationVertex.class.getName()).setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger(Font3D.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(SharedMesh.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(DummyJoystickInput.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLDisplaySystem.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLRenderer.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(AbstractCamera.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLTimer.class.getName()).setLevel(Level.SEVERE);

	}


	public static BlendState defaultBlendState = null;

	private static int enqueuedRunnables;

	private static LWJGLZBufferState zOrderState;

	private static LWJGLZBufferState imageState;

	private static LWJGLZBufferState faceState;

	protected Scheduler scheduler;
	private Video3D video;
	private Space volume;
	private JmeNode volumeNode;

	/** cameraUp can be used to rotate (twist) the camera around the center of the screen. normally it should be up (+y) */
	Vector3f camUp = new Vector3f(0,1,0);
	Vector3f camPos = new Vector3f(0,1,0);
	Vector3f camTarget = new Vector3f(0,1,0);


	private JmeKeyboard mainKeyboard;

	private JmeNode skyNode;
	private Space sky;

	private Color ambientLightColor = new Color(0.5, 0.5, 0.5, 0.5);
	private Vector3f cameraLightPosition = new Vector3f( 0, 0, 500 );

	private Box face;

	private JmeNode faceNode;

	private RenderFlow renderFlow;

	private LightState ambientLight;

	private PointLight cameraLight;

	private Vector3f camAntiDirection = new Vector3f();
	private Vector3f camAntiLeft = new Vector3f();
	private Vector3f camAntiUp = new Vector3f();

	private double visibleRadius;

	private JmeVideoState videoState;


	//set JME's logging
	static {
		java.util.logging.Logger.getLogger(Scope.class.getName()).setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger(SharedMesh.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(DummyJoystickInput.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLDisplaySystem.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLRenderer.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(AbstractCamera.class.getName()).setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger(LWJGLTimer.class.getName()).setLevel(Level.SEVERE);
	}

	static {
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).setExecuteAll(true);
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).setExecuteAll(true);
	}

	//	public static int getCursorIcon() {
	//		if (JMECore.mainComponent == null)
	//			return -1;
	//		
	//		return JMECore.mainComponent.getCursor().getType();
	//	}


	public Jme(JmeVideoState videoState) {
		super();

		this.videoState = videoState;
	}

	public JmeVideoState getVideoState() {
		return videoState;
	}
	
	@Override public void start(Spacetime spaceTime) {
		this.scheduler = spaceTime.time();
		scheduler.setEnabled(false);	//it will be re-enabled later		

		this.video = spaceTime.video();

		this.sky = spaceTime.sky();
		this.face = spaceTime.face();
		this.volume = spaceTime.volume();


		add(new IfColorChanges(video.getBackgroundColor()) {
			@Override public void afterColorChanged(Color c) {
				setBackgroundColor(c);
			}				
		});


		volumeNode = new JmeNode(Jme.this, volume, new DefaultNodeBuilder()) {
			@Override public int getDefaultRenderQueue() {
				return Renderer.QUEUE_OPAQUE;
			}
		};

		//Background should be initialized before content, since content may modify it
		skyNode = new JmeNode(this, sky, new DefaultNodeBuilder()) {
			@Override public int getDefaultRenderQueue() {
				return Renderer.QUEUE_SKIP;
			}
		};		

		
		faceNode = new JmeNode(Jme.this, face, new DefaultNodeBuilder()) {
			@Override public int getDefaultRenderQueue() {
				return Renderer.QUEUE_TRANSPARENT;
			}			
		};
		face.tangible(false);

		//setDrawOrderState(faceNode);


		renderFlow = new RenderFlow(this);		

		Jme.doLater(new Runnable() {
			@Override public void run() {
				//getDisplaySystem().getRenderer().getQueue().setTwoPassTransparency(true);

				// Make the object default colors shine through
				getRootNode().attachChild(skyNode);

				getRootNode().attachChild(getVolumeNode());

				getRootNode().attachChild(faceNode);



				
				MaterialState ms = getDisplaySystem().getRenderer().createMaterialState();
				ms.setColorMaterial(ColorMaterial.AmbientAndDiffuse);
				getRootNode().setRenderState(ms);

				//Jme.setBlendState(Jme.this, getRootNode());	//updates render state

				initRenderFlow(renderFlow, getRootNode());

				updateLights();

				initSkyNode();

				scheduler.setEnabled(true);		
			}			
		});

	}


	protected void setBackgroundColor(final Color c) {
		doLater(new Runnable() {
			@Override public void run() {
				getDisplaySystem().getRenderer().setBackgroundColor(Jme.asJMEColor(c));				
			}			
		});
	}

	@Override public void stop() {
	}

	protected void updateLights() {
		RenderState currentLightState = getRootNode().getRenderState(StateType.Light);
		if ((cameraLight == null) && (getVideoState().getLightsEnabled().b())) {
			
			/** Attach the light to a lightState and the lightState to rootNode. */
			ambientLight = getDisplaySystem().getRenderer().createLightState();
	
	
	
			float globalAmbient = 0.9f;
	
			//ambientLight.setTwoSidedLighting(true);
			ambientLight.setEnabled( true );
			ambientLight.setGlobalAmbient( new ColorRGBA( 1.0f, 1.0f, 1.0f, globalAmbient));
			
			
			// ---- LIGHTS
	
			cameraLight = new PointLight();
	
			cameraLight.setAttenuate(true);
			cameraLight.setLinear(0.01f);
			//cameraLight.setQuadratic(0.005f);
	
			//light.setDiffuse( new ColorRGBA( 0.75f, 0.75f, 0.75f, 0.5f ) );
			//cameraLight.setDiffuse( new ColorRGBA( 1f, 1f, 1f, 0.9f ) );
			//light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
			//cameraLight.setAmbient(new ColorRGBA(0.5f,0.5f,0.5f,0.5f));		
	
			//light.setSpecular( new ColorRGBA( 1f, 1f, 1f, 1f ) );
	
			cameraLight.setLocation( cameraLightPosition );
			
			cameraLight.setEnabled( true );
	
			ambientLight.attach( cameraLight );
	
	//		getContentNode().setRenderState( ambientLight );	
	//		getContentNode().updateRenderState();				
			getRootNode().setRenderState( ambientLight );	
			getRootNode().updateRenderState();
		}
		else if ((cameraLight!=null) && (!getVideoState().getLightsEnabled().b())) {
			getRootNode().clearRenderState(StateType.Light);
			getRootNode().updateRenderState();
			ambientLight = null;
			cameraLight = null;
		}

		if (cameraLight!=null) {
			cameraLightPosition.set(getJmeCamera().getLocation());
			float lf = getVideoState().getAmbientLightLevel().f();
			lf = (float) Math.min(Math.max(0.0, lf), 1.0);
			cameraLight.setConstant(lf);
		}

	}


	protected void initRenderFlow(RenderFlow renderFlow, com.jme.scene.Node rootNode) {

		renderFlow.getProcedure().add(new RenderNode(rootNode));

		//renderFlow.getProcedure().add(new DebugFilter(rootNode));

		//renderFlow.getProcedure().add(new WaterFilter(volumeNode, volumeNode, backgroundNode));

		//renderFlow.getProcedure().add(new BloomFilter(rootNode));

		//renderFlow.getProcedure().add(new MotionBlurFilter(space));

	}

	private void initSkyNode() {
		ZBufferState zbuff = getDisplaySystem().getRenderer().createZBufferState();
		zbuff.setWritable(false);
		zbuff.setEnabled(true);
		zbuff.setFunction(ZBufferState.TestFunction.Always);
		
		skyNode.setRenderState(zbuff);
		//faceNode.setRenderState(zbuff);

		// We don't want it making our skybox disapear, so force view
		//backgroundNode.setCullHint(Spatial.CullHint.Never);

		//        for (int i = 0; i < 6; i++) {
		//            // Make sure texture is only what is set.
		//            skyboxQuads[i].setTextureCombineMode(TextureCombineMode.Replace);
		//
		//            // Make sure no lighting on the skybox
		//            skyboxQuads[i].setLightCombineMode(Spatial.LightCombineMode.Off);
		//
		//            // Make sure the quad is viewable
		//            skyboxQuads[i].setCullHint(Spatial.CullHint.Never);
		//
		//            // Set a bounding volume
		//            skyboxQuads[i].setModelBound(new BoundingBox());
		//            skyboxQuads[i].updateModelBound();
		//
		//            skyboxQuads[i].setRenderQueueMode(Renderer.QUEUE_SKIP);
		//            skyboxQuads[i].setVBOInfo(null);
		//
		//            // And attach the skybox as a child
		//            attachChild(skyboxQuads[i]);
		//        }


		skyNode.setLightCombineMode(LightCombineMode.Off);
		//faceNode.setLightCombineMode(LightCombineMode.Off);
		
		skyNode.setRenderQueueMode(Renderer.QUEUE_SKIP);
		
		skyNode.updateGeometricState(0.0f, true);
		faceNode.updateGeometricState(0.0f, true);
		
		faceNode.setModelBound(new BoundingBox());
		skyNode.setModelBound(new BoundingBox());

		skyNode.updateModelBound();
		faceNode.updateModelBound();
	}


	public static ColorRGBA asJMEColor(Color cl) {
		return new ColorRGBA((float)cl.r(), (float)cl.g(), (float)cl.b(), (float)cl.a());
	}
	public static ColorRGBA toJMEColor(Color cl, ColorRGBA c) {
		c.set((float)cl.r(), (float)cl.g(), (float)cl.b(), (float)cl.a());
		return c;
	}

	//	public static void detach(final Node parent, final Spatial child) {
	//		invokeLater(new Runnable() {
	//			@Override public void run() {
	//				parent.detachChild(child);
	//			}			
	//		});
	//	}


	public static Future enqueue(final Callable callable) {
		enqueuedRunnables++;
		Future future = GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).enqueue(callable);
		return future;
	}

	public static Object doLaterButWait(Runnable runnable) {
		Future f = doLater(runnable);
		try {
			return f.get();
		} catch (Exception e) {
			logger.error("enqueueAndWait: " + e);
		}
		return null;
	}

	public static void flushInvokeLaters() {
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();
	}

	public static Color getColor(java.awt.Color c) {
		float[] compArray = new float[4];
		c.getComponents(compArray );		
		return new Color( compArray[0], compArray[1], compArray[2], compArray[3] );		
	}

	public static int getEnqueuedRunnables(boolean reset) {
		int n = enqueuedRunnables;
		if (reset == true) {
			enqueuedRunnables = 0;
		}
		return n;
	}


	abstract public DisplaySystem getDisplaySystem();

	public static Future doLater(final Runnable runnable) {
		enqueuedRunnables++;		
		Future future = GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).enqueue(new Callable() {
			@Override public Object call() throws Exception {
				runnable.run();
				return null;
			}
		});
		return future;
	}

	public static void removeBlendState(Jme jme, Spatial spatial, boolean doUpdate) {
		spatial.clearRenderState( StateType.Blend );
		spatial.setRenderQueueMode( Renderer.QUEUE_OPAQUE );
		
		if (doUpdate)
			spatial.updateRenderState();
	}

	public static void setBlendState(Jme jme, Spatial spatial, boolean doUpdate) {
		if (defaultBlendState == null) {
			defaultBlendState = jme.getDisplaySystem().getRenderer().createBlendState();

			defaultBlendState.setBlendEnabled(true);
			defaultBlendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
			defaultBlendState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
			//defaultBlendState.setTestEnabled(true);
			defaultBlendState.setTestFunction(TestFunction.Always);
		}

		if (spatial.getRenderState(StateType.Blend) == null) {
			//spatial.setRenderQueueMode( Renderer.QUEUE_TRANSPARENT );
			spatial.setRenderState( defaultBlendState );
			
			if (doUpdate)
				spatial.updateRenderState();
		}
	}

	//	/** warning: must be called from JME thread */
	//	@Deprecated public void updateRenderStateWithBlend(Spatial node) {
	//		//http://www.jmonkeyengine.com/wiki/doku.php?id=transparency
	//		if (defaultBlendState == null) {
	//			defaultBlendState = getDisplaySystem().getRenderer().createBlendState();
	//
	//			defaultBlendState.setBlendEnabled(true);
	//			defaultBlendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
	//			defaultBlendState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
	//			defaultBlendState.setTestEnabled(true);
	//
	//		}
	//
	//		node.setRenderState( defaultBlendState );		
	//		node.updateRenderState();
	//
	//	}
	//
	//	/** produces a worse-looking blend effect than what's currently used */
	//	@Deprecated public void updateRenderStateWithBlendOLD(Spatial node) {
	//		//http://www.jmonkeyengine.com/wiki/doku.php?id=transparency
	//		if (defaultBlendState == null) {
	//			defaultBlendState = getDisplaySystem().getRenderer().createBlendState();
	//			defaultBlendState.setEnabled(alphaEnabled);
	//			defaultBlendState.setBlendEnabled(blendEnabled);
	//
	//			defaultBlendState.setBlendEquationAlpha(BlendEquation.Add);
	//			defaultBlendState.setBlendEquationRGB(BlendEquation.Add);
	//
	//			//THIS src/dest WORKS. DO NOT REMOVE THIS:
	//			defaultBlendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
	//			defaultBlendState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
	//			//defaultBlendState.setDestinationFunctionRGB(BlendState.DestinationFunction.OneMinusSourceAlpha);
	//
	//			defaultBlendState.setTestEnabled(true);
	//			//defaultBlendState.setTestFunction(BlendState.TestFunction.GreaterThan);
	//			defaultBlendState.setTestFunction(BlendState.TestFunction.Always);
	//
	//
	//
	//			//			if(general_alphastate == null)
	//			//			{
	//			//		        general_alphastate = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
	//			//		        general_alphastate.setBlendEnabled(true);
	//			//		        general_alphastate.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
	//			//		        general_alphastate.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
	//			//		        general_alphastate.setTestEnabled(true);
	//			//		        general_alphastate.setTestFunction(BlendState.TestFunction.Always);
	//			//		        general_alphastate.setEnabled(true);
	//			//			}
	//
	//			//			defaultBlendState.setTestEnabled(true);
	//			//			defaultBlendState.setReference(0.5f);
	//			//			defaultBlendState.setTestFunction(BlendState.TestFunction.GreaterThan);
	//
	//
	//			//	    defaultBlendState.setSrcFunction(defaultBlendState.SB_SRC_ALPHA);
	//			//	    defaultBlendState.setDstFunction(defaultBlendState.DB_ONE_MINUS_SRC_ALPHA);
	//			//defaultBlendState.setTestEnabled(true);
	//			//defaultBlendState.setTestFunction(defaultBlendState.TF_GREATER);			
	//		}
	//		//System.out.println("applying alpha state to: " + node);
	//
	//		node.setRenderState( defaultBlendState );		
	//		node.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
	//		node.updateRenderState();
	//	}

	public static void setCursorIcon(int awtCursorID) {
		logger.error("cursor setting not supported yet.  cursor ID = " + awtCursorID);

		//		if (JMECore.mainComponent == null) {
		//			//TODO provide alternate way to set system cursor
		//			logger.error("mainComponent null, can not set cursor " + awtCursorID);
		//			 //c = (LWJGLCanvas)((LWJGLDisplaySystem)DisplaySystem.getDisplaySystem()).getCurrentContext();
		//		}
		//		else {
		//			if (JMECore.mainComponent.getCursor().getType() != awtCursorID)
		//				JMECore.mainComponent.setCursor(Cursor.getPredefinedCursor(awtCursorID));
		//		}

	}


//	public static void setDrawOrderState(Spatial n) {
//
//		if (drawOrderState == null) {
//			drawOrderState = new LWJGLZBufferState();
//			drawOrderState.setEnabled(true);
//
//			drawOrderState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
//			//drawOrderState.setWritable(false);
//			drawOrderState.setWritable(true);
//
//		}
//
//		//n.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
//		n.setRenderState(drawOrderState);
//		n.updateRenderState();
//	}

	public static void setZOrderState(Spatial n) {

		if (zOrderState == null) {
			zOrderState = new LWJGLZBufferState();
			zOrderState.setEnabled(true);
			
			zOrderState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
			zOrderState.setWritable(true);
		}

		n.setRenderState(zOrderState);
		n.updateRenderState();
	}

	public static void setFaceState(Spatial n) {

		if (faceState == null) {
			faceState = new LWJGLZBufferState();
			
			faceState.setEnabled(false);

			faceState.setFunction(ZBufferState.TestFunction.Always);
			faceState.setWritable(false);

		}

		n.setRenderState(faceState);
		n.updateRenderState();
	}

	public static void setImageState(Spatial n) {
		if (imageState == null) {
			imageState = new LWJGLZBufferState();
			imageState.setEnabled(true);
			
			imageState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
			//imageState.setFunction(ZBufferState.TestFunction.Always);
			imageState.setWritable(true);
		}

		n.setRenderState(imageState);
		n.updateRenderState();
		
	}


	public abstract com.jme.scene.Node getRootNode();

	abstract public Vector2 getPixelSize();


//	public PhysicsSpace getJmePhysics(final PhySpace p) {
//		PhysicsSpace sp = (PhysicsSpace) get(p);
//		if (sp == null) {
//			sp = newPhysicsSpace(p);
//			add(p, sp);
//		}
//		return sp;
//	}

//	private PhysicsSpace newPhysicsSpace(final PhySpace p) {
//		PhysicsSpace ps = new JmePhysicsSpace(this, p);
//		jmePhysics.put(p, ps);
//		return ps;
//	}

	/** JME's main loop is used to drive its repeatContext	 */
	public void forward(double dt) {
		scheduler.forward(dt);

		updateCamera(getVideo());

	}

	public UURI newScreenShot(String uriString) {
		logger.warn("screenshots not impl yet");
		return null;

		//		if(KeyBindingManager.getKeyBindingManager().isValidCommand("shot", false)) {
		//			display.getRenderer().takeScreenShot("shot" + screenshotIndex++);
		//		}
	}

	public Object getSpatial(Object o) {
		return objects.get(o);		
	}

	public Color getAmbientLightColor() {
		logger.warn("ambient light color updating not impl yet");
		return ambientLightColor;
	}

	public Video3D getVideo() {
		return video;
	}
	public JmeNode getVolumeNode() {
		return volumeNode;
	}

	public Space getSky() {
		return sky;
	}
	public JmeNode getSkyNode() {
		return skyNode;
	}
	public Space getFace() {
		return face;
	}
	public JmeNode getFaceNode() { return faceNode; }

	public RenderFlow getRenderFlow() {
		return renderFlow;
	}

	public int getTriangleCount(Spatial s) {
		int t = s.getTriangleCount();
		if (s instanceof com.jme.scene.Node) {
			com.jme.scene.Node n = (Node) s;
			if (n.getChildren()!=null)
				for (Spatial st : n.getChildren())
					t += getTriangleCount(st);
		}
		return t;
	}
	
	public int getTriangleCount() {
		return getTriangleCount(getRootNode());
	}
	
	public String getStatistics() {		
		return "volume.tri=" + getTriangleCount(getVolumeNode()) + ", face.tri=" + getTriangleCount(getFaceNode());
	}
	
	protected void updateCamera(Video3D video) {

		Camera cam = getJmeCamera();

		cam.setFrustumPerspective( video.getFocusAngle().f(), 
				((float)getDisplaySystem().getWidth())/((float)getDisplaySystem().getHeight()), 
				video.getZNear().f(), 
				video.getZFar().f());

//		camDir.set((float)video.getTarget().x(), (float)video.getTarget().y(), (float)video.getTarget().z());
		
//		cam.getLocation().set((float)video.getPosition().x(), (float)video.getPosition().y(), (float)video.getPosition().z());		
//		cam.getDirection().set( camDir ).subtractLocal( cam.getLocation() ).normalizeLocal();		
//        cam.getUp().set(camUp).normalizeLocal();
//		cam.getLeft().set(camUp).crossLocal(cam.getDirection()).normalizeLocal();       //left.set(up).crossLocal(direction).normalizeLocal();
//        cam.onFrameChange();

		camUp.set((float)video.getUp().x(), (float)video.getUp().y(), (float)video.getUp().z());		

		fMaths.fromDouble(video.getPosition(), camPos);
		fMaths.fromDouble(video.getTarget(), camTarget);
		cam.getLocation().set(camPos);
		cam.lookAt(camTarget, camUp);

		double hfa = Math.toRadians( video.getFocusAngle().d() ) / 2.0;
		visibleRadius = Math.sin( hfa ) / Math.sin ( Math.PI/2.0 - hfa ); 
		getSkyNode().getLocalTranslation().set(cam.getLocation());
		getSkyNode().updateGeometricState(0.0f, true);

		float dx = (float)(video.getTarget().x() - video.getPosition().x());		
		float dy = (float)(video.getTarget().y() - video.getPosition().y());		
		float dz = (float)(video.getTarget().z() - video.getPosition().z());

		{
			final double ht = Math.toRadians( video.getFocusAngle().d()/2.0 );
			final double halfPi = Math.PI / 2.0; 
			float faceDistance = (float)( Math.sin( halfPi - ht ) / 2 / Math.sin( ht ) );
			
			//this 's' parameter is used to move the face closer, and shrink it to maintain an apparent size.
			//s should not be too large, otherwise volume geometry may overlap and occlude face geometry.
			//s should not be too small, otherwise face geometry may intersect with the near clipping plane, rendering it invisible.
			float s = 0.5f;
			faceDistance *= s;
			getFaceNode().getLocalScale().set(s,s,s);
			
			getFaceNode().getLocalTranslation().set(dx,dy,dz);
			getFaceNode().getLocalTranslation().normalizeLocal();		
			getFaceNode().getLocalTranslation().multLocal(faceDistance);		
			getFaceNode().getLocalTranslation().addLocal(cam.getLocation());

//			camAntiDirection.set(cam.getDirection()).normalizeLocal().multLocal(1f);
//			camAntiUp.set(cam.getLeft()).crossLocal(cam.getDirection()).normalizeLocal().multLocal(1f);
//			camAntiLeft.set(cam.getUp()).crossLocal(cam.getDirection()).normalizeLocal().multLocal(-1f);
			
			getFaceNode().getLocalRotation().fromAxes(cam.getLeft().mult(-1f), cam.getUp(), cam.getDirection().mult(-1f));
			getFaceNode().updateGeometricState(0.0f, true);
		}

		updateLights();
		

		
		
		cam.update();

	}

//	protected void updateCamera(Video3D video) {
//
//		Camera cam = getJmeCamera();//window.getCamera();
//
//		cam.setFrustumPerspective( video.getFocusAngle().f(), 
//				((float)getDisplaySystem().getWidth())/((float)getDisplaySystem().getHeight()), 
//				video.getZNear().f(), 
//				video.getZFar().f());
//
//		sightUp.set((float)Math.sin(video.getTilt().d()), (float)Math.cos(video.getTilt().d()), (float)0);
//		sightUp.normalizeLocal();
//		sightLeft.set(-(float)Math.cos(video.getTilt().d()), (float)Math.sin(video.getTilt().d()), (float)0);
//		sightLeft.normalizeLocal();
//		
//		sightTargetF.set((float)video.getTarget().x(), (float)video.getTarget().y(), (float)video.getTarget().z());
//		
//		cam.getLocation().set((float)video.getPosition().x(), (float)video.getPosition().y(), (float)video.getPosition().z());		
//		cam.getLeft().set(sightLeft);
//		cam.getUp().set(sightUp);
//		
//		cam.lookAt(sightTargetF, sightUp);
//
//		double hfa = Math.toRadians( video.getFocusAngle().d() ) / 2.0;
//		visibleRadius = Math.sin( hfa ) / Math.sin ( Math.PI/2.0 - hfa ); 
//
//		float dx = (float)(video.getTarget().x() - video.getPosition().x());		
//		float dy = (float)(video.getTarget().y() - video.getPosition().y());		
//		float dz = (float)(video.getTarget().z() - video.getPosition().z());
//
//		getSkyNode().getLocalTranslation().set(cam.getLocation());
//		getSkyNode().updateGeometricState(0.0f, true);
//
//		{
//			final double ht = Math.toRadians( video.getFocusAngle().d()/2.0 );
//			final double halfPi = Math.PI / 2.0; 
//			float faceDistance = (float)( Math.sin( halfPi - ht ) / 2 / Math.sin( ht ) );
//			
//			getFaceNode().getLocalTranslation().set(dx,dy,dz);
//			getFaceNode().getLocalTranslation().normalizeLocal();		
//			getFaceNode().getLocalTranslation().multLocal(faceDistance);		
//			getFaceNode().getLocalTranslation().addLocal(cam.getLocation());
//
//			camAntiDirection.set(cam.getDirection()).multLocal(-1f);
//			camAntiLeft.set(cam.getLeft()).multLocal(-1f);
//			
//			getFaceNode().getLocalRotation().fromAxes(camAntiLeft, cam.getUp(), camAntiDirection);
//			getFaceNode().updateGeometricState(0.0f, true);
//		}
//
//		updateLights();
//		
//
//		
//		
//		cam.update();
//
//	}

	abstract protected Camera getJmeCamera();


	abstract public DoubleVar getUpdatesPerSecond();
	abstract public DoubleVar getRendersPerSecond();

//	public DoubleVar getAmbientLightLevel() {
//		return ambientLightLevel;
//	}
//
//	protected int getSubSamples() {
//		return subSamples ;
//	}
//	
	/** visible radius at certain depth */
	public double getVisibleRadius(double depth) {
		return visibleRadius * depth;
	}
//
//	public BooleanVar getLightsEnabled() {
//		return lightsEnabled ;
//	}
//
//	public DoubleVar getRenderPeriod() {
//		return renderPeriod;
//	}
//
//	public DoubleVar getUpdatePeriod() {
//		return updatePeriod;
//	}


}
