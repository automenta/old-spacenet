package automenta.spacenet.space;



import automenta.spacenet.var.number.DoubleVar;

public interface HasTransparency {

	/** returns a value between 0..1.0: 0=invisible, 0.5=halfway transparent, 1.0=totally opaque (solid) */
	public DoubleVar getTransparency();
	
}
