/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.plugin.web.yahoo;

import automenta.spacenet.var.graph.DiGraph;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.string.StringVar;

/**
 *
 * @author seh
 */
public class YahooImageSearchGrapher {

    public static class Finds { }
    
    public YahooImageSearchGrapher(DiGraph graph, StringVar query, int num) {
        super();
        
        YahooImageSearch yis = new YahooImageSearch();
        yis.update(query.s(), num);

        graph.addVertex(query);
        for (Found f : yis.get()) {
            graph.addVertex(f);
            graph.addEdge(new Finds(), query, f);
        }

    }
}
