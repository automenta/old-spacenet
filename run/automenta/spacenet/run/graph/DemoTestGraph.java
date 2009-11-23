/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.graph;

import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.graph.DiGraph;
import java.util.UUID;

public class DemoTestGraph extends AbstractDemoGraph implements Starts {

    protected void initGraph(DiGraph g) {
        //add(new ObjectCanvas());
        
        //addSequence(g, 5, true);
        //addSequence(g, 4, false);
//        TwitterGrapher twitter = add(new TwitterGrapher(g));
//        twitter.getProfile("TransAlchemy");
//        twitter.getPublicTimeline();

        final Video3D video3d = getThe(Video3D.class);
        video3d.getBackgroundColor().set(Color.GrayMinusMinusMinus);
//        add(new Repeat() {
//
//            @Override
//            public double repeat(double t, double dt) {
//                float h = (float)(1.0 + Math.cos(t/10.0)) * 0.5f;
//                float b = (float)(0.1 * (1.0 + Math.sin(t*3.14*2.0)));
//                float s = 0.2f;
//                Color c = Color.hsb(h, s, b);
//                video3d.getBackgroundColor().set(c);
//               return 0.0;
//            }
//
//        });
    }

    protected void addSequence(DiGraph g, int num, boolean connected) {
        Object first=null, last = null;
        for (int i = 0; i < num; i++) {
            Object current = UUID.randomUUID();
            g.addVertex(current);
            if (last!=null) {
                g.addEdge(UUID.randomUUID(), current, last);
            }
            else {
                first = current;
            }
            last = current;
        }
        if (connected)
            g.addEdge(UUID.randomUUID(), last, first);
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoTestGraph());
    }
}
