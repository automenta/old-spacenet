/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Action;
import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.plugin.comm.Agent;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.plugin.comm.Transformation;
import automenta.spacenet.plugin.comm.content.AgentWindow;
import automenta.spacenet.plugin.comm.content.FoundWindow;
import automenta.spacenet.plugin.comm.content.MessageWindow;
import automenta.spacenet.plugin.comm.irc.IRCGrapher;
import automenta.spacenet.plugin.comm.twitter.TwitterGrapher;
import automenta.spacenet.plugin.shell.StringViaShell;
import automenta.spacenet.plugin.web.yahoo.YahooImageSearchGrapher;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.keyboard.IfKeyReleased;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.data.MatrixRect;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;
import com.jme.input.KeyInput;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Transformer;

abstract public class AbstractDemoGraph extends Box implements Starts {

    private DiGraph g;
    private LinkedList<Action> stringActions;

    public void initStringActions(LinkedList<Action> a) {
        a.add(new Action<StringVar, String>() {

            @Override public String run(StringVar i) throws Exception {
                return new String(new Date().toString());
            }

            @Override public String getName(StringVar i) {
                return "Date";
            }

            @Override public double getStrength(StringVar i) {
                return i.s().equals("date") ? 1.0 : 0.0;
            }
        });
        a.add(new Action<StringVar, DiGraph>() {

            @Override public DiGraph run(StringVar i) throws Exception {
                DiGraph g = new DiGraph();
                new YahooImageSearchGrapher(g, i, 20);
                return g;
            }

            @Override public String getName(StringVar i) {
                return "Images";
            }

            @Override public double getStrength(StringVar i) {
                return 1.0;
            }
        });
        a.add(new Action<StringVar, String>() {

            @Override public String run(StringVar i) throws Exception {
                return StringViaShell.executeShell(i.s());
            }

            @Override public String getName(StringVar i) {
                return "System Shell";
            }

            @Override public double getStrength(StringVar i) {
                return 1.0;
            }
        });
        a.add(new Action<StringVar, DiGraph>() {

            @Override public DiGraph run(StringVar i) throws Exception {
                DiGraph g = new DiGraph();
                TwitterGrapher twitter = new TwitterGrapher(g);
                twitter.getPublicTimeline();
                return g;
            }

            @Override public String getName(StringVar i) {
                return "Public Twitter";
            }

            @Override public double getStrength(StringVar i) {
                return i.s().equals("now") ? 1.0 : 0.0;
            }
        });
        a.add(new Action<StringVar, DiGraph>() {

            @Override public DiGraph run(StringVar i) throws Exception {
                String userName = i.s().substring(1);
                DiGraph g = new DiGraph();
                TwitterGrapher twitter = new TwitterGrapher(g);
                twitter.getProfile(userName);
                return g;
            }

            @Override public String getName(StringVar i) {
                return "Twitterer";
            }

            @Override public double getStrength(StringVar i) {
                return i.s().startsWith("@") ? 1.0 : 0.0;
            }
        });
        a.add(new Action<StringVar, Object>() {

            IRCGrapher irc;

            @Override public Object run(StringVar i) throws Exception {

                if (irc == null) {
                    irc = new IRCGrapher("irc.freenode.net", g);
                }

                irc.joinChannel(i.s());
                return null;
            }

            @Override public String getName(StringVar i) {
                return "IRC Channel";
            }

            @Override public double getStrength(StringVar i) {
                return i.s().startsWith("#") ? 1.0 : 0.0;
            }

        });
    }

