package automenta.spacenet.space.geom2;

import automenta.spacenet.space.Surface;
import automenta.spacenet.var.number.DoubleVar;

public class RoundedBorder extends RectBorder {

	public RoundedBorder(Surface surface, DoubleVar thickStart,	DoubleVar thickEnd) {
		super(surface, thickStart, thickEnd);
	}

	@Override public void addCorners() {
		super.addCorners();
		
		
	}
	
	
}
