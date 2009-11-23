/**
 * 
 */
package automenta.spacenet.os;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.IfKeyReleased;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.control.video.ClickZoomPress;
import automenta.spacenet.space.control.video.FirstPersonLook;
import automenta.spacenet.space.control.video.NumericKeypadMovesEye;
import automenta.spacenet.space.control.video.WheelClicksZ;
import automenta.spacenet.run.text.DemoGroovyTerminal.GroovyTerminal;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.swing.SceneMonitor;
import automenta.spacenet.space.video3d.AbstractVideo3D;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

import com.jme.input.KeyInput;

public class DefaultZoomControls extends Scope implements StartsIn<Spacetime> {
	private static final Logger logger = Logger.getLogger(DefaultZoomControls.class);
	private Spacetime spaceTime;
	SceneMonitor sceneMonitor;

	double wheelZSpeed = 0.03;
	DoubleVar keyMovementVelocity = new DoubleVar(100.0);
	DoubleVar keyAngularVelocity = new DoubleVar(1.5);
	private GroovyTerminal faceConsole;
	
	@Override public void start(Spacetime s) {
		this.spaceTime = s;

		add(new WheelClicksZ(s.pointer().getWheel(), s.video(), wheelZSpeed));
		//add(new WheelAcceleratesZ(s.pointer().getWheel(), s.video().getPosition(), -0.005));

		
		add(new NumericKeypadMovesEye(s.video(), s.keyboard(), keyMovementVelocity));
		//	add(new NumericKeypadRotatesEye(s.video(), s.keyboard(), keyAngularVelocity, keyMovementVelocity));


		add(new ClickZoomPress(s, Pointer.PointerButton.Right));

		add(new FirstPersonLook(s.video(), s.keyboard(), s.pointer(), keyAngularVelocity, KeyInput.KEY_LCONTROL));

		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_F5) {
			@Override public void afterKeyReleased(BooleanVar key) {
				adjustLights(-0.1);
			}				
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_F6) {
			@Override public void afterKeyReleased(BooleanVar key) {
				adjustLights(0.1);
			}				
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_F7) {
			@Override public void afterKeyReleased(BooleanVar key) {
				toggleLights();
			}				
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_F12) {
			@Override public void afterKeyReleased(BooleanVar key) {
				toggleFullscreen();
			}				
		});

		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_F1) {
			@Override public void afterKeyReleased(BooleanVar key) {
				toggleSceneMonitorWindow();
			}				
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_PGUP) {
			@Override public void afterKeyReleased(BooleanVar key) {
				rotateView(Math.PI/4.0);
			}
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_PGDN) {
			@Override public void afterKeyReleased(BooleanVar key) {
				rotateView(-Math.PI/4.0);
			}
		});
		add(new IfKeyReleased(s.keyboard(), Keyboard.KEY_HOME) {
			@Override public void afterKeyReleased(BooleanVar key) {
				toggleFaceConsole();
			}			
		});
	}
	
	@Override public void stop() {
	}
	
	protected void toggleSceneMonitorWindow() {
		if (sceneMonitor == null) {
			sceneMonitor = addThe(SceneMonitor.class);
		}
		else {
			sceneMonitor.stop();
			sceneMonitor = null;
		}
	}

	public Spacetime getSpaceTime() {
		return spaceTime;
	}

	private void rotateView(double dTheta) {
		Vector3 forward = AbstractVideo3D.getDirection(getSpaceTime().video());
		Vector3.rotateAboutAxis(getSpaceTime().video().getUp(), forward, dTheta);
	}			

	protected void toggleLights() {
		getThe(Jme.class).getVideoState().getLightsEnabled().negate();
	}
	protected void toggleFullscreen() {
		getThe(Jme.class).getVideoState().getFullScreen().negate();			
	}

	protected void adjustLights(double d) {
		getThe(Jme.class).getVideoState().getAmbientLightLevel().add(d);
	}
	
	protected void toggleFaceConsole() {
		if (faceConsole == null) {
			double x = 0;
			double y = -0.25;
			double z = 0;
			double w = 0.5;
			double h = 0.5;
			
			faceConsole = new GroovyTerminal(40, 10);
			faceConsole.bind("jme", getThe(Jme.class));
			faceConsole.bind("space", spaceTime);
			
			faceConsole.move(x, y, z).size(w, h);
			spaceTime.face().add(faceConsole);


		}
		else {
			spaceTime.face().remove(faceConsole);
			faceConsole = null;
		}
	}

}