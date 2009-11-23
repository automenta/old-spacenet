package automenta.spacenet.act.flow;


public interface ChainAction {

	public static enum ChainState {
		//Unaccepted,
		Consumed,
		Continue
	};
	
	public ChainState handle(Object context, Object object);
	
}
