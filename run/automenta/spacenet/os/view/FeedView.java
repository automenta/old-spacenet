package automenta.spacenet.os.view;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.data.ListMatrix;
import automenta.spacenet.space.object.data.ListMatrix.ListMode;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.plugin.web.Feed;

public class FeedView implements ObjectView<UURI> {


	@Override
	public String getName(UURI i) {
		return "Feed";
	}
	@Override
	public double getStrength(UURI i) {
		if (i.getType().equals(Feed.FeedType))
			return 0.75;
		return 0;
	}
	

	@Override public void run(UURI feed, ObjectVar<Space> o) throws Exception {
		Feed list = new Feed(feed);
		ListMatrix lr = new ListMatrix(list, ListView.getRectBuilder(), ListMode.Column);
		o.set(lr);
	}


}
