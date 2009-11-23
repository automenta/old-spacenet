/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.spacenet.space.swing;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author seh
 */
public class SwingWindow {

    
    private final JFrame frame;

    public SwingWindow(String title, JComponent content, Dimension size, boolean exitOnClose) {
        super();

        frame = new JFrame(title);
        frame.getContentPane().add(content);
        frame.setSize(size);

        if (exitOnClose) {
            frame.addWindowListener(new WindowAdapter() {
                @Override public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    System.exit(0);
                }
            });
        }
        
        frame.setVisible(true);
    }

}
