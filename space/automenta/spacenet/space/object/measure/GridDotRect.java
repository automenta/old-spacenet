package automenta.spacenet.space.object.measure;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

public class GridDotRect extends GridRect {

	public GridDotRect(Surface backSurface, Surface foreSurface, Vector2 gridFrequency, DoubleVar thick) {
		super(backSurface, foreSurface, gridFrequency, thick);
	}



	@Override
	protected void updateGrid() {
		clear();

		int nx = (int)gridFrequency.x();
		int ny = (int)gridFrequency.y();


		double spacingX = 1.0 / (nx);
		double spacingY = 1.0 / (ny);

		double dx;
		double dy = -0.5 + spacingY/2.0;

		double thickX = thick.get() / (nx);
		double thickY = thick.get() / (ny);
		
		for (int y = 0; y < ny; y++) {
			dx = -0.5 + spacingX/2.0;
			
			for (int x = 0; x < nx; x++) {
				Rect r = new Rect(gridSurface);
				r.tangible(false);
				r.move(dx, dy);
				r.scale(thickX, thickY);
				add(r);
	
				dx += spacingX;
			}
			dy += spacingY;
		}
	}

}
