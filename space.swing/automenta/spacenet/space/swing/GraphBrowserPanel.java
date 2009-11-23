package automenta.spacenet.space.swing;

import automenta.spacenet.act.Action;
import automenta.spacenet.os.OS;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.graph.Focus;
import automenta.spacenet.var.graph.IfGraphChanges;
import java.awt.BorderLayout;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class GraphBrowserPanel extends JPanel {

    private final OS os;
    private final DiGraph graph;
    private final GraphNodeList graphNodeList;
    private final GraphNodePanel nodePanel;
    private final Collection<Action> views;
    private final Focus focus;

    public GraphBrowserPanel(OS os, DiGraph g, Collection<Action> views) {
        super(new BorderLayout());

        this.os = os;
        this.graph = g;
        this.views = views;

        this.focus = new Focus(g);
        focus.getAttention().randomize(0, 1.0f);

        graphNodeList = new GraphNodeList(focus) {
            @Override protected void onObjectSelected(Object o) {
                selectObject(o);
            }
        };

        nodePanel = new GraphNodePanel(g, views);

        final JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(graphNodeList), nodePanel);

        SwingUtilities.invokeLater(new Runnable() {

            @Override public void run() {
                jsp.setDividerLocation(0.25);
            }
        });

        add(jsp, BorderLayout.CENTER);

        g.addIfGraphChanges(new IfGraphChanges() {

            @Override
            public void vertexAdded(DiGraph graph, Object vertex) {
                graphNodeList.refresh();
            }

            @Override
            public void vertexRemoved(DiGraph graph, Object vertex) {
                graphNodeList.refresh();
            }

            @Override
            public void edgeAdded(DiGraph graph, Object edge) {
            }

            @Override
            public void edgeRemoved(DiGraph graph, Object edge) {
            }
        });

        graphNodeList.refresh();
    }

    public DiGraph getGraph() {
        return graph;
    }

    public OS getOS() {
        return os;
    }

    public void selectObject(Object o) {
        nodePanel.setObject(o);
        ;
    }
}
