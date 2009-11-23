/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.twitter;

import automenta.spacenet.Starts;
import automenta.spacenet.plugin.comm.Agent;
import automenta.spacenet.plugin.comm.Created;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.plugin.comm.NextReceived;
import automenta.spacenet.var.graph.DiGraph;
import java.util.HashMap;
import java.util.Map;
import twitter4j.Status;
import twitter4j.User;

public class TwitterGrapher extends TwitterAgent  {

    private final DiGraph graph;
    private TwitterAgent twitter;
    private Map<String, Agent> idAgent = new HashMap();
    private Message previousMessage = null;

    public TwitterGrapher(DiGraph graph) {
        super();

        this.graph = graph;
    }

    @Override protected void onStatus(Status s) {
        Agent a = getAgent(s.getUser());
        Message m = new Message(s.getText(), s.getUser().getProfileImageURL());

        getGraph().addVertex(a);
        getGraph().addVertex(m);
        getGraph().addEdge(new Created(), a, m);

        if (previousMessage != null) {
            getGraph().addEdge(new NextReceived(), previousMessage, m);
        }

        previousMessage = m;
    }

    public Agent getAgent(User u) {
        Agent a = idAgent.get(u.getName());
        if (a == null) {
            a = new Agent(u.getName(), u.getProfileImageURL());
            idAgent.put(u.getName(), a);
        }
        return a;
    }

    public DiGraph getGraph() {
        return graph;
    }

}
