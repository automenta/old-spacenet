package automenta.spacenet.test.feed;

import junit.framework.TestCase;
import automenta.spacenet.UURI;
import automenta.spacenet.plugin.web.Feed;

public class TestRSSFeed extends TestCase {

	public void testReadRSS() {
		//UURI u = new UURI("http://automenta.com/rss.xml");
		UURI u = new UURI(getClass().getResource("data/automenta.xml.txt"));
	
		Feed f = new Feed(u);
	
		assertTrue(f.getTitle().length() > 0);
		assertTrue(f.size() > 1);

		System.out.println(f);
	}

	
}
