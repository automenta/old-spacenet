package automenta.spacenet.space.audio.jme.javasound;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;

import org.apache.log4j.Logger;

import automenta.spacenet.space.audio.Audio;

/**
 * 
 * @link http://www.jsresources.org/examples/SimpleAudioPlayer.java.html
 * @link http://www.jsresources.org/examples/ClipPlayer.java.html
 * @link http://www.jsresources.org/examples/OscillatorPlayer.java.html
 */
public class JavaAudioSystem /* implements Audio, LineListener */ {
//	private static Logger logger = Logger.getLogger(JavaAudioSystem.class);
//
//	private static final int	BUFFER_SIZE = 128000;
//	private static boolean		DEBUG = false;
//	private AudioSystemOptions options;
//	private float fSampleRate;
//	private AudioFormat audioFormat;
//	private Info info;
//	private SourceDataLine playback;
//	private Map<Clip, SoundURI> audioClips = new HashMap();
//
//
//	public JavaAudioSystem(AudioSystemOptions options) {
//		super();
//		this.options = options;
//
//		fSampleRate = 44100.0F;
//
//		audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,  fSampleRate, 16, 2, 4, fSampleRate, false);
//
//		info = new DataLine.Info( SourceDataLine.class,	audioFormat);
//
//		try
//		{
//			System.out.println(audioFormat);
//			playback = (SourceDataLine) javax.sound.sampled.AudioSystem.getLine(info);
//			playback.open();
//			//playback.open(audioFormat);
//		}
//		catch (LineUnavailableException e)
//		{
//			e.printStackTrace();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//
//	}
//
//
//	@Override public double samplesToSeconds(long samples) {
//		return fSampleRate / fSampleRate;
//	}
//
//	@Override public PlaySound start(PlaySound ps) throws Exception {
//		if (ps.getSound() instanceof SampledSound) {
//			SampledSound s = (SampledSound)ps.getSound();
//			startSampledSound(s);
//		}
//		return null;
//	}
//
//	private void startSampledSound(SampledSound s) throws Exception {
//
//		URL u = new URL(s.getSoundID());
//
//		System.out.println("starting: " +  s + " " +  s.getSoundID() + " " + u);
//
//		AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(u);
//
//		AudioFormat	format = audioInputStream.getFormat();
//		DataLine.Info	info = new DataLine.Info(Clip.class, format);
//
//		Clip audioClip;
//		try
//		{
//			audioClip = (Clip) javax.sound.sampled.AudioSystem.getLine(info);
//			audioClips.put(audioClip, s);
//			audioClip.addLineListener(this);
//			audioClip.open(audioInputStream);
//			s.setAudioSystem(this);
//			audioClip.start();
//			s.hasStarted();
//		}
//		catch (LineUnavailableException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//
//
//
//	}
//
//	@Override public PlaySound stop(PlaySound ps) {
//		logger.warn(this + " stop(PlayingSound) is not supported yet");
//		return null;
//	}
//
//	@Override public void dispose() {
//		if (playback!=null) {
//			if (playback.isRunning() || playback.isOpen()) {
//				stopJme(this);
//				playback = null;
//			}
//		}
//
//	}
//
//	@Override public void start() {
//		playback.start();
//	}
//
//	@Override public void stop() {
//		playback.stop();
//		playback.close();
//	}
//
//	@Override public AudioSystemOptions getOptions() {
//		return options;
//	}
//
//	@Override public void update(LineEvent event) {
//		if (event.getType().equals(LineEvent.Type.STOP))
//		{
//
//			if (event.getSource() instanceof Clip) {
//				Clip c = (Clip) event.getSource();
//				SoundURI sound = audioClips.getEntity( c );
//				if (sound == null) {
//					logger.warn(this + " received " + event + " about non-active sound");
//					return;
//				}
//
//				sound.hasFinished();
//				audioClips.remove(c);
//				c.removeLineListener(this);
//			}
//
//
//		}
//		else if (event.getType().equals(LineEvent.Type.CLOSE))
//		{
//			/*
//			 *	There is a bug in the jdk1.3/1.4.
//			 *	It prevents correct termination of the VM.
//			 *	So we have to exit ourselves.
//			 */
//			System.exit(0);
//		}
//
//		// TODO Auto-generated method stub
//
//	}
//
//
////	public static void main(String[] args)	throws	IOException
////	{
////
////		byte[]		abData;
////
////		int	nWaveformType = Oscillator.WAVEFORM_SINE;
////		AudioInputStream	oscillator = new Oscillator(
////				nWaveformType,
////				fSignalFrequency,
////				fAmplitude,
////				audioFormat,
////				AudioSystem.NOT_SPECIFIED);
////
////		abData = new byte[BUFFER_SIZE];
////		while (true)
////		{
////			if (DEBUG) { out("OscillatorPlayer.main(): trying to read (bytes): " + abData.length); }
////			int	nRead = oscillator.read(abData);
////			if (DEBUG) { out("OscillatorPlayer.main(): in loop, read (bytes): " + nRead); }
////			int	nWritten = playback.write(abData, 0, nRead);
////			if (DEBUG) { out("OscillatorPlayer.main(): written: " + nWritten); }
////		}
////	}
//
//
////	private static int getWaveformType(String strWaveformType)
////	{
////		int	nWaveformType = Oscillator.WAVEFORM_SINE;
////		strWaveformType = strWaveformType.trim().toLowerCase();
////		if (strWaveformType.equals("sine"))
////		{
////			nWaveformType = Oscillator.WAVEFORM_SINE;
////		}
////		else if (strWaveformType.equals("square"))
////		{
////			nWaveformType = Oscillator.WAVEFORM_SQUARE;
////		}
////		else if (strWaveformType.equals("triangle"))
////		{
////			nWaveformType = Oscillator.WAVEFORM_TRIANGLE;
////		}
////		else if (strWaveformType.equals("sawtooth"))
////		{
////			nWaveformType = Oscillator.WAVEFORM_SAWTOOTH;
////		}
////		return nWaveformType;
////	}
//
//
}
