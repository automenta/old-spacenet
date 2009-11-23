package automenta.spacenet.space.object.measure;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;



/** a window with controls for inspecting and manipulating a specific object */
public class GeometryViewer extends Panel {
	
	private Space object;

	public GeometryViewer(Space w) {
		super();
		this.object = w;
	}
	
	@Override public void start() {
		
		add(new GridRect(Color.Invisible, Color.White.alpha(0.02), 8, 8, 0.1).scale(0.9));
		add(object);

		double widgetScale = 0.3;
		
		if (object instanceof Rect) {
			//((Rect)widget)
		}
		else if (object instanceof Box) {
			((Box)object).span(-0.5 * widgetScale, -0.5 * widgetScale, 0.5 * widgetScale, 0.5 * widgetScale).moveDelta(0,0,1.0);
		}

		double dz = 0.05;
		
		final Slider rotX = add(new Slider(0, -Math.PI, Math.PI, 0.1, SliderType.Horizontal));
		rotX.span(-0.4, -0.4, 0.4, -0.44);
		rotX.moveDelta(0,0,dz);

		final Slider rotY = add(new Slider(0, -Math.PI, Math.PI, 0.1, SliderType.Vertical));
		rotY.span(-0.4, -0.4, -0.44, 0.4);
		rotY.moveDelta(0,0,dz);
		
		add(new IfDoubleChanges(rotX.getValue(), rotY.getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				((HasOrientation)object).getOrientation().set(rotY.getValue().get(),  rotX.getValue().get(), 0);
			}			
		});
		

	}
	
	
}