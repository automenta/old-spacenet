package automenta.spacenet.space.object.measure;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.RectBorder;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class MeasureRect extends Rect implements StartsIn<Scope>, Stops {

	private boolean updateNecessary;
	private double updatePeriod = 0.5;
	private double borderWidth = 0.05;

	
	public MeasureRect(double x, double y, double w, double h) {
		super(x, y, 0, w, h);
	}
	
	public MeasureRect(double x, double y, double z, double w, double h) {
		super(x, y, z, w, h); //, new ColorSurface(Color.White.deriveAlpha(0.1)));
	}

	@Override public void start(Scope c) {
		final StringVar message = new StringVar("");

		add(new IfVector2Changes(getAbsoluteSize()) {
			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
				update();
			}			
		});
		add(new IfVector3Changes(getAbsolutePosition() ) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				update();
			}			
		});
		
		add(new TextRect(message, 50).move(0,0,0.1).scale(0.8, 0.8));

		add(new RectBorder(new ColorSurface(Color.White), new DoubleVar(1.0-borderWidth/2), new DoubleVar(1.0+borderWidth/2)));
		//start(new Box(3,3,3,7,7,7, new ColorSurface(Color.Green)));
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				if (updateNecessary) {
					String s = 
						 "Pos: " + getPosition() + "\n" +
						 "Abs Pos: " + getAbsolutePosition() + "\n" +
						 "Size: " + getSize() + "\n" + 
						 "Abs Size: " + getAbsoluteSize() + "\n"; 
						
					message.set(s);
					updateNecessary = false;
				}
				return updatePeriod;
			}			
		});
	}

	protected synchronized void update() {
		updateNecessary = true;
	}

	@Override
	public void stop() {
	}
}
