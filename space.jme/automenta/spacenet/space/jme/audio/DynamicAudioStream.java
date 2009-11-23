/**
 * 
 */
package automenta.spacenet.space.jme.audio;

import java.io.IOException;
import java.nio.ByteBuffer;

import automenta.spacenet.space.audio.DynamicSound;

import com.jmex.audio.stream.AudioInputStream;

abstract class DynamicAudioStream extends AudioInputStream {

	private static final double bufferSeconds = 0.05;
	private int samplesPerSecond = 11025;

	public DynamicAudioStream() throws IOException {
		
		//supply a dummy URL for the superclass
		super(DynamicAudioStream.class.getClassLoader().getResource("."), (float) bufferSeconds);
	}
	
	@Override public int available() {
		return (int)(samplesPerSecond * bufferSeconds);
	}

	public int getSamplesPerSecond() { return samplesPerSecond; }
	
	public int getBytesPerSample() {
		return getChannelCount() * getDepth()/8;		
	}

	public int getBytesPerSecond() {
		return getChannelCount() * getDepth()/8 * samplesPerSecond;
	}

	@Override public int getChannelCount() {
		return 1;
	}

	@Override public int getDepth() {
		return 16;
	}

	@Override public AudioInputStream makeNew() throws IOException {
		return this;
	}

	@Override
	abstract public int read(ByteBuffer b, int offset, int length) throws IOException;

//	/** synthesizes the next audio buffer */
//	@Override public int read(ByteBuffer b, int offset, int length)	throws IOException {
//		//System.out.println("read: " + b+ " " + offset + " " + length);
//		
//		for (int i = offset; i < length; i++) {
//			b.put(i, (byte)(Math.random() * 200));
//		}
//		return length;
//	}
	
	@Override
	public int getBitRate() {
		return getBytesPerSecond() * 8;
	}

	abstract public DynamicSound getSound();
}