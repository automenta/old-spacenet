/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.UURI;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.text.TextEditRectDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class ShadedTextEditRectDecorator implements TextEditRectDecorator {
	private GLSLSurface trSurface;

	public ShadedTextEditRectDecorator() {
		super();

		UURI vert = new UURI("file:../spacenet.media/glsl/rectborder.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/rectborder.frag");

		this.trSurface = new GLSLSurface(vert, frag);
		trSurface.getVars().put("Color", new Color(0.1f, 0.1f, 0.1f, 1.0));
		trSurface.getVars().put("BorderColor", new Color(0.2, 0.2, 0.2, 0.2));
		trSurface.getVars().put("BorderSize", new DoubleVar(0.05));
		trSurface.getVars().put("BorderShade", new DoubleVar(0.01));
		trSurface.getVars().put("LightPosition", new Vector3(0,0,1));
	}

	@Override public void decorateTextEditRect(TextEditRect t) {
		t.getPanelRect().surface(trSurface);
	}

}