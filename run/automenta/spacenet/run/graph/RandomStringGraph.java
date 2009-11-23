package automenta.spacenet.run.graph;

import automenta.spacenet.var.graph.DiGraph;
import java.util.ArrayList;
import java.util.List;

public class RandomStringGraph extends DiGraph<String, String> {

    public RandomStringGraph(int numVertices, int numEdges) {
        super();
        for (int v = 0; v < numVertices; v++) {
            addVertex("v" + Integer.toString(v));
        }
        List<String> vlist = new ArrayList(getVertices());
        for (int e = 0; e < numEdges; e++) {
            int i1 = (int) (Math.random() * vlist.size());
            int i2;
            do {
                i2 = (int) (Math.random() * vlist.size());
            } while (i1 == i2);
            String v1 = vlist.get(i1);
            String v2 = vlist.get(i2);
            addEdge("e" + Integer.toString(e), v1, v2);
        }
    }
}
