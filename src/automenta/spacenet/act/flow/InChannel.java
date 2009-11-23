package automenta.spacenet.act.flow;


abstract public class InChannel {

	//TODO find a more standard way to do this, something like 'boolean predicate'
	public interface Validity<O> {
		public boolean isValid(O o);
	}
	
	
	abstract public ChannelModel getChannelModel();
	
	public boolean receive(Object key, Object value) {
		return false;
	}
	
	abstract public InPort getInPort();
	
}
