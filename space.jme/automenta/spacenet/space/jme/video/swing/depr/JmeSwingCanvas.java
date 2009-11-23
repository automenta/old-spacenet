package automenta.spacenet.space.jme.video.swing.depr;


import org.apache.log4j.Logger;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.canvas.SimpleCanvasImpl;
import com.jme.util.NanoTimer;
import com.jme.util.geom.Debugger;



public class JmeSwingCanvas /*extends SimpleCanvasImpl*/ {
//	private static final Logger logger = Logger.getLogger(JmeSwingCanvas.class);
//
//	private InputHandler input = new InputHandler();
//
//	private boolean showBounds = false;
//
//	private boolean showNormals;
//
//	private SwingJmeOLD jme;
//
//	private JmeJPanel jmePanel;
//
//	private Node theRootNode;
//
//    public JmeSwingCanvas(JmeJPanel jmePanel, int initialWidth, int initialHeight) {
//        super(initialWidth, initialHeight);
//        this.jmePanel = jmePanel;
//        this.jme = jmePanel.getJmeSwing();
//    }
//
//    protected void doDebug(Renderer r) {
//        /**
//         * If showing bounds, draw rootNode's bounds, and the bounds of all its
//         * children.
//         */
//        if ( showBounds ) {
//            Debugger.drawBounds( rootNode, r, true );
//        }
//
//        if ( showNormals ) {
//            Debugger.drawNormals( rootNode, r );
//            Debugger.drawTangents( rootNode, r );
//        }
//    }
//
//    @Override	public void doRender() {
//        renderer.clearBuffers();
//        renderer.draw(rootNode);
//        simpleRender();
//        renderer.displayBackBuffer();
//        doDebug(renderer);
//    }
//
//
//	@Override public void doSetup() {
//        DisplaySystem display = DisplaySystem.getDisplaySystem();
//    	//DisplaySystem display = new LWJGLDisplaySystem();
//
//        renderer = display.getRenderer();
//    	//System.out.println("renderer: " +renderer);
//
//        /**
//         * Create a camera specific to the DisplaySystem that works with the
//         * width and height
//         */
//        cam = renderer.createCamera(width, height);
//
//        /** Set up how our camera sees. */
//        cam.setFrustumPerspective(45.0f, (float) width / (float) height, 1,
//                1000);
//        Vector3f loc = new Vector3f(0.0f, 0.0f, 25.0f);
//        Vector3f left = new Vector3f(-1.0f, 0.0f, 0.0f);
//        Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
//        Vector3f dir = new Vector3f(0.0f, 0f, -1.0f);
//
//        /** Move our camera to a correct place and orientation. */
//        cam.setFrame(loc, left, up, dir);
//        /** Signal that we've changed our camera's location/frustum. */
//        cam.update();
//        /** Assign the camera to this renderer. */
//        renderer.setCamera(cam);
//
//        /** Set a black background. */
//        renderer.setBackgroundColor(ColorRGBA.black.clone());
//
//        /** Get a high resolution timer for FPS updates. */
//        timer = new NanoTimer();
//
//        /** Create rootNode */
//        rootNode = this.theRootNode = new com.jme.scene.Node();
//
//
//        initZBuffer(rootNode);
//        //initLighting(rootNode);
//
//        simpleSetup();
//
//        /* Update geometric and rendering information for both the rootNode and fpsNode.  */
//        rootNode.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
//        rootNode.updateGeometricState(0.0f, true);
//        rootNode.updateRenderState();
//
//        setup = true;
//    }
//
//	public InputHandler getInputHandler() {
//		return input;
//	}
//
//	/** do not use this, instead use getRootSpaceNode() */
//	@Override public Node getRootNode() {
//		return super.getRootNode();
//	}
//
//	private void initZBuffer(Node rootNode) {
//        /* Create a ZBuffer to display pixels closest to the camera above farther ones.  */
//        ZBufferState buf = renderer.createZBufferState();
//        buf.setEnabled(true);
//        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
//        buf.setWritable(true);
//        rootNode.setRenderState(buf);
//        rootNode.updateRenderState();
//	}
//
//    @Override public void simpleUpdate() {
//        input.update(tpf);
//    }
//
//	@Override public void simpleSetup() {
//
////        input.addAction(new InputAction() {
////            public void performAction(InputActionEvent evt) {
////                //logger.info(evt.getTriggerName());
////        		//System.out.println("mouse press: " + System.nanoTime());
////
////            }
////        }, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_ALL, InputHandler.AXIS_NONE, false);
////
////        input.addAction(new InputAction() {
////            public void performAction(InputActionEvent evt) {
////                logger.info(evt.getTriggerName() + " " + evt.getTriggerPosition());
////            }
////        }, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_NONE, InputHandler.AXIS_ALL, false);
////
////        input.addAction(new InputAction() {
////            public void performAction(InputActionEvent evt) {
////                logger.info(evt.getTriggerName());
////            }
////        }, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL,  InputHandler.AXIS_NONE, false);
//    }
//

}
