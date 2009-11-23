package automenta.spacenet.act;

import java.awt.EventQueue;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.list.SetVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.time.TimeDuration;


abstract public class Scheduler implements Starts, Stops, Disposable  {
	private static final Logger logger = Logger.getLogger(Scheduler.class);
	
	//	private class Repetition {
	//		public double remaining;
	//		public double accumulated;
	//
	//		public Repetition(double initialRemaining, double initialAccumulated) {
	//			remaining = initialRemaining;
	//			accumulated = initialAccumulated;
	//		}
	//	}


	/** current simulation elapsed time, as experienced by this space locally (not necessarily equal to time of other spaces) */
	private final DoubleVar time = new DoubleVar(0);
	private final DoubleVar timeScale = new DoubleVar(1.0);

	/** maps a WhenTimeElapsed to the remaining time until it should be invoked again */
	private final List<Repeat> toAdd = new LinkedList(), toRemove = new LinkedList();
	
	private final Collection<Repeat> repeats = new HashSet();

	int currentTiming = 0;
	int timingWindowSize = 5000;
	double accumulatedTime = 0;

	private boolean enabled;

	private long lastWindowTime = System.nanoTime();
	
	abstract public void forwardRepeats(Collection<Repeat> repeats, double dt);

	private final ScheduledThreadPoolExecutor exe;

	private int corePoolThreads;

	private final Map<Timed, ScheduledFuture> futures = new FastMap();
	private final SetVar<Timed> running = new SetVar<Timed>();

	static final int cpuProcessors = Runtime.getRuntime().availableProcessors();
	
	public Scheduler() {
		super();
		
		corePoolThreads = cpuProcessors * 2;
		exe = new ScheduledThreadPoolExecutor(corePoolThreads);
	}
	
	

	public static void doLater(Runnable runnable) {		
		EventQueue.invokeLater(runnable);		
	}

	public static double now() {
		return (System.nanoTime()) / (1e9); 
	}

	
	public boolean isEnabled() {
		return enabled;		
	}
	
	public void setEnabled(boolean b) { enabled = b; }
	
	/** proceed/evolve/continue the set of associated Repeat's by a discrete time duration */
	public synchronized void forward(double dt) {
		if (!isEnabled()) {
			return;
		}
		
		double startTime = now();
		
		
		synchronized (toRemove) {
			repeats.removeAll(toRemove);
			toRemove.clear();
		}

		synchronized (toAdd) {
			repeats.addAll(toAdd);
			toAdd.clear();
		}

		double DT = dt * getTimeScale().get();
		
		if (repeats.size() > 0)
			forwardRepeats(repeats, DT);
		
		getTime().add(DT);
		
		
		
		double stopTime = now();
		
		accumulatedTime += stopTime - startTime;
		
		if (currentTiming == timingWindowSize) {
			long currentWindowTime = System.nanoTime();
			double realTime = (currentWindowTime - lastWindowTime) / 1e9;
			//double avgPeriod = accumulatedTime / (timingWindowSize);
			if (logger.isDebugEnabled())
				logger.debug("time consumed per iteration: " + (accumulatedTime / realTime)*100.0 + "%");
			accumulatedTime = 0;
			currentTiming = 0;
			lastWindowTime  = currentWindowTime;
		}
		else {
			currentTiming++;
		}
		
	}

	public void start(Repeat r) {
		synchronized (toAdd) {
			toAdd.add(r);
		}
	}

	public void stop(Repeat r) {
		synchronized (toRemove) {
			toRemove.add(r);
		}
	}

	@Override public void start() {
	}

	@Override public void stop() {
		for (Repeat r : repeats) {
			r.stop();
		}
		repeats.clear();
	}

	public void start(final Timed t) {
		Date now = new Date();
		long nowMS = now.getTime();
		long thenMS = t.getWhen().getAbsoluteTimeMS();
		long delayNS = (thenMS - nowMS) * 1000 * 1000;

		final TimeDuration period = t.getPeriod();
		
		Runnable r = new Runnable() {
			@Override public void run() {
				if ((!t.run()) || (period == null)) {
					t.whenStopped();
					futures.remove(t);
					running.remove(t);
				}
			}
		};
		
		t.whenStarted();
		
		ScheduledFuture<?> future;
		if (period!=null) {
			long periodNS = (long) (period.f() * 1e9); 
			future = exe.scheduleAtFixedRate(r, delayNS, periodNS, TimeUnit.NANOSECONDS);
		}
		else {
			future = exe.schedule(r, delayNS, TimeUnit.NANOSECONDS);
		}
		
		futures.put(t, future);
		running.add(t);
	}

	public void stop(Timed timed) {
		ScheduledFuture future = futures.get(timed);
		if (future!=null) {
			future.cancel(timed.isInterruptable());
			
			futures.remove(timed);
			running.remove(timed);
		}
	}


	public SetVar<Timed> getRunning() {
		return running;
	}


	@Override public void dispose() {
		stop();

		if (repeats!=null) {
			repeats.clear();
		}

	}

	public DoubleVar getTime() {
		return time;
	}

	public DoubleVar getTimeScale() {
		return timeScale;
	}


}
