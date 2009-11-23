/**
 * 
 */
package automenta.spacenet.space.object.data;

import automenta.spacenet.Starts;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;

abstract public class NumberChart extends Rect implements Starts {

	private ListVar<DoubleVar> values;
	protected Rect chartRect;
	protected double min;
	protected double max;
	protected double numValues;

	
	public NumberChart(ListVar<DoubleVar> values) {
		super();
		
		this.values = values;
	}

	@Override public void start() {
		chartRect = add(new Rect());
		
		add(new IfCollectionChanges<DoubleVar>(getValues()) {
			@Override  public void afterObjectsAdded(CollectionVar list, DoubleVar[] added) {
				redrawChart();
			}

			@Override public void afterObjectsRemoved(CollectionVar list, DoubleVar[] removed) {
				redrawChart();
			}				
		});
		
		redrawChart();
	}

	protected void redrawChart() {
		chartRect.clear();				
		
		
		numValues = getValues().size();
		
		min=0;
		max=0;
		
		if (getValues().size() > 0) {
			min = max = getValues().get(0).d();
		}
		
		for (DoubleVar v : getValues()) {
			if (v.d() > max)
				max = v.d();
			if (v.d() < min)
				min = v.d();
		}
		
		

		updateChart();
	}
	
	abstract protected void updateChart();
	
	
	protected Color getColor(double v, double min, double max) {
		double p = (v - min) / (max - min);
		return new Color(p, p, p, 1.0);
	}
	
	public ListVar<DoubleVar> getValues() {
		return values;
	}

	@Override	public void stop() { 	}

	
}