package automenta.spacenet.space.geom2;

import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;

abstract public class Curve2D {

	abstract public ListVar<Vector2> getPoints();
	
	abstract public DoubleVar getThickness();
}
