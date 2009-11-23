/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.twitter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author seh
 */
abstract public class TwitterAgent {

    private final Twitter t;

    public TwitterAgent() {
        super();

        t = new Twitter();


    }

    public void getProfile(String userID) {
        try {
            List<Status> pt = t.getUserTimeline(userID);
            onStatus(pt);

        } catch (TwitterException ex) {
            Logger.getLogger(TwitterAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPublicTimeline() {
        List<Status> pt;
        try {
            pt = t.getPublicTimeline();
            onStatus(pt);
        } catch (TwitterException ex) {
            ex.printStackTrace();
        }

    }

    void stop() {
    }

    public void onStatus(List<Status> pt) {
        for (Status s : pt) {
            onStatus(s);
        }
    }

    abstract protected void onStatus(Status s);
}
