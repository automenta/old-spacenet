package automenta.spacenet.space.dynamic.vector;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.vector.Vector2;

public abstract class WhenVector2CrossesRect extends IfChanges<Vector2> {

	Boolean isInside = null;
	private Vector2 point;
	private Rect rect;
	
	public WhenVector2CrossesRect(Vector2 point, Rect rect) {
		super(point, rect.getPosition(), rect.getSize());
		this.point = point;
		this.rect = rect;
		//TODO handle rect's orientation
	}

	@Override
	public void afterValueChange(ObjectVar o, Vector2 previous, Vector2 next) {
		boolean currentlyInside = getIsInside();

		if (isInside!=null) {
			if (isInside.equals(currentlyInside))
				return;
		}
		
		this.isInside = currentlyInside;
		if (currentlyInside) {
			afterPointEntersRect();
		}
		else {
			afterPointExitsRect();
		}		
	}
	
	private boolean getIsInside() {
//		double px = point.x();
//		double py = point.y();
//		double ulX = rect.getUpperLeftX();
//		double ulY = rect.getUpperLeftY();
//		double brX = rect.getBottomRightX();
//		double brY = rect.getBottomRightY();
//		if (px >= ulX)
//			if (py >= ulY)
//				if (px <= brX)
//					if (py <= brY)
//						return true;
		return false;
	}

	abstract public void afterPointExitsRect();
	abstract public void afterPointEntersRect();

}
