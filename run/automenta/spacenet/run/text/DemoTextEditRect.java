package automenta.spacenet.run.text;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.string.StringVar;

public class DemoTextEditRect extends ProcessBox {


	private StringVar text;

	@Override public void run() {
		
		text = new StringVar("Type here");
		
		Window win = add(new Window());
		win.move(-0.6,0);		
		
		final TextEditRect te = win.add(new TextEditRect(text, 20));
		
		final StringVar textEditRectStats = new StringVar();
		add(new TextRect(textEditRectStats ).move(0.6,0.6));
		
		final TextRect mirror = add(new TextRect());
		mirror.move(0.6, -0.6);
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				String s = "";
				
				s += "dim: " + te.getCharsWide() + ", " + te.getCharsHigh() + "\n"; 
				s += "cursor: " + te.getCursor() + " / " + te.getNumChars();
				
				textEditRectStats.set(s);
				
				String j = new String(te.getText().s());
				j = j.replace('\n', '#');
				mirror.getText().set(j);
				
				return 0.25;
			}
		});
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoTextEditRect().scale(1));
	}
}
