package automenta.spacenet.run.sound;


import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.piano.PianoOctaveBox;


public class PianoStereo extends ProcessBox {

	private PianoOctaveBox leftPiano;
	private PianoOctaveBox rightPiano;
	
	@Override public void run() {
		leftPiano = (PianoOctaveBox) add(new PianoOctaveBox()).scale(2,1,0.2).move(0,-0.75,0);
		rightPiano = (PianoOctaveBox) add(new PianoOctaveBox()).scale(2,1,0.2).move(0,0.75,0);
		
	}

	
	public static void main(String[] args) {	
		new DefaultJmeWindow(new PianoStereo());
	}

}
