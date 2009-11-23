package automenta.spacenet.space.control;

import automenta.spacenet.var.vector.Vector2;

public interface DragTarget {

	//TODO provide localPosition (from JmePointer)
	public void onDraggableDropped(Pointer p, Draggable d, Vector2 localPosition);
	
}
