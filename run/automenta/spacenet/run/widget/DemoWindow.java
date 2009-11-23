package automenta.spacenet.run.widget;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.string.StringVar;

public class DemoWindow extends ProcessBox implements Starts, Stops {


	public static class PointerWindow extends Window {
	
		@Override
		public void start() {
			super.start();

			final StringVar touchedString = new StringVar();
			add(new TextRect(touchedString).move(0, 0, 0.1));
			
//			final StringVar uvString = new StringVar();
//			add(new TextRect(uvString).move(0, -0.2));
			
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					Space touched = (Space) getPointer().getTouchedNode().get();
					if (touched != null)
						touchedString.set( touched.toString() );
					else
						touchedString.set( "none" );
			
					return getUpdatePeriod();
				}
			});
			
		}
		
		protected double getUpdatePeriod() {
			return 0.15;
		}

		
	}
	public void run() {


		Window macroWin = (Window) add(new Window()).scale(1.6,1);

		Window subWin = macroWin.add( new Window() );
		subWin.scale(0.25, 0.25);
		
		add(new PointerWindow().move(-2,0,0.1));

		
	}
	

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoWindow().scale(4));		
	}
}
