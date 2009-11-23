/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.space.object.graph;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;

public interface SpatializeGraph<V, E> {
    public Space newEdgeSpace(E edge, Box pa, Box pb);
    public Box newVertexBox(V vertex);
}
