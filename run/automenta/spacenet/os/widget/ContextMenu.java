package automenta.spacenet.os.widget;

import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.data.ListRect;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.IntegerVar;

public class ContextMenu extends Panel  {
	
	public ContextMenu() {
		super(false);
	}
	
	@Override public void start() {
		super.start();
		
		ListVar menu = new ListVar();
		menu.add("Tag");
		menu.add("Links");
		menu.add("Actions");
		
		ListRect lr = new ListRect(menu, new ArrangeColumn(0.01, 0.01), new IntegerVar(4), new RectBuilder() {
			@Override public Rect newRect(Object y) {
				Rect r = new Rect();
				r.add(new TextButton(y.toString()));
				return r;
			}			
		});

		add(lr);
		
	}

	
}
