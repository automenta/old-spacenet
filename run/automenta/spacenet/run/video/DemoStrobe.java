package automenta.spacenet.run.video;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.video3d.Video3D;

import com.jme.input.KeyInput;


public class DemoStrobe extends ProcessBox implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(DemoStrobe.class);
	
	private double frequency = 7.8;

	private Video3D video;

	boolean isSquare = true;
	
	@Override public void run() {
		video = getThe(Video3D.class);		
	
		add(new Repeat() {

			@Override public double repeat(double t, double dt) {
				double a = 0.5 * (Math.sin(Math.PI/2.0 * frequency * t)+1.0);

				if (isSquare) {
					a = (a > 0.5) ? 0 : 1.0;
				}
				
				video.getBackgroundColor().set(a,a,a,1.0);
				
				if (keyboard().getKey(KeyInput.KEY_9).b()) {
					adjustFrequency(-2.0 * dt);					
				}
				if (keyboard().getKey(KeyInput.KEY_0).b()) {
					adjustFrequency(2.0 * dt);										
				}

				return 0;
			}

			
		});
	}

	@Override public void stop() {
	}
	

	public void adjustFrequency(double df) {
		frequency += df;
		logger.info("strobe frequency=" + frequency);
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoStrobe());
	}

}
