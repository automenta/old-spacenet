package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.IfColorChanges;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

public class ColorEditView implements ObjectView<Color> {

	@Override
	public String getName(Color i) {
		return "Edit Color";
	}

	@Override
	public double getStrength(Color i) {
		return 0.75;
	}

	@Override
	public void run(final Color i, ObjectVar<Space> o) throws Exception {
		Rect r = new Rect();
		
		r.add(new Rect(i).span(-0.5, 0, 0.5, 0.5));
		
		final Slider red = r.add(new Slider(i.r(), 0, 1.0, 0.3, SliderType.Vertical ));
		final Slider green = r.add(new Slider(i.g(), 0, 1.0, 0.3, SliderType.Vertical ));
		final Slider blue = r.add(new Slider(i.b(), 0, 1.0, 0.3, SliderType.Vertical ));
		final Slider alpha = r.add(new Slider(i.a(), 0, 1.0, 0.3, SliderType.Vertical ));
		
		red.span(-0.4, -0.1, -0.3, -0.5);
		green.span(-0.2, -0.1, -0.1, -0.5);
		blue.span(0, -0.1, 0.1, -0.5);
		alpha.span(0.2, -0.1, 0.3, -0.5);
		
		r.add(new IfDoubleChanges(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				double r = red.getValue().d();
				double g = green.getValue().d();
				double b = blue.getValue().d();
				double a = alpha.getValue().d();
				i.set(r, g, b, a);
			}			
		});
		
		r.add(new IfColorChanges(i) {
			@Override public void afterColorChanged(Color c) {
				double r = c.r();
				double g = c.g();
				double b = c.b();
				double a = c.a();
				red.getValue().set(r);
				green.getValue().set(g);
				blue.getValue().set(b);
				alpha.getValue().set(a);
			}			
		});
		
		o.set(r);		
	}

}
