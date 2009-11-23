/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.space.object.graph.arrange.ScatterArranger;
import automenta.spacenet.Maths;
import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.graph.ArrangeGraph;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.SpatializeGraph;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.number.DoubleVar;

/**
 *
 * @author seh
 */
public class DemoGraphScatter extends Box implements Starts {

    @Override public void start() {
        DiGraph g = new RandomStringGraph(10, 5);

        System.out.println("graph: " + g);

        add(new GraphBox(g, new ToStringSpatializer(), new ScatterArranger()));
    }

    @Override public void stop() {  }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoGraphScatter());
    }
}
