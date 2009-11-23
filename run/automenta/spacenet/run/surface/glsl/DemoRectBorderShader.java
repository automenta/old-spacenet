package automenta.spacenet.run.surface.glsl;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.geometry.DemoRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoRectBorderShader extends DemoRect {

	public static GLSLSurface newGridSurface() {
		UURI vert = new UURI("file:../spacenet.media/glsl/rectborder.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/rectborder.frag");
		GLSLSurface s = new GLSLSurface(vert, frag);
		s.getVars().put("Color", new Color(0.8f, 0.8f, 0.8f, 1.0));
		s.getVars().put("BorderColor", new Color(0.2, 0.2, 0.2, 0.2));
		s.getVars().put("BorderSize", new DoubleVar(0.1));
		s.getVars().put("BorderShade", new DoubleVar(0.05));
		s.getVars().put("LightPosition", new Vector3(0,0,4));
		return s;
	}
	
	@Override protected Surface newSurface() {
		return newGridSurface();
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoRectBorderShader().scale(8));
	}

}
