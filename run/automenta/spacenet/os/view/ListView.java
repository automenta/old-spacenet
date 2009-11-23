package automenta.spacenet.os.view;

import automenta.spacenet.space.object.widget.icon.WideIcon;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.data.ListMatrix;
import automenta.spacenet.space.object.data.ListMatrix.ListMode;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.ListVar;

public class ListView<L> implements ObjectView<ListVar<L>> {

	
	@Override public String getName(ListVar<L> i) {
		return "Column List";
	}

	@Override
	public double getStrength(ListVar<L> i) {
		return 0.75;
	}

	@Override public void run(ListVar<L> list, ObjectVar<Space> o) throws Exception {
		ListMatrix lr = new ListMatrix(list, getRectBuilder(), ListMode.Column);

		//TODO get these default sizing working
		//		lr.getVisCY().set(0);
		//		lr.getVisHeight().set(Math.ceil(list.size()/2.0));
		
		o.set(lr);
	}

	public static RectBuilder getRectBuilder() {
		return new RectBuilder() {
			@Override public Rect newRect(Object y) {
				Rect r = new Rect();
				
				r.add(new WideIcon(y));
				return r;
			}			
		};
	}

}
