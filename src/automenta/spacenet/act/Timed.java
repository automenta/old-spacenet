/**
 * 
 */
package automenta.spacenet.act;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.var.time.TimeDuration;
import automenta.spacenet.var.time.TimePoint;

abstract public class Timed implements StartsIn<Scope>, Stops {
	
	private TimePoint when;
	private TimeDuration interval;
	
	private Scheduler timeRunner;

	public Timed(double delay) {
		when = TimePoint.secondsAfterNow(delay);
	}

	public Timed(double delay, double period) {
		this(delay);
		interval = new TimeDuration(period);
	}

	/** when to begin invocation */
	public TimePoint getWhen() {
		return when;
	}
	
	/** period that this should be repeated, or null if only invoked once */
	public TimeDuration getPeriod() {
		return interval;
	}

	@Override public void start(Scope c) {
		timeRunner = c.getThe(Scheduler.class);
		timeRunner.start(this);
	}
	
	@Override public void stop() {
		timeRunner.stop(this);
	}
	
	/** when periodic, returns true to repeat, or false to not continue */ 
	abstract public boolean run();

	public boolean isInterruptable() {
		return true;
	}

	public void whenStarted() {	}
	public void whenStopped() {	}
	
}