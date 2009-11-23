package automenta.spacenet.var.number;


abstract public class Angle extends DoubleVar {


	public Angle(double radians) {
		super(radians);
	}

	@Override
	public boolean set(Double nextValue) {		
		return super.set(getNormalizedRadians(nextValue));
	}

	private Double getNormalizedRadians(Double d) {		
		//TODO normalization code
		return d;
	}
}
