package automenta.spacenet.space.jme.video.filter;

import org.apache.log4j.Logger;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.input.KeyBindingManager;
import com.jme.renderer.pass.Pass;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jmex.effects.glsl.BloomRenderPass;


public class BloomFilter extends PassAction {
	private static final Logger logger = Logger.getLogger(BloomFilter.class);
	
	boolean supported = true;
	private BloomRenderPass bloomRenderPass;

	private Node rootNode;

	public BloomFilter(Node rootNode) {
		super();
		
		this.rootNode = rootNode;
	}

	@Override
	public void start(Jme jme) {
		super.start(jme);

		bloomRenderPass = new BloomRenderPass(getCamera(), 3);
		if(!bloomRenderPass.isSupported()) {  
			logger.warn("GLSL Not supported on this computer.");
			supported = false;
		} else {
			bloomRenderPass.add(rootNode);
			
			//original values from JME's Bloom
			//			blurSize = 0.02f;
			//			blurIntensityMultiplier = 1.3f;
			//			exposurePow = 3.0f;
			//			exposureCutoff = 0.0f;

			bloomRenderPass.setUseCurrentScene(true);
			bloomRenderPass.setExposurePow(3.0f);
			bloomRenderPass.setExposureCutoff(0.0f);
			bloomRenderPass.setBlurSize(0.02f);
			bloomRenderPass.setBlurIntensityMultiplier(0.5f);
			bloomRenderPass.setNrBlurPasses(2);
		}

	}
	
	
	@Override public void render(DisplaySystem display) {
		if (supported)
			super.render(display);
	}
	
	
	@Override
	public Pass getPass() {
		return bloomRenderPass;
	}
	
	public void oldKeyboardInit() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("1", false)) {
			bloomRenderPass.setEnabled(!bloomRenderPass.isEnabled());
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("2", false)) {
			bloomRenderPass.setBlurSize(bloomRenderPass.getBlurSize() - 0.001f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("3", false)) {
			bloomRenderPass.setBlurSize(bloomRenderPass.getBlurSize() + 0.001f);
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("4", false)) {
			bloomRenderPass.setExposurePow(bloomRenderPass.getExposurePow() - 1.0f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("5", false)) {
			bloomRenderPass.setExposurePow(bloomRenderPass.getExposurePow() + 1.0f);
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("6", false)) {
			bloomRenderPass.setExposureCutoff(bloomRenderPass.getExposureCutoff() - 0.1f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("7", false)) {
			bloomRenderPass.setExposureCutoff(bloomRenderPass.getExposureCutoff() + 0.1f);
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("8", false)) {
			bloomRenderPass.setBlurIntensityMultiplier(bloomRenderPass.getBlurIntensityMultiplier() - 0.1f);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("9", false)) {
			bloomRenderPass.setBlurIntensityMultiplier(bloomRenderPass.getBlurIntensityMultiplier() + 0.1f);
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("0", false)) {
			bloomRenderPass.resetParameters();
			bloomRenderPass.setUseCurrentScene(true);
			bloomRenderPass.setThrottle(1/50f);
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("`", false)) {
			bloomRenderPass.setUseCurrentScene(!bloomRenderPass.useCurrentScene());
		}

		if(KeyBindingManager.getKeyBindingManager().isValidCommand("-", false)) {
			float throttle = bloomRenderPass.getThrottle() - 1/200f;
			if (throttle < 0) throttle = 0;
			logger.info("throttle: "+throttle);
			bloomRenderPass.setThrottle(throttle);
		}
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("+", false)) {
			float throttle = bloomRenderPass.getThrottle() + 1/200f;
			logger.info("throttle: "+throttle);
			bloomRenderPass.setThrottle(throttle);
		}


		if(KeyBindingManager.getKeyBindingManager().isValidCommand("separate", false)) {
			bloomRenderPass.setUseSeparateConvolution(!bloomRenderPass.isUseSeparateConvolution());
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
