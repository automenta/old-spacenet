package automenta.spacenet.run.widget;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.window.Window;

public class DemoWindow2 extends ProcessBox {

	@Override  public void run() {
		Box b = add(new Box().move(2,0,0).scale(1.5));
		
		b.add(new Window());
	
		add(new Window());
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoWindow2());		
	}
}
