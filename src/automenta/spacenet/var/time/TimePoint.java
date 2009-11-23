package automenta.spacenet.var.time;

import java.util.Date;

import automenta.spacenet.var.ObjectVar;


/** a specific point in time */
public class TimePoint extends ObjectVar<Date> {

	public TimePoint(Date d) {
		super();
		set(d);
	}

	/** new TimePoint for present time */
	public TimePoint() {
		this(new Date());
	}

	public long getAbsoluteTimeMS() {
		return get().getTime();
	}

	public static TimePoint secondsAfterNow(double delay) {
		Date d = new Date((long)(new Date().getTime() + (delay * 1000.0)));
		return new TimePoint(d);
	}

	/** relative number of seconds until now */ 
	public double getSecondsToNow() {
		Date now = new Date();
		double dMS = now.getTime() - getAbsoluteTimeMS();
		return dMS / 1000.0;
	}

	public void setNow() {
		set(new Date());		
	}

}
