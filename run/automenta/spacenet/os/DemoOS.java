package automenta.spacenet.os;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.object.measure.GridRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

public class DemoOS extends ProcessBox {

	@Override public void run() {
		GridRect gridRect = add(new GridRect(getGridBackSurface(), getGridForeSurface(), new Vector2(17,17), new DoubleVar(0.1)));
		//gridRect.getTangible().set(false);
	}
	
	
	private Surface getGridForeSurface() {
		return new ColorSurface(Color.gray(0.2));
	}
	private Surface getGridBackSurface() {
		return new ColorSurface(Color.gray(0.4).alpha(0.1));
	}

	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoOS());		
	}
	
}
