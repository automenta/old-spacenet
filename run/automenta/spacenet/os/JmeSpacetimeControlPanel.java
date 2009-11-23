/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.os;

import automenta.spacenet.space.swing.SwingWindow;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author seh
 */
public class JmeSpacetimeControlPanel extends JPanel {
    private final DefaultJmeWindow jmeWindow;

    public JmeSpacetimeControlPanel() {
        super();

        JButton x = new JButton("?");
        add(x);

        jmeWindow = new DefaultJmeWindow();
    }
    
    //public void update(JmeSpacetime)
    public static void main(String[] arg) {
        new SwingWindow("jME SpaceTime", new JmeSpacetimeControlPanel(), new Dimension(250, 500), true);
    }
}
