package automenta.spacenet.os;

import automenta.spacenet.UURI;
import automenta.spacenet.space.geom2.BitmapRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;

public class DemoPage extends ProcessBox {

	@Override public void run() {
		Color lightBlue = new Color(0.2,0.2,0.9);
		Color darkBlue = new Color(0,0,0.4);

		Rect b = add(new Rect(darkBlue).size(1, 1/1.6));

		Rect bar = b.add(new Rect(darkBlue).span(-0.5, 0.5, 0.5, 0.45));
		bar.add(new TextRect("Title", Color.White));
		
		Rect r = b.add(new Rect(Color.GrayPlusPlus).scale(0.9));
		
		final UURI uri = new UURI(getClass().getResource("data/earth.jpg"));
		
		r.add(new BitmapRect(new BitmapSurface(uri) ).span(0,0,0.45,0.45));
		
		StringVar s1 = new StringVar("Earth");
		r.add(new TextRect(s1, lightBlue).span(-0.45,0.45,-0.1,0.2));

		StringVar s2 = new StringVar("Earth is the third planet\nfrom the Sun, and the largest\nof the terrestrial planets\nin the Solar System in terms of\ndiameter, mass and density.");
		r.add(new TextRect(s2, Color.Black).span(-0.45,-0.1,0.45,-0.45));

	}
	

	public static void main(String[] args) {
		
		new DefaultJmeWindow(new DemoPage());
	}
}
