package automenta.spacenet.space.geom3;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;

public class BevelRect extends Rect {

	private DoubleVar bevelBorderY;
	private DoubleVar bevelBorderX;
	private DoubleVar bevelZ;

	public BevelRect(Surface s) {
		this(s, new DoubleVar(0.05), new DoubleVar(0.05));
	}

	public BevelRect(Color c) {
		this(new ColorSurface(c));
	}

	public BevelRect(Surface surface, DoubleVar bevelZ, DoubleVar bevelBorder) {
		super(surface);
		this.bevelZ = bevelZ;
		this.bevelBorderX = this.bevelBorderY = bevelBorder;
	}

	public BevelRect(Color color, double bevelZ, double bevelBorder) {
		this(new ColorSurface(color), new DoubleVar(bevelZ), new DoubleVar(bevelBorder));
	}

	public DoubleVar getBevelBorderY() {
		return bevelBorderY;
	}

	public DoubleVar getBevelBorderX() {
		return bevelBorderX;
	}

	public DoubleVar getBevelZ() {
		return bevelZ;
	}
	
}
