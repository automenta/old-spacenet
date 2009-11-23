package automenta.spacenet.space.control;

import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public interface Pressable {

	public void pressStart(Pointer c, Vector3 location, Vector2 relativeToRect);
	public void pressUpdate(Pointer c, double timePressed);
	public void pressStopped(Pointer c);
	
}
