/**
 * 
 */
package automenta.spacenet.act;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;

public abstract class Repeat<Parent extends Scope> implements StartsIn<Parent>, Stops {

	double t = 0;
	private Scheduler repeatContext;
	private double remaining, accumulated;
	private Parent parent;
	
	@Override public void start(Parent c) {
		this.parent = c;
		repeatContext = c.getThe(Scheduler.class);
		remaining = accumulated = 0;
		repeatContext.start(this);		
	}
	
	@Override public void stop() {
		repeatContext.stop(this);
	}
	
	public final double afterElapsed(double dt) {
		this.t += dt;
		double minDT = repeat(t, dt);
		if (minDT < 0) {
			if (removeAfterFinished()) {
				//calls stop() indirectly:
				parent.remove(this);
			}
			else
				stop();
		}
		return minDT;
	}
	
	private boolean removeAfterFinished() {
		return true;
	}

	/** returns the minimum amount of time before it can be invoked next, to set a limit on update frequency.
	 * returning < 0 will stop this repeat. 
	 *  */
	abstract public double repeat(double t, double dt);

	public double getRemaining() {
		return remaining;
	}
	public double getAccumulated() {
		return accumulated;
	}

	public void setRemaining(double remaining) {
		this.remaining = remaining;
	}

	public void setAccumulated(double accumulated) {
		this.accumulated = accumulated;
	}

}