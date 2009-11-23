package automenta.spacenet.space.object.widget.slider;

import org.apache.log4j.Logger;

import automenta.spacenet.StartsIn;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.Vector3;

public class Slider extends Panel  {
	//TODO correct the minor numeric / geometry errors
	
	boolean knobMoving = false;
	
	protected static final Logger logger = Logger.getLogger(Slider.class);
	
	public static enum SliderType {
		Horizontal, Vertical
	}
	
	public static class SliderNumbers extends Box implements StartsIn<Slider> {

		private Slider slider;
		private TextRect minText;
		private TextRect maxText;
		private TextRect valText;

		@Override public void start(Slider s) {
			this.slider = s;
			
			minText = slider.add(new TextRect());
			minText.span(-0.5, -0.5, -0.4, 0.5);
			minText.moveDZ(0.1);
			minText.tangible(false);

			
			maxText = slider.add(new TextRect());
			maxText.span(0.5, -0.5, 0.4, 0.5);
			maxText.moveDZ(0.1);
			maxText.tangible(false);
			
			valText = slider.add(new TextRect("", Color.Black));
			valText.alignText(0,0);			
			valText.moveDZ(0.3);
			valText.tangible(false);
			
			add(new IfDoubleChanges(slider.getValue(), slider.getMin(), slider.getMax()) {
				@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
					updateNumbers();
				}				
			});
			
			updateNumbers();
		}

		protected void updateNumbers() {
			minText.getText().set(slider.getMin().d(), 2);
			maxText.getText().set(slider.getMax().d(), 2);
			valText.getText().set(slider.getValue().d(), 4);
			
			valText.move(slider.getKnob().getPosition());
			valText.moveDZ(0.1);
			valText.scale(Math.max(slider.getKnob().getSize().x(), slider.getKnob().getSize().y()));
		}
		
