/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.irc;

import automenta.spacenet.plugin.comm.Agent;
import automenta.spacenet.plugin.comm.Created;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.plugin.comm.NextReceived;
import automenta.spacenet.var.graph.DiGraph;
import java.util.LinkedList;
import java.util.List;
import jerklib.Channel;
import jerklib.Session;
import jerklib.events.ChannelMsgEvent;
import jerklib.events.IRCEvent;
import jerklib.events.NoticeEvent;
import jerklib.events.PrivateMsgEvent;

/**
 *
 * @author seh
 */
public class IRCGrapher extends IRCAgent {

    private Object prevNode;
    private final DiGraph graph;
    private Session session;

    private List<String> toJoin = new LinkedList();

    public IRCGrapher(String network, DiGraph graph) {
        super(network);
        this.graph = graph;
    }

    public void joinChannel(String channel) {
        if (session!=null)
            session.joinChannel(channel);
        else {
            toJoin.add(channel);
        }
    }

    @Override
    protected void onConnected(Session session) {
        this.session = session;

        for (String c : toJoin) {
            session.joinChannel(c);

        }
        toJoin.clear();
    }

    @Override
    protected void onJoined(Session session, Channel channel) {
        graph.addVertex(getChannelNode(channel.getName()));
    }

    public automenta.spacenet.plugin.comm.Channel getChannelNode(String id) {
        //TODO use cache
        return new automenta.spacenet.plugin.comm.Channel(id);
    }
    public Agent getAgentNode(String id) {
        //TODO use cache
        return new Agent(id, null);
    }

    protected void onEvent(IRCEvent e) {
        Object newNode = null;

        if (e instanceof ChannelMsgEvent) {
            ChannelMsgEvent cme = (ChannelMsgEvent) e;
            String aName = cme.getNick();
            Channel c = cme.getChannel();
            String cName = c.getName();

            Object channelNode = getChannelNode(cName);
            Object agentNode = getAgentNode(aName);

            newNode = new Message(cme.getMessage());
            graph.addVertex(newNode);


            graph.addEdge(new Created(), channelNode, newNode);
            graph.addEdge(new Created(), agentNode, newNode);
        } else if (e instanceof PrivateMsgEvent) {
            PrivateMsgEvent pme = (PrivateMsgEvent) e;
            String aName = pme.getNick();

            Object agentNode = getAgentNode(aName);
            newNode = new Message(pme.getMessage());
            graph.addVertex(newNode);
            graph.addEdge(new Created(), agentNode, newNode);
        } else if (e instanceof NoticeEvent) {
            NoticeEvent ne = (NoticeEvent) e;
            newNode = new Message(ne.getNoticeMessage());
            graph.addVertex(newNode);
        } else {
            System.err.println("unhandled IRC message: " + e + " of type: " + e.getClass());
        }

        if (newNode != null) {
            if (prevNode != null) {
                //link prev->new
                graph.addEdge(new NextReceived(), prevNode, newNode);
            }
            prevNode = newNode;
        }

    }

}
