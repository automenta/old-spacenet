package automenta.spacenet.space.control;


public interface Touchable {

	public void startTouch(Pointer c);
	public void updateTouch(Pointer c, double timeTouched);
	public void stopTouch(Pointer c);
	
}
