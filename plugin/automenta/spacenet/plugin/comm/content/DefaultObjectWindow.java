/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.content;

import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;
import java.net.URL;

/**
 *
 * @author seh
 */
public class DefaultObjectWindow<O> extends Window {
    protected final Rect backRect;
    protected final TextRect textRect;

    public DefaultObjectWindow(O v, int maxTextLineLength) {
        super();
        
        backRect = new Rect(Color.newRandomHSB(0.5, 0.25));
        backRect.tangible(false);

        add(backRect);

        String text = getText(v);
        textRect = backRect.add(new TextRect(new StringVar(text), maxTextLineLength, Color.White));

        doLayout();

    }

    protected void doLayout() {
        backRect.move(0, 0, 0.05);
        backRect.scale(0.80);

        textRect.move(0, 0, 0.1);
    }

    protected String getText(O v) {
        return v.toString();
    }
}
