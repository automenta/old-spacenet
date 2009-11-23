/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.UURI;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.panel.PanelDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class ShadedPanelDecorator implements PanelDecorator {
	private GLSLSurface panelSurface;

	public ShadedPanelDecorator() {
		super();

		UURI vert = new UURI("file:../spacenet.media/glsl/rectborder.vert");
		UURI frag = new UURI("file:../spacenet.media/glsl/rectborder.frag");

		this.panelSurface = new GLSLSurface(vert, frag);
		panelSurface.getVars().put("Color", new Color(0.4f, 0.4f, 0.4f, 1.0));
		panelSurface.getVars().put("BorderColor", new Color(0.3, 0.3, 0.3, 0.6));
		panelSurface.getVars().put("BorderSize", new DoubleVar(0.03));
		panelSurface.getVars().put("BorderShade", new DoubleVar(0.03));
		panelSurface.getVars().put("LightPosition", new Vector3(0,0,1));
	}

	@Override public void decoratePanel(Panel p) {
		if (p.hasBack())
			p.getPanelRect().surface(panelSurface);
		else
			p.getPanelRect().surface(new ColorSurface(Color.Invisible));
	}		
}