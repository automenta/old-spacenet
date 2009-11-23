/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.panel.PanelDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

public class FlatPanelDecorator implements PanelDecorator {
	private ColorSurface panelSurface;

	public FlatPanelDecorator() {
		super();

		panelSurface = new ColorSurface(Color.Gray.alpha(0.3));
	}

	@Override public void decoratePanel(Panel p) {
		if (p.hasBack())
			p.getPanelRect().surface(panelSurface);
		else
			p.getPanelRect().surface(new ColorSurface(Color.Invisible));
	}		
}