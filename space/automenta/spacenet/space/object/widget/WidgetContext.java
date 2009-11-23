package automenta.spacenet.space.object.widget;

import automenta.spacenet.Scope;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.UURI;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.map.MapVar;

abstract public class WidgetContext extends Scope implements Starts, Stops {

	private ObjectVar<Space> focus = new ObjectVar();
	private Keyboard keyboard;
	private MapVar<String, UURI> media = new MapVar();

	public WidgetContext() {
		super();
	}

	public MapVar<String,UURI> getMedia() { return media ; }

	public ObjectVar<Space> getFocus() {
		return focus ;
	}

	public void requestFocus(Space space) {
		if (getFocus().get() instanceof Focusable) {
			((Focusable)getFocus().get()).onFocusChange(false);
		}

		getFocus().set(space);
		
		if (space!=null)
			if (space instanceof Focusable) {
				((Focusable)space).onFocusChange(true);
			}
	}


	@Override public void start() {	
		setThe(VectorFont.class, newDefaultFont());
		
		initMedia();
		initWidgetStyles();
		
	}

	abstract protected void initMedia();
	abstract protected void initWidgetStyles();
	
	abstract public double getZLayerDepth();

	abstract protected VectorFont newDefaultFont();


	@Override
	public void stop() {
		
	}

	public void pullDZ(HasPosition3... n) {
		pullDZ(1, n);
	}

	public void pullDZ(double layers, HasPosition3... n) {
		for (HasPosition3 hp : n)
			hp.getPosition().add(0,0,getZLayerDepth()*layers);
	}


}
