package automenta.spacenet.space.surface;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.var.number.DoubleVar;

public class BitmapSurface implements Surface {

	private final UURI location;
	private final DoubleVar aspectXY = new DoubleVar(1.0);


	public BitmapSurface(UURI bitmapResource) {
		this.location = bitmapResource;
	}
	
	public BitmapSurface(String uri) {
		this(new UURI(uri));
	}

	public UURI getURI() {
		return location;
	}

	public DoubleVar getAspectXY() {
		return aspectXY;
	}

}
