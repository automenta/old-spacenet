package automenta.spacenet.space.jme.video.filter;


import automenta.spacenet.space.jme.video.Jme;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.renderer.pass.Pass;
import com.jme.system.DisplaySystem;


/** adapts JME 'RenderPass' implementations */
abstract public class PassAction implements RenderAction {

	private Jme jme;
	private DisplaySystem display;
	private Camera camera;

	abstract public Pass getPass();
	
	@Override
	public void start(Jme jme) {
		this.jme = jme;
		this.display = jme.getDisplaySystem();
		this.camera = display.getRenderer().getCamera();
	}

	public Camera getCamera() { return camera; }
	public DisplaySystem getDisplay() { return display; }
	
	@Override
	public void forward(float tpf) {
		if (getPass()!=null)
			getPass().updatePass(tpf);		
	}

	@Override
	public void render(DisplaySystem display) {
		Renderer renderer = display.getRenderer();
		if (getPass()!=null)
			getPass().renderPass(renderer);		
	}


	@Override
	public void dispose() {
		getPass().cleanUp();
	}

}
