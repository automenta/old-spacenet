package automenta.spacenet.space.jme.video.window;

import com.jme.system.GameSettings;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import org.apache.log4j.Logger;

import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.filter.RenderFlow;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfBoolChanges;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.Vector2;

import com.jme.app.AbstractGame;
import com.jme.app.BaseSimpleGame;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.InputSystem;
import com.jme.input.MouseInput;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jme.util.Timer;
import java.awt.EventQueue;
import java.net.URL;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;


public class JmeWindowLoop extends BaseSimpleGame {
    private SpaceNetPropertiesDialog propertiesDialog;
    public enum ConfigShowMode {
        /**
         * Never displays a <code>PropertiesDialog</code> on startup, using
         * defaults if no configuration file is found.
         */
        NeverShow,
        /** Always displays a <code>PropertiesDialog</code> on startup. */
        AlwaysShow,
        /**
         * Displays a <code>PropertiesDialog</code> only if the properties
         * file is not found or could not be loaded.
         */
        ShowIfNoConfig;
    }
    private ConfigShowMode configShowMode = ConfigShowMode.ShowIfNoConfig;
    private URL settingsDialogImageOverride = null;

    private SpaceNetSettings spaceNetSettings;

	private static final double sleepThreshold = 5.0e-3;

	private static final Logger logger = Logger.getLogger(JmeWindowLoop.class.getName());

	private static final boolean wireStateEnabled = false;

	private static final int FPS_Frames = 128;

	private static final boolean THREAD_FRIENDLY = true;

	private boolean enableFirstPersonHandler = false;

	private Jme jme;



	final private Vector3f loc = new Vector3f( 0.0f, 0.0f, 25.0f );
	final private Vector3f left = new Vector3f( -1.0f, 0.0f, 0.0f );
	final private Vector3f up = new Vector3f( 0.0f, 1.0f, 0.0f );
	final private Vector3f dir = new Vector3f( 0.0f, 0f, -1.0f );



	private RenderFlow renderFlow;

	private Vector2 pixelSize;

	private IfVector2Changes whenPixelSizeChanges;



	public JmeWindowLoop(Jme jme, RenderFlow renderFlow, int subSamples) {
		super();

		this.samples = subSamples;

		/** Create rootNode */
		rootNode = new Node( "RootNode" );

		this.jme = jme;
		this.renderFlow = renderFlow;
		showNormals = false;
		showBounds = false;



	}

	int updateNumber;

	private long updateStartNano;

	private int renderNumber;

	private long renderStartNano;

	private IfBoolChanges whenFullscreenChanges;

	private int depth;

	private int frequency;

	private IfStringChanges whenTitleChanges;

	/**
     * This is called every frame in BaseGame.start()
     *
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#update(float interpolation)
     */
    @Override
	protected final void update(float interpolation) {
		// handle input events prior to updating the scene
		// - some applications may want to put this into update of
		// the game state
		InputSystem.update();

        timer.update();

        tpf = timer.getTimePerFrame();

        // Execute updateQueue
        GameTaskQueueManager.getManager().getQueue(GameTaskQueue.UPDATE).execute();

        updateInput();

		if ( !pause ) {
			renderFlow.forward(tpf);
		}


		updateUpdatesPerSecond();

	}


    private void updateUpdatesPerSecond() {
		if (updateNumber % FPS_Frames == 0) {
			long currentNano = System.nanoTime();
			getJme().getUpdatesPerSecond().set( 1.0 / ((currentNano - updateStartNano)/1e9/(FPS_Frames)) );
			updateStartNano = currentNano;
		}

		updateNumber++;
	}
    private void updateRendersPerSecond() {
		if (renderNumber % FPS_Frames == 0) {
			long currentNano = System.nanoTime();
			getJme().getRendersPerSecond().set( 1.0 / ((currentNano - renderStartNano)/1e9/(FPS_Frames)) );
			renderStartNano = currentNano;
		}

		renderNumber++;
	}



