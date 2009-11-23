package automenta.spacenet.space.object.widget.slider;

import automenta.spacenet.space.object.widget.slider.Slider.SliderKnob;

public interface SliderDecorator {

	public void decorateSlider(Slider s);
	public void decorateSliderKnob(SliderKnob knob);
	
}
