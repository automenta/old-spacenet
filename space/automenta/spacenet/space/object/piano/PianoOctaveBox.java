/**
 * 
 */
package automenta.spacenet.space.object.piano;

import automenta.spacenet.Maths;
import java.nio.FloatBuffer;

import automenta.spacenet.Starts;
import automenta.spacenet.space.audio.DynamicSound;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;

public class PianoOctaveBox extends Box implements Starts, PianoKeyControl {

	double rootFreq = 128.0;
	double frequency = 0;
	float volume = 0.5f;
	boolean latch = false;
	
	@Override public void start() {
		double x = -0.5;			
		double whiteY = 0;
		
		double keyWidth = 1/7.0;
		double keyGap = 0.15;
		
		double w = keyWidth;
		double blackHeight = 0.5;
		double whiteHeight = 1.0;
		double blackY = 0.5 - blackHeight / 2.0 - keyGap/2.0;
		double blackZ = 0.35;

		//White Keys: C D E F G A B
		double whiteWidth = w;
		double blackWidth = w*0.75;

		double pd = 0.25;
		
		double freq = rootFreq;
		
		//C
		add(new PianoKey(this, Color.White.alpha(0.7), freq, pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;
		
		//D
		add(new PianoKey(this, Color.White.alpha(0.7), freq * 1.1224610703829132, pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//E
		add(new PianoKey(this, Color.White.alpha(0.7), freq * 1.259920650088294, pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//F
		add(new PianoKey(this, Color.White.alpha(0.7), freq * (349.228 / 261.626), pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//G
		add(new PianoKey(this, Color.White.alpha(0.7), freq * (391.995 / 261.626), pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//A
		add(new PianoKey(this, Color.White.alpha(0.7), freq * (440 / 261.626), pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//B
		add(new PianoKey(this, Color.White.alpha(0.7), freq * (493.883 / 261.626), pd, latch).move(x, whiteY).scale(whiteWidth*(1.0-keyGap), whiteHeight, 1));			
		x += keyWidth;

		//Black Keys: C# D# F# G# A#
		x = -0.5 + keyWidth/2.0;
		
		//C#
		add(new PianoKey(this, Color.Black.alpha(0.7), freq * 1.059462744528449, pd, latch).move(x, blackY, blackZ).scale(blackWidth*(1.0-keyGap), blackHeight, 1));			
		x += keyWidth;

		//D#
		add(new PianoKey(this, Color.Black.alpha(0.7), freq * (311.127 / 261.626), pd, latch).move(x, blackY, blackZ).scale(blackWidth*(1.0-keyGap), blackHeight, 1));			
		x += keyWidth * 2;

		//F#
		add(new PianoKey(this, Color.Black.alpha(0.7), freq * (369.994 / 261.626), pd, latch).move(x, blackY, blackZ).scale(blackWidth*(1.0-keyGap), blackHeight, 1));			
		x += keyWidth;

		//G#
		add(new PianoKey(this, Color.Black.alpha(0.7), freq * (415.305 / 261.626), pd, latch).move(x, blackY, blackZ).scale(blackWidth*(1.0-keyGap), blackHeight, 1));			
		x += keyWidth;

		//A#
		add(new PianoKey(this, Color.Black.alpha(0.7), freq * (466.164 / 261.626), pd, latch).move(x, blackY, blackZ).scale(blackWidth*(1.0-keyGap), blackHeight, 1));			
		x += keyWidth;

		
		add(new DynamicSound() {
			double T = 0;
			
			@Override public int forward(double t, double secondsPerSample, FloatBuffer db) {				
				float s;
				//System.out.print(T + ", " + dt + " > " + frequency + " " + db.remaining());
				int i;
				
				
				int samplesToGenerate = db.remaining()/2;
				
				for (i = 0; i < samplesToGenerate; i++) {
					if (frequency > 0) {
						s = (float) (volume * Math.sin((T * frequency)));
					}
					else {
						s = 0;
					}
					
					T += secondsPerSample;
					db.put(s);
				}
								
				
				return samplesToGenerate;
			}			
		});

	}

	@Override public void stop() {
		
	}

	@Override public void pianoKeyChange(boolean on, double freq) {
		if (on) {
			frequency = freq;
		}
		else {
			frequency = 0;
		}
	}
	
	
}