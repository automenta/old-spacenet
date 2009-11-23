/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.content;

import automenta.spacenet.plugin.comm.Agent;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.surface.BitmapSurface;
import java.net.URL;

public class AgentWindow<A extends Agent> extends DefaultObjectWindow<A> {

    private final Rect imgRect;

    public AgentWindow(A a) {
        super(a, 32);

        URL imgUrl = a.imageURL;
        imgRect = (imgUrl!=null) ? backRect.add(new Rect(new BitmapSurface(imgUrl.toString()))) : null;

        if (imgRect!=null) {
            imgRect.span(-0.5, -0.5, 0.5, 0.4);
            imgRect.setZ(0.1);
            textRect.span(-0.5, 0.5, 0.5, 0.4);
            textRect.setZ(0.1);
        }
    }

    @Override protected String getText(A v) {
        return v.id;
    }

}
