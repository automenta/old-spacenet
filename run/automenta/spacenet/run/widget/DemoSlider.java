package automenta.spacenet.run.widget;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderNumbers;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

public class DemoSlider extends ProcessBox {
	
	private Slider vSlider;
	private Slider hSlider;
	private Rect posRect;

	
	@Override
	public void run() {
		hSlider = add(new Slider(0, -0.5, 0.5, 0.2, SliderType.Horizontal));
		hSlider.scale(2.0, 0.2).move(0, -1.2);		
		hSlider.add(new SliderNumbers());

		vSlider = add(new Slider(0, -0.5, 0.5, 0.2, SliderType.Vertical));
		vSlider.scale(0.2, 2.0).move(-1.2,0);
		vSlider.add(new SliderNumbers());
		
		posRect = add(new Rect(Color.Orange).scale(0.1,0.1));
		
		add(new IfDoubleChanges(hSlider.getValue(), vSlider.getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				posRect.move(hSlider.getValue().get(), vSlider.getValue().get());
			}
			
		});
		
		scale(0.5);
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoSlider().scale(8));
	}

}
