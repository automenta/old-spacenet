package automenta.spacenet.var.graph;

public interface IfGraphChanges<V, E> {

    public void vertexAdded(DiGraph<V, E> graph, V vertex);

    public void vertexRemoved(DiGraph<V, E> graph, V vertex);

    public void edgeAdded(DiGraph<V, E> graph, E edge);

    public void edgeRemoved(DiGraph<V, E> graph, E edge);
}
