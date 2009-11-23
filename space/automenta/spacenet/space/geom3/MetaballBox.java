package automenta.spacenet.space.geom3;

import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;

public class MetaballBox extends Box {

	private ListVar<Sphere> points;
	private DoubleVar cellSize;

	public MetaballBox(ListVar<Sphere> points, double cellSize) {
		super();
		this.points = points;
		this.cellSize = new DoubleVar(cellSize);
		
	}
	
	public ListVar<Sphere> getPoints() {
		return points;
	}

	public DoubleVar getCellSize() {
		return cellSize ;
	}
}
