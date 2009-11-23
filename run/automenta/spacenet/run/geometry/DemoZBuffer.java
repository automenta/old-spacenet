package automenta.spacenet.run.geometry;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;

/** compares z-buffer and draw-order */
public class DemoZBuffer extends ProcessBox {

	@Override public void run() {
		Rect c = new Rect();
		c.add(new Rect(Color.Blue.alpha(0.5)).span(-1,-1,1,1).scale(0.4));
		c.add(new Rect(Color.Red.alpha(0.5)).span(-1,-1,1,1).scale(0.3));
		c.add(new CharRect('@', getThe(VectorFont.class)).span(-1,-1,1,1).scale(0.2));

		add(new GeometryViewer(c));
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoZBuffer());
	}
}
