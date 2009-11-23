package automenta.spacenet.space.audio;

/** audio system interface */
public interface Audio {
	
	abstract public void startSound(Sound s) throws Exception;
	abstract public void stopSound(Sound s);

}
