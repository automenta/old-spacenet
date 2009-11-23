package automenta.spacenet;

/** an input interface, or sink/destination of signals */
public interface Input<O> {

	/**receives an object
	 * @return whether object is accepted 
	 */
	public boolean in(O o);
	
}
