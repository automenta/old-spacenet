package automenta.spacenet.space.swing;

import automenta.spacenet.act.Action;
import automenta.spacenet.var.graph.DiGraph;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * displays a node's in/out links, possible views, and action menu
 */
public class GraphNodePanel extends JPanel {

    private final DiGraph graph;
    private Object object;
    private final JPanel titlePanel;
    private final JPanel linkPanel;
    private final JPanel viewPanel;
    private final Collection<Action> views;
    final private LinkedList<Action> availableViews = new LinkedList();
    private JComboBox viewSelector;
    private DefaultComboBoxModel viewSelection;

    public GraphNodePanel(DiGraph g, Collection<Action> views) {
        super(new BorderLayout());

        this.graph = g;
        this.views = views;

        viewSelection = new DefaultComboBoxModel();

        viewSelector = new JComboBox(viewSelection);
        viewSelector.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                int i = viewSelector.getSelectedIndex();
                setView(i);
            }
        });

        titlePanel = new JPanel(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);

        viewPanel = new JPanel();
        add(viewPanel, BorderLayout.CENTER);

        linkPanel = new JPanel(new FlowLayout());
        add(linkPanel, BorderLayout.SOUTH);


    }

    public DiGraph getGraph() {
        return graph;
    }

    public Collection<Action> getViews() {
        return views;
    }

    public void setObject(Object o) {
        if (this.object == o)
            return;
        
        this.object = o;

        updateActions();
        updateTitle();
        updateLinks();
        updateViews();

        updateUI();
    }

    public Object getObject() {
        return object;
    }

    private void updateActions() {
    }

    private void updateTitle() {
        titlePanel.removeAll();
        titlePanel.add(new JLabel(getObject().toString() + "\n" + getObject().getClass().toString()), BorderLayout.WEST);


        titlePanel.add(viewSelector, BorderLayout.EAST);

        titlePanel.updateUI();
    }

    private void updateLinks() {
        linkPanel.removeAll();

        Collection ins = getGraph().getPredecessors(getObject());
        for (Object o : ins) {
            JButton b = new JButton("In: " + o.toString());
            linkPanel.add(b);
        }
        Collection outs = getGraph().getSuccessors(getObject());
        for (Object o : outs) {
            JButton b = new JButton("Out: " + o.toString());
            linkPanel.add(b);
        }

        linkPanel.updateUI();

    }

    private void updateViews() {
        availableViews.clear();
        viewSelection.removeAllElements();
        
        for (Action a : getViews()) {
            try {
                double s = a.getStrength(getObject());
                if (s > 0) {
                    availableViews.add(a);
                    viewSelection.addElement(a.getName(getObject()));
                }
            } catch (ClassCastException e) {
            }
        }

        if (availableViews.size() > 0) {
            viewSelector.setVisible(true);
            setView(0);
        } else {
            viewSelector.setVisible(false);
            setDefaultView();
        }
    }

    protected void setDefaultView() {
        viewPanel.removeAll();
        viewPanel.add(new JLabel(getObject().toString()));
        viewPanel.updateUI();
    }

    protected void setView(int viewNumber) {
        if ((viewNumber < 0) || (viewNumber >= availableViews.size())) {
            return;
        }

        viewPanel.removeAll();

        Action a = availableViews.get(viewNumber);
        try {
            JComponent component = (JComponent) a.run(getObject());
            viewPanel.add(component);
        } catch (Exception e) {
            viewPanel.add(new JLabel(e.toString() + "\n" + views.toString()));
        }
        viewPanel.updateUI();
    }
}
