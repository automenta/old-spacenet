package automenta.spacenet.space.object.data;

import java.util.LinkedList;
import java.util.List;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IntegerVar;

public class BarChart extends NumberChart {

	List<Space> toAdd = new LinkedList();
	private IntegerVar barAlignmentY = new IntegerVar(0);

	public BarChart(ListVar<DoubleVar> values) {
		super(values);
	}
	

	@Override
	protected void updateChart() {
		toAdd.clear();

		double x = -0.5;
		double barWidth = 1.0 / numValues;


		for (DoubleVar v : getValues()) {
			double d = v.d();
			Rect r = new Rect();
			toAdd.add(r);

			double yLow, yHigh;

			if (getBarAlignmentY().get() == -1) {
				yLow = -0.5;
				yHigh = -0.5 + (d - min) / (max-min);
			}
			else if (getBarAlignmentY().get() == 1) {
				yLow = 0.5 - (d - min) / (max-min);
				yHigh = 0.5; 						
			}
			else /*if (getBarAlignmentY().get() == 0)*/ {
				yLow = -(d - min) / (max-min) / 2.0;
				yHigh = -yLow; 												
			}

			r.span(x, yLow, x + barWidth, yHigh);

			r.color(getColor(d, min, max));
			x+=barWidth;
		}


		chartRect.addAll(toAdd);

	}

	/** 0: centered, -1: bottom, +1: top */
	public IntegerVar getBarAlignmentY() {
		return barAlignmentY ;
	}
	

}
