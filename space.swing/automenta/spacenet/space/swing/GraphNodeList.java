package automenta.spacenet.space.swing;

import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.graph.Focus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class GraphNodeList extends JPanel implements ListCellRenderer {

    private final DefaultListModel nodeListModel;
    private final JList nodeList;
    private final DiGraph graph;
    private final Focus focus;

    public GraphNodeList(Focus f) {
        super(new BorderLayout());

        this.focus = f;
        this.graph = focus.getGraph();

        nodeListModel = new DefaultListModel();
        nodeList = new JList(nodeListModel);
        nodeList.setCellRenderer(this);
        nodeList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                //selectObject(nodeListModel.getElementAt(nodeList.getSelectedIndex()));
                onObjectSelected(nodeListModel.getElementAt(nodeList.getSelectedIndex()));
            }
        });
        add(nodeList, BorderLayout.CENTER);
    }

    protected abstract void onObjectSelected(Object o);

    protected void refresh() {
        nodeListModel.clear();

        List vertices = new ArrayList(getGraph().getVertices());
        Collections.sort(vertices, new Comparator() {
            @Override public int compare(Object o1, Object o2) {
                double a1 = getFocus().getAttention().get(o1);
                double a2 = getFocus().getAttention().get(o2);
                if (a1 == a2) return 0;
                return (a1 > a2) ? -1 : 1;
            }
        });

        for (Object v : vertices) {
            nodeListModel.addElement(v);
        }
    }

    public DiGraph getGraph() {
        return graph;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel j = new JPanel(new BorderLayout());

        j.setBackground(getBackgroundColor(value, isSelected));

        int t = 4;
        if (cellHasFocus) {
            j.setBorder(BorderFactory.createLineBorder(Color.GREEN, t));
        } else {
            j.setBorder(BorderFactory.createEmptyBorder(t, t, t, t));
        }
        JLabel label = new JLabel("<html><font size=\'+1\'>" + value.toString() + "</font></html>");
        j.add(label, BorderLayout.CENTER);
        JLabel typeLabel = new JLabel(value.getClass().getSimpleName());
        j.add(typeLabel, BorderLayout.SOUTH);
        return j;
    }

    public Focus getFocus() {
        return focus;
    }

    private Color getBackgroundColor(Object value, boolean isSelected) {
        double nA = getFocus().getAttention().getNormalized(value);
        float h = ((float)value.getClass().hashCode()) / ((float)Integer.MAX_VALUE);

        float s = isSelected ? 0.2f : 0.6f;
        float b = (float)nA;
        return Color.getHSBColor(h, s, b);
    }
}
