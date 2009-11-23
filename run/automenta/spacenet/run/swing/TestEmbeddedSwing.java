package automenta.spacenet.run.swing;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;

import com.jme.input.InputHandler;
import com.jmex.awt.swingui.JMEDesktop;

/** JME's JMEDesktop */
public class TestEmbeddedSwing extends ProcessBox {

	private JComponent content;

	public TestEmbeddedSwing(JComponent content) {
		super();
		
		this.content = content;
	}

	@Override public void run() {
		JMEDesktop desktop = new JMEDesktop("X", 200, 200, new InputHandler() {
			
		});
		desktop.getJDesktop().add(content);
		add(desktop);
	}
	
	public static void main(String[] args) {
		JPanel j = new JPanel(new BorderLayout());
		j.add(new JButton("X"), BorderLayout.CENTER);
		j.add(new JTextField("Y"), BorderLayout.SOUTH);		
		
		new DefaultJmeWindow(new TestEmbeddedSwing(j));
	}

}
