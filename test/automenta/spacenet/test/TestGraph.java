/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.test;

import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.graph.IfGraphChanges;
import junit.framework.TestCase;

public class TestGraph extends TestCase {

    public void testGraph() {
        DiGraph g = new DiGraph();
        g.addVertex("x");
        g.addVertex("y");
        g.addEdge("xy", "x", "y");

        assertEquals(2, g.getVertices().size());
        assertEquals(1, g.getEdges().size());
    }

    int vertAdded = 0;
    int vertRemoved = 0;
    int edgeAdded = 0;
    int edgeRemoved = 0;
    
    public void testGraphChange() {
        DiGraph g = new DiGraph();


        g.addIfGraphChanges(new IfGraphChanges() {

            @Override
            public void vertexAdded(DiGraph graph, Object vertex) {
                vertAdded++;
            }

            @Override
            public void vertexRemoved(DiGraph graph, Object vertex) {
                vertRemoved++;
            }

            @Override
            public void edgeAdded(DiGraph graph, Object edge) {
                edgeAdded++;
            }

            @Override
            public void edgeRemoved(DiGraph graph, Object edge) {
                edgeRemoved++;
            }

        });


        g.addVertex("x");        assertEquals(1, vertAdded);
        g.addVertex("y");        assertEquals(2, vertAdded);

        g.addEdge("xy", "x", "y");  assertEquals(1, edgeAdded);

        g.removeVertex("x");      assertEquals(1, vertRemoved); 
        assertEquals(1, g.getVertexCount());    assertEquals(0, g.getEdgeCount());

        
    }
}
