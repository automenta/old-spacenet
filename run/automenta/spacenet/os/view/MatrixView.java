package automenta.spacenet.os.view;

import automenta.spacenet.space.object.widget.icon.SquareIcon.SquareIconBuilder;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.data.ListMatrix;
import automenta.spacenet.space.object.data.ListMatrix.ListMode;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.ListVar;

public class MatrixView<L> implements ObjectView<ListVar<L>> {

	@Override public String getName(ListVar<L> i) {
		return "Matrix";
	}

	@Override public double getStrength(ListVar<L> i) {
		if (i.size() > 0)
			return 0.75;
		return 0;
	}

	@Override public void run(ListVar<L> i, ObjectVar<Space> o) throws Exception {
		ListMatrix ml = new ListMatrix(i, new SquareIconBuilder(), ListMode.Square);		
		o.set(ml);		
	}

}
