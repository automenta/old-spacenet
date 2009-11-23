package automenta.spacenet.os;
import automenta.spacenet.UURI;
import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.act.CoreActions;
import automenta.spacenet.plugin.web.StringToURI;
import automenta.spacenet.os.view.BitmapView;
import automenta.spacenet.os.view.ClassInfoView;
import automenta.spacenet.os.view.ClassMethodsView;
import automenta.spacenet.os.view.ColorEditView;
import automenta.spacenet.os.view.FeedView;
import automenta.spacenet.os.view.FileDirectoryView;
import automenta.spacenet.os.view.ListView;
import automenta.spacenet.os.view.MatrixView;
import automenta.spacenet.os.view.MeshView;
import automenta.spacenet.os.view.NewSpatialClassView;
import automenta.spacenet.os.view.SpatialView;
import automenta.spacenet.os.view.StringVarEditView;
import automenta.spacenet.os.view.StringView;
import automenta.spacenet.space.Space;
import automenta.spacenet.run.data.DemoBarChart;
import automenta.spacenet.run.data.DemoFPSChart;
import automenta.spacenet.run.data.DemoMemoryChart;
import automenta.spacenet.run.geometry.DemoBox;
import automenta.spacenet.run.geometry.DemoLine3D;
import automenta.spacenet.run.geometry.DemoRect;
import automenta.spacenet.run.geometry.bevel.DemoBevelRect;
import automenta.spacenet.run.geometry.curve3d.DemoCurve3D;
import automenta.spacenet.run.geometry.curve3d.DemoCurve3DEdit;
import automenta.spacenet.run.geometry.scalar.DemoEditableMetaballs;
import automenta.spacenet.run.surface.DemoMultiSurface;
import automenta.spacenet.run.text.DemoGroovyTerminal;
import automenta.spacenet.run.text.DemoShellTerminal;
import automenta.spacenet.run.video.DemoStrobeControl;
import automenta.spacenet.run.widget.DemoPanel;
import automenta.spacenet.run.widget.DemoSlider;
import automenta.spacenet.run.widget.DemoSliderInSlider;
import automenta.spacenet.run.widget.DemoWindow;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.var.index.MemoryIndex;
import automenta.spacenet.plugin.web.Feed;


public class OSSpace extends ProcessBox implements OS {

	private ActionIndex actions = new ActionIndex();
	private ActionIndex<Object, Space> views = new ActionIndex<Object, Space>();
	private MemoryIndex index = new MemoryIndex();

	private Linker linker;
	private Space[] initialSpaces;

	public OSSpace(Space... initialSpaces) {
		super();
		
		this.initialSpaces = initialSpaces;
		
	}

	@Override public void run() {		
		init();

		for (Space s : initialSpaces) {
			add(s);
		}
	}

	protected void init() {
		setThe(OS.class, this);
		setThe(OSSpace.class, this);
		
		initActions();
		initViews();
		initIndex();

		linker = add(new Linker(index()));
		

	}


