package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.var.ObjectVar;

public class SpatialView implements ObjectView<Space> {

	@Override
	public String getName(Space i) {
		return "Space";
	}

	@Override public double getStrength(Space i) {
		return 1.0;
	}

	@Override public void run(Space i, ObjectVar<Space> o) throws Exception {
		
		o.set(i);		
	}

}
