/**
 * 
 */
package automenta.spacenet.os.widget;

import org.apache.log4j.Logger;

import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect3;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.IconButton;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.Color;

/** window with control buttons: hide, etc... */
public class MetaWindow extends Window {
	private static final Logger logger = Logger.getLogger(MetaWindow.class);
	

	public MetaWindow() {
		super();			
		
		tangible(false);
	}

	public MetaWindow(String title) {
		this();
		getName().set(title);
	}

	public MetaWindow(Box box) {
		super(box);
	}


	double titleHeight = 0.05;

	protected Rect nameButton;

	private IconButton hideButton;


	double windowBorder = 0.05;
	
	double contentScaleX = 0.9;
	double contentScaleY = 0.9;

	private Box windowContent;

	@Override public void start() {
		super.start();
		
		
		windowContent = add(new Box());
		windowContent.tangible(false);
		

		addButtons();
		layoutButtons();

	}
	


	protected void layoutButtons() {
		windowContent.span(-0.48, 0.5-titleHeight-0.02, 0.48,-0.48);
		hideButton.span(0.45, 0.45, 0.5, 0.5, true);		
		nameButton.span(-0.5, 0.5, 0.45, 0.5-titleHeight, false);

		widgets().pullDZ(windowContent, hideButton, nameButton);

	}

	protected void addButtons() {
		
		nameButton = add(new TextRect3(getName(), Color.White));
		
		hideButton = add(new IconButton("icon.hide", false));		
		hideButton.addButtonAction(new ButtonAction() {
			@Override public void onButtonPressed(Button b) {
				hide();
			}
		});

	}


	public void hide() {
		Scheduler.doLater(new Runnable() {
			@Override public void run() {
				getParent().remove(MetaWindow.this);
				
			}			
		});
	}
	public Box getContent() {
		return windowContent;
	}



}