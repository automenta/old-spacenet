package automenta.spacenet.run.video;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;

public class DemoStrobeControl extends ProcessBox {

	@Override public void run() {
		Box b = add(new Box());
		
		final Slider randomness = b.add(new Slider(0.5, 0, 1, 0.1, SliderType.Vertical));
		randomness.span(-0.5, -0.5, -0.25, 0.5);
		final Slider speed = b.add(new Slider(0.5, 0, 1, 0.1, SliderType.Vertical));
		speed.span(0.5, -0.5, 0.25, 0.5);
		
		add(new Repeat() {
			double countDownToPulse = 0;
			double v = 0;
			
			@Override public double repeat(double t, double dt) {
				video().getBackgroundColor().set(v, v, v);
				v *= 0.9;
				
				countDownToPulse -= dt;
				if (countDownToPulse < 0) {
					countDownToPulse = speed.getValue().d() + Maths.random(0, randomness.getValue().d());
					v = 1.0;
				}
				
				return 0.05;
			}
		});
	}

	@Override
	public void stop() {
		super.stop();
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoStrobeControl());		
	}

}
