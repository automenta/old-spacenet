package automenta.spacenet;


/** start=on=enable=initialize : called automatically when added to a Scope; should never be called directly */
public interface Starts extends Stops {
	
	public void start();
	
}
