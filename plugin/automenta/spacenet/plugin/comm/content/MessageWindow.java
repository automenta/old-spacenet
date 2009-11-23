/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.content;

import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.surface.BitmapSurface;
import java.net.URL;

/**
 *
 * @author seh
 */
public class MessageWindow<M extends Message> extends DefaultObjectWindow<M> {

    private final Rect imgRect;

    public MessageWindow(M m, int maxTextLineLength) {
        super(m, maxTextLineLength);

        URL imgUrl = m.image;
        imgRect = (imgUrl!=null) ? backRect.add(new Rect(new BitmapSurface(imgUrl.toString()))) : null;

        if (imgRect!=null) {
            imgRect.span(-0.5, -0.5, -0.4, 0.5);
            imgRect.setZ(0.1);
            textRect.span(-0.4, -0.5, 0.5, 0.5);
            textRect.setZ(0.1);
        }
    }

    @Override protected String getText(M v) {
        return v.text;
    }

}
