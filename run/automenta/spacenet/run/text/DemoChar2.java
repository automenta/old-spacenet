package automenta.spacenet.run.text;

import java.awt.Font;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;


public class DemoChar2 extends Box implements Starts, Stops {

	private VectorFont vectorFont;

	
	@Override public void start() {
		vectorFont = new VectorFont("Sans", Font.PLAIN, Color.Orange, Color.Blue, 0.01, false);

//		for (double a = 0.05; a < 3.0; a+=0.1) {
//			double w = 0.7;
//			double h = w*a;
//			addChar('@', w, h);
//		}
		
		add(new CharRect(('@'), vectorFont).scale(1, 1).move(0, 0));
		add(new CharRect(('@'), vectorFont, Color.Red).scale(1, 1).move(1, 0));
		
	}

	@Override public void stop() {
		
	}
	
	
	private void addChar(char c, double w, double h) {
		Rect r = new Rect();
		r.add(new CharRect(c, vectorFont).scale(w,h));
		add(r);
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoChar2().scale(5,5,5));
	}
}
