package automenta.spacenet.run.face;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.os.SkyUtil;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.object.text.TextRect3;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;

public class DemoFace extends ProcessBox {

	double px, py;
	
	double x = 1;
	double y = 1;
	
	public class CoordLabel extends Rect implements Starts {
		StringVar text = new StringVar();
		
		public CoordLabel() {
			super(Color.Orange.alpha(0.3));
		}

		@Override public void start() {
			tangible(false);
			
			TextRect3 tr = new TextRect3(text, Color.Black);
			tr.moveDZ(0.02);
			tr.scale(0.9);
			add(tr);
			
			
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					text.set(CoordLabel.this.getPosition().toString());
					return 0.5;
				}
			});
		}

		@Override
		public void stop() {
		}
		
		
	}
	
	@Override public void run() {
		final Jme jme = getThe(Jme.class);
	
		jme.getSky().add(SkyUtil.newRainbowSkyBox());
		
		add(new Box(Color.Purple.alpha(0.5)).move(1, 0));
		add(new Box(Color.Blue.alpha(0.5)).move(-1,0));
		
//		double a = 0.25;
//		double d = 0.1;
//		for (double x = -a; x <= a; x+=d) {
//			for (double y = -a; y <= a; y+=d) {
//				Color c = Color.Orange.alpha(Maths.random(0, 0.5));
//				final Rect r = new Rect(c);
//				r.tangible(false);
//				r.move(x, y);
//				r.scale(d);
//				spacetime().face().add(r);				
//			}
//		}
		
		spacetime().face().add(new CoordLabel().move(-0.5, 0).scale(0.1));
		spacetime().face().add(new CoordLabel().move(-0.25, 0).scale(0.1));

		spacetime().face().add(new CoordLabel().move(0.5, 0).scale(0.1));
		spacetime().face().add(new CoordLabel().move(0.25, 0).scale(0.1));

		spacetime().face().add(new CoordLabel().move(0, 0.5).scale(0.1));
		spacetime().face().add(new CoordLabel().move(0, 0.25).scale(0.1));
		spacetime().face().add(new CoordLabel().move(0, -0.5).scale(0.1));
		spacetime().face().add(new CoordLabel().move(0, -0.25).scale(0.1));
		
		spacetime().face().add(new CoordLabel().move(0, 0).scale(0.1));
		
		//jme.getFace().add(new TextRect("1x1").scale(0.5));
		
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
////				double px = getPointer().getPositionRelativeToScreen().x();
////				double py = getPointer().getPositionRelativeToScreen().y();
////				System.out.println(px + " " + py);
//				px = Maths.random(0, 300);
//				py = Maths.random(0, 300);
//				s.move(px, py);
//				return 0;
//			}
//		});
//		r.move(20, 10);
//		r.scale(40, 40);
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoFace());
	}
}
