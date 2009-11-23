package automenta.spacenet.run.geometry.border;


import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.RectBorder;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;

public class DemoRoundedBorder extends ProcessBox {

	@Override public void run() {
		DoubleVar start = new DoubleVar(1.0);
		DoubleVar stop = new DoubleVar(0.9);
		
		
		add(new RectBorder(new ColorSurface(Color.White), start, stop));
		
		double dz = 0.05;
		
		Rect r = add(new Rect(Color.Orange.alpha(0.5))).scale(1.6, 1).move(2,2);
		r.add(new RectBorder(new ColorSurface(Color.Purple.alpha(0.5)), start, stop).move(0,0,-dz));

		Rect s = add(new Rect(Color.Orange.alpha(0.5))).scale(1, 1.6).move(2,4);
		s.add(new RectBorder(new ColorSurface(Color.Purple.alpha(0.5)), start, stop).move(0,0,-dz));

		Rect t = add(new Rect(Color.Orange.alpha(0.5))).scale(1.6, 1.6).move(2,6);
		t.add(new RectBorder(new ColorSurface(Color.Purple.alpha(0.5)), start, stop).move(0,0,-dz));
		
		Rect u = add(new Rect(Color.Gray.alpha(0.8))).scale(1, 2).move(-2,-2);
		u.add(new RectBorder(new ColorSurface(Color.White), start, stop).move(0,0,-dz));

	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoRoundedBorder());
	}
	
}
