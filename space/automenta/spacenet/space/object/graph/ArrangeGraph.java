package automenta.spacenet.space.object.graph;

import automenta.spacenet.StartsIn;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;

public interface ArrangeGraph<V,E> extends StartsIn<GraphBox<V,E>> {

    public void addedVertex(V v, Box b);
    public void removedVertex(V v);

    public void addedEdge(E e, Space s, Box from, Box to);
    public void removedEdge(E e);

}
