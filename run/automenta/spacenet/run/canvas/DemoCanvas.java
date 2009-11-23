/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.run.canvas;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ObjectCanvas;
import automenta.spacenet.space.geom3.Box;

public class DemoCanvas extends Box {

    public DemoCanvas() {
        super();
        
        ObjectCanvas oc = add(new ObjectCanvas());
    }



    public static void main(String[] args) {
        new DefaultJmeWindow( new DemoCanvas() );
    }
}
