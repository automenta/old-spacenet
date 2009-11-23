package automenta.spacenet.space.control.rect.depr;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;

@Deprecated abstract public class IfButtonPress implements StartsIn<Scope>, Stops {

	private BooleanVar button;
	protected Pointer cursor;
	private Scope targetNode;
	private IfBoolChanges whenBoolChanges;

	public IfButtonPress(Scope targetNode, Pointer cursor, BooleanVar button) {
		super();
		this.cursor = cursor;
		this.button = button;
		this.targetNode = targetNode;
	}

	public IfButtonPress(Scope targetNode, Pointer cursor, PointerButton cb) {
		this(targetNode, cursor, cursor.getButton(cb));
	}

	boolean pressed = false;
	
	abstract protected void whenReleased();
	abstract protected void whenPressed(int repeatClicks);

	@Override public void start(Scope n) {
		whenBoolChanges = n.add(new IfBoolChanges(button) {
			@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
				if (nextValue) {
					if (cursor.getTouchedNode().is( targetNode ))  {
						pressed = true;
						whenPressed(1);
					}
				}
				else {
					if (pressed) {
						whenReleased();
						pressed = false;
					}
				}

			}			
		});
	}

	@Override public void stop() {
		//getNode().remove(whenBoolChanges);		
	}

	public Scope getNode() { return targetNode; }
	
	public Pointer getPointer() { return cursor; }

}
