package automenta.spacenet.space.control;


public interface Draggable {

//	public void startDrag(Pointer c, Vector3 absPos);
//	public void updateDrag(Pointer c, Vector3 absPos, Vector2 rectPos);
//	public void stopDrag(Pointer c);

	public void startDrag(Pointer p, Drag drag);
	public void updateDrag(Pointer p, Drag drag);
	public void stopDrag(Pointer p, Drag drag);
	
}
