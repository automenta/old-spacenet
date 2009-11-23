package automenta.spacenet.space.jme.video.filter;

import org.apache.log4j.Logger;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.renderer.pass.Pass;
import com.jme.scene.Node;
import com.jmex.effects.glsl.MotionBlurRenderPass;


public class MotionBlurFilter extends PassAction {
	private static final Logger logger = Logger.getLogger(MotionBlurFilter.class);

	private Node rootNode;
	private MotionBlurRenderPass motionBlurRenderPass;

	private boolean supported = true;

	public MotionBlurFilter(Node rootNode) {
		super();
		this.rootNode = rootNode;
	}

	@Override
	public void start(Jme jme) {
		super.start(jme);

		motionBlurRenderPass = new MotionBlurRenderPass(getCamera());

		if (!motionBlurRenderPass.isSupported()) {
			logger.error("GLSL Not supported on this computer.");
			supported = false;
			return;
		}

		motionBlurRenderPass.add(rootNode);
//		            motionBlurRenderPass.addMotionBlurSpatial(rootNode);
		//            motionBlurRenderPass.addMotionBlurSpatial(torus);
		motionBlurRenderPass.setUseCurrentScene(false);
		motionBlurRenderPass.setBlurStrength(-0.8f);
		motionBlurRenderPass.setEnabled(true);
		motionBlurRenderPass.reloadShader();

	}

	@Override
	public Pass getPass() {
		return motionBlurRenderPass;
	}

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
