package automenta.spacenet.space.geom2;

import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.var.number.DoubleVar;

public class BitmapRect extends Rect {

	private BitmapSurface bitmapSurface;
	private Rect bitmapRect;

	public BitmapRect(final BitmapSurface bitmapSurface) {
		super(bitmapSurface);
		
		this.bitmapSurface = bitmapSurface;
	}

	@Override public DoubleVar getAspectXY() {
		return bitmapSurface.getAspectXY();
	}			

	public BitmapRect(String imgUri) {
		this(new BitmapSurface(imgUri));
	}

}
