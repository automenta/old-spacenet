package automenta.spacenet.run.widget;


import automenta.spacenet.plugin.groovy.GroovyScope;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.FaceCamera;
import automenta.spacenet.space.dynamic.SpaceFifo;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.string.StringVar;

public class DemoMultipleTextConsole extends ProcessBox {


	private GroovyScope groovy;

	private SpaceFifo fifo;

	@Override public void run() {
		
		groovy = add(new GroovyScope());
		groovy.bind("node", this);
		
		newConsole(0,0);
	}

	private void newConsole(final double x, final double y) {
		int maxCharsPerLine = 40;
		
		final Window w = add(new Window());
		w.size(5.0, 5.0);
		w.move(x,y,0);		
		w.add(new FaceCamera());

		StringVar text = new StringVar("Edit this");

		TextEditRect t = w.add(new TextEditRect(text, maxCharsPerLine) {
			@Override protected void enter() {
				super.enter();
				
				setEditable(false);
		
				Object value = groovy.evaluate(getText().s());
				
				try {
					getText().set("> " + getText().s().replace('\n', ' ') + "\n\n" + value.toString());
				}
				catch (Exception e) {
					getText().set(e.toString());
				}
				
				newConsole(getAbsolutePosition().x(), getAbsolutePosition().y() - getAbsoluteSize().y() * 3.5);
			}
		});
				
	}


	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMultipleTextConsole());
	}
}