	private void initIndex() {
		String classTags = "class";


		index.index(this, "OSSpace Class", "space" );

		index.index(OSSpace.class, "OSSpace", classTags );

		index.index(getThe(Jme.class).getUpdatesPerSecond(), "Video Frame Rate", "stat");
		index.index(video().getBackgroundColor(), "Background Color", "option");
		

		indexDemo(DemoBox.class, "Demo Box" );
		indexDemo(DemoRect.class, "Demo Rect" );
		indexDemo(DemoPanel.class, "Demo Panels" );
		indexDemo(DemoSlider.class, "Demo Sliders" );
		indexDemo(DemoSliderInSlider.class, "Demo Sliders in Sliders" );
		indexDemo(DemoBarChart.class, "Demo Bar Chart" );
		indexDemo(DemoBevelRect.class, "Demo Bevel Rect" );
		indexDemo(DemoWindow.class, "Demo Window" );
		indexDemo(DemoMultiSurface.class, "Demo MultiSurface" );
		indexDemo(DemoCurve3D.class, "Demo Curve3D" );
		indexDemo(DemoCurve3DEdit.class, "Demo Curve3D Edit" );
		indexDemo(DemoStrobeControl.class, "Demo Strobe Control" );
		indexDemo(DemoShellTerminal.class, "System Shell" );
		indexDemo(DemoGroovyTerminal.class, "Groovy Shell" );
		indexDemo(DemoEditableMetaballs.class, "Demo MetaBalls" );
//		indexDemo(DemoFindWeb.class, "Web Find (Yahoo!)" );
//		indexDemo(DemoFindImages.class, "Image Find (Yahoo!)" );
		indexDemo(DemoLine3D.class, "Demo Line3D" );
//		indexDemo(DemoForceDirectedXMLNet.class, "XML Net" );
//		indexDemo(DemoForceDirectedMeshNet.class, "Mesh Net" );
//		indexDemo(DemoForceDirectedTorusNet.class, "Torus Net" );
		indexDemo(DemoFPSChart.class, "Frame Rate" );
		indexDemo(DemoMemoryChart.class, "Free Memory" );

//		index.index(new UURI(mediaPath + "3d/bike.3ds"), "Bike", "3d, model");
//		index.index(new UURI(mediaPath + "3d/oxytocin.x3d"), "Oxytocin Molecule", "3d, model");
//		index.index(new UURI(mediaPath + "3d/teapot.x3d"), "Teapot", "3d, model");
//
//		index.index(new UURI(mediaPath + "image/face-grin.png"), "Face Grin", "bitmap, icon");
//		index.index(new UURI(mediaPath + "image/face-wink.png"), "Face Wink", "bitmap, icon");
//		index.index(new UURI(mediaPath + "image/eagle_kp09_big.jpg"), "Space Photograph", "bitmap, icon");

		{
			index.index(new UURI("http://automenta.com/rss.xml", Feed.FeedType), "automenta, feed");
			
			//http://news.yahoo.com/rss
			index.index(new UURI("http://rss.news.yahoo.com/rss/topstories", Feed.FeedType), "yahoo, news, top, feed");
			index.index(new UURI("http://rss.news.yahoo.com/rss/science", Feed.FeedType), "yahoo, news, science, feed");
			index.index(new UURI("http://rss.news.yahoo.com/rss/tech", Feed.FeedType), "yahoo, news, tech, feed");
		}
		

	}

//	protected void registerIcon(String... icons) {
//		MapVar<String, UURI> media = getThe(WidgetContext.class).getMedia();
//		for (String i : icons) {
//			UURI u = new UURI(mediaPath + "icon/" + i + ".3ds");
//			if (u!=null) {
//				media.put("icon." + i, u);
//
//				index.index(u, i, "icon");
//			}
//		}
//	}

	
//	public void addBitmapBackground() {
//		double gridDZ = 1.2;
//
//		UURI imgUrl = new UURI(mediaPath + "/image/eagle_kp09_big.jpg");
//		add(new Rect(new BitmapSurface(imgUrl )).moveDelta(0,0,-gridDZ)).scale(20).tangible(false);
//		
//	}
	
	private void indexDemo(Class c, String name) {
		final String demoTags = "class, demo";
		index.index(c, name, demoTags);
	}



//	@Deprecated protected ListVar<Space> getActionMenu(Object o) {		
//		ListVar<Space> l = new ListVar();
//
//		if (o instanceof String) {
//			l.add(new TextButton("Find"));
//			l.add(new TextButton("To Javascript"));
//			l.add(new TextButton("To Python"));
//			l.add(new TextButton("To Groovy"));
//			l.add(new TextButton("New URL"));
//			l.add(new TextButton("New Note"));
//			l.add(new TextButton("New List"));
//			l.add(new TextButton("New Table"));
//			l.add(new TextButton("Clone"));
//		}
//
//		return l ;
//	}



	protected void initActions() {
		CoreActions.addCoreActions(actions());
		actions().addAction(new StringToURI());
	}

	protected void initViews() {
		views().addAction(new FileDirectoryView());

		views().addAction(new NewSpatialClassView());
		views().addAction(new SpatialView());

		views().addAction(new FeedView());

		views().addAction(new StringView());

		views().addAction(new MeshView());
		views().addAction(new BitmapView());

		views().addAction(new MatrixView());
		views().addAction(new ListView());
		views().addAction(new ClassInfoView());
		views().addAction(new ClassMethodsView());

		views().addAction(new StringVarEditView());

		views().addAction(new ColorEditView());
	}

	@Override public ActionIndex<Object,Space> views() {	return views;	}
	@Override public ActionIndex actions() {	return actions;	}
	@Override public Linker linker() {	return linker;	}
	@Override public MemoryIndex index() {	return index;	}

}
