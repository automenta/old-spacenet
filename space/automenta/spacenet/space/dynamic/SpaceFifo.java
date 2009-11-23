/**
 * 
 */
package automenta.spacenet.space.dynamic;

import automenta.spacenet.Disposable;
import automenta.spacenet.space.Space;
import automenta.spacenet.var.list.FifoVar;

public class SpaceFifo<O> extends FifoVar<O> {
	private Space space;
	private boolean disposeRemoved;

	public SpaceFifo(Space s, int size, boolean disposeRemoved) {
		super(size);
		this.space = s;
		this.disposeRemoved = disposeRemoved;
	}

	public SpaceFifo(Space s, int size) {
		this(s, size, false);
	}

	@Override
	protected <X extends O> void onIn(O o) {
		space.add(o);		
	}
	@Override
	protected <X extends O> void onOut(O o) {
		space.remove(o);
		
		if (disposeRemoved)	{
			if (o instanceof Disposable) {
				((Disposable)o).dispose();
			}
		}
	}
	

}