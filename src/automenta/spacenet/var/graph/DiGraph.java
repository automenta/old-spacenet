package automenta.spacenet.var.graph;

import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DiGraph<V, E> extends SparseMultigraph<V, E> {

    private final List<IfGraphChanges<V, E>> graphChanges = new LinkedList();

    public synchronized void addIfGraphChanges(IfGraphChanges<V, E> c) {
        graphChanges.add(c);

        //add existing vertices and edges
        for (V v : getVertices()) {
            c.vertexAdded(this, v);
        }
        for (E e : getEdges()) {
            c.edgeAdded(this, e);
        }
    }

    public synchronized void removeIfGraphChanges(IfGraphChanges<V, E> c) {
        graphChanges.remove(c);
    }

    @Override public boolean addVertex(V vertex) {
        if (super.addVertex(vertex)) {
            for (IfGraphChanges<V, E> ig : graphChanges) {
                ig.vertexAdded(this, vertex);
            }
            return true;
        }
        return false;
    }

    @Override public boolean removeVertex(V vertex) {
        if (super.removeVertex(vertex)) {
            for (IfGraphChanges<V, E> ig : graphChanges) {
                ig.vertexRemoved(this, vertex);
            }
            return true;
        }
        return false;
    }

//    @Override
//    public boolean addEdge(E e, V v1, V v2) {
//        return addEdge(e, v1, v2, EdgeType.DIRECTED);
//    }

    @Override public boolean addEdge(E edge, Pair<? extends V> endpoints, edu.uci.ics.jung.graph.util.EdgeType edgeType) {
        if (super.addEdge(edge, endpoints, edgeType)) {
            for (IfGraphChanges<V, E> ig : graphChanges) {
                ig.edgeAdded(this, edge);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(E edge) {
        if (super.removeEdge(edge)) {
            for (IfGraphChanges<V, E> ig : graphChanges) {
                ig.edgeRemoved(this, edge);
            }
            return true;
        }
        return false;
    }

    public void addGraph(DiGraph<V,E> diGraph) {

        for (V v : diGraph.getVertices()) {
            addVertex(v);
        }
        for (E e : diGraph.getEdges()) {
            V from = diGraph.getEndpoints(e).getFirst();
            V to = diGraph.getEndpoints(e).getSecond();
            addEdge(e, from, to);
        }
    }

    public synchronized void clear() {
        ArrayList<E> edgeList = new ArrayList(getEdges());
        ArrayList<V> vertList = new ArrayList(getVertices());

        for (E e : edgeList) {
            removeEdge(e);
        }
        for (V v : vertList) {
            removeVertex(v);
        }
    }
}
