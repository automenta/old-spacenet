package automenta.spacenet.space.object.widget;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.UURI;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;

abstract public class Widget extends Box implements Starts, Stops {
	
	private StringVar description = new StringVar();
	private StringVar name = new StringVar();
	private Pointer pointer;
	private Video3D video;
	private WidgetContext widgets;

	protected Color panelBackColor = new Color(0.2, 0.2, 0.2, 0.9);
	protected Color panelHiliteColor = new Color(Color.Orange);

	protected Color buttonTextColor = new Color(Color.White);
	protected Color buttonBackTouchedColor = Color.gray(0.3, 0.9);	
	protected Color buttonBackPressedColor = Color.gray(0.25, 0.9);	

	protected double buttonDepressionDepth = 0.1;
	protected double buttonDepressionTime = 0.05;

	protected Color textEditColor = new Color(Color.White); 
	protected Color textEditCursorColor = Color.Orange.alpha(0.4);

	protected DoubleVar panelBevelBorderDepth = new DoubleVar(0.0);
	protected DoubleVar panelBevelBorderWidth = new DoubleVar(0.05);

	protected DoubleVar windowResizeEdgeWidth = new DoubleVar(0.05);

	protected Color sliderKnobColor = Color.GrayMinusMinus.alpha(0.8);
	protected double sliderKnobWidth = 0.9;
	protected double sliderKnobDepth = 0.1;

	
	public Widget() {
		super();		
	}
	
	public Widget(Vector3 position, Vector3 size, Vector3 orientation) {
		super(position, size, orientation);
	}
	
	public StringVar getName() { return name ; } 
	public StringVar getDescription() { return description ; }		
	
	@Override public void start() {
		//renderOrder = RenderOrder.Order;
		
		this.pointer = getThe(Pointer.class);
		this.video = getThe(Video3D.class);
		this.widgets = getThe(WidgetContext.class);
	}
	
	@Override public void stop() {
		
	}
	
	
	public Pointer getPointer() {	return pointer;	}
	public Video3D getVideo() {	return video;	}
	
	public WidgetContext widgets() { return widgets; }

	public MapVar<String,UURI> getMedia() { return widgets().getMedia(); }

	public void requestFocus() {
		widgets().requestFocus(this);		
	}

}
