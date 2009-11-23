package automenta.spacenet.run.surface;

import java.net.URL;

import automenta.spacenet.UURI;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.BitmapRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.surface.BitmapSurface;

public class DemoBitmap extends ProcessBox {
	int i = 0;

	
	@Override public void run() {
		final URL u1 = getClass().getResource("data/face-grin.png");
		final URL u2 = getClass().getResource("data/face-wink.png");
		
		final UURI uri = new UURI(u1);
		
		Window w = add(new Window());
		
		Rect r = w.add(new BitmapRect(new BitmapSurface(uri) ));
		r.scale(0.9);
		r.moveDZ(0.05);

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				if (i%2 == 0) {
					uri.set(u2);
				}
				else
					uri.set(u1);
				
				i++;
				
				return 0.25;
			}
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoBitmap().scale(4));
	}
}
