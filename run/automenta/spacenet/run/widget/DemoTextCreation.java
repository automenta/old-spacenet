package automenta.spacenet.run.widget;

import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.string.StringVar;

public class DemoTextCreation extends Box implements Starts {


	@Override public void start() {

		newInput();
	}
	@Override public void stop() {

	}

	private void newInput() {
		Window w = (Window) new Window().scale(10.0, 7.0);
		StringVar text = new StringVar("Edit this");

		TextEditRect t = new TextEditRect(text) {
			@Override protected void enter() {
				super.enter();

				setEditable(false);

				newInput();
			}
		};

		t.move(0,0,0.1);
		w.add(t);

		add(w);
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoTextCreation());
	}
}
