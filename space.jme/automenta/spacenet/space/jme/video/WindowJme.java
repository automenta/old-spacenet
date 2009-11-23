package automenta.spacenet.space.jme.video;

import org.apache.log4j.Logger;

import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.jme.video.window.JmeWindowLoop;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

import com.jme.renderer.Camera;
import com.jme.system.DisplaySystem;


/** Native OpenGL Window implementation */ 
public class WindowJme extends Jme {
	private static final Logger logger = Logger.getLogger(WindowJme.class);

	private DoubleVar updatesPerSecond = new DoubleVar(0);
	private DoubleVar rendersPerSecond = new DoubleVar(0);
	
	private JmeWindowLoop window;
	private Thread windowLoop;


	public WindowJme(JmeVideoState videoState) {
		super(videoState);
	}

	@Override public void start(Spacetime spaceTime) {
		super.start(spaceTime);

		window = new JmeWindowLoop(this, getRenderFlow(), getVideoState().getSubsamples().i());

		windowLoop = new Thread(new Runnable() {
			@Override public void run() {
				window.run();
				
			}
		});
		windowLoop.setPriority(Thread.MAX_PRIORITY);
		windowLoop.start();
		
		Jme.doLater(new Runnable() {
			@Override public void run() {
				getPixelSize().set(  window.getDisplaySystem().getWidth(), window.getDisplaySystem().getHeight() );				
			}			
		});

	}
	

	@Override public String toString() {
		return "JmeWindow@" + hashCode();
	}

	@Override public void stop() {
		if (window!=null) {
			window.finish();
			window = null;
		}
	}

	@Override public DisplaySystem getDisplaySystem() {
		return window.getDisplaySystem();
	}

//	@Override public DoubleVar getTime() {
//		return null;
//	}


	@Override public Vector2 getPixelSize() {
		return getVideo().getPixelSize();
	}

	@Override public com.jme.scene.Node getRootNode() {
		return window.getRootNode();
	}

	@Override protected Camera getJmeCamera() {
		return window.getCamera();
	}
	
	@Override public DoubleVar getUpdatesPerSecond() { 	return updatesPerSecond;	}
	@Override public DoubleVar getRendersPerSecond() {	return rendersPerSecond ;	}
}
