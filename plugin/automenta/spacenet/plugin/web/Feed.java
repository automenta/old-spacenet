package automenta.spacenet.plugin.web;

import java.net.URL;

import org.apache.log4j.Logger;

import automenta.spacenet.UURI;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.string.StringVar;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/** syndication feed (RSS, Atom, FOAF, etc..) */
public class Feed extends ListVar<Found> {
	private static final Logger logger = Logger.getLogger(Feed.class);

	public final static String FeedType = "application/rss+xml";
	
	private UURI uri;

	private StringVar title = new StringVar();
	private StringVar summary = new StringVar(); 

	public Feed(UURI uri) {
		super();
		
		this.uri = uri;
		
		updateFeed();
	}

	public UURI getURI() {
		return uri;
	}
	
	/**
	 * @see https://rome.dev.java.net/
	 * @see http://wiki.java.net/bin/view/Javawsxml/Rome04TutorialFeedReader
	 */
	public void updateFeed() {
	    try {
            URL feedUrl = getURI().toURL();

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            getTitle().set(feed.getTitle());
            getSummary().set(feed.getDescription());
            
            for (Object o : feed.getEntries()) {
            	SyndEntry s = (SyndEntry) o;

            	final String title = s.getTitle();
            	final String uri = s.getUri();
            	final String desc = s.getDescription().getValue();
            	
            	final UURI uuri = new UURI(uri);
            	
            	add(new Found() {

					@Override public String getDescription() {
						return desc;
					}

					@Override
					public String getName() {
						return title;
					}

					@Override
					public Object getObject() {
						return uuri;
					}

					@Override
					public double getStrength() {
						return 1.0;
					}

					@Override
					public String getTags() {
						return "item";
					}

					@Override
					public UURI getUURI() {
						return uuri;
					}
            		
            	});
            }

        }
        catch (Exception ex) {
        	logger.error(ex);
        	ex.printStackTrace();
        }

	}
	
	public StringVar getTitle() {
		return title;
	}
	public StringVar getSummary() {
		return summary ;
	}
	
}
