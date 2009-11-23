/**
 * 
 */
package automenta.spacenet.os.style;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.button.TextButtonDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

public class GradientTextButtonDecorator implements TextButtonDecorator {

		private Surface pressedSurface;
		private Surface touchedSurface;
		private Surface normalSurface;

		public GradientTextButtonDecorator() {
			super();
			
//			pressedSurface = new GradientSurface(Color.GrayMinusMinus, Color.GrayPlus);
//			touchedSurface = new GradientSurface(Color.GrayMinusMinus, Color.Gray);
//			normalSurface = new GradientSurface(Color.GrayMinus, Color.Gray);
			pressedSurface = new ColorSurface(Color.GrayPlusPlus.alpha(0.5));
			touchedSurface = new ColorSurface(Color.GrayPlus.alpha(0.5));
			normalSurface = new ColorSurface(Color.GrayMinus.alpha(0.5));
			
		}
		
		@Override public void decorateTextButton(TextButton b, boolean touched, boolean pressed) {
			if (pressed) {
				b.getPanelRect().surface(pressedSurface);
				b.getLabel().color(Color.Black);
			}
			else if (touched) {
				b.getPanelRect().surface(touchedSurface);				
				b.getLabel().color(Color.White);
			}
			else {
				b.getPanelRect().surface(normalSurface);
				b.getLabel().color(Color.White);
			}
			
		}
		
	}