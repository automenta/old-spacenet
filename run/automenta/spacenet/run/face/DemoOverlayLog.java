package automenta.spacenet.run.face;

import java.util.Date;

import org.apache.log4j.Logger;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.OverlayRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.hud.ScrollingLog;
import automenta.spacenet.var.number.DoubleVar;


public class DemoOverlayLog extends ProcessBox /* extends DefaultSpace */ {
	
	@Override public void run() {
		OverlayRect overlay = face().add(new OverlayRect());
		
		//OverlayRect overlay = add(new OverlayRect());
		
		final double w = 0.4;
		final double h = 0.3;
		final Rect log = overlay.add(new ScrollingLog().scale(w,h)/*.move(0.6 - w, -0.5 + h)*/);
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				Logger.getRootLogger().info(new Date().toString());
				return DoubleVar.random(1.0, 2.0);
			}			
		});
//		repeat(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				double d = (1.0 + Math.cos(t)*0.1);
//				log.getSize().set(w * d, h * d);
//				log.getOpacity().set(0.5 + Math.cos(t/4)*0.5);
//				return 0;
//			}			
//		});

	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoOverlayLog());		
	}
}
