package automenta.spacenet.run.geometry.bevel;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.BevelRect;
import automenta.spacenet.space.Color;

public class DemoBevelRect2 extends ProcessBox {

	@Override public void run() {
		
		add(new BevelRect(Color.Gray).scale(1.6, 1.6).move(-2, -2));	
		
		add(new BevelRect(Color.Gray).scale(1, 2).move(-2, 2));	

		add(new BevelRect(Color.Gray).scale(2, 1).move(2, -2));	

		add(new BevelRect(Color.Gray, -0.05, 0.05).scale(1.6, 1.6).move(2, 2));	

	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoBevelRect2().scale(4.0));
	}
}
