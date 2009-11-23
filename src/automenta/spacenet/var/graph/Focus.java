/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.var.graph;

import automenta.spacenet.var.map.ScalarMap;
import automenta.spacenet.var.map.TimeMap;
import java.util.Date;
import java.util.Iterator;
import org.apache.commons.collections15.Predicate;

/**
 * updates maps when a graph changes (attention, time, ...) and provides methods to control attention dynamics and attention selection
 */
public class Focus implements IfGraphChanges {
    private final DiGraph graph;
    private final ScalarMap attention;
    private final TimeMap time;

    public Focus(DiGraph g) {
        super();

        this.graph = g;
        this.attention = new ScalarMap();
        this.time = new TimeMap();

        g.addIfGraphChanges(this);
    }

    public Date getWhenCreated(Object o) {
        return null;
    }

    public void diffuse(double proportion) {

    }
    public void spread(Predicate includeEdge, double proportion) {
        
    }
    public void forget(int remainingAtoms) {

    }
    public void forget(double minPercentile) {

    }
    public void forget(Predicate toForget) {

    }
    public DiGraph extract(int maxAtoms) {
        return null;
    }

    /** normalizes attention to range of 0.0 to 1.0 */
    public void normalize() {

    }

    public double getMaxAttention() { return 0; }
    public double getMinAttention() { return 0; }
    public double getMeanAttention() { return 0; }

    public Iterator iterateHighest() { return null; }

    @Override public void vertexAdded(DiGraph graph, Object vertex) {
        attention.put(vertex, getDefaultAttention());
    }

    @Override public void vertexRemoved(DiGraph graph, Object vertex) {
        attention.remove(vertex);
    }

    @Override public void edgeAdded(DiGraph graph, Object edge) {
        attention.put(edge, getDefaultAttention());
    }

    @Override public void edgeRemoved(DiGraph graph, Object edge) {
        attention.remove(edge);
    }
    
    public double getDefaultAttention() { return 1.0; }

    public DiGraph getGraph() {
        return graph;
    }

    public ScalarMap getAttention() {
        return attention;
    }


    
}
