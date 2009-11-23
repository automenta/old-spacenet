package automenta.spacenet.run.graph;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.graph.SpatializeGraph;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;

public class ToStringSpatializer implements SpatializeGraph {
    private final double scale;

    public ToStringSpatializer() {
        this(1.0);
    }

    public ToStringSpatializer(double scale) {
        super();
        this.scale = scale;
    }

    @Override
    public Space newEdgeSpace(Object edge, Box pa, Box pb) {
        return new Line3D(pa.getPosition(), pb.getPosition(), new DoubleVar(0.02), 2);
    }

    @Override
    public Box newVertexBox(Object vertex) {
        Window w = new Window();

        Color c = Color.newRandomHSB(0.5, 0.5);
        w.add(new TextRect(vertex.toString(), c).scale(0.9).move(0,0,0.1));

        w.scale(scale);
        
        return w;
    }
}