	/**
     * This is called every frame in BaseGame.start(), after update()
     *
     * @param interpolation
     *            unused in this implementation
     * @see AbstractGame#render(float interpolation)
     */
    @Override
	protected void render(float interpolation) {
        super.render(interpolation);

        renderFlow.render(display);

		// swap buffers
		display.getRenderer().displayBackBuffer();

		updateRendersPerSecond();
    }


	public void run() {
		try {
			getAttributes();


			if (!finished) {
				initSystem();

				assertDisplayCreated();

				initGame();

				updateStartNano = System.nanoTime();

				//double timeUntilUpdate = getUpdatePeriod().d();
				//double timeUntilRender = getRenderPeriod().d();

				long frameStartTick = -1;
		        long frames = 0;
		        long frameDurationTicks = -1;
		        int preferredTicksPerFrame = -1;
		        double preferredFPS = 1.0 / getUpdatePeriod().d();
				if (preferredFPS >= 0) {
		             preferredTicksPerFrame = Math.round(timer.getResolution() / (float)preferredFPS);
		        }

				// main loop
				long lastT = System.nanoTime();;
				while (!finished && !display.isClosing()) {
		            if (preferredTicksPerFrame >= 0) {
		                frameStartTick = timer.getTime();
		            }

					long tNano = System.nanoTime();
					long dtNano = tNano - lastT;
					double dt = (dtNano) * 1e-9;

//					timeUntilUpdate-=dt;
//					timeUntilRender-=dt;

					update(-1.0f);
					render(-1.0f);

			           // Fixed framerate End
		            if (preferredTicksPerFrame >= 0) {
		                frames++;
		                frameDurationTicks = timer.getTime() - frameStartTick;
		                while (frameDurationTicks < preferredTicksPerFrame) {
		                    long sleepTime = ((preferredTicksPerFrame - frameDurationTicks) * 1000) / timer.getResolution();
		                    try {
		                        Thread.sleep(sleepTime);
		                    } catch (InterruptedException exc) {
		                        logger.error("Interrupted while sleeping in fixed-framerate: " + exc);
		                    }
		                    frameDurationTicks = timer.getTime() - frameStartTick;
		                }
		                if (frames == Long.MAX_VALUE) frames = 0;
		            }


//					if (THREAD_FRIENDLY)
//						Thread.yield();

//					double lagTime = Math.min(timeUntilUpdate, timeUntilRender);
//					if (lagTime > sleepThreshold) {
//						Thread.sleep( (long)(lagTime * 1.0e-3 ) );
//					}
//					else {
//						Thread.yield();
//					}

					lastT = tNano;
				}
			}
		} catch (Throwable t) {
			logger.error(t);
			t.printStackTrace();
			if (throwableHandler != null) {
				throwableHandler.handle(t);
			}
		}

		cleanup();

		if (display != null)
			display.reset();
		quit();
	}



//	private DoubleVar getRenderPeriod() {
//		return getJme().getVideoState().getRenderPeriod();
//	}


	private DoubleVar getUpdatePeriod() {
		return getJme().getVideoState().getUpdatePeriod();
	}


	@Override
	protected void cleanup() {
		super.cleanup();

		renderFlow.dispose();

		getJme().remove(whenPixelSizeChanges);
	}

//	public static void initSystem(DisplaySystem display, String windowTitle, int depthBits, int stencilBits, int alphaBits, int samples) {
//		display.setTitle(windowTitle);
//		display.setMinDepthBits( depthBits );
//		display.setMinStencilBits( stencilBits );
//		display.setMinAlphaBits( alphaBits );
//		display.setMinSamples( samples );
//	}

	public static void initRoot(DisplaySystem display, Node rootNode, Camera cam) {
		/**
		 * Create a ZBuffer to display pixels closest to the camera above
		 * farther ones.
		 */
//		ZBufferState buf = display.getRenderer().createZBufferState();
//		buf.setEnabled( true );
//		buf.setFunction( ZBufferState.TestFunction.LessThanOrEqualTo );
//		rootNode.setRenderState( buf );

	}

	/**
	 * Creates rootNode, lighting, statistic text, and other basic render
	 * states. Called in BaseGame.start() after initSystem().
	 *
	 * @see AbstractGame#initGame()
	 */
	@Override protected void initGame() {
		initRoot(display, rootNode, cam);

		timer.reset();
	}


