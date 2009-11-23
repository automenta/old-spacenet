package automenta.spacenet.run.video;

import automenta.spacenet.Maths;
import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.geometry.DemoBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.video3d.Video3D;

public class DemoJmeWindowResize extends Box implements Starts {

	
	private Video3D video;

	@Override public void start() {
		video = getThe(Video3D.class);
		
		add(new DemoBox());
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				int px = (int)Maths.random(300, 700);
				int py = (int)Maths.random(300, 700);
				
				video.getPixelSize().set(px,py);
				return 1.0;
			}
		});
	}

	@Override public void stop() {
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoJmeWindowResize());
	}
}
