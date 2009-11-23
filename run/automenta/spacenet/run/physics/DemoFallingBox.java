package automenta.spacenet.run.physics;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.dynamic.physics.PhyBox;
import automenta.spacenet.space.dynamic.physics.PhySpace;
import automenta.spacenet.space.Color;

public class DemoFallingBox extends ProcessBox {

	double s = 1.0;
	double r = 16;
	int numBoxes = 26;
	double height = 4;
	
	@Override public void run() {
		PhySpace phy = add(new PhySpace());
		
		phy.getGravity().set(0,-1.0,0);
		phy.getTimeScale().set(1.0);
		
		for (int i = 0; i < numBoxes; i++) {
			PhyBox b = phy.newBox(1.0);
			double x = Maths.random(-r, r);
			double y = height;
			double z = 0;
			
			double w = Maths.random(s/2, s);
			double h = Maths.random(s/2, s);
			double d = Maths.random(s/2, s);
			
			b.move(x, y, z);			
			b.scale(w, h, d);
			
			add(new Box(b).color(Color.Orange));
		}
		
		PhyBox s = phy.newBox(1.0);
		//s.isDynamic().set(false);
		s.move(0,-4, 0);
		s.scale(10, 0.1, 10);
		
		add(new Box(s).color(Color.Purple));

	}
	
	public static void main(String[] args) {
		
		new DefaultJmeWindow(new DemoFallingBox());
	}
}
