package automenta.spacenet.space.object.data;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class LineChart extends NumberChart {

	private DoubleVar lineRadius = new DoubleVar(0.01);
	private int numEdges = 3;
	private int numSegments = 3;
	private ListVar<Vector3> points;
	private Curve3D curve;

	public LineChart(ListVar<DoubleVar> values) {
		super(values);
	}

	@Override public void start() {
		points = new ListVar<Vector3>();
		
		super.start();

		curve = add(new Curve3D(points, lineRadius, numEdges, numSegments));
		curve.surface(getCurveSurface());
		//curve.moveDZ(0.1);
		
	}
	

	private Surface getCurveSurface() {
		return new ColorSurface(Color.Orange);
	}

	@Override protected void updateChart() {
		double x = -0.5;
		
		points.clear();
		
		double barWidth = 1.0 / numValues;

		for (DoubleVar v : getValues()) {
			double d = v.d();
			double y = -0.5 + (d - min) / (max-min);
			Vector3 p = new Vector3(x, y, 0);
			points.add(p);
			x+=barWidth;
		}
		
		
	}
	
//	else if (type.get() == ChartType.Line) {
//		double x = -0.5;
//		Vector3 lastP = null;
//		for (DoubleVar v : getValues()) {
//			double d = v.d();
//			double y = -0.5 + (d - min) / (max-min);
//			Vector3 p = new Vector3(x, y, 0);
//			if (lastP!=null) {
//				toAdd.add(new Line3D(p, lastP, lineRadius, 3));
//			}
//			lastP = p;
//			x+=barWidth;
//		}				
//	}

}
