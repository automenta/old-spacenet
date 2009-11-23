package automenta.spacenet.run.text;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;

public class DemoTextRect extends ProcessBox {

	@Override public void run() {
		final Color textColor = new Color();
		
		Rect r = new Rect(Color.Black.alpha(0.2)).scale(0.5);
		r.add(new TextRect(new StringVar("Abc"),textColor).moveDelta(0,0,0.1));		
		add(new GeometryViewer(r));

//		Rect s = new Rect(Color.Black.alpha(0.2)).scale(0.5);
//		s.add(new CharRect(new Character('x'), getThe(VectorFont.class)).moveDelta(0,0,0.1));		
		//add(new ObjectWindow(s).move(2,0,0));

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double r = Math.sin(t)/2.0 + 0.5;
				double g = Math.cos(t)/2.0 + 0.5;
				double b = Math.sin(2*t)/2.0 + 0.5;
				double a = Math.sin(t/4.0)/2.0 + 0.5;
				textColor.set(r, g, b, a);
				return 0;
			}
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoTextRect());
	}
}
