package automenta.spacenet;

/** an output interface, or source of signals */
public interface Output<O> {

	/** sends an object
	 * @return whether an attempt is made to send the object
	 *  */
	public boolean out(O o);
	
}
