package automenta.spacenet.run.widget;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.FaceCamera;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.vector.Vector3;


public class DemoButtonBackgroundColor extends ProcessBox {

	@Override public void run() {
		double inc = 0.1;
		
		
		for (double x = 0; x < 1.0-inc; x+=inc) {
			for (double y = 0; y < 1.0-inc; y+=inc) {
			
				final Color c = Color.hsb(x, y, 0.5);
				
				Box b = add(new Box());
				
				Button bb = b.add(new TextButton("x"));
				b.add(new Box(c).scale(0.25));
				
				b.scale(inc * 1.5, inc * 1.5);
				
				double theta = (-0.5 + x) * Math.PI;
				double psi = (-0.5 + y) * Math.PI * 2.0;
				Vector3 v = new Vector3(1.0, theta, psi);
				v.sphereToCartesian();

				b.move(v.x(), v.y(), v.z());

				b.add(new FaceCamera());

				bb.addButtonAction(new ButtonAction() {
					@Override public void onButtonPressed(Button b) {
						video().getBackgroundColor().set(c);
					}					
				});
				
			}			
		}
	
	}

	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoButtonBackgroundColor());		
	}

	
}