	private Jme getJme() {
		return jme;
	}

	@Override protected void updateInput() {
        input.update( tpf );

		getJme().forward(tpf);
	}

	public Vector2 getPixelSize() {
		return pixelSize;
	}


	/**
	 * Creates display, sets up camera, and binds keys. Called in
	 * BaseGame.start() directly after the dialog box.
	 *
	 * @see AbstractGame#initSystem()
	 */
	@Override
	protected void initSystem() throws JmeException {

		logger.debug("version: " + getVersion());
		logger.debug(" alpha bits: " + alphaBits);
		logger.debug(" depth bits: " + depthBits);
		logger.debug(" stencil bits: " + stencilBits);

//		settings.setSamples(samples);
//		settings.setAlphaBits(alphaBits);
//		settings.setDepthBits(depthBits);
//		settings.setStencilBits(stencilBits);

		try {
			/**
			 * Get a DisplaySystem acording to the renderer selected in the
			 * startup box.
			 */
			display = DisplaySystem.getDisplaySystem(spaceNetSettings.getRenderer() );



            jme.getVideoState().getSubsamples().set( spaceNetSettings.getSubSamples() );
            logger.info("subsamples: " + jme.getVideoState().getSubsamples());

			display.setMinSamples( jme.getVideoState().getSubsamples().i() );
//			initSystem(display,
//					jme.getVideoState().getWindowTitle().s(),
//					jme.getVideoState().getDepthBits().i(),
//					jme.getVideoState().getStencilBits().i(),
//					jme.getVideoState().getAlphaBits().i(),
//					jme.getVideoState().getSubsamples().i() );


			depth = spaceNetSettings.getDepth();
			frequency = spaceNetSettings.getFrequency();

			Vector2 pixelSize = getJme().getPixelSize();

			pixelSize.set(spaceNetSettings.getWidth(), spaceNetSettings.getHeight());

			//System.out.println("depth="  + depth + ", freq=" + frequency + ", pixelSize=" + pixelSize + ", fullscreen=" + jme.getVideoState().getFullScreen().b());
			/** Create a window with the startup box's information. */
			display.createWindow((int)pixelSize.x(), (int)pixelSize.y(),
					depth, frequency,
					jme.getVideoState().getFullScreen().b() );


			updateTitle();
			whenTitleChanges = getJme().add(new IfStringChanges(getJme().getVideoState().getWindowTitle()) {
				@Override public void afterTextChanged(StringVar t, String previous, String current) {
					updateTitle();
				}
			});

			whenPixelSizeChanges = getJme().add(new IfVector2Changes(pixelSize) {
				@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
					recreateWindow();
				}
			});
			whenFullscreenChanges = getJme().add(new IfBoolChanges(jme.getVideoState().getFullScreen()) {
				@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
					recreateWindow();
				}
			});

			logger.info("video: " + display.getAdapter()
					+ ", driver: " + display.getDriverVersion() + ", vendor: "
					+ display.getDisplayVendor() + ", renderer: "
					+ display.getDisplayRenderer() + ", api: "
					+ display.getDisplayAPIVersion() + ")");


