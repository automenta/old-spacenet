package automenta.spacenet.space.object.widget.slider;

import java.text.DecimalFormat;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.string.StringVar;

public class LabeledSlider extends Slider {

	private Rect valueLabelRect;
	private Rect minValueLabelRect;
	private Rect maxValueLabelRect;
	private StringVar valueLabel;
	private StringVar minValueLabel;
	private StringVar maxValueLabel;
	
	double labelZ = 0.1;

	public LabeledSlider(double value, double minValue, double maxValue, double knobProportion, SliderType type) {
		super(value, minValue, maxValue, knobProportion, type);
	}
	
	@Override public void start() {
		super.start();
		
		valueLabel = new StringVar("");
		valueLabelRect = getKnob().add(new TextRect(valueLabel)).move(0, 0);

		minValueLabel = new StringVar("");
		minValueLabelRect = add(new TextRect(minValueLabel));

		maxValueLabel = new StringVar("");
		maxValueLabelRect = add(new TextRect(maxValueLabel));

		if (getType() == SliderType.Horizontal) {
			minValueLabelRect.size(0.2, 1.0).move(-0.5, 0, labelZ);
			maxValueLabelRect.size(0.2, 1.0).move(0.5, 0, labelZ);
		}
		else if (getType() == SliderType.Vertical) {
			minValueLabelRect.size(1.0, 0.2).move(0, -0.5, labelZ);
			maxValueLabelRect.size(1.0, 0.2).move(0, 0.5, labelZ);			
		}
		
		updateLabels();
		
		add(new IfDoubleChanges(getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				updateLabels();
			}			
		});
	}
	

	DecimalFormat decFormat = new DecimalFormat("###.##");
	
	protected void updateLabels() {
		
		valueLabel.set(decFormat.format(getValue().d()));
		minValueLabel.set(decFormat.format(getMin().d()));
		maxValueLabel.set(decFormat.format(getMax().d()));
	}

}
