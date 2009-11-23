package automenta.spacenet.space;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;

public abstract class IfColorChanges implements Starts, Stops {

	private final Color color;

	public IfColorChanges(Color c) {
		this.color = c;
	}

	@Override public void start() {
		if (color!=null) {
			color.whenStarted(this);
		}
	}
	
	@Override public void stop() {
		if (color!=null) {
			color.whenStopped(this);
		}

	}

	abstract public void afterColorChanged(Color c);

}