			/**
			 * Create a camera specific to the DisplaySystem that works with the
			 * display's width and height
			 */
			cam = display.getRenderer().createCamera( display.getWidth(),
					display.getHeight() );

		} catch ( JmeException e ) {
			/**
			 * If the displaysystem can't be initialized correctly, exit
			 * instantly.
			 */
			logger.error("Could not create displaySystem", e);
			System.exit( 1 );
		}

		/** Set a black background. */
		display.getRenderer().setBackgroundColor( ColorRGBA.black.clone() );

		/** Set up how our camera sees. */
		cameraPerspective();
		Vector3f loc = new Vector3f( 0.0f, 0.0f, 25.0f );
		Vector3f left = new Vector3f( -1.0f, 0.0f, 0.0f );
		Vector3f up = new Vector3f( 0.0f, 1.0f, 0.0f );
		Vector3f dir = new Vector3f( 0.0f, 0f, -1.0f );
		/** Move our camera to a correct place and orientation. */
		cam.setFrame( loc, left, up, dir );
		/** Signal that we've changed our camera's location/frustum. */
		cam.update();
		/** Assign the camera to this renderer. */
		display.getRenderer().setCamera( cam );

		{

			MouseInput.get().setCursorVisible(true);


			final InputHandler rootHandler = new InputHandler();
//			input = rootHandler;

			if (enableFirstPersonHandler) {
				FirstPersonHandler firstPersonHandler = new FirstPersonHandler( cam, 50,  1 );
				input = firstPersonHandler;
				//input.addToAttachedHandlers(firstPersonHandler);
			}
			else {
				input = rootHandler;
			}
		}

		/** Get a high resolution timer for FPS updates. */
		timer = Timer.getTimer();

	}

	private void updateTitle() {
		display.setTitle(getJme().getVideoState().getWindowTitle().s());
	}


	protected void recreateWindow() {
		int w = 0, h = 0;

		boolean fullscreen = jme.getVideoState().getFullScreen().b();

		if (fullscreen) {
			int mw = 0;
			int mh = 0;
			int ma = 0;
		    DisplayMode[] modes = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes();
		    for (DisplayMode dm : modes) {
		    	System.out.println("mode: " + modes.length);
		    	int a = dm.getWidth() * dm.getHeight();
		    	if (a > ma) {
		    		ma = a;
		    		mw = dm.getWidth();
		    		mh = dm.getHeight();
		    	}
		    }

		    w = mw;
		    h = mh;
		}

		if ((w == 0) || (h == 0)) {
			w = (int) jme.getVideo().getPixelSize().x();
			h = (int) jme.getVideo().getPixelSize().y();
		}

		display.recreateWindow(w, h, depth, frequency, fullscreen);
	}



	public Camera getCamera() {
		return cam;
	}


	public DisplaySystem getDisplaySystem() {
		return display;
	}


	public Node getRootNode() {
		return rootNode;
	}

	@Override
	public String toString() {
		return "JmeWindow internals";
	}


	@Override
	protected void simpleInitGame() {
		// TODO Auto-generated method stub

	}

    protected SpaceNetSettings getNewSettings() {
        return new SpaceNetSettings();
    }

    @Override
    protected void getAttributes() {

        spaceNetSettings = getNewSettings();
        if ((spaceNetSettings.isNew() && configShowMode == ConfigShowMode.ShowIfNoConfig) || configShowMode == ConfigShowMode.AlwaysShow) {
            URL dialogImage = settingsDialogImageOverride;
            if (dialogImage == null) {
                String dflt = spaceNetSettings.getDefaultSettingsWidgetImage();
                if (dflt != null) {
                    try {
                        dialogImage = AbstractGame.class.getResource(dflt);
                    } catch (Exception e) {
                        logger.warn("Resource lookup of '" + dflt + "' failed.  Proceeding.");
                    }

                }
            }
            if (dialogImage == null) {
                logger.debug("No dialog image loaded");
            } else {
                logger.debug("Using dialog image '" + dialogImage + "'");
            }

            final URL dialogImageRef = dialogImage;
            final AtomicReference<SpaceNetPropertiesDialog> dialogRef =
                    new AtomicReference<SpaceNetPropertiesDialog>();
            final Stack<Runnable> mainThreadTasks = new Stack<Runnable>();
            try {
                if (EventQueue.isDispatchThread()) {
                    dialogRef.set(new SpaceNetPropertiesDialog(spaceNetSettings,
                            dialogImageRef, mainThreadTasks));
                } else {
                    EventQueue.invokeLater(new Runnable() {

                        public void run() {
                            dialogRef.set(new SpaceNetPropertiesDialog(spaceNetSettings,
                                    dialogImageRef, mainThreadTasks));
                        }
                    });
                }
            } catch (Exception e) {
                logger.warn(this.getClass().toString() +  "AbstractGame.getAttributes(): " + e);
                return;
            }

            propertiesDialog = dialogRef.get();
            while (propertiesDialog == null || propertiesDialog.isVisible()) {
                try {
                    // check worker queue for work
                    while (!mainThreadTasks.isEmpty()) {
                        mainThreadTasks.pop().run();
                    }
                    // go back to sleep for a while
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    logger.warn("Error waiting for dialog system, using defaults.");
                } catch (UnsatisfiedLinkError t) {
                    if (t.getLocalizedMessage() != null && t.getLocalizedMessage().contains("java.library.path")) {
                        logger.warn("\n\nNative library not set - go to \nhttp://www.jmonkeyengine.com/wiki/doku.php?id=no_lwjgl_in_java.library.path \nfor details.");
                    }
                    t.printStackTrace();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                propertiesDialog = dialogRef.get();
            }

            if (propertiesDialog != null && propertiesDialog.isCancelled()) {
                //System.exit(0);
                finish();
            }
        }
    }


}

