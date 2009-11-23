package automenta.spacenet.test;

import automenta.spacenet.test.*;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import automenta.spacenet.Root;
import automenta.spacenet.act.DefaultScheduler;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.act.Timed;


public class TestTimed extends TestCase {
	private static final Logger logger = Logger.getLogger(TestTimed.class);
	
	protected Date whenRun = null;
	
	double testDurationSeconds = 0.5;
	
	public void testTimed() throws Exception {		
		Root r = new Root();
		
		Scheduler timeRunner = r.addThe(DefaultScheduler.class);
		
		Date whenStarted = new Date();
		
		logger.info("testTimed: start");
		r.add(new Timed(testDurationSeconds) {
			@Override public boolean run() {
				logger.info(" run");
				whenRun = new Date();
				return true;
			}			
		});
		
		assertTrue(timeRunner.getRunning().size() == 1);
		
		assertTrue(whenRun==null);

		Thread.sleep((long)(testDurationSeconds * 1000 * 1.1));
		
		assertTrue(whenRun!=null);
		
		assertTrue(timeRunner.getRunning().size() == 0);
		
		r.stop();
		
	}

	int repeatCount = 0;
	int repeats = 3;
	
	public void testRepeat() throws Exception {
		Root r = new Root();
		
		Scheduler timeRunner = r.addThe(DefaultScheduler.class);
		
		logger.info("testRepeat: start");
		r.add(new Timed(testDurationSeconds, testDurationSeconds) {
			@Override public boolean run() {
				logger.info(" run");
				repeatCount++;
				if (repeatCount == repeats)
					return false;
				return true;
			}			
		});
		
		assertTrue(timeRunner.getRunning().size() == 1);
		
		assertTrue(repeatCount == 0);

		Thread.sleep((long)(testDurationSeconds * repeats * 1000 * 1.1));
		
		assertTrue(repeatCount == repeats);
		
		assertTrue(timeRunner.getRunning().size() == 0);
		
		r.stop();
	}
}
