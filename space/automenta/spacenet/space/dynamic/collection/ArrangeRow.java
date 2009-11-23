package automenta.spacenet.space.dynamic.collection;

import automenta.spacenet.space.Space;



public class ArrangeRow extends ArrangeGrid {

	public ArrangeRow(Space space, double cellPaddingProp, double marginProp) {
		super(space, cellPaddingProp, marginProp, Mode.Row);
	}

	public ArrangeRow(Space space, Class subClassesOf, double cellPaddingProp, double marginProp) {
		super(space, subClassesOf, cellPaddingProp, marginProp, Mode.Row);
	}

	public ArrangeRow(double cellPaddingProp, double marginProp) {
		this(null, cellPaddingProp, marginProp);
	}


}
