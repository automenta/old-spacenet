package automenta.spacenet.run.surface;

import java.net.URL;

import automenta.spacenet.UURI;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.run.surface.glsl.DemoBrickShader;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.surface.GLSLSurface;

/** tests applying multiple kinds of surfaces, replacing w/ each new one so that none interfere simultaneously */
public class DemoMultiSurface extends ProcessBox {

	private Rect rect;

	int c = 0;

	private BitmapSurface bitmapSurface;

	private GLSLSurface shaderSurface;
	
	@Override public void run() {
		final URL u1 = getClass().getResource("data/face-grin.png");		
		final UURI uri = new UURI(u1);		
		bitmapSurface = new BitmapSurface(uri);

		shaderSurface = DemoBrickShader.newBrickSurface();
		
		double dz = 0.2;
		rect = add(new Rect().moveDZ(dz));
		
		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				if (c % 3 == 0) {
					applyBitmapSurface();
				}
				else if (c % 3 == 1) {
					applyShaderSurface();
				}
				else if (c % 3 == 2) {
					applyColorSurface();
				}	
				
				c++;				
				return 0.8;
			}

		});
	}
	
	private void applyShaderSurface() {
		rect.surface(shaderSurface);
	}
	
	private void applyColorSurface() {
		rect.surface(new ColorSurface(Color.Purple.alpha(0.5)));
	}
	
	private void applyBitmapSurface() {
		rect.surface(bitmapSurface);
	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoMultiSurface());
	}
}
