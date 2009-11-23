/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.plugin.comm.twitter.TwitterGrapher;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.var.graph.DiGraph;

public class DemoTwitterGraph extends AbstractDemoGraph implements Starts {

    protected void initGraph(DiGraph g) {
        TwitterGrapher twitter = add(new TwitterGrapher(g));
        twitter.getProfile("...");
        twitter.getPublicTimeline();
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoTwitterGraph());
    }
}
