package automenta.spacenet.run.data;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.ListRect2;
import automenta.spacenet.space.object.data.ListRect2.ListDirection;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;

public class DemoListRect extends ProcessBox {

	public static class LabelBuilder implements ListRect2.RectBuilder {

		@Override public Rect newRect(Object x) {
			double hue = ((x.hashCode() % 100)) / 100.0;
			Color h = Color.hsb(hue, 0.2, 0.1);
			Rect r = new Rect().color(h);
			r.add(new TextRect(new StringVar(x.toString()), 8));
			return r;
		}
		
	}
	
	@Override public void run() {

		final ListVar l = new ListVar();
		
		final ListRect2 listRectV = add(new ListRect2(l, ListDirection.Vertical, new DoubleVar(0), new DoubleVar(3), new LabelBuilder()));
		listRectV.span(-0.5, -0.25, -0.35, 0.5);
		
		final ListRect2 listRectH = add(new ListRect2(l, ListDirection.Horizontal, new DoubleVar(0), new DoubleVar(3), new LabelBuilder()));
		listRectH.span(-0.25, -0.5, 0.5, -0.35);
		

		for (int i = 0; i < 10; i++)
			l.add(i);
		
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoListRect().scale(8));
	}
}
