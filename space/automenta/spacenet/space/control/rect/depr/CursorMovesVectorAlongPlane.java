package automenta.spacenet.space.control.rect.depr;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class CursorMovesVectorAlongPlane extends Repeat<Rect> {


	private Rect rect;
	private Pointer cursor;

	Vector2 cursorCurrent = new Vector2(); 
	Vector2 cursorLast = new Vector2();
	Vector2 cursorVelocity = new Vector2();
	private Vector3 movedVector;
	private DoubleVar speed;
	
	Vector3 target = new Vector3();
	
	public CursorMovesVectorAlongPlane(Rect rect, Vector3 movedVector, DoubleVar speed) {
		super();
		this.rect = rect;
		this.movedVector = movedVector;
		this.speed = speed;
		
		target = new Vector3(movedVector);
	}

	

	@Override
	public double repeat(double t, double dt) {
		if (cursor == null) {
			cursor = rect.getThe(Pointer.class);
		}
		
		cursorCurrent.set(cursor.getPositionRelativeToScreen());
		
		cursorVelocity.set(cursorCurrent);
		cursorVelocity.subtract(cursorLast);
		cursorVelocity.multiply(speed.get() * dt);

		double vx = -cursorVelocity.x();
		double vy = -cursorVelocity.y();
		
		moveVector(vx, vy);
		

		if (cursorLast == null) {
			cursorLast = new Vector2(cursorCurrent);
		}
		else {
			cursorLast.set(cursorCurrent);
		}
		
		// TODO Auto-generated method stub
		return 0;
	}



	protected void moveVector(double vx, double vy) {
		if (cursor.getButton(PointerButton.Left).get()) {
			target.add(vx, vy, 0);
			applyTarget(target);
		}
	}
	
	protected void applyTarget(Vector3 target) {
		movedVector.set(target);		
	}
	
	public Rect getRect() { return rect; }
	
}
