package automenta.spacenet.os.view;

import automenta.spacenet.ID;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.var.ObjectVar;

public class BitmapView implements ObjectView<ID> {

	@Override public String getName(ID i) {
		return "Bitmap";
	}

	@Override
	public double getStrength(ID i) {
		String s = i.getUURI().toString();
		if (s.endsWith(".jpg")) {
			return 1.0;
		}
		if (s.endsWith(".png")) {
			return 1.0;
		}
		if (s.endsWith(".bmp")) {
			return 1.0;
		}
		return 0;
	}

	@Override
	public void run(ID i, ObjectVar<Space> o) throws Exception {
		double dz = 0.05;
		o.set( new Rect( new BitmapSurface(i.getUURI()) ).move(0, 0, dz) );		
	}
	

}
