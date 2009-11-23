package automenta.spacenet.space.jme.video.swing.depr;

import java.awt.event.ComponentListener;

import javax.swing.JPanel;





abstract  public class JmeJPanel extends JPanel implements ComponentListener {
//	private static final Logger logger = Logger.getLogger(JmeJPanel.class);
//
//
//	/*
//	 * Note: you probably want to add this to a container that will explicitly
//	 * use this component to fill it some amount (ex: BorderLayout & BorderLayout.CENTER).
//	 * other layouts do not do this, and will appear blank until resize
//	 */
//
//	LWJGLCanvas canvas = null;
//
//	JmeSwingCanvas jmeCanvas;
//
//	private DisplaySystem display;
//
//	float fps = 60;
//
//
//	private Color backgroundColor;
//
//
//	private Vector2 pixelSize = new Vector2();
//
//	private SwingJmeOLD jme;
//
//	public JmeJPanel(SwingJmeOLD jme, Vector2 pixelSize) {
//		super(new BorderLayout());
//
//		this.jme = jme;
//		this.pixelSize = pixelSize;
//
//		//Setup JME Canvas
//		{
//			display = DisplaySystem.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
//
//			display.registerCanvasConstructor("AWT", LWJGLAWTCanvasConstructor.class);
//
//			canvas = (LWJGLCanvas)display.createCanvas((int)pixelSize.x(), (int)pixelSize.y());
//			canvas.setUpdateInput(true);
//			canvas.setTargetRate((int)fps);
//
//			// Important! Here is where we add the guts to the panel:
//			jmeCanvas = new JmeSwingCanvas(this, (int)pixelSize.x(), (int)pixelSize.y());
//			canvas.setImplementor(jmeCanvas);
//		}
//
//		//Attach JME Canvas to this Swing panel
//		{
//			add(canvas, BorderLayout.CENTER);
//
//			doLayout();
//
//			// listen for window resize
//			addComponentListener(this);
//
//			doResize();
//		}
//
//
//		//Setup Keyboard Input
//		{
//			try {
//				KeyInput.setProvider(KeyInput.INPUT_AWT);
//			}
//			catch (Exception e) {
//				System.err.println(e);
//			}
//
//			KeyListener kl = (KeyListener) KeyInput.get();
//
//			//canvas.addKeyListener(kl);
//
//			//canvas.addKeyListener(this);
//
//		}
//
//		//Setup Mouse Input
//		//TODO extract these to swing-specific implementation/wrapper of Cursor interface
//		{
//		}
//
//	}
//
//
//	@Override	public void componentResized(ComponentEvent e) {	doResize();	}
//
//	@Override	public void componentHidden(ComponentEvent e) {	}
//
//	@Override	public void componentMoved(ComponentEvent e) {	}
//
//	@Override	public void componentShown(ComponentEvent e) {	}
//
//
//	public Canvas getCanvas() { return canvas; }
//
//	protected void doResize() {
//		Jme.doLater(new Runnable() {
//
//			@Override public void run() {
//				int w = getWidth();
//				int h = getHeight();
//
//				//				if (impl.getRenderer()!=null)
//				//					System.out.println("BEFORE: " + impl.getRenderer().getWidth() + impl.getRenderer().getHeight());
//
//
//				//				if ((getJMEDisplay().getHeight() != h) || (getJMEDisplay().getWidth() != w)) {
//
//				canvas.setSize(w, h);
//				jmeCanvas.resizeCanvas(w, h);
//				pixelSize.set(w, h);
//				getJMEDisplay().setWidth(w);
//				getJMEDisplay().setHeight(h);
//
//				//				}
//
//				//				if (impl.getRenderer()!=null)
//				//					System.out.println("AFTER: " +
//				//							getJMEDisplay().getWidth() + " " +
//				//							getJMEDisplay().getHeight() + " " +
//				//							impl.getRenderer().getWidth() + " " +
//				//							impl.getRenderer().getHeight());
//
//
//
//				//((JMECanvas)canvas).makeDirty();
//
//				doLayout();
//				updateUI();
//
//			}
//
//		});
//	}
//
//
////	@Override
////	public DisplaySystem getDisplaySystem() {
////		return display;
////	}
//
//	public InputHandler getInputHandler() {
//		return jmeCanvas.getInputHandler();
//	}
//
//	public DisplaySystem getJMEDisplay() { return display; }
//
//	public Renderer getJMERenderer() { return jmeCanvas.getRenderer(); }
//
////	public AbstractNode getNode() {
////		return contentNode;
////	}
//
//	public Vector2 getPixelSize() {
//		return pixelSize;
//	}
//
//
//
//	public Node getRootNode() {
//		return jmeCanvas.getRootNode();
//	}
//
//
//
////	private void setAmbientNode(AbstractNode c) {
////		if (this.ambientNode!=null) {
////			this.ambientNode.dispose();
////		}
////
////		this.ambientNode = c;
////
////		if (this.ambientNode!=null) {
////			rootNode.attach(ambientNode);
//////			this.ambientNode.foreachState(SpaceState.class, new ObjectVisitor<SpaceState>() {
//////				@Override public void onVisit(SpaceState object) {
//////					object.onEnabled(getRootSpace());
//////				}
//////			});
////		}
////	}
//
//
//	//	@Override public void setBackground(java.awt.Color c) {
//	//		//setBackgroundColor(c);
//	//	}
//
//	public void setBackground(automenta.spacenet.space.surface.Color c) {
//		setBackground(c.toAWTColor());
//	}
//
//	@Override
//	public void setBackground(final Color c) {
//		Jme.doLater(new Runnable() {
//			@Override public void run() {
//				backgroundColor = c;
//				canvas.setBackground(c);
//			}
//		});
//	}
//
//
//	public SwingJmeOLD getJmeSwing() {
//		return jme;
//	}
//
//
//
//
//	//	@Override
//	//	public RealValue getMaxSpaceUpdatePeriod() {
//	//		// TODO Auto-generated method stub
//	//		return null;
//	//	}
//	//
//	//	@Override
//	//	public RealValue getMaxVideoUpdatePeriod() {
//	//		// TODO Auto-generated method stub
//	//		return null;
//	//	}
//	//
//	//	@Override
//	//	public RealValue getSpaceUpdatePeriod() {
//	//		// TODO Auto-generated method stub
//	//		return null;
//	//	}
//	//
//	//	@Override
//	//	public RealValue getVideoUpdatePeriod() {
//	//		return videoUpdatePeriod;
//	//	}
//
////	public void setRootSpaceNode(RootSpaceNode rootSpaceNode) {
////		if (this.rootNode!=null) {
////			JME.invokeLater(new Runnable() {
////				@Override public void run() {
////					getRootNode().detach(JMEPanel.this.rootNode);
////				}
////			});
////		}
////
////		this.rootNode = rootSpaceNode;
////
////		if (this.rootNode!=null) {
////			this.rootNode.setVideo(this);
////		}
////
////		JME.invokeLater(new Runnable() {
////			@Override public void run() {
////				getRootNode().attach(JMEPanel.this.rootNode);
////			}
////		});
////
////	}
//
//
//
//
////	public static AbstractNode getParentAbstractNode(Geometry g) {
////	Node parent = g.getParent();
////	while (parent!=null) {
////		if (parent instanceof AbstractNode) {
////			return (AbstractNode)parent;
////		}
////		parent = parent.getParent();
////	}
////	return null;
////}
//
////public static SpaceNode getParentSpaceNodeOf(Geometry g) {
////	Node parent = g.getParent();
////	while (parent!=null) {
////		if (parent instanceof SpaceNode) {
////			return (SpaceNode) parent;
////		}
////		parent = parent.getParent();
////	}
////	return null;
////}
//
////public static AbstractNode getTouchableParentSpaceNodeOf(Geometry g) {
////	Spatial parent = g;
////	//System.out.print("getTouchable(" + g + ")=");
////	while (parent!=null) {
////		if (parent instanceof SpaceNode) {
////			//System.out.println((((SpaceNode)parent).getTouchable()));
////			return (((SpaceNode)parent).getTouchable());
////		}
////		parent = parent.getParent();
////		//System.out.print(parent+" -> ");
////	}
////	//System.out.println("none");
////
////	return null;
////}
//
}
