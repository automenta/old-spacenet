package automenta.spacenet.run.widget;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.run.geometry.DemoBox;
import automenta.spacenet.run.geometry.subgeometry.DemoSubBoxSpace;
import automenta.spacenet.space.object.widget.tab.TabPanel;
import automenta.spacenet.space.object.widget.window.Window;

public class DemoTabPanel extends ProcessBox {

	@Override public void run() {
		Window window = add(new Window());
		
		TabPanel tabPanel = (TabPanel) window.add(new TabPanel().scale(0.9,0.9).move(0,0,0.1));
		tabPanel.addTab("First", new DemoBox());
		tabPanel.addTab("Second", new DemoSubBoxSpace().scale(0.15));
		tabPanel.addTab("Third", new DemoButton());		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoTabPanel().scale(1.0));
	}
}
