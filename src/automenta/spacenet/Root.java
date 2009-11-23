package automenta.spacenet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

import automenta.spacenet.act.SimultaneousContext;
import automenta.spacenet.var.map.MapVar;



/** root = start = main = seed = beginning */
public class Root extends Scope implements Stops {
	private static final Logger logger = Logger.getLogger(Root.class);

	static {
		BasicConfigurator.configure();
		//Logger.getRootLogger().setLevel(Level.DEBUG);
		Logger.getRootLogger().setLevel(Level.INFO);
	}

	final private DefaultPicoContainer builder = new DefaultPicoContainer();
	public Root() {

		setThe(SimultaneousContext.class);		

		//		BasicConfigurator.configure(new ConsoleAppender() {
		//			@Override public void append(LoggingEvent event) {
		//				super.append(event);
		//				//...
		//			}			
		//		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override public void run() {
				Root.this.stop();
			}
		});

		getBuilder().start();

		started = true;

		if (logger.isDebugEnabled())
			logger.debug("started " + this);
	}

	public Root(Class...cl) {
		super();
		for (Class c : cl) {
			addThe(c);
		}
	}

	@Override public void stop() {
		if (!isStarted()) {
			if (logger.isDebugEnabled())
				logger.debug("never started: " + this);
			return;
		}

		
		logger.info("starting shutdown");

		clear();

		getBuilder().stop();		
				
		started = false;
		
	}




	@Override
	public MutablePicoContainer getBuilder() {
		return builder;
	}

	@Override
	public MapVar<Object, Object> getObjects() {
		return objects;
	}




}
