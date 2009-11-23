package automenta.spacenet.run.data;


import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.data.LineChart;
import automenta.spacenet.space.object.data.NumberChart;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;

public class DemoMemoryChart extends ProcessBox {

	@Override public void run() {
		final FifoVar<DoubleVar> values = new FifoVar(32);
		
		NumberChart nc0 = add(new LineChart(values));

		final StringVar fpsText = new StringVar(""); 
		add(new TextRect(fpsText, Color.Red).scale(0.5));
		
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
				double v = Runtime.getRuntime().freeMemory();

				fpsText.set("Free Memory:\n" + Double.toString( (((int)(v / 1024.0 * 100.0))/100.0) ) + " kB" );
				values.push(new DoubleVar(v));

				return 1.0;
			}		
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoMemoryChart().scale(4));
	}
	
	
}
