package automenta.spacenet.run.data;


import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect.RectPosition;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.object.data.LineChart;
import automenta.spacenet.space.object.data.NumberChart;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;

public class DemoFPSChart extends ProcessBox {

	double updatePeriod = 1.0;
	
	@Override public void run() {
		final FifoVar<DoubleVar> updateFPS = new FifoVar(32);
		final FifoVar<DoubleVar> renderFPS = new FifoVar(32);
		
		NumberChart nc0 = add(new LineChart(updateFPS));
		NumberChart nc1 = add(new LineChart(renderFPS));

		final StringVar fpsText = new StringVar(""); 
		add(new TextRect(fpsText, Color.White)).inside(RectPosition.S, 1.0, 0.25);
		
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
		
		final Jme jme = getThe(Jme.class);
		
		add(new Repeat() {
			int time = 0;
			@Override public double repeat(double t, double dt) {
				double u = jme.getUpdatesPerSecond().d();
				double r = jme.getRendersPerSecond().d();

				fpsText.set(Double.toString( (((int)(u * 100.0))/100.0) ) + " Updates / Second\n" + 
											 Double.toString((((int)(r * 100.0))/100.0))  + " Renders / Second");
		
				updateFPS.push(new DoubleVar(u));
				renderFPS.push(new DoubleVar(r));

				return updatePeriod;
			}		
		});
	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoFPSChart().scale(4));
	}
	
	
}
