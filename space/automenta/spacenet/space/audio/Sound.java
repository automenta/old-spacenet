package automenta.spacenet.space.audio;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;


abstract public class Sound implements StartsIn<Scope>, Stops {
	
	private static final Logger logger = Logger.getLogger(Sound.class);
		
	private Audio audio;

	@Override public void start(Scope n) {
		audio = n.getThe(Audio.class);
		try {
			audio.startSound(this);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	@Override public void stop() {
		if (audio!=null) {
			audio.stopSound(this);
		}
	}
	
}
