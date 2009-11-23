package automenta.spacenet.act;

import java.util.Date;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.var.time.TimePoint;


public class SimultaneousContext implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(SimultaneousContext.class);
	
	ScheduledThreadPoolExecutor exe;
	private int coreSize;
	
	private static final int defaultCoreThreads = 16;

	public SimultaneousContext() {
		this(defaultCoreThreads);
	}
	
	public SimultaneousContext(int coreSize) {
		this.coreSize = coreSize;

		exe = new ScheduledThreadPoolExecutor(getCoreSize(), newThreadFactory(), newRejectionHandler());
		exe.prestartAllCoreThreads();
	}
	
	@Override public void start() {
		
	}
	
	private int getCoreSize() {
		return coreSize;
	}

	private ThreadFactory newThreadFactory() {
		return new ThreadFactory() {
			@Override public Thread newThread(Runnable r) {
				return new Thread(r);
			}			
		};
	}

	private RejectedExecutionHandler newRejectionHandler() {
		return new RejectedExecutionHandler() {
			@Override public void rejectedExecution(Runnable r,	ThreadPoolExecutor executor) {
				logger.error(this + " unable to execute " + r + " (in " + executor + " )");
			}			
		};
	}

	public void start(Simultaneous s) {
		long initialDelayMS = 0;
		
		TimePoint d = s.getStartTime();
		
		if (d!=null) {
			long nowMS = new Date().getTime();
			long startMS = d.getAbsoluteTimeMS();
			if (nowMS < startMS) {
				initialDelayMS = startMS - nowMS;
			}
		}
		
		ScheduledFuture<?> future = exe.schedule(s, initialDelayMS, TimeUnit.MILLISECONDS);
		s.setFuture(future);
		
		
		
		
		if (logger.isDebugEnabled())
			logger.debug(this + " started " + s);
	}

	@Override public void stop() {
		if (isImmediateShutdown()) {
			List<Runnable> remainingThreads = exe.shutdownNow();
			if (remainingThreads.size() > 0) {
				logger.warn("Immediately shutdown threads: " + remainingThreads);
			}
		}
		else {
			logger.info("Shutting down " + exe.getActiveCount() + " threads...");			 
			exe.shutdown();
		}
	}

	public boolean isImmediateShutdown() {
		return true;
	}

	@Override
	public String toString() {
		return "simultaneously";
	}
	
	
}
