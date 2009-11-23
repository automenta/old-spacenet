/**
 * 
 */
package automenta.spacenet.space.jme.audio;

import com.jmex.audio.AudioTrack;
import com.jmex.audio.openal.OpenALStreamedAudioPlayer;
import com.jmex.audio.player.StreamedAudioPlayer;


public class DynamicAudioTrack extends AudioTrack {
	
	private StreamedAudioPlayer stream;
	private DynamicAudioStream dynStream;

	public DynamicAudioTrack(DynamicAudioStream das) {
		//supply dummy URL for superclass
		super(DynamicAudioStream.class.getClassLoader().getResource("."), true);

		this.dynStream = das;
		
		try {
			this.stream = new OpenALStreamedAudioPlayer(das, this);
			stream.init();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		setPlayer(stream);


		setMinVolume(0.0f);
		setMaxVolume(1.0f);
		setType(TrackType.POSITIONAL);
		setRelative(true);
		setTargetVolume(1.0F);
		
		setReferenceDistance(0.1f);
		setMaxAudibleDistance(5.0f);
		autosetRolloff();
		
		setEnabled(true);

	}


	
}