package automenta.spacenet.run.data;


import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.data.BarChart;
import automenta.spacenet.space.object.data.NumberChart;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.number.DoubleVar;

public class DemoBarChart extends ProcessBox {

	@Override public void run() {
		final FifoVar<DoubleVar> values = new FifoVar(32);
		
		NumberChart nc0 = add(new BarChart(values));

//		NumberChart nc0 = add(new NumberChart(values, ChartType.Bar));
//		nc0.move(-1,0,0).scale(2*0.95, 1*0.95);
//		
//		NumberChart nc1 = add(new NumberChart(values, ChartType.Line));
//		nc1.move(1, 0, 0).scale(2*0.95, 1*0.95);
//
//		NumberChart nc2 = add(new NumberChart(values, ChartType.Bar));
//		nc2.move(-1,-1,0).scale(2*0.95, 1*0.95);
//		nc2.getBarAlignmentY().set(-1);
//
//		NumberChart nc3 = add(new NumberChart(values, ChartType.Bar));
//		nc3.move(-1,1,0).scale(2*0.95, 1*0.95);
//		nc3.getBarAlignmentY().set(1);
		
		add(new Repeat() {
			int time = 0;
			@Override public double repeat(double t, double dt) {
				double v = Maths.random(-1, 1);
				if (values.size() > 0) {
					v = (values.getLast().d() + v)/2.0;
				}
				values.push(new DoubleVar(v));
				
				time++;
				return (time < 10) ? 0.2 : 1.0;
			}		
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoBarChart().scale(4));
	}
	
	
}
