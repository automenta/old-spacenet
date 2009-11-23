package automenta.spacenet.run.perf;

import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.Jme;


/** measures the decrease in framerate as increasingly more geometric objects are added to a scene */
abstract public class AbstractGeometryMeter extends ProcessBox {
	
	private int maxCount;
	private int phaseIncrement;
	private double secondsPerPhase;


	public AbstractGeometryMeter(int maxCount, int phaseIncrement, double secondsPerPhase) {
		super();
		this.maxCount = maxCount;
		this.phaseIncrement = phaseIncrement;
		this.secondsPerPhase = secondsPerPhase;
	}
	
	@Override public void run() {

		final Box b = add(new Box());
		
		final Jme jme = getThe(Jme.class);
		
		new Thread(new Runnable() {
			@Override public void run() {
				int n = 0;

				do {
					b.clear();
					for (int i = 0; i < n; i++) {
						addGeometry(b);
					}
					
					try {
						Thread.sleep( (long) (secondsPerPhase * 1000.0) );
					} catch (InterruptedException e) { 	}

					double ups = jme.getUpdatesPerSecond().d();
					double rps = jme.getRendersPerSecond().d();
					
					System.out.println("n=" + n + ": u/s=" + ups + ", r/s=" + rps);
					
					n += phaseIncrement;
				} while (n <= maxCount);

			}			
		}).start();
	}
	
	
	abstract public void addGeometry(Box s);

}
