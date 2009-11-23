/**
 * 
 */
package automenta.spacenet.space.control.keyboard;

import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;

abstract public class IfKeyPressed extends IfBoolChanges {
	public IfKeyPressed(Keyboard keyboard, int keyCode) {
		super(keyboard.getKey(keyCode));
	}

	@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
		if (nextValue)
			afterKeyPressed(b);
	}

	abstract public void afterKeyPressed(BooleanVar key);			
}