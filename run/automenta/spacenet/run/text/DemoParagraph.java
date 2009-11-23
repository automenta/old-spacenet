package automenta.spacenet.run.text;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.StringVar;

public class DemoParagraph extends Box implements Starts, Stops {
	
	public DemoParagraph() {
		super();
	}
	
	@Override public void start() {
		
		VectorFont vectorFont = new VectorFont("Arial", Color.Orange, 0.001);		

		final StringVar s = new StringVar("abcdefg");
		
		addText(s,	0,0,1,1,30,vectorFont);
		
		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				if (Math.random() > 0.5) {
					s.append((char)(Math.random() * 26 + 'a'));
				}
				else {
					if (s.length() > 2)
						s.set(s.get().substring(0, s.length()-2));
				}
				return 0.1;
			}

		});
		
//		double y = 5;
//		for (double a = 0.2; a < 4.0; a+=0.4) {
//			double w = 1.0;
//			double h = a / w;
//			addText(1, y, w, h, 16, vectorFont);
//			addText(-1, y, w, h, -1, vectorFont);
//			y -= h * 2.5;
//		}
		
	}
	
	@Override public void stop() {
		
	}
	
	
	private void addText(StringVar s, double x, double y, double w, double h, int lineChars,	VectorFont vectorFont) {
		final Color c = new Color();
		final TextRect text = (TextRect) new TextRect(s, vectorFont, lineChars, c).move(x,y).scale(w, h).aspect(1,1);
		final Rect r = new Rect().color(Color.Gray.alpha(0.8));
		add(r);
		r.add(text);		
		

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double a = (1.0 + Math.sin(t))/2.0;
				double b = (1.0 + Math.cos(t/2.0))/2.0;
				c.set(a, (a+b)/2.0, b, 1.0);
				r.size(6.1 + 4*Math.sin(t/2.0), 6.1 + 4*Math.cos(t/4.0));
				//r.getOrientation().set(t, t, t);
				return 0;
			}			
		});

	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoParagraph().scale(5,5,5));
	}

}

