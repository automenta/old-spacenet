package automenta.spacenet.space.object.widget.panel;

import org.apache.log4j.Logger;

import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pressable;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.RectBorder;
import automenta.spacenet.space.object.widget.Widget;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;


public class Panel extends Widget implements Touchable, Pressable {
	private static final Logger logger = Logger.getLogger(Panel.class);

	DoubleVar hiliteStart = new DoubleVar(1.00);
	DoubleVar hiliteEnd = new DoubleVar(1.02);

	protected BooleanVar pressed = new BooleanVar(false);
	protected BooleanVar touched = new BooleanVar(false);

	protected Rect panelRect;

	private RectBorder hilite;

	private ColorSurface hiliteSurface;

	protected boolean withBacking = true;

//	public static class PanelRect extends Rect implements Touchable, Pressable {
//
//		private Panel panel;
//
//		public PanelRect(Panel p, Surface surface) {
//			super(surface);
//			getTangible().set(true);
//			this.panel = p;
//		}
//
//		@Override
//		public void startTouch(Pointer c) {
//			panel.startTouch(c);
//		}
//
//		@Override
//		public void stopTouch(Pointer c) {
//			panel.stopTouch(c);
//		}
//
//		@Override
//		public void updateTouch(Pointer c, double timeTouched) {
//			panel.updateTouch(c, timeTouched);
//		}
//
//		@Override
//		public void pressStart(Pointer c, Vector3 location,
//				Vector2 relativeToRect) {
//			panel.pressStart(c, location, relativeToRect);
//		}
//
//		@Override
//		public void pressStopped(Pointer c) {
//			panel.pressStopped(c);
//		}
//
//		@Override
//		public void pressUpdate(Pointer c, double timePressed) {
//			panel.pressUpdate(c, timePressed);
//		}
//
//	}

	public static class PanelRect extends Rect implements Touchable, Pressable {

		private Panel panel;

		public PanelRect(Panel p, Surface surface) {
			super(surface);
			getTangible().set(true);
			this.panel = p;
		}

		@Override
		public void startTouch(Pointer c) {
			panel.startTouch(c);
		}

		@Override
		public void stopTouch(Pointer c) {
			panel.stopTouch(c);
		}

		@Override
		public void updateTouch(Pointer c, double timeTouched) {
			panel.updateTouch(c, timeTouched);
		}

		@Override
		public void pressStart(Pointer c, Vector3 location,
				Vector2 relativeToRect) {
			panel.pressStart(c, location, relativeToRect);
		}

		@Override
		public void pressStopped(Pointer c) {
			panel.pressStopped(c);
		}

		@Override
		public void pressUpdate(Pointer c, double timePressed) {
			panel.pressUpdate(c, timePressed);
		}

		@Override
		public String toString() {
			return panel.toString() + "(back)";
		}
		
	}

	public Panel() {
		super();
	}

	public Panel(Vector3 position, Vector3 size, Vector3 orientation) {
		super(position, size, orientation);
	}

	public Panel(boolean withBacking) {
		super();
		this.withBacking = withBacking;
	}

	@Override public void start() {
		super.start();
		
		panelRect = add(newPanelRect());
		PanelDecorator pd = getThe(PanelDecorator.class);
		if (pd!=null) {
			pd.decoratePanel(this);
		}
		
		updateWidget();
	}

	
	protected Rect newPanelRect() {
		return new PanelRect(this, new ColorSurface(getBackColor()));
	}

	protected DoubleVar getBevelZ() {
		return panelBevelBorderDepth;
	}
	protected DoubleVar getBevelBorder() {
		return panelBevelBorderWidth;
	}
	
	protected boolean hasBorder() { return true;	}

	public boolean hasBack() {	return withBacking; }



	public Color getBackColor() {
		return panelBackColor;
	}
	
	protected void updateWidget() {
		if (isTouched()) {
			if (hilite == null) {
				
				if (hiliteSurface == null)
					hiliteSurface = newHiliteSurface();
				
				hilite = add(new RectBorder(hiliteSurface, hiliteStart, hiliteEnd ));
				hilite.tangible(false);
				//hilite.setRenderOrder(RenderOrder.Transparent)
			}
			
			hilite.visible(true);
			hiliteSurface.getColor().set(Color.Orange.alpha(0.3));
		}
		else {
			if (hilite!=null) {
				//remove(hilite);
				//hilite = null;
				hiliteSurface.getColor().setA(0);
				hilite.visible(false);
			}
		}
//		if (isTouched()) {
//			backSurface.getColor().set(Color.gray(0.5, 0.2));
//		} else {
//			backSurface.getColor().set(Color.gray(0.5, 0.18));
//		}
	}

	private ColorSurface newHiliteSurface() {
//		double hue = ((double)(getClass().hashCode() % 100)) / 100.0;
//		return new ColorSurface(Color.hsb(hue, 1.0, 1.0).alpha(0.5));
		return new ColorSurface(panelHiliteColor.alpha(0));
	}

	public boolean isTouched() {
		return touched.b();
	}

	@Override
	public void startTouch(Pointer c) {
		touched.set(true);
		updateWidget();
	}

	@Override
	public void stopTouch(Pointer c) {
		touched.set(false);
		updateWidget();
	}

	@Override public void updateTouch(Pointer c, double timeTouched) {
		double fullTimeTouched = 2.0;
		hiliteSurface.getColor().setA(0.25 + 0.35 * timeTouched / fullTimeTouched);
	}

	@Override
	public void pressStart(Pointer c, Vector3 location, Vector2 relativeToRect) {
		pressed.set(true);
	}

	@Override
	public void pressStopped(Pointer c) {
		pressed.set(false);
	}

	@Override
	public void pressUpdate(Pointer c, double timePressed) {
	}

	public BooleanVar getPressed() {
		return pressed;
	}

	public BooleanVar getTouched() {
		return touched;
	}

	public Rect getPanelRect() {
		return panelRect;
	}
	
	public <X extends HasPosition3> X addOn(X space) {
		add(space);
		widgets().pullDZ(space);
		return space;
	}

}
