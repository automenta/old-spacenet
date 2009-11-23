package automenta.spacenet.run.surface.glsl;

import org.apache.log4j.Logger;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.geometry.DemoRect;
import automenta.spacenet.space.surface.GLSLSurface;



public class DemoGridShader extends DemoRect {
	private static final Logger logger = Logger.getLogger(DemoGridShader.class);

	public static GLSLSurface newGridSurface() {
		UURI vert = new UURI("file:../spacenet.media/glsl/grid.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/grid.frag");
		return new GLSLSurface(vert, frag);		
	}
	
	@Override protected Surface newSurface() {
		return newGridSurface();
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoGridShader().scale(8));
	}
	
}
