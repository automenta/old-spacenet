package automenta.spacenet.plugin.meta;

public interface Next<N> {

	//TODO decide better name than 'severity'
	public enum Severity {
		ImmediatelyNecessary,	//or 'exclusively necessary', as in modal popups? 
		Necessary, 
		Optional
	}
	
	public N getNextObject();
	
}
