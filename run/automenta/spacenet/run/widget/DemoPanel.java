package automenta.spacenet.run.widget;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.collection.ArrangeGrid;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.Widget;
import automenta.spacenet.space.object.widget.panel.Panel;


public class DemoPanel extends ProcessBox implements Starts, Stops {

	@Override public void run() {
		Space s = add(new Rect().move(0,0,0.1));
		for (int i = 0; i < 4; i++) {
			Widget w = newWidget();
			w.getAspectXY().set((i+1.0)/2.0);
			s.add(w);
		}
		
		add(new ArrangeGrid(s,0.1,0.1));
		
	}
	
	public Widget newWidget() {
		Panel p = new Panel();
		p.getDescription().set("This is a panel.");
		return p;
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoPanel().scale(6));
	}
}
