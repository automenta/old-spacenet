package automenta.spacenet.act;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.time.TimePoint;



/** runs a thread in threadpool (via SimultaneousContext).  optionally removes itself from the scope to which it was added when finished */
@Deprecated public abstract class Simultaneous<R> implements StartsIn<Scope>, Callable<R>, Stops{
	private static final Logger logger = Logger.getLogger(Simultaneous.class);
	
	private TimePoint startTime;
	private ScheduledFuture<R> future;
	private BooleanVar finished = new BooleanVar(false);
	private BooleanVar started = new BooleanVar(false);
	private Scope scope;

	public void start(Scope node) {
		this.scope = node;
		
		SimultaneousContext context = node.getThe(SimultaneousContext.class);
		context.start(this);	
	}
	
	//public Simultaneous(double delay)
	//public Simultaneous(double delay, double repeatPeriod)
	//public Simultaneous(Date delayUntil)
	
	abstract public R run() throws Exception;
	
	public R call() throws Exception {
		if (logger.isDebugEnabled())
			logger.debug(this + " starting");
		
		R r = run();
		end();
		
		if (logger.isDebugEnabled())
			logger.debug(this + " stopped");
		
		return r;
	}
	
	public TimePoint getStartTime() {
		return startTime;
	}

	void setFuture(ScheduledFuture<R> future) {
		this.future = future;		
	}
	
	
//	private Thread thread;
//
//	public SimultaneousAction(Node superNode) {
//		super(superNode);
//		
//		thread = new Thread(new Runnable() {
//			@Override public void run() {
//				SimultaneousAction.this.run();				
//			}			
//		});
//		thread.start();
//	}
//
//	abstract protected void run();

	public R waitForResult() throws InterruptedException, ExecutionException {
		if (future!=null) {
			R r = future.get();
			stop();
			return r;
		}
		else {
			return null;
		}
	}
	
	public R getResultIfAvailable() {
		try {
			return future.get(0, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.warn(this + " has null 'future'");
			return null;
		}
	}
	
	public BooleanVar getHasStarted() {
		if (future == null) {
			started.set(false);
		}
		else {
			started.set(true);
		}
		return started;
	}
	
	public BooleanVar getHasStopped() {
		if (future==null) {
			finished.set(false);
		}
		else {
			finished.set(future.isDone() || future.isCancelled());
		}
		return finished ;
	}
	
	@Override public void stop() {			
		if (future!=null) {
			if (!(future.isDone() || future.isCancelled())) {
				future.cancel(true);				
			}			
		}
	}

	public void stopIfNotRunning() {
		if (future!=null) {
			if (!(future.isDone() || future.isCancelled())) {
				future.cancel(false);				
			}
		}
		end();
	}

	public static void wait(double seconds) {
		try {
			Thread.sleep( (int) ( seconds * 1000.0 ) );
		} catch (InterruptedException e) {	}		
	}
	
	protected void end() {
		if (removeWhenFinished()) {
			if (scope!=null) {
				scope.remove(this);
				scope = null;
			}
		}
	}

	private boolean removeWhenFinished() {
		return true;
	}
	
	protected void delay(double s) {
		try {
			Thread.sleep( (long)(s* 1000.0) );
		} catch (InterruptedException e) {	}
	}
	

}
