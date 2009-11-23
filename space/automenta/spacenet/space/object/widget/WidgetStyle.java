package automenta.spacenet.space.object.widget;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.panel.Panel;

/** determines how widget objects are decorated */
abstract public class WidgetStyle extends Space {

	/** the history of touched objects.  getLast() is the most recent */  
	//abstract public FifoVar<Space> getTouchedPreviously();
	
	/** currently touched */
	//abstract public Node<Object, Object> getTouched();
	
	/** list of objects selected; also known as 'focused' in some GUI frameworks (ex: Swing) */
	//abstract public ListVar<Space> getSelected();
	
	/** widget spaces can be enabled or disabled (non functional; control is blocked from accessing its widgets) */
	//abstract public BooleanVar getEnabled();

//	abstract public void decoratePanel(Panel p);
//	abstract public void decorateWindow(Window w);
//	abstract public void decorateButton(Button b);
	
	public static interface PanelStyle {
		public void updatePanel(Panel p, boolean isTouched);
	}
	
	public static interface ButtonStyle {
		public void updateButton(Button b);
	}
	
	abstract public PanelStyle panel();	
	abstract public ButtonStyle button();
	
	
}
