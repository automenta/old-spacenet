/**
 * 
 */
package automenta.spacenet.space.control.keyboard;

import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;

abstract public class IfKeyReleased extends IfBoolChanges {
	public IfKeyReleased(Keyboard keyboard, int keyCode) {
		super(keyboard.getKey(keyCode));
	}

	@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
		if (!nextValue)
			afterKeyReleased(b);
	}

	abstract public void afterKeyReleased(BooleanVar key);			
}