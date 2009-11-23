/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.run.dynamic.DemoClassReloading;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.graph.GraphBox;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger;
import automenta.spacenet.space.object.graph.arrange.ForceDirectedGraphArranger.ForceDirectedParameters;
import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.vector.Vector3;

/**
 *
 * @author seh
 */
public class DemoGraphForceDirected extends Box implements Starts {

    @Override public void start() {
        DiGraph g = new RandomStringGraph(10, 15);

        System.out.println("graph: " + g);
        ForceDirectedParameters params = new ForceDirectedParameters(new Vector3(50, 50, 50), 0.9, 0.01, 0.1);

        ForceDirectedGraphArranger fd = new ForceDirectedGraphArranger(params, 0.1, 0.01, 0.25);

        add(new GraphBox(g, new ToStringSpatializer(), fd));
    }

    @Override public void stop() {  }

    public static void main(String[] args) {
        //new DefaultJmeWindow(new DemoGraphForceDirected());
        new DefaultJmeWindow(new DemoClassReloading(DemoGraphForceDirected.class));
    }
}
