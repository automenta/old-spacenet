package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.RectBorder;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class MetaFrame extends Box implements Starts, Stops {

	private Space space;

	public MetaFrame(Space s) {
		super();
	
		setSpace(s);
	}

	public void setSpace(Space s) {
		this.space = s;		
	}

	@Override public void start() {
		final Rect r = add(new Rect());
		r.add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				Vector3 p = ((HasPosition3)space).getAbsolutePosition();
				r.setNextAbsolutePosition(p.x(), p.y(), p.z());
				return 0;
			}
		});
		r.add(new RectBorder(new ColorSurface(Color.Orange.alpha(0.5)), new DoubleVar(1.2), new DoubleVar(1.5)));
	}
	
	@Override public void stop() {
		
	}
	
	
}
