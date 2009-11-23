package automenta.spacenet.space.object.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.graph.IfGraphChanges;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author seh
 */
public class GraphBox<V,E> extends Space implements Starts, IfGraphChanges<V, E> {

    private DiGraph<V,E> graph;
    
    private ArrangeGraph<V,E> arrange;
    private SpatializeGraph<V,E> spatialize;

    private Map<V, Box> vertexBox = new HashMap();
    private Map<E, Space> edgeSpace = new HashMap();

    public GraphBox(DiGraph<V,E> graph, SpatializeGraph<V,E> initialSpatialize, ArrangeGraph<V,E> initialArrange) {
        super();
        this.graph = graph;
        this.arrange = initialArrange;
        this.spatialize = initialSpatialize;
    }


    @Override
    public void start() {
        this.arrange.start(this);
        
        getGraph().addIfGraphChanges(this);
    }

    @Override public void stop() {
        this.arrange.stop();
    }


    public DiGraph<V, E> getGraph() {     return graph;   }

    public SpatializeGraph<V, E> getSpatialize() {     return spatialize;   }

    public ArrangeGraph<V, E> getArrange() {    return arrange;   }


    @Override public void vertexAdded(DiGraph<V, E> graph, V vertex) {
        Box b = getSpatialize().newVertexBox(vertex);
        vertexBox.put(vertex, b);
        getArrange().addedVertex(vertex, b);
        add(b);
    }

    @Override public void vertexRemoved(DiGraph<V, E> graph, V vertex) {
        getArrange().removedVertex(vertex);
        remove(getVertexBox(vertex));
        vertexBox.remove(vertex);
    }

    @Override public void edgeAdded(DiGraph<V, E> graph, E edge) {
        V a = graph.getEndpoints(edge).getFirst();
        V b = graph.getEndpoints(edge).getSecond();

        Box aBox = getVertexBox(a);
        Box bBox = getVertexBox(b);

        Space s = getSpatialize().newEdgeSpace(edge, aBox, bBox);
        edgeSpace.put(edge, s);
        getArrange().addedEdge(edge, s, aBox, bBox);
        add(s);
    }

    public Space getEdgeSpace(E e) {
        return edgeSpace.get(e);
    }
    public Box getVertexBox(V v) {
        return vertexBox.get(v);
    }

    @Override public void edgeRemoved(DiGraph<V, E> graph, E edge) {
        getArrange().removedEdge(edge);
        remove(getEdgeSpace(edge));
        edgeSpace.remove(edge);
    }

    

}
