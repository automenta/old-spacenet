package automenta.spacenet;

/** a state that can be started=on=enabled=activated, "in" another node : indicates that instances must be added to a node of a certain type
 	scope automatically calls start(c); it should never be called directly */
public interface StartsIn<C> extends Stops {

	public void start(C c);
	
}
