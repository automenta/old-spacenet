package automenta.spacenet.act;

import java.util.Collection;


public class DefaultScheduler extends Scheduler {
	
	protected double dt;
	
	@Override public void forwardRepeats(final Collection<Repeat> repeats, final double dt) {
		this.dt = dt;
		for (Repeat w : repeats) {
			repeat(w);
		}
	}

	protected void repeat(Repeat w) {
		double remaining = w.getRemaining();
		double accumulated = w.getAccumulated();

		if (remaining <= 0) {
			double edt = -remaining + dt;
			double nextRemaining = w.afterElapsed(edt + accumulated);
			w.setRemaining(nextRemaining);
			w.setAccumulated(nextRemaining);
		}
		else {
			w.setRemaining(remaining - dt);
		}		
	}

}
