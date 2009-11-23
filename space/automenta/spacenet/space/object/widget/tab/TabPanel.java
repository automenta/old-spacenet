package automenta.spacenet.space.object.widget.tab;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.ListRect;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;

public class TabPanel extends Panel {

	private ListVar<Space> tabs = new ListVar();
	private Rect content;

	public TabPanel(DoubleVar contentProportion) {
		super();
		this.contentProportion = contentProportion;
	}

	public TabPanel() {
		this(new DoubleVar(0.2));
	}

	double dz = 0.1;
	private ListRect tabsRow;
	private DoubleVar contentProportion;
	
	public DoubleVar getContentProportion() {
		return contentProportion;
	}
	
	@Override
	public void start() {
		super.start();
		
		double p = getContentProportion().d();
		
//		tabsRow = add(new ListRect(tabs));
//		tabsRow.span(-0.5, 0.5, 0.5, 0.5 - p).moveDelta(0,0,dz);
		

	//		add(new ArrangeRow(tabs, Button.class, 0.05,0.05));
		
		content = add(new Rect());
		content.span(-0.5, 0.5-p, 0.5, -0.5).moveDelta(0,0, dz);
	}

	public void addTab(String label, final Space tabContent) {
		tabs.add(new TextButton(label) {
			
			@Override public void pressStopped(Pointer c) {
				super.pressStopped(c);
				
				content.clear();
				content.add(tabContent);
			}
			
		});
	}
	
}
