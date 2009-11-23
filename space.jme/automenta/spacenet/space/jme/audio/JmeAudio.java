package automenta.spacenet.space.jme.audio;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Scope;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.audio.Audio;
import automenta.spacenet.space.audio.Audio3D;
import automenta.spacenet.space.audio.DynamicSound;
import automenta.spacenet.space.audio.Sound;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;

import com.jmex.audio.AudioTrack;


/** JME audio interface. implemented as a list of the sounds in playback (or paused). */
public class JmeAudio extends Scope implements Audio3D, Disposable, Starts, Stops {
	private static Logger logger = Logger.getLogger(Audio.class);
	
	//TODO use DoubleRangeVar 0..1
	DoubleVar mainVolume = new DoubleVar(1.0);
	
	float sampleRate = 11025;
	int bitRate = 16;
	long currentSample = 0;
	
	private com.jmex.audio.AudioSystem audioSys;

	Map<Sound,AudioTrack> soundTracks = new HashMap();
	
	public JmeAudio() throws Exception {
		this(com.jmex.audio.AudioSystem.getSystem());
	}
	
	public JmeAudio(com.jmex.audio.AudioSystem audioSys) throws Exception {
		super();
		
		this.audioSys = audioSys;
		
		if (!com.jmex.audio.AudioSystem.isCreated())
			throw new Exception(this + " unable to create com.jmex.audio.AudioSystem");
		
		audioSys.getEar().getPosition().set(0,0,0);
		audioSys.getEar().getFacingVector().set(0,0,-1);
		
//		jmeAudio.getEar().trackOrientation(cam);
//		jmeAudio.getEar().trackPosition(cam);
		
	}


	public void startSound(Sound s) throws Exception {
		
		if (s instanceof DynamicSound) {
			DynamicSound ds = (DynamicSound)s;
			logger.info("starting DynamicSound: " + s);
//			SampledSound ss = (SampledSound) s;
//			String url = ss.getSoundID();
//			
//			System.out.println("starting: " + url);
			final AudioTrack track = new DynamicAudioTrack(new JmeDynamicSound(ds));
			
			add(s, track);
			soundTracks.put(s, track);

			audioSys.getEnvironmentalPool().addTrack(track);
			track.play();

			//TODO remove IfVector3Changes when track is removed 
			add(new IfVector3Changes(ds.getWorldPosition()) {
				@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
					System.out.println(v);
					track.setWorldPosition((float)v.x(), (float)v.y(), (float)v.z());
					track.update(0.0f);
					audioSys.update();
				}				
			});
		}
//		else if (s instanceof SynthesizedSound) {
//		}
		else {
			logger.warn("sound : " + s + " not in " + this.getClass().getSimpleName());
		}
		
	}

	public void stopSound(Sound s) {
		if (s instanceof DynamicSound) {
			logger.info("stopping DynamicSound: " + s + " in " + objects);

			AudioTrack track = soundTracks.remove(s);
			track.stop();
			audioSys.getEnvironmentalPool().removeTrack(track);
//			SampledSound ss = (SampledSound) s;
//			String uri = ss.getSoundID();
			
		}
//		else if (s instanceof SynthesizedSound) {
//		}
		else {
			logger.warn("sound : " + s + " not in " + this.getClass().getSimpleName());
		}
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public double samplesToSeconds(long samples) {
		return ((double)samples) / ((double)getSampleRate());
	}

	public DoubleVar getMainVolume() {
		return mainVolume;
	}


	@Override public void start() {
		
	}

	@Override public void stop() {
		audioSys.cleanup();
	}

//	@Override public AudioSystemOptions getOptions() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override public void dispose() {
		// TODO Auto-generated method stub
		
	}


	
	
}
