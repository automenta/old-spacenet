package automenta.spacenet.run.widget;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.widget.slider.LabeledSlider;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;

public class DemoSliderInSlider extends ProcessBox {
	
	@Override public void run() {
		for (double i = 0; i < 4; i++) {
			Slider s = add(new Slider(0.3, 0, 1, 0.1, SliderType.Vertical));
			s.scale(0.2, 1.0);
			s.move(-0.5 + i/4.0 + 0.1, 0);
			
			Slider e = s.getKnob().add(new LabeledSlider(0.3, 0, 1, 0.1, SliderType.Horizontal));
			e.move(0,0,0.05);
			e.scale(0.9, 0.5);
		}
	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoSliderInSlider());
	}
}
