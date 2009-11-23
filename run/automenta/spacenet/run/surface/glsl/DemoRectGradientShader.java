package automenta.spacenet.run.surface.glsl;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.geometry.DemoRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.vector.Vector3;

public class DemoRectGradientShader extends DemoRect {

	public static GLSLSurface newGridSurface() {
		UURI vert = new UURI("file:../spacenet.media/glsl/rectgradient.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/rectgradient.frag");
		GLSLSurface s = new GLSLSurface(vert, frag);
//		s.getVars().put("TLcolor", Color.Orange);
//		s.getVars().put("BRcolor", Color.Blue);
		s.getVars().put("TLcolor", Color.White);
		s.getVars().put("BRcolor", Color.Black);

		s.getVars().put("LightPosition", new Vector3(0,0,4));
		return s;
	}
	
	@Override protected Surface newSurface() {
		return newGridSurface();
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoRectGradientShader().scale(8));
	}

}
