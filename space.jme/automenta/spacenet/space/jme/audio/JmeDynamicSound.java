package automenta.spacenet.space.jme.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import automenta.spacenet.space.audio.DynamicSound;


public class JmeDynamicSound extends DynamicAudioStream {

	private DynamicSound sound;
	FloatBuffer db;
	private double t;

	public JmeDynamicSound(DynamicSound sound) throws Exception {
		super();
		this.sound = sound;
	}
	
	@Override public DynamicSound getSound() {
		return sound;
	}

	@Override
	public int read(ByteBuffer b, int offset, int length) throws IOException {
		int maxSamples = length / getBytesPerSample();	//may need to be length/2 or length/4, depending on channels and bitdepth
	
		
		//System.out.println(length + " " + getBytesPerSample());
		
		//System.out.println(offset + "/" + length + ": t= " + t + ", samples=" + maxSamples);
		
		if (db == null) {
			db = FloatBuffer.allocate(maxSamples);
		}
		else if (db.capacity() < maxSamples) {
			db = FloatBuffer.allocate(maxSamples);
		}
				
		db.limit(maxSamples);
		db.rewind();
		
		double secondsPerSample = 1.0 / (getSamplesPerSecond());
		
		int samplesGenerated = sound.forward(t, secondsPerSample, db);
		//t += ((double)samplesGenerated) / ((double)getSamplesPerSecond());
		
		//float[] f = db.array();
		db.rewind();
		float a = Short.MAX_VALUE;
		for (int i = 0; i < samplesGenerated; i++) {
			b.putShort((short)(db.get()* a));
			
			//b.putShort((short)(Math.random() * Short.MAX_VALUE));			
		}
		
		return samplesGenerated * getBytesPerSample();
	}


}
