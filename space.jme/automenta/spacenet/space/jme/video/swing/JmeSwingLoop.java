/**
 * 
 */
package automenta.spacenet.space.jme.video.swing;

/**  Not functional yet */
abstract public class JmeSwingLoop /*extends SimpleCanvasImpl*/ {

//	private final SwingJme swing;
//    long startTime = 0;
//    long fps = 0;
//    private InputHandler input;
//
//    public JmeSwingLoop(SwingJme swingJme, Vector2 pixelSize) {
//    	super((int)pixelSize.x(), (int)pixelSize.y());
//    	swing = swingJme;
//	}
//
//    //public Jme getJme() { return swing; }
//
//	@Override
//	public void simpleSetup() {
//		int depthBits = 8;
//		int stencilBits = 0;
//		int alphaBits = 0;
//		int samples = 0;
//
//		//JmeWindowLoop.initSystem(swing.getDisplaySystem(), depthBits, stencilBits, alphaBits, samples);
//
//        startTime = System.currentTimeMillis() + 5000;
//
//        input = new InputHandler();
//        input.addAction(new InputAction() {
//            public void performAction(InputActionEvent evt) {
//                //SwingJme.logger.info(evt.getTriggerName());
//            }
//        }, InputHandler.DEVICE_MOUSE, InputHandler.BUTTON_ALL,
//                InputHandler.AXIS_NONE, false);
//
//        input.addAction(new InputAction() {
//            public void performAction(InputActionEvent evt) {
//                //SwingJme.logger.info(evt.getTriggerName());
//            }
//        }, InputHandler.DEVICE_KEYBOARD, InputHandler.BUTTON_ALL,
//                InputHandler.AXIS_NONE, false);
//
//        JmeWindowLoop.initRoot(swing.getDisplaySystem(), rootNode, cam);
//    }
//
//    @Override
//	public void simpleUpdate() {
//        input.update(tpf);
//
//        swing.forward(tpf);
//
//        if (startTime > System.currentTimeMillis()) {
//            fps++;
//        } else {
//            long timeUsed = 5000 + (startTime - System.currentTimeMillis());
//            startTime = System.currentTimeMillis() + 5000;
//
////            SwingJme.logger.info(fps + " frames in " + (timeUsed / 1000f)
////                    + " seconds = " + (fps / (timeUsed / 1000f))
////                    + " FPS (average)");
//            fps = 0;
//        }
//    }
}