		@Override public void stop() {
		}
		
	}

	
	public class SliderKnob extends Rect implements Draggable {

		private double dragStartZ;
		private Vector3 dragStartRelative = new Vector3();

		public SliderKnob() {
			super(Color.Gray);

			//backSurface = new ColorSurface(c);
		}
		
		@Override public void startDrag(Pointer c, Drag drag) {
			this.dragStartZ = getAbsolutePosition().z();
			this.dragStartRelative.set(getAbsolutePosition());
			this.dragStartRelative.subtract(drag.getMeshIntersectStart());
			knobMoving = true;
		}

		@Override public void stopDrag(Pointer c, Drag drag) {
			knobMoving = false;
			geometryToValues();
		}

		@Override public void updateDrag(Pointer c, Drag drag) {
			geometryToValues();
			
			double px, py;
			if (type == SliderType.Horizontal) {
				px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
				py = getAbsolutePosition().y();

				double minX = Slider.this.getAbsolutePosition().x() - Slider.this.getAbsoluteSize().x() + this.getAbsoluteSize().x();
				double maxX = Slider.this.getAbsolutePosition().x() + Slider.this.getAbsoluteSize().x() - this.getAbsoluteSize().x();				
				px = Math.max(minX, Math.min(maxX, px));				

			}
			else if (type == SliderType.Vertical) {
				py = drag.getRealPositionCurrent().y() + dragStartRelative.y();
				px = getAbsolutePosition().x();		
				
				double minY = Slider.this.getAbsolutePosition().y() - Slider.this.getAbsoluteSize().y() + this.getAbsoluteSize().y();
				double maxY = Slider.this.getAbsolutePosition().y() + Slider.this.getAbsoluteSize().y() - this.getAbsoluteSize().y();
				py = Math.max(minY, Math.min(maxY, py));				
			}
			else {
				px = py = 0;				
			}
			
			double pz = dragStartZ;
			
			setNextAbsolutePosition(px, py, pz);

			
			//dragSnap.put(drag, true);
		}
		
	}

	private SliderKnob knob;
	private DoubleVar value;
	private DoubleVar min;
	private DoubleVar max;
	private DoubleVar knobLength;
	private SliderType type;
	protected double sliderKnobDZ;


	public Slider(DoubleVar currentValue, DoubleVar minValue, DoubleVar maxValue, DoubleVar knobProportion, SliderType sliderType) {
		super();
		
		this.value = currentValue;
		this.min = minValue;
		this.max = maxValue;
		this.knobLength = knobProportion;
		this.type = sliderType;
	}
	

	public Slider(double value, double minValue, double maxValue, double knobProportion, SliderType type) {
		this(new DoubleVar(value), new DoubleVar(minValue), new DoubleVar(maxValue), new DoubleVar(knobProportion), type);
	}


	public Slider(DoubleVar value, DoubleVar minValue, DoubleVar maxValue,	double knobProportion, SliderType type) {
		this(value, minValue, maxValue, new DoubleVar(knobProportion), type);
	}


	/** proportional to entire length of the slider, 0..1.0 */
	public DoubleVar getKnobLength() {
		return knobLength;
	}
	

	@Override public void start() {
		super.start();

		sliderKnobDZ = widgets().getZLayerDepth();
		
		knob = add(new SliderKnob());
		
		SliderDecorator sd = getThe(SliderDecorator.class);
		if (sd!=null) {
			sd.decorateSlider(this);
			sd.decorateSliderKnob(knob);
		}
		
		add(new IfDoubleChanges(getValue(), getMax(), getMin(), getKnobLength()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				updateKnob();
			}			
		});
		
		updateKnob();
	}
	
	protected void geometryToValues() {
		if (!isValid()) {
			disableKnob();
			return;
		}

		double p;
		
		double lh = getKnobLength().d()/(2.0);
		//double lh = 0;
		
		if (type == SliderType.Horizontal) {
			p = knob.getPosition().x();
			knob.move(Math.max(-0.5 + lh, Math.min(0.5 - lh, p)), 0, sliderKnobDZ);
		}
		else /*if (type == SliderType.Vertical) */ {
			p = knob.getPosition().y();
			knob.move(0, Math.max(-0.5 + lh, Math.min(0.5 - lh, p)), sliderKnobDZ);
		}
		
		
		double pLeft = p + lh;
		double pRight = p - lh;
		double c = (0.5 + p) * pLeft + (1.0 - (0.5 + p)) * pRight;
		double v = positionToValue(c);
		
		value.set(v);
		
	}

	private boolean isValid() {
		if ((getValue().d() == getMax().d()) && (getValue().d() == getMin().d())) {
			return false;
		}
		if (getMax().d() <= getMin().d()) {
			return false;			
		}

		return true;
	}


	private double positionToValue(double c) {
		double v;

		if (type == SliderType.Horizontal) {
			v = (c + 0.5) * (max.get() - min.get()) + min.get();
		}
		else {
			v = (c + 0.5) * (max.get() - min.get()) + min.get();			
		}
		v = Math.max(min.get(), Math.min(max.get(), v));
		return v;
	}


	protected double valueToPosition(double v) {
		v = Math.max(min.get(), Math.min(max.get(), v));
		double c = -0.5 + (v - min.get() ) / (max.get() - min.get());
		c *= (1.0 - knobLength.d());
		return c;		
	}
	
	
	protected void updateKnob() {
		if (!isValid()) {
			disableKnob();
			return;
		}
		
		double p = valueToPosition(value.get());

		if (!knobMoving) {
			if (type == SliderType.Horizontal) {
				knob.scale(knobLength.get(), sliderKnobWidth/*, sliderKnobDepth*/);
				knob.move(p, 0, sliderKnobDZ);
			}
			else if (type == SliderType.Vertical) {
				knob.scale(sliderKnobWidth, knobLength.get()/*, sliderKnobDepth*/);
				knob.move(0, p, sliderKnobDZ);
			}
		}

	}
	
	private void disableKnob() {
		knob.scale(0,0);
		knob.move(0,0);
	}


	@Override public void stop() {
		
	}


	public DoubleVar getValue() { 	return value;	}
	public DoubleVar getMin() { return min; }
	public DoubleVar getMax() { return max; }
	


	public SliderKnob getKnob() {
		return knob;
	}
	
	public SliderType getType() {
		return type;
	}

}
