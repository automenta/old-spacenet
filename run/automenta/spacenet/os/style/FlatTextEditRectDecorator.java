/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.text.TextEditRectDecorator;
import automenta.spacenet.space.Color;

public class FlatTextEditRectDecorator implements TextEditRectDecorator {
	private Color color = Color.Black;
	
	public FlatTextEditRectDecorator() {
		super();
	}

	@Override public void decorateTextEditRect(TextEditRect t) {
		t.getPanelRect().color(color);
	}

}