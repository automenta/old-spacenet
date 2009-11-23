package automenta.spacenet.act.flow;

import automenta.spacenet.act.flow.InChannel.Validity;
import automenta.spacenet.var.map.MapVar;

public class ChannelModel {

	/** a map of keys to an object validator, defining the schema or profile of this channel */
	public MapVar<Object,Validity> getValidity() {
		return null;
	}

}
