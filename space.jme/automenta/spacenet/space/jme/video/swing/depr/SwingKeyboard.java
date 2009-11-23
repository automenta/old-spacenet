package automenta.spacenet.space.jme.video.swing.depr;

import automenta.spacenet.space.control.keyboard.Keyboard;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.TreeMap;


abstract public class SwingKeyboard extends Keyboard implements KeyEventDispatcher {
	 
//	public static final int CONSUME_PRESSED = 1;
//	public static final int CONSUME_RELEASED = 2;
//	public static final int CONSUME_TYPED = 4;
//
//	private static SwingKeyboard sSingleton;
//
////	private TreeMap<Integer, Action> mPressCommands = new TreeMap<Integer, Action>();
////	private TreeMap<Integer, Action> mReleaseCommands = new TreeMap<Integer, Action>();
////	private TreeMap<Integer, Action> mTypedCommands = new TreeMap<Integer, Action>();
//	private TreeMap<Integer, Integer> mConsumeKeys = new TreeMap<Integer, Integer>();
//
//	private SwingKeyboard() {
//		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
//	}
//
//	public static SwingKeyboard getSingleton() {
//		if (sSingleton == null) {
//			sSingleton = new SwingKeyboard();
//		}
//		return sSingleton;
//	}
//
//	/**
//	 *
//	 * @param keyCode the keycode to consume
//	 * @param use combinations of CONSUME_PRESSED, CONSUME_RELEASED, CONSUME_TYPED defined in this class
//	 */
//	public void setConsumeKeyEvent(int keyCode, int when) {
//		mConsumeKeys.put(keyCode, when);
//	}
//
//	public boolean dispatchKeyEvent(KeyEvent e) {
//		if (e.getID() == KeyEvent.KEY_TYPED)
//			return false;
//
//		// TODO Auto-generated method stub
//		int keyCode = e.getKeyCode();
//		int id = e.getID();
//		if (id == KeyEvent.KEY_PRESSED) {
//
//			getKey(keyCode).setValue(true);
//
//		}
//		else if (id == KeyEvent.KEY_RELEASED)  {
//				getKey(keyCode).setValue(false);
//		}
//
////		else if (id == KeyEvent.KEY_TYPED) {
////			keyCode = e.getKeyChar();
////			//System.out.println("typed");
////		}
//
//		Integer consume = mConsumeKeys.get(keyCode);
//		if (consume != null) {
//			int con = consume.intValue();
//			if ((id == KeyEvent.KEY_PRESSED && ((con & CONSUME_PRESSED) != 0))
//					|| (id == KeyEvent.KEY_RELEASED && ((con & CONSUME_RELEASED) != 0))
//					|| (id == KeyEvent.KEY_TYPED && ((con & CONSUME_TYPED) != 0))) {
//				//System.out.println("Consumed");
//				e.consume();
//				return true;
//			}
//		}
//
//		return false;
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		updateKeyEvent(e, true);
//	}
//
//	@Override
//	public void keyReleased(final KeyEvent e) {
//		updateKeyEvent(e, false);
//	}
//
//	@Override	public void keyTyped(KeyEvent e) {	}
//
//
//	private void updateKeyEvent(final KeyEvent e, final boolean pressed) {
//		keyboard.updateKeyEvent(e, pressed);
//
//		updateKeyEvent(ambientNode, e, pressed);
//
//		if (touchedNode!=null) {
//			updateKeyEvent(touchedNode, e, pressed);
//		}
//		else {
//			updateKeyEvent(rootNode, e, pressed);
//		}
//	}
//
//	private void updateKeyEvent(AbstractNode keyReceiver, final KeyEvent e, final boolean pressed) {
//		keyReceiver.foreachState(KeyAware.class, new ObjectVisitor<KeyAware>() {
//			@Override public void onVisit(KeyAware object) {
//				object.onKeyEvent(e.getKeyCode(), pressed);
//			}
//		});
//
//	}
//
////	public static void main(String[] args) {
////		JTextArea area = new JTextArea();
////		// TODO Auto-generated method stub
////		Action a = new AbstractAction() {
////			public void actionPerformed(ActionEvent ae) {
////				System.out.println("hit!");
////			}
////		};
////		//KeyboardCommands kc = new KeyboardCommands();
////		KeyboardCommands kc = KeyboardCommands.getSingleton();
////		kc.addKeyReleasedAction(KeyEvent.VK_C, a);
////		//kc.setConsumeKeyEvent('c', CONSUME_TYPED);
////		kc.setConsumeKeyEvent(KeyEvent.VK_C, CONSUME_PRESSED);
////		JFrame jf = new JFrame();
////		jf.getContentPane().add(area);
////		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
////		jf.setBounds(100,100,200,200);
////		jf.setVisible(true);
////		//WindowUtilities.visualize(area);
////	};
////
//
//	/*
//	public KeyboardCommands(int keycode, Action action) {
//		this();
//		addKeyPressedAction(keycode, action);
//	}
//	*/
//
////	/**
////	 * By default, actions are added to the KeyPressed event
////	 * @param keyCode
////	 * @param action
////	 */
////	public void addKeyAction(int keyCode, Action action) {
////		addKeyPressedAction(keyCode, action);
////	}
////
////	/**
////	 * Add an action on the specified keyCode that takes place on the
////	 * keyPressed event
////	 * @param keyCode
////	 * @param action
////	 */
////	public void addKeyPressedAction(int keyCode, Action action) {
////		mPressCommands.put(keyCode, action);
////	}
////
////	public void removeKeyPressedAction(int keyCode) {
////		mPressCommands.remove(keyCode);
////	}
////
////	/**
////	 * Add an action on the specified keyCode that takes place on the
////	 * keyReleased event
////	 * @param keyCode
////	 * @param action
////	 */
////
////	public void addKeyReleasedAction(int keyCode, Action action) {
////		mReleaseCommands.put(keyCode, action);
////	}
////
////	public void removeKeyReleasedAction(int keyCode) {
////		mReleaseCommands.remove(keyCode);
////	}
////
/////**
//// * Add an action on the specified keyCode that takes place on the
//// * keyTyped event
//// * @param keyCode
//// * @param action
//// */
////	public void addKeyTypedAction(int keyCode, Action action) {
////		mTypedCommands.put(keyCode, action);
////	}
////
////	public void removeKeyTypedAction(int keyCode) {
////		mTypedCommands.remove(keyCode);
////	}

}