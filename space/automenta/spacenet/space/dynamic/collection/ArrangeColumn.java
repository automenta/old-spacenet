package automenta.spacenet.space.dynamic.collection;

import automenta.spacenet.space.Space;



public class ArrangeColumn extends ArrangeGrid {

	public ArrangeColumn(Space space, double cellPaddingProp, double marginProp) {
		super(space, cellPaddingProp, marginProp, Mode.Column);
	}

	public ArrangeColumn(Space space) {
		this(space, 0, 0);
	}

	public ArrangeColumn(double cellPaddingProp, double marginProp) {
		this(null, cellPaddingProp, marginProp);
	}


}
