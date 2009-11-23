package automenta.spacenet.space.object.measure;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

abstract public class CursorDetailsFocus implements StartsIn<Space>, Stops {

	private Pointer cursor;
	private Space space;
	private Space decoration;
	private Space decorated;
	private double updatePeriod;

	private Scope currentPrevious;
	private Scope currentNext;
	private Scope nextPrevious;
	private Scope nextNext;

	public CursorDetailsFocus(double updatePeriod) {
		super();
		this.updatePeriod = updatePeriod;
	}

	@Override public void start(Space space) {
		this.space = space;
		this.cursor = space.getThe(Pointer.class);
		
		space.add(new IfChanges<Scope>(cursor.getTouchedNode()) {

			@Override public void afterValueChange(ObjectVar o, Scope previous, Scope next) {
				nextPrevious = previous;
				nextNext = next;
			}		
		});
		space.add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				if (currentPrevious!=nextPrevious) {
					if (currentNext!=nextNext) {
						
						updateFocused(currentPrevious, nextNext);
						
						currentPrevious = nextPrevious;
						currentNext = nextNext;
						
					}
				}
			
				return updatePeriod;
			}			
		});
	}

	protected synchronized void updateFocused(Scope previous, Scope next) {
		if (isDecorable(previous)) {
			System.out.println("clearing");
			clear();
		}
		
		if (isDecorable(next)) {
			System.out.println("decorating " + next);
			decorate(next);
		}
	}

	@Override public void stop() {
		clear();
	}

	abstract protected Space newDecoration(Space next);

	protected synchronized void clear() {
		if (decorated!=null) {
			if (decoration!=null) {
				space.removeAndDispose(decoration);
				this.decoration = null;
			}
			this.decorated = null;
		}
	}

	protected Space decorate(Scope next) {
		if (next!=null) {
			if (next instanceof Space) {
				this.decorated = (Space)next;
				this.decoration = newDecoration(decorated);
				decoration.getTangible().set(false);
				if (decoration!=null)
					space.add(decoration);
			}
			else {
				this.decorated = null;
				this.decoration = null;
			}
			return decoration;		
		}
		else {
			decoration = null;
			return null;
		}
	}

	abstract protected boolean isDecorable(Scope n);
	
}
