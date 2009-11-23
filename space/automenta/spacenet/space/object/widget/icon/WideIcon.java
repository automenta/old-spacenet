package automenta.spacenet.space.object.widget.icon;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.button.IconButton;

public class WideIcon extends AbstractIcon {

	public WideIcon(Object object) {
		super(object);
	}

	@Override
	protected void layoutIcon() {
		double x1 = -0.5;
		double y1 = -0.4;
		double x2 = -0.45;
		double y2 = 0.4;
		if (glyph instanceof Rect) {
			((Rect)glyph).span(x1, y1, x2, y2);
		}
		
		if (glyph instanceof Box)
			((Box)glyph).span(x1, y1, x2, y2);
		
		if (glyph instanceof IconButton)
			((IconButton)glyph).getPanelRect().aspect(1, 1);

		
		text.span(-0.3, -0.4, 0.5, 0.4);
		widgets().pullDZ(glyph, text);
	}
	
}
