package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

public class GridRect extends Rect implements Starts, Stops {

	protected Vector2 gridFrequency;
	protected DoubleVar thick;
	protected Surface gridSurface;

	/** grid frequency x & y components */
	public GridRect(Surface backSurface, Surface foreSurface, Vector2 gridFrequency, DoubleVar thick) {
		super(backSurface);
		this.gridSurface = foreSurface;
		this.gridFrequency = gridFrequency;
		this.thick = thick;
		tangible(false);
	}

	public GridRect(Color back, Color fore, double freqX, double freqY, double thick) {
		this(new ColorSurface(back), new ColorSurface(fore), new Vector2(freqX, freqY), new DoubleVar(thick));
	}

	@Override public void start() {
		updateGrid();

		//TODO watch gridFrequency for changes		
	}
	
	protected void updateGrid() {
		clear();

		int nx = (int)gridFrequency.x();
		int ny = (int)gridFrequency.y();


		double spacingX = 1.0 / (nx);
		double spacingY = 1.0 / (ny);

		double dx = -0.5 + spacingX/2.0;
		double dy = -0.5 + spacingY/2.0;

		double thickX = thick.get() / (nx);
		double thickY = thick.get() / (ny);
		
		for (int x = 0; x < nx; x++) {
			Rect s = new Rect(gridSurface);
			s.tangible(false);
			s.move(dx, 0);
			s.scale(thickX, 1.0);
			add(s);

			dx += spacingX;
		}

		for (int y = 0; y < ny; y++) {
			Rect r = new Rect(gridSurface);
			r.tangible(false);
			r.move(0, dy);
			r.scale(1.0, thickY);
			add(r);

			dy += spacingY;
		}
	}

	@Override public void stop() {		
	}



}
