package automenta.spacenet.plugin.comm.irc;

import jerklib.Channel;
import jerklib.ConnectionManager;
import jerklib.Profile;
import jerklib.Session;
import jerklib.events.IRCEvent;
import jerklib.events.IRCEvent.Type;
import jerklib.events.JoinCompleteEvent;
import jerklib.events.listeners.IRCEventListener;

public abstract class IRCAgent implements IRCEventListener {

    public IRCAgent(String networkHost) {
        super();
        ConnectionManager conman = new ConnectionManager(new Profile() {

            @Override
            public String getActualNick() {
                return "IRCSpace";
            }

            @Override
            public String getFirstNick() {
                return "IRCSpace";
            }

            @Override
            public String getSecondNick() {
                return "IRCSpace_";
            }

            @Override
            public String getThirdNick() {
                return "IRCSpace__";
            }

            @Override
            public String getName() {
                return "automenta.IRCSpace";
            }
        });
        conman.requestConnection(networkHost).addIRCEventListener(this);
    }

    @Override
    public void recieveEvent(IRCEvent e) {
        if (e.getType() == Type.CONNECT_COMPLETE) {
            onConnected(e.getSession());
        } else if (e.getType() == Type.JOIN_COMPLETE) {
            JoinCompleteEvent jce = (JoinCompleteEvent) e;
            onJoined(e.getSession(), jce.getChannel());
        } else {
            onEvent(e);
        }
    }

    protected abstract void onConnected(Session session);

//        {
//            session.joinChannel("#automenta");
//        }
    protected abstract void onJoined(Session session, Channel channel);

//        {
//            channel.say("Sup' Metaverse?");
//        }
    protected abstract void onEvent(IRCEvent e);
}
