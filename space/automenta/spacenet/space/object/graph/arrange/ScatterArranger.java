package automenta.spacenet.space.object.graph.arrange;

import automenta.spacenet.Maths;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.graph.ArrangeGraph;

public class ScatterArranger implements ArrangeGraph {

    double r = 3;

    @Override
    public void addedVertex(Object v, Box b) {
        double px = Maths.random(-r, r);
        double py = Maths.random(-r, r);
        double pz = Maths.random(-r, r);
        b.move(px, py, pz);
    }

    @Override
    public void removedVertex(Object v) {
    }

    @Override
    public void addedEdge(Object e, Space s, Box from, Box to) {
    }

    @Override
    public void removedEdge(Object e) {
    }

    @Override    public void start(Object c) {   }

    @Override    public void stop() {   }

}
