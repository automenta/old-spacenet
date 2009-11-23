package automenta.spacenet.run.text;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.object.text.TextRect2;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;

public class DemoTextRect2 extends ProcessBox {

	@Override public void run() {
		Rect r = new Rect(Color.Black.alpha(0.2)).scale(0.5);
		r.add(new TextRect2(new StringVar("Abc")).moveDelta(0,0,0.1));		
		add(new GeometryViewer(r).move(0,0,0));


	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoTextRect2());
	}
}
