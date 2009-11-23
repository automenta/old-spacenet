/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.comm.content;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.var.index.Found;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoundWindow<F extends Found> extends DefaultObjectWindow<F> {

    private Rect imgRect;

    public FoundWindow(F a) {
        super(a, 32);

        URL imgUrl;
        try {
            imgUrl = a.getUURI().toURL();
            imgRect = (imgUrl!=null) ? backRect.add(new Rect(new BitmapSurface(imgUrl.toString()))) : null;

            if (imgRect!=null) {
                imgRect.span(-0.5, -0.5, 0.5, 0.4);
                imgRect.setZ(0.1);
                textRect.span(-0.5, 0.5, 0.5, 0.4);
                textRect.setZ(0.1);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(FoundWindow.class.getName()).log(Level.SEVERE, null, ex);
            
        }

    }

    @Override protected String getText(F v) {
        return v.getName();
    }

}
