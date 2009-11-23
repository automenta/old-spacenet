/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.SliderDecorator;
import automenta.spacenet.space.object.widget.slider.Slider.SliderKnob;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

public class BorderedSliderDecorator implements SliderDecorator {
	private Surface knobSurface;

	public BorderedSliderDecorator() {
		super();

//		UURI vert = new UURI("file:../media/glsl/rectborder.vert");
//		UURI frag = new UURI("file:../media/glsl/rectborder.frag");
//
//		this.knobSurface = new GLSLSurface(vert, frag);
//		knobSurface.getVars().put("Color", new Color(0.9f, 0.9f, 0.9f, 1.0));
//		knobSurface.getVars().put("BorderColor", new Color(0.3, 0.3, 0.3, 0.2));
//		knobSurface.getVars().put("BorderSize", new DoubleVar(0.05));
//		knobSurface.getVars().put("BorderShade", new DoubleVar(0.03));
//		knobSurface.getVars().put("LightPosition", new Vector3(0,0,1));

        this.knobSurface = new ColorSurface(Color.GrayPlus);
	}

	@Override public void decorateSlider(Slider s) {

	}

	@Override public void decorateSliderKnob(SliderKnob knob) {
		knob.surface(knobSurface);			
	}		
}