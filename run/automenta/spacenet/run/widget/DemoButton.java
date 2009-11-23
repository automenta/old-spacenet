package automenta.spacenet.run.widget;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.object.widget.Widget;
import automenta.spacenet.space.object.widget.button.TextButton;


public class DemoButton extends DemoPanel {

	@Override public Widget newWidget() {
		return new TextButton("Button");
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoButton());		
	}
	
}