///**
//* Create a wirestate to toggle on and off. Starts disabled with default
//* width of 1 pixel.
//*/
//wireState = display.getRenderer().createWireframeState();
//wireState.setEnabled( wireStateEnabled );
//rootNode.setRenderState( wireState );

//// -- STATS, text node
//// Finally, a stand alone node (not attached to root on purpose)
//statNode = new Node( "Stats node" );
//statNode.setCullHint( Spatial.CullHint.Never );
//statNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
//
//if (Debug.stats) {
//	graphNode = new Node( "Graph node" );
//	graphNode.setCullHint( Spatial.CullHint.Never );
//	statNode.attachChild(graphNode);
//
//	setupStatGraphs();
//	setupStats();
//}

//Setup lights
//		PointLight light = new PointLight();
//		light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
//		light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
//		light.setLocation(new Vector3f(0, 30, 0));
//		light.setEnabled(true);
//		lightState.attach(light);

//public void run() {
//	try {
//		getAttributes();
//
//
//		if (!finished) {
//			initSystem();
//
//			assertDisplayCreated();
//
//			initGame();
//
//			updateStartNano = System.nanoTime();
//
//			double timeUntilUpdate = getUpdatePeriod().d();
//			double timeUntilRender = getRenderPeriod().d();
//
//			long frameStartTick = -1;
//	        long frames = 0;
//	        long frameDurationTicks = -1;
//	        int preferredTicksPerFrame = -1;
//	        double preferredFPS = 1.0 / getRenderPeriod().d();
//			if (preferredFPS >= 0) {
//	             preferredTicksPerFrame = Math.round((float)timer.getResolution() / (float)preferredFPS);
//	        }
//
//			// main loop
//			long lastT = System.nanoTime();;
//			while (!finished && !display.isClosing()) {
//	            if (preferredTicksPerFrame >= 0) {
//	                frameStartTick = timer.getTime();
//	            }
//
//				long tNano = System.nanoTime();
//				long dtNano = tNano - lastT;
//				double dt = ((double)dtNano) * 1e-9;
//
//				timeUntilUpdate-=dt;
//				timeUntilRender-=dt;
//
//				if (timeUntilUpdate < 0.0) {
//					// update game state, do not use interpolation parameter
//					update(-1.0f);
//					timeUntilUpdate = getUpdatePeriod().d();
//				}
//
//				if (timeUntilRender < 0.0) {
//					// render, do not use interpolation parameter
//					render(-1.0f);
//					timeUntilRender = getRenderPeriod().d();
//				}
//
//
//				Thread.yield();
//
////				double lagTime = Math.min(timeUntilUpdate, timeUntilRender);
////				if (lagTime > sleepThreshold) {
////					Thread.sleep( (long)(lagTime * 1.0e-3 ) );
////				}
////				else {
////					Thread.yield();
////				}
//
//				lastT = tNano;
//			}
//		}
//	} catch (Throwable t) {
//		logger.error(t);
//		t.printStackTrace();
//		if (throwableHandler != null) {
//			throwableHandler.handle(t);
//		}
//	}
//
//	cleanup();
//
//	if (display != null)
//		display.reset();
//	quit();
//}
