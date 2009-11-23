/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.vector.Vector3;

public class GradientSurface extends GLSLSurface {
	
	public GradientSurface(Color topLeft, Color bottomRight) {
		super(new UURI("file:../spacenet.media/glsl/rectgradient.vert"), new UURI("file:../spacenet.media/glsl/rectgradient.frag"));

		getVars().put("TLcolor", topLeft);
		getVars().put("BRcolor", bottomRight);
		getVars().put("LightPosition", new Vector3(0,0,4));			
	}
}