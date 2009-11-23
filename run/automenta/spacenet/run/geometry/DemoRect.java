package automenta.spacenet.run.geometry;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Surface;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

/** shows several moving boxes, each with a different update interval.  some will appear more 'jumpy' than others */
public class DemoRect extends Rect implements Starts {

	private Rect rect;

	@Override public void start() {
		
		rect = new Rect(newSurface());
		rect.tangible(false);
		rect.scale(0.5);
		//add(rect);
		
		add(new GeometryViewer(rect));
		
//		add(new Repeat() {
//
//			private double timeScale = 0.85;
//
//			@Override public double repeat(double t, double dt) {
//				t = t * timeScale ;
//				rect.getPosition().set(Math.sin(t) * 3.0, Math.cos(t)*3.0, -5);
//				
////				double sDim = 16 * (1.0 + Math.cos(t)/2.0);			
////				rect.getSize().set(sDim, sDim);
//				
//				rect.getOrientation().set(t, t, t);
//				
//				return 0;
//			}
//		});		

	}
	
	public Rect getRect() {
		return rect;
	}
	

	protected Surface newSurface() {
		final Color c = new Color(1,1,1,1);
		
		add(new Repeat() {

			private double timeScale = 0.25;

			@Override public double repeat(double t, double dt) {
				c.set((Math.sin(t)+1.0)/2.0, (Math.cos(t*2)+1.0)/2.0, 0, 1.0);
				return 0;
			}
		});		

		return new ColorSurface( c );
	}

	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoRect().scale(8));
	}

	
}


