/**
 * 
 */
package automenta.spacenet.space.object.measure;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom2.OverlayRect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.string.StringVar;

public class CursorStatusOverlay extends OverlayRect implements StartsIn<Scope> {
	private Pointer cursor;

	double textUpdatePeriod = 0.25;
	double cursorUpdatePeriod = 0.02;
	
	StringVar leftCaption = new StringVar();
	StringVar rightCaption = new StringVar();
	
	public CursorStatusOverlay(Pointer c) {
		super();
		this.cursor = c;
	}
	
	@Override
	public void start(Scope c) {
		
		add(new TextRect(leftCaption).
				scale(0.2,0.4).move(-0.25,0));
		add(new TextRect(rightCaption).
				scale(0.8,0.8).move(0.25,0));
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				leftCaption.set(getButtonString(cursor.getButtons()));
				
				String r = cursor.getPixelPosition().toString() + "\n";
				r += cursor.getTouchedNode() + "\n";
				rightCaption.set(r);
				return textUpdatePeriod;
			}			
		});
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double cx = cursor.getPixelPosition().x();
				double cy = cursor.getPixelPosition().y();
				
				getPosition().set(cx, cy,0);
				return cursorUpdatePeriod;
			}

		});
	}

	private String getButtonString(MapVar<Object, BooleanVar> buttons) {
		String s = "";
		for (Object o : buttons.keySet()) {
			if (buttons.get(o).get()) {
				s += "+\n";
			}
			else {
				s += "-\n";
			}
		}
		return s;
	}				

}