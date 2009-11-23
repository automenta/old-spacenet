package automenta.spacenet.space.surface;

import automenta.spacenet.Scope;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.number.DoubleVar;

public class Fading extends Box implements Starts, Stops {

	private double fadeInTime;
	private double sustainTime;
	private double fadeOutTime;
	
	private DoubleVar opacity = new DoubleVar(0.0);
	private Space content;

	public Fading(Space content, double fadeInTime, double sustainTime, double fadeOutTime) {
		super();
		this.fadeInTime = fadeInTime;
		this.sustainTime = sustainTime;
		this.fadeOutTime = fadeOutTime;
		this.content = content;
	}

	@Override public void start() {
		add(content);

		add(new Repeat<Scope>() {
			@Override 	public double repeat(double t, double dt) {
				if (t < fadeInTime) {
					getOpacity().set(t/fadeInTime);
				}
				else if ( (t-fadeInTime) < sustainTime ) {
					getOpacity().set(1.0);					
				}
				else if ( (t-fadeInTime - sustainTime) < fadeOutTime ) {
					double o = (t-fadeInTime - sustainTime);
					getOpacity().set(1.0 - (o/fadeOutTime) );
				}
				else {
					getOpacity().set(0);
					if (size() > 0) {
						remove(content);
					}
				}
				return 0;
			}		
		});
	}
	
	@Override public DoubleVar getOpacity() {
		return opacity;
	}
	

	@Override public void stop() {
		
	}
	
}
