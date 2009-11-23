package automenta.spacenet.act.flow;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections15.multimap.MultiHashMap;

import automenta.spacenet.act.flow.ChainAction.ChainState;


/** processing chain: sequence of actions, each of which can optionally end the sequence */  
public class Chain {

	public MultiHashMap<Object, ChainAction> chainActions = new MultiHashMap();
	
	public Iterator<ChainAction> iterateActions(Object key) {
		Collection<ChainAction> c = chainActions.getCollection(key);
		if (c!=null) {
			return c.iterator();
		}
		return null;
	}
	
	public void onEvent(Object key, Object context, Object obj) {
		Iterator<ChainAction> i = iterateActions(key);
		if (i!=null) {
			while (i.hasNext()) {
				ChainAction a = i.next();
				ChainState result = a.handle(context, obj);
				if (result == ChainState.Consumed)
					break;
			}
		}
	}

	public void add(Object key, ChainAction action) {
		chainActions.put(key, action);
	}
	
	
}
