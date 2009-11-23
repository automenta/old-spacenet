package automenta.spacenet.run.communication;


import automenta.spacenet.space.object.net.NetSpace;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.Starts;
import automenta.spacenet.plugin.comm.irc.IRCAgent;
import automenta.spacenet.plugin.comm.twitter.TwitterAgent;
import automenta.spacenet.var.net.Node;
import jerklib.Channel;
import jerklib.Session;
import jerklib.events.ChannelMsgEvent;
import jerklib.events.IRCEvent;
import jerklib.events.NoticeEvent;
import jerklib.events.PrivateMsgEvent;
import twitter4j.Status;

public class IRCSpace extends NetSpace implements Starts {

    private IRCAgent irc;
    private TwitterAgent twitter;

    public IRCSpace() {
        super();
    }

    @Override
    public void start() {
        super.start();
        
        irc = new IRCAgent("irc.freenode.net") {

            private Node prevNode;

            @Override
            protected void onConnected(Session session) {
                session.joinChannel("#automenta");
            }

            @Override
            protected void onJoined(Session session, Channel channel) {
                addNode(getChannelNode(channel.getName()));
            }

            @Override
            protected void onEvent(IRCEvent e) {
                Node newNode = null;

                if (e instanceof ChannelMsgEvent) {
                    ChannelMsgEvent cme = (ChannelMsgEvent) e;
                    String aName = cme.getNick();
                    Channel c = cme.getChannel();
                    String cName = c.getName();

                    Node channelNode = getChannelNode(cName);
                    Node agentNode = getAgentNode(aName);

                    newNode = addNode(new Message(cme.getMessage()));

                    addLink("said", channelNode, newNode);
                    addLink("said", agentNode, newNode);
                } else if (e instanceof PrivateMsgEvent) {
                    PrivateMsgEvent pme = (PrivateMsgEvent) e;
                    String aName = pme.getNick();

                    Node agentNode = getAgentNode(aName);
                    newNode = addNode(new Message(pme.getMessage()));
                    addLink("said", agentNode, newNode);
                } else if (e instanceof NoticeEvent) {
                    NoticeEvent ne = (NoticeEvent) e;
                    newNode = addNode(new Message(ne.getNoticeMessage()));
                } else {
                    System.err.println("unhandled IRC message: " + e + " of type: " + e.getClass());
                }

                if (newNode != null) {
                    if (prevNode != null) {
                        //link prev->new
                        addLink("next", prevNode, newNode);
                    }
                    prevNode = newNode;
                }

            }
        };


        twitter = new TwitterAgent() {

            private Node prevStatus;

            @Override protected void onStatus(Status s) {
                Node curStatus = addNode(new Message(s.getText(), s.getUser().getProfileImageURL()));

                if (prevStatus != null) {
                    addLink("next", prevStatus, curStatus);
                }
                prevStatus = curStatus;
            }
        };
    }

    @Override
    public void stop() {
        super.stop();

        //TODO disconnect from IRC, Twitter
    }

    public static void main(String[] args) {
        new DefaultJmeWindow( new IRCSpace() );
    }


}
