package automenta.spacenet.run.interact;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pressable;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.dynamic.collection.ArrangeGrid;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class DemoTouchPress extends Rect implements Starts, Stops {

	double onTime = 1.0;
	int numPads = 15*15;
	double decayRate = 0.99;
	
	public class Pad extends Rect implements Starts, Stops, Pressable, Touchable {

		private Color c;
		private double pressTime;
		private double touchTime;

		public Pad() {
			super();
			c = new Color();
		}

		@Override public void start() {
			getSurface().set(new ColorSurface(c));
			
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					double b = 0.2;
					double r = b;
					double g = b;
					
					r = b + (1.6 - b) * Math.min(pressTime, onTime) / onTime;
					pressTime *= decayRate;
					g = b + (1.6 - b) * Math.min(touchTime, onTime) / onTime;
					touchTime *= decayRate;
					
					c.set(Math.min(1.0,r),Math.min(1.0,g),Math.min(1.0,b));			
					
					return 0;
				}
			});
			
		}
		
		@Override public void stop() {
			
		}
		
		
		@Override public void pressStart(Pointer c, Vector3 location, Vector2 relativeToRect) {
			pressTime = 0.0;
		}

		@Override public void pressStopped(Pointer c) {
		}

		@Override public void pressUpdate(Pointer c, double timePressed) {
			pressTime += timePressed;			
		}

		@Override
		public void startTouch(Pointer c) {
			touchTime = 0.0;			
		}

		@Override public void stopTouch(Pointer c) {
		}

		@Override public void updateTouch(Pointer c, double timeTouched) {
			touchTime = timeTouched;			
		}
		
	}
	
	
	@Override
	public void start() {
		
		Rect r = add(new Rect());
		
		for (int i = 0; i < numPads; i++) {
			r.add(new Pad());
		}
		add(new ArrangeGrid(r));
			
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoTouchPress().scale(10.0));
	}
}
