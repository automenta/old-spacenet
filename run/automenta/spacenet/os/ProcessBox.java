package automenta.spacenet.os;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.audio.Audio;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.video3d.Video3D;

/** a box in which a self-contained process executes.  its dimensions are normalized to +/-<0.5,0.5,0.5>  */
abstract public class ProcessBox extends Box implements Starts, Spacetime, Runnable {
	private static final Logger logger = Logger.getLogger(ProcessBox.class);

	private Jme jme;

	private Spacetime spacetime;
	
	@Override public void start() {
		spacetime = getThe(Spacetime.class);
		
		tangible(false);

		run();
	}

	@Override public void stop() {	}

	public Spacetime spacetime() {
		return spacetime;
	}
	
	@Override public Video3D video() {
		return spacetime().video();
	}

	@Override public Pointer pointer() {
		return spacetime().pointer();
	}

	@Override public Keyboard keyboard() {
		return spacetime().keyboard();
	}

	@Override public Box face() {
		return spacetime().face();
	}

	@Override public Space sky() {
		return spacetime().sky();
	}

	@Override public Audio audio() {
		return spacetime().audio();
	}

 
	@Override public Scheduler time() {
		return spacetime().time();
	}

	/** root volume space of the SpaceTime this is run inside of */
	@Override public Space volume() {
		return spacetime().volume();
	}

}