    public static class MessageToMessageWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            return new MessageWindow((Message) o, 25);
        }
    }

    public static class AgentToAgentWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            return new AgentWindow((Agent) o);
        }
    }

    public static class FoundToFoundWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            return new FoundWindow((Found) o);
        }
    }

    public static class StringToTextEditWindow implements Transformer<Object, Box> {

        private final Collection<Action> actions;
        private MatrixRect actionButtonList;
        private final DiGraph graph;

        public StringToTextEditWindow(DiGraph graph, Collection<Action> actions) {
            super();
            this.graph = graph;
            this.actions = actions;
        }

        @Override public Box transform(Object o) {
            final StringVar sv = (StringVar) o;
            Window w = new Window();

            actionButtonList = w.add(new MatrixRect());
            actionButtonList.span(0.4, 0.4, 0.5, -0.4);

            TextEditRect ter = w.add(new TextEditRect(sv));
            ter.span(-0.45, -0.45, 0.35, 0.45);

            w.add(new IfStringChanges(ter.getText()) {

                @Override public void afterTextChanged(StringVar t, String previous, String current) {
                    sv.set(t.s());
                    updateActionButtons(sv);
                }
            });

            ter.moveDZ(0.05);
            actionButtonList.moveDZ(0.05);

            return w;
        }

        protected void updateActionButtons(final StringVar string) {

            ListVar<Action> possibleActions = ActionIndex.getPossibleActions(actions, string);

            actionButtonList.removeAll();

            int i = 0;
            for (final Action a : possibleActions) {
                final String actionName = a.getName(string);
                Button b = new TextButton(actionName);
                b.addButtonAction(new ButtonAction() {

                    @Override public void onButtonPressed(Button b) {
                        Object result;
                        try {
                            result = a.run(string);

                            if (result != null) {
                                System.out.println(string + " -> " + actionName + "  -> " + result);
                                if (result instanceof DiGraph) {
                                    graph.addGraph((DiGraph) result);
                                } else {
                                    graph.addVertex(result);
                                    graph.addEdge(new Transformation(actionName), string, result);
                                }
                            }

                        } catch (Exception ex) {
                            Logger.getLogger(AbstractDemoGraph.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                Rect r = new Rect();
                r.add(b);
                actionButtonList.put(0, i++, r);
            }

        }
    }

    public static class SpaceToSpaceWindow implements Transformer<Object, Box> {

        @Override public Box transform(Object o) {
            Box b = (Box) o;
            Window w = new Window();
            w.add(b);
            b.moveDZ(0.1);
            b.scale(0.9);
            return w;

        }
    }

    @Override public void start() {
        Keyboard keyboard = getThe(Keyboard.class);

        g = new DiGraph();

        initGraph(g);
//        TwitterGrapher twitter = add(new TwitterGrapher(g));
//        //twitter.getProfile("...");
//        twitter.getPublicTimeline();


        ForceDirectedParameters params = new ForceDirectedParameters(new Vector3(50, 50, 50), 0.01, 0.01, 2.0);
        ForceDirectedGraphArranger fd = new ForceDirectedGraphArranger(params, 0.1, 0.01, 0.25);
        fd.setFaceCamera(true);


        stringActions = new LinkedList<Action>();
        initStringActions(stringActions);

        TypeSpatializer sp = new TypeSpatializer();
        sp.add(Space.class, new SpaceToSpaceWindow());
        sp.add(StringVar.class, new StringToTextEditWindow(g, stringActions));
        sp.add(Message.class, new MessageToMessageWindow());
        sp.add(Agent.class, new AgentToAgentWindow());
        sp.add(Found.class, new FoundToFoundWindow());

        add(new GraphBox(g, sp, fd));

        //g.addVertex(new ForceDirectedControlPanel(fd));

        add(new IfKeyReleased(keyboard, KeyInput.KEY_INSERT) {

            @Override public void afterKeyReleased(BooleanVar key) {
                newTextBox();
            }
        });

        ForceDirectedControlWindow fdControlWin = add(new ForceDirectedControlWindow(fd));
        fdControlWin.move(-2, 0, 0);

    }

    public void newTextBox() {
        g.addVertex(new StringVar("text"));
    }

    @Override public void stop() {
    }

    abstract protected void initGraph(DiGraph g);
//    public static void main(String[] args) {
//        new DefaultJmeWindow(new DemoTwitterGraph());
//    }
}
