package automenta.spacenet.run.interact;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.vector.DynamicVectorSet;
import automenta.spacenet.space.object.measure.CursorDrawingRect;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class DemoDrawingRect extends ProcessBox {
	
	@Override public void run() {
		
		DynamicVectorSet vset = add(new DynamicVectorSet(0.02));
		
		final Vector3 rot = vset.newVector3(0, 0.5, 0, 0.1);
		
		add(new CursorDrawingRect(new Vector3(0,0,-3), new Vector2(12,12), rot));
		add(new CursorDrawingRect(new Vector3(4,-2,8), new Vector2(5,5), new Vector3()));
		add(new CursorDrawingRect(new Vector3(-4,2,8), new Vector2(5,5), new Vector3()));
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				rot.set(DoubleVar.random(-0.5,0.5), 
						DoubleVar.random(-0.5,0.5), 
						DoubleVar.random(-0.5,0.5));
				return 2.0;
			}			
		});
	}
	
	
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoDrawingRect());
	}

}
