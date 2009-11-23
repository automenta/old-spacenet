package automenta.spacenet.run.sound;

import java.nio.FloatBuffer;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.audio.DynamicSound;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;

import com.jme.math.FastMath;


public class DemoDynamicSound extends ProcessBox {

	private final class SineSound extends DynamicSound {
		double T = 0;
		double x = 0;
		private double frequency;

		public SineSound(double frequency) {
			super();
			this.frequency = frequency;
		}
		
		@Override public int forward(double t, double dt, FloatBuffer db) {				
			float f;
			//System.out.print(T + ", " + dt + " > " + frequency + " " + db.remaining());
			int i;
			for (i = 0; i < db.remaining(); i++) {
				f = (FastMath.sin((float)x));
				db.put(f);
				//t+=dt;
				x += frequency*dt;
				T += dt;
				
				//frequency = (0.9999) * frequency + (0.0001) * targetFrequency;
				
			}
			//System.out.println("  < " + frequency + " " + T);
			
			
			return i;
		}
	}



	@Override public void run() {
		final DynamicSound soundA = add(new SineSound(100.0));
		final DynamicSound soundB = add(new SineSound(110.0));
		

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double r = 2.0;
				double s = 6;
				soundA.getWorldPosition().set(r * Math.sin(t * s), 0, r * Math.cos(t * s));
				soundB.getWorldPosition().set(r * Math.cos(t * s), 0, r * Math.sin(t * s));
				return 0;
			}
			
		});
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoDynamicSound());
	}
}
