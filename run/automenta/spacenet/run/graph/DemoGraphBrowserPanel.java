package automenta.spacenet.run.graph;

import automenta.spacenet.act.Action;
import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.os.Linker;
import automenta.spacenet.os.OS;
import automenta.spacenet.plugin.comm.Agent;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.plugin.comm.twitter.TwitterGrapher;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.swing.GraphBrowserPanel;
import automenta.spacenet.space.swing.SwingWindow;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.index.MemoryIndex;
import edu.uci.ics.jung.algorithms.filters.VertexPredicateFilter;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.collections15.Predicate;

public class DemoGraphBrowserPanel {

    public static void main(String[] args) {

        OS os = new OS() {

            @Override
            public ActionIndex<Object, Space> views() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ActionIndex actions() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Linker linker() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public MemoryIndex index() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        DiGraph graph = new DiGraph();
        List<Action> actions = new LinkedList();

        initActions(graph, actions);
        initGraph(graph);

        new SwingWindow(DemoGraphBrowserPanel.class.getName(),
            new GraphBrowserPanel(os, graph, actions),
            new Dimension(800, 600),
            true);
    }

    private static void initGraph(DiGraph graph) {
        TwitterGrapher twitter = new TwitterGrapher(graph);
        twitter.getProfile("...");
        twitter.getPublicTimeline();
    }

    private static void initActions(final DiGraph graph, List<Action> swingViews) {
        swingViews.add(new Action<Message, JPanel>() {

            @Override
            public JPanel run(Message i) throws Exception {
                JPanel j = new JPanel();
                j.add(new JLabel("<html><font size='+2'>" + i + "</font></html>"));
                return j;
            }

            @Override
            public String getName(Message i) {
                return "Message 1";
            }

            @Override public double getStrength(Message i) {
                return 1.0;
            }
        });

        swingViews.add(new Action<Message, JPanel>() {

            @Override
            public JPanel run(Message i) throws Exception {
                JPanel j = new JPanel();
                j.add(new JLabel("<html><font size='+1'>" + i + "</font></html>"));
                return j;
            }

            @Override
            public String getName(Message i) {
                return "Message 2";
            }

            @Override public double getStrength(Message i) {
                return 1.0;
            }
        });

        swingViews.add(new Action<Agent, JPanel>() {

            @Override
            public JPanel run(Agent i) throws Exception {
                JPanel j = new JPanel();
                URL imgURL = i.imageURL;
                j.add(new JLabel("<html><font size='+1'>" + i + "</font><br/><img src='" + imgURL + "'/></html>"));
                return j;
            }

            @Override
            public String getName(Agent i) {
                return "Agent";
            }

            @Override public double getStrength(Agent i) {
                return 1.0;
            }
        });

        swingViews.add(new Action<Object, JPanel>() {

            @Override  public JPanel run(final Object i) throws Exception {

                Graph subGraph = new VertexPredicateFilter(new Predicate() {
                    @Override public boolean evaluate(Object o) {
                        return (i==o) || graph.isSuccessor(i, o) || graph.isPredecessor(o, i);
                    }
                }).transform(graph);

                Layout layout = new SpringLayout(subGraph);

                //layout.setSize(new Dimension(700, 700)); // sets the initial size of the space
                // The BasicVisualizationServer<V,E> is parameterized by the edge types

                VisualizationViewer vv = new VisualizationViewer(layout);

                //vv.setPreferredSize(new Dimension(700, 700)); //Sets the viewing area size
                vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

                // Create a graph mouse and add it to the visualization component
                DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
                gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
                
                vv.setGraphMouse(gm);

                return vv;
            }

            @Override
            public String getName(Object i) {
                return "Network (JUNG)";
            }

            @Override public double getStrength(Object i) {
                return 1.0;
            }
        });

        swingViews.add(new Action<String, URI>() {

            @Override
            public URI run(String i) throws Exception {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName(String i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public double getStrength(String i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
}
