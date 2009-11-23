package automenta.spacenet.space.object.widget.icon;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.widget.button.IconButton;

public class SquareIcon extends AbstractIcon {

	public static class SquareIconBuilder implements RectBuilder {
		@Override public Rect newRect(Object y) {
			Rect r = new Rect();
			r.add(new SquareIcon(y));
			return r;
		}				
	}
	

	public SquareIcon(Object object) {
		super(object);
	}

	@Override
	protected void layoutIcon() {
		double x1 = -0.25;
		double y1 = 0.5;
		double x2 = 0.25;
		double y2 = 0;
		
		if (glyph instanceof Rect) {
			((Rect)glyph).span(x1, y1, x2, y2);
			((Rect)glyph).moveDZ(0.02);
		}
		
		if (glyph instanceof Box)
			((Box)glyph).span(x1, y1, x2, y2);
		
		if (glyph instanceof IconButton)
			((IconButton)glyph).getPanelRect().aspect(1, 1);

		text.span(-0.5, -0.1, 0.5, -0.5);
		widgets().pullDZ(glyph, text);
	}

}
