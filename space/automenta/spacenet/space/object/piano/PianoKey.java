/**
 * 
 */
package automenta.spacenet.space.object.piano;

import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pressable;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class PianoKey extends Box implements Touchable, Pressable {

	private PianoKeyControl piano;
	private double freq;
	private double originalZ;
	private double pressDistance;
	private boolean latch;
	private boolean pressed;

	public PianoKey(PianoKeyControl piano, Color c, double freq, double pressDistance, boolean latch) {
		super(c);
		this.piano = piano;
		this.freq = freq;
		this.pressDistance = pressDistance;
		this.latch = latch;
	}

	@Override public void startTouch(Pointer c) {
	}

	@Override public void stopTouch(Pointer c) {
		
	}

	@Override public void updateTouch(Pointer c, double timeTouched) {
		
	}

	protected void down() {
		originalZ = getPosition().z();
		getPosition().setZ(originalZ - pressDistance);
		
		piano.pianoKeyChange(true, freq);
		
		pressed = true;
	}
	
	protected void up() {
		double newZ = originalZ;
		getPosition().setZ(newZ);

		piano.pianoKeyChange(false, freq);
		
		pressed = false;
	}
	
	@Override public void pressStart(Pointer c, Vector3 location,	Vector2 relativeToRect) {
		if (latch) {
			if (pressed) {
				up();
			}
			else {
				down();
			}
		}
		else {
			down();
		}
	}

	@Override public void pressStopped(Pointer c) {
		if (!latch) {
			up();
		}
		
	}

	@Override public void pressUpdate(Pointer c, double timePressed) {
		
	}
	
}