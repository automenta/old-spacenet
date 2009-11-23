package automenta.spacenet.run.geometry;


import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;


public class DemoBox extends Space implements Starts, Stops {

	@Override public void start() {	
		add(new Box().color(Color.Gray.alpha(0.5)));
	}

	@Override public void stop() {
		
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoBox());
	}

}
