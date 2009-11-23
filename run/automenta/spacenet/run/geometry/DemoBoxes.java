package automenta.spacenet.run.geometry;



import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;

public class DemoBoxes extends Box implements Starts {

	int n = 25;

	public DemoBoxes() {
		super();
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	@Override public void start() {

		for (int x = -n; x < n; x+=4) {
			for (int y = -n; y < n; y+=4) {
				double w = 0.5 + Math.random()*0.5;
				double h = 0.5 + Math.random()*0.5;
				double d = 0.5 + Math.random()*0.5;
				
				Color c = Color.newRandomHSB(1.0, 1.0);
				
				add(new Box(c).move(x,y,-5).scale(w,h,d));
			}
		}

	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoBoxes());
	}

}
