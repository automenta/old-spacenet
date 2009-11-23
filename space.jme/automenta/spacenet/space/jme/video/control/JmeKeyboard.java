package automenta.spacenet.space.jme.video.control;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Toggle;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.var.map.MapVar;

import com.jme.input.KeyInput;
import com.jme.input.KeyInputListener;



public class JmeKeyboard extends Keyboard implements Starts, Stops {

	private Jme jme;
	protected KeyInputListener keyListener;
	
	private static MapVar<Integer,Toggle> key = new MapVar();

	double repeatPeriod = 0.07;
	double repeatStartDelay = 0.2;
	
	/** the last key that was pressed, if pressed; -1 if nothing pressed */
	int currentlyPressed = -1;
	char currentlyPressedChar = 0;
	
	public class JmeKeyRepeater extends Repeat {
		double whenFirstPressed = -1;
		int currentlyRepeated = -1;
		char currentlyRepeatedCharacter = 0;
		
		@Override
		public String toString() {
			return "repeating keys that continue to be pressed";
		}
		
		@Override public double repeat(double t, double dt) {
			if (currentlyRepeated == -1) {
				if (currentlyPressed!=-1) {
					//setup repeat
					currentlyRepeated = currentlyPressed;
					currentlyRepeatedCharacter = currentlyPressedChar;
					whenFirstPressed = t;
				}
			}
			if (currentlyRepeated != -1) {
				if (currentlyPressed != currentlyRepeated) {
					//stop repeat
					currentlyRepeated = -1;
					whenFirstPressed = -1;
				}
				else {
					//send repeat
					if (t - whenFirstPressed > repeatStartDelay) {
						onKey(currentlyRepeatedCharacter, currentlyRepeated, true);
					}
				}
			}
			
			return repeatPeriod;
		}
	}

	@Override public String toString() {
		return "noticing keyboard";
	}

	public JmeKeyboard(Jme jme) {
		super();
		this.jme = jme;
	}
	
	@Override public void start() {


		int pw = (int) jme.getPixelSize().x();
		int ph = (int) jme.getPixelSize().y();

		keyListener = new KeyInputListener() {
			@Override public void onKey(char character, int keyCode, boolean pressed) {
				JmeKeyboard.this.onKey(character, keyCode, pressed);
			}			
		};

		jme.add(new JmeKeyRepeater());
		
		Jme.doLater(new Runnable() {
			@Override public void run() {
				KeyInput.get().addListener(keyListener);
			}			
		});
		
	}

	@Override protected void onKey(char character, int keyCode, boolean pressed) {
		getKey(keyCode).set(pressed);
		for (IfKeyboardChanges w : whenChange) {
			w.onKey(character, keyCode, pressed);
		}
		if (pressed) {
			currentlyPressed = keyCode;
			currentlyPressedChar = character;
		}
		else {
			if (keyCode == currentlyPressed) {
				currentlyPressed = -1;				
			}
		}
	}

	@Override public Toggle getKey(int keyCode) {
		Toggle t = key.get(keyCode);
		if (t == null) {
			t = new Toggle(Integer.toString(keyCode)); //KeyInput.get().getKeyName(keyCode));
			key.put(new Integer(keyCode), t);			
		}
		return t;
	}



	@Override public void stop() {
		if (keyListener!=null) {
			Jme.doLater(new Runnable() {
				@Override public void run() {
					KeyInput.get().removeListener(keyListener);
					keyListener = null;
				}			
			});
		}
	}

}
