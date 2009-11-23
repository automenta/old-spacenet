package automenta.spacenet.run.surface.glsl;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.geometry.DemoRect;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class DemoBrickShader extends DemoRect {


	/*
	 * for Brick shader:
	 * 
	 * 	//			        so.setUniform("BrickColor", 1.0f, 0.3f, 0.2f);
		//			        so.setUniform("MortarColor", 0.85f, 0.86f, 0.84f);
		//			        so.setUniform("BrickSize", 0.30f, 0.15f);
		//			        so.setUniform("BrickPct", 0.90f, 0.85f);
		//			        so.setUniform("LightPosition", 0.0f, 0.0f, 4.0f);

	 * 
	 */
	public static GLSLSurface newBrickSurface() {
		UURI vert = new UURI("file:../spacenet.media/glsl/brick.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/brick.frag");
		GLSLSurface s = new GLSLSurface(vert, frag);
		s.getVars().put("BrickColor", new Vector3(1.0f, 0.3f, 0.2f));
		s.getVars().put("MortarColor", new Vector3(0.85, 0.86, 0.84));
		s.getVars().put("BrickSize", new Vector2(0.3, 0.15));
		s.getVars().put("BrickPct", new Vector2(0.9, 0.85));
		s.getVars().put("LightPosition", new Vector3(0,0,4));
		return s;
	}
	
	@Override protected Surface newSurface() {
		return newBrickSurface();
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoBrickShader().scale(8));
	}

}
