/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.panel.PanelDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

public class RandomColorPanelDecorator implements PanelDecorator {

	public RandomColorPanelDecorator() {
		super();

	}

	@Override public void decoratePanel(Panel p) {
		ColorSurface panelSurface;
		
		panelSurface = new ColorSurface(Color.newRandomHSB(0.5, 0.2).alpha(0.8));

		if (p.hasBack())
			p.getPanelRect().surface(panelSurface);
		else
			p.getPanelRect().surface(new ColorSurface(Color.Invisible));
	}		
}