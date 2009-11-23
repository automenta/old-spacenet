package automenta.spacenet.run.sound;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.FaceCamera;
import automenta.spacenet.space.object.piano.PianoOctaveBox;

public class PianoSpace extends ProcessBox {

	
	@Override public void run() {
		double r = 4;
		int numPianos = 64;
		
		for (int i = 0; i < numPianos; i++) {
			PianoOctaveBox p = add(new PianoOctaveBox());
			
			p.move(Maths.random(-r, r), Maths.random(-r, r), Maths.random(-r, r));
			
			p.add(new FaceCamera());
		}
		
	}
	
	public static void main(String[] args) {
		
		new DefaultJmeWindow(new PianoSpace());
	}
}
