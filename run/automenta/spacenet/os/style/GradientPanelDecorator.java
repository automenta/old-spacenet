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
import automenta.spacenet.var.vector.Vector3;

public class GradientPanelDecorator implements PanelDecorator {

	private GLSLSurface s;

	public GradientPanelDecorator() {
		UURI vert = new UURI("file:../media/glsl/rectgradient.vert");
		UURI frag = new UURI("file:../media/glsl/rectgradient.frag");
		s = new GLSLSurface(vert, frag);
		s.getVars().put("TLcolor", new Color(0.65, 0.65, 0.65));
		s.getVars().put("BRcolor", new Color(0.3, 0.3, 0.3));

		s.getVars().put("LightPosition", new Vector3(0,0,4));
	}

	@Override
	public void decoratePanel(Panel p) {
		if (p.hasBack())
			p.getPanelRect().surface(s);
		else
			p.getPanelRect().surface(new ColorSurface(Color.Invisible));
	}


}