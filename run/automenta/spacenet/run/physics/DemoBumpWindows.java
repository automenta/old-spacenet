package automenta.spacenet.run.physics;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.dynamic.physics.PhyBox;
import automenta.spacenet.space.dynamic.physics.PhySpace;
import automenta.spacenet.var.vector.Vector3;

public class DemoBumpWindows extends ProcessBox {
	
	private PhySpace phy;

	int numWindows = 35;
	
	@Override public void run() {
		phy = add(new PhySpace());
		
		phy.getUpdatePeriod().set(0);
		phy.getTimeScale().set(0.3);
		
		for (int i = 0; i < numWindows; i++) {
			Scheduler.doLater(new Runnable() {
				@Override public void run() {
					addWindow();			
				}
			});
		}
	}

	double r = 10;
	
	Vector3 up = new Vector3(0,0,1);
	
	private void addWindow() {
		final PhyBox b = phy.newBox(1.0);
		double x = Maths.random(-r, r);
		double y = Maths.random(-r, r);
		b.move(x, y, 0);
		
		final Window w = add(new Window(b) {
			@Override protected void stopDrag(Pointer c, Drag drag) {
				super.stopDrag(c, drag);
				b.getVelocity().set(0,0,0);
			}
			
		});
		w.add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				b.getPosition().setZ(b.getPosition().z() * 0.9);
				//w.getOrientation().set(0,0,0);
				b.getOrientation().multiply(0.9, 0.9, 0.9);
				b.getAngularVelocity().multiply(0.9, 0.9, 0.9);
				return 0;
			}			
		});
		
		w.add(new TextRect(":)"));
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoBumpWindows());
	}
}
