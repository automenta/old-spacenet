package automenta.spacenet.os.view;

import automenta.spacenet.space.Space;
import automenta.spacenet.var.ObjectVar;

public class NewSpatialClassView implements ObjectView<Class<? extends Space>> {

	@Override public String getName(Class<? extends Space> i) {
		return "New " + i.getSimpleName();
	}

	@Override
	public double getStrength(Class<? extends Space> i) {
		return 1.0;
	}

	@Override
	public void run(Class<? extends Space> i, ObjectVar<Space> o) throws Exception {
		
		o.set( i.newInstance() );		
	}



}
