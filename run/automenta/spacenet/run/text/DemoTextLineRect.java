package automenta.spacenet.run.text;


import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect3;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.StringVar;


public class DemoTextLineRect extends ProcessBox {

	@Override public void run() {

//		{
//			StringVar text = new StringVar("Abc.defg");
//			final VectorFont font = getThe(VectorFont.class);
//
//			Window w = add(new Window());
//			w.add(new TextLineRect(text, font).scale(1,1));
//		}

		{
			final StringVar text = new StringVar("Abc.defg\nZxc vfg\nXvfs");
			final VectorFont font = getThe(VectorFont.class);

			Window w = add(new Window());
			w.add(new TextRect3(text));
			w.move(-1, 0);
			
			Window y = add(new Window());
			y.add(new TextEditRect(text));
			y.move(1, 0);
			
			
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					text.set(Double.toString(t));
					return 0.5;
				}
			});
		}

	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoTextLineRect().scale(5));
	}


}
