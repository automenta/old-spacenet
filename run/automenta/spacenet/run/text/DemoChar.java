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


public class DemoChar extends Box implements Starts, Stops {

	private VectorFont vectorFont;

	
	@Override public void start() {
		vectorFont = new VectorFont("Sans", Font.PLAIN, Color.Orange, Color.Blue, 0.01, false);

//		for (double a = 0.05; a < 3.0; a+=0.1) {
//			double w = 0.7;
//			double h = w*a;
//			addChar('@', w, h);
//		}
		
		for (int i = 0; i < 5; i++) {
			for (double x = 0; x < 10; x++) {
				double X =-5 + x*4;
				double y = -3+i*3.0;
				double w = (1+x/5.0)*1.8;
				double h = (1+x/5.0)*1.8;
				
				Rect front = add(new CharRect((char)('a'+i), vectorFont).scale(w, h).move(X, y));
				Rect back = add(new Rect(Color.newRandom()).scale(w, h).move(X, y));
			}
		}
		
	}

	@Override public void stop() {
		
	}
	
	
	private void addChar(char c, double w, double h) {
		Rect r = new Rect();
		r.add(new CharRect(c, vectorFont).scale(w,h));
		add(r);
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoChar().scale(5,5,5));
	}
}
