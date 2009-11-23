package automenta.spacenet.act;

import java.util.Collection;

import javolution.context.ConcurrentContext;

import org.apache.log4j.Logger;


public class ParallelScheduler extends DefaultScheduler {
	
	private static final Logger logger = Logger.getLogger(ParallelScheduler.class);
	private int numProcs;
	
	public static int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();	
	}
	
	public ParallelScheduler() {
		this(getAvailableProcessors());
	}
	
	public ParallelScheduler(int numProcs) {
		super();
		
		numProcs = Math.max(1, numProcs);
		
		setProcessors(numProcs);
		
	}
	
	public void setProcessors(int p) {
		this.numProcs = p;
		
		logger.info(this.getClass().getSimpleName() + " running on " + numProcs + " processors");
		
	    ConcurrentContext.setConcurrency(numProcs);		
	}

	Repeat[] arepeat = new Repeat[] { };
	
	@Override public void forwardRepeats(final Collection<Repeat> repeats, final double dt) {
		arepeat = repeats.toArray(arepeat);
		
		this.dt = dt;
		
		ConcurrentContext.enter(); 
		try {
			
			final int numProcs = getProcessors();
			final int numRepeat = repeats.size();
			for (int i = 0; i < numProcs; i++) {
				final int j = i;									
				ConcurrentContext.execute(new Runnable() {
					@Override public void run() {
						for (int k = j; k < numRepeat; k+=numProcs) {
							repeat(arepeat[k]);
						}
					}					
				});
			}
		}
		finally {
			ConcurrentContext.exit();
		}
	}

	private int getProcessors() {
		return numProcs;
	}
	

}


//	ExecutorService exe;
//	
//	/** proceed or evolve the simulation by a discrete time duration */
//	public void forward(final double dt) {
//		time.set(getTime().get() + dt * getTimeScale().get());
//
//		//		frameDT.add(dt);
//		//		double avgFramePeriod = 0;
//		//		for (Double d : frameDT) {
//		//			avgFramePeriod += d;
//		//		}
//		//		avgFramePeriod /= frameDT.size();
//		//		averageFPS.set( 1.0 / avgFramePeriod );
//
//		if (repeatAction == null)
//			return;
//
//		//		for (Repeat w : repeatAction.keySet()) {
//		//			Repetition e = repeatAction.get(w);
//		//			if (e.remaining <= 0) {
//		//				double edt = -e.remaining + dt;
//		//				double nextRemaining = w.afterElapsed(edt + e.accumulated);
//		//				e.remaining = e.accumulated = nextRemaining;
//		//			}
//		//			else {
//		//				e.remaining -= dt;
//		//			}
//		//
//		//		}
//
//
//		int numThreads = 2;
//		if (exe!=null) {
//			List<Runnable> remaining = exe.shutdownNow();
//		}
//		exe = Executors.newFixedThreadPool(numThreads);
//
//		final Object[] r = repeatAction.keySet().toArray();
//		int rsize = r.length;
//		final Object[] q = new Object[rsize];
//		for (int i = 0; i < rsize; i++)
//			q[i] = repeatAction.get(r[i]);
//		int n = rsize / numThreads;
//		int a = 0, b;
//
//		for (int i = 0; i < numThreads; i++) {
//			b = Math.min(a + n, rsize);
//			final int aFinal = a;
//			final int bFinal = b;
//			exe.submit(new Runnable() {
//				@Override public void run() {
//					double startTime = time.get(); 
//					int count = 0;
//					for (int j = aFinal; j < bFinal; j++) {
//						Repeat next = (Repeat)r[j];
//						Repetition e = (Repetition)q[j];
//						double edt = -e.remaining + dt;
//						double nextRemaining = next.afterElapsed(edt + e.accumulated);
//						e.remaining = e.accumulated = nextRemaining;
//						count++;						
//					}
//					//System.out.println(this + " did " + count);
//				}
//			});
//			a = b+1;
//		}
//		
//
//	}

