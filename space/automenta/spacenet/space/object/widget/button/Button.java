package automenta.spacenet.space.object.widget.button;

import java.util.List;

import org.apache.log4j.Logger;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pressable;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;


abstract public class Button extends Panel implements Touchable, Pressable {
	private static final Logger logger = Logger.getLogger(Button.class);
	
	StringVar caption;

	public List<ButtonAction> buttonActions = new ListVar();
	
//	public static class ButtonRect extends Rect implements Pressable, Touchable {
//		
//		private Button button;
//
//		public ButtonRect(Button b) {
//			super();
//			getTangible().set(true);
//			this.button = b;
//		}
//
//		@Override public void startPress(Cursor c, Vector3 location,	Vector2 relativeToRect) {
//			button.startPress(c, location, relativeToRect);			
//		}
//
//		@Override public void stopPress(Cursor c) {
//			button.stopPress(c);			
//		}
//
//		@Override public void updatePress(Cursor c, double timePressed) {
//			button.updatePress(c, timePressed);			
//		}
//
//		@Override public void startTouch(Cursor c) {
//			button.startTouch(c);			
//		}
//
//		@Override public void stopTouch(Cursor c) {
//			button.stopTouch(c);			
//		}
//
//		@Override public void updateTouch(Cursor c, double timeTouched) {
//			button.updateTouch(c, timeTouched);			
//		}
//	}

	public Button(String label) {
		super();
	
		this.caption = new StringVar(label);
	}
	
	public Button(StringVar label) {
		super();
		this.caption = label;
	}

	@Override public void start() {
		super.start();
		
		initButton();
		
	}

	abstract public Space getTouchable();

	abstract protected void initButton();

	public StringVar getCaption() { return caption; }

	@Override
	public boolean isTouched() { return touched.b(); }
	public boolean isPressed() { return pressed.b() && touched.b(); }

	@Override public void pressStart(Pointer c, Vector3 location, Vector2 relativeToRect) {
		super.pressStart(c, location, relativeToRect);
		//logger.info(this + " press");
		widgets().requestFocus(this);
		updateWidget();
	}
	
	@Override public void pressUpdate(Pointer c, double timePressed) {
		super.pressUpdate(c, timePressed);
		//logger.info("update press: " + timePressed);				
	}
	
	@Override public void pressStopped(Pointer c) {
		super.pressStopped(c);
		
		Space s = (Space) c.getTouchedNode().get();
		if (s!=null) {
			if (s.hasParent(this)) {
				//logger.info(this + " unpress");
				updateWidget();
				onClicked();
				return;
			}
		}
		
	}
	
	@Override public void startTouch(Pointer c) {
		super.startTouch(c);
		touched.set(true);
		updateWidget();		
	}
	@Override public void stopTouch(Pointer c) {
		super.stopTouch(c);
		touched.set(false);
		updateWidget();				
	}
	
	@Override public void updateTouch(Pointer c, double timeTouched) {
		super.updateTouch(c, timeTouched);
	}

	protected void onClicked()  { 
		for (ButtonAction ba : buttonActions)
			ba.onButtonPressed(this);		
	}

	public void addButtonAction(ButtonAction ba) {
		buttonActions.add(ba);
	}
	public void removeButtonAction(ButtonAction ba) {
		buttonActions.remove(ba);
	}

}
