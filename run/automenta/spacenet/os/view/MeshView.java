package automenta.spacenet.os.view;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.object.widget.button.IconButton;
import automenta.spacenet.var.ObjectVar;

public class MeshView implements ObjectView<UURI> {

	@Override public String getName(UURI i) {
		return "Mesh";
	}

	@Override
	public double getStrength(UURI i) {
		if (i.toString().endsWith(".3ds")) {
			return 1.0;
		}
		if (i.toString().endsWith(".x3d")) {
			return 1.0;
		}
		return 0;
	}

	@Override
	public void run(UURI i, ObjectVar<Space> o) throws Exception {
		o.set( new GeometryViewer(new IconButton(i)) );		
	}
	

}
