package automenta.spacenet.run.face;

import java.util.Calendar;

import automenta.spacenet.Maths;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.os.SkyUtil;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.Rect.RectPosition;
import automenta.spacenet.space.object.data.BarChart;
import automenta.spacenet.space.object.data.NumberChart;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.FifoVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;

public class DemoFaceBorder extends ProcessBox {

	@Override public void run() {
		
		sky().add(SkyUtil.newRainbowSkyBox());
		
		add(new Rect(Color.Purple.alpha(0.5)).move(2, 0));
		add(new Rect(Color.Blue.alpha(0.5)).move(-2,0));
		add(new Rect(Color.Orange.alpha(0.5)).move(0, 0));

		
		final StringVar timeString = new StringVar("11:11");
		face().add(new TextRect(timeString).inside(RectPosition.NE, 0.25, 0.1));
		
		face().add(new TextButton("OK").inside(RectPosition.SE, 0.25, 0.1));
		
		final FifoVar<DoubleVar> values = new FifoVar(32);
		NumberChart nc0 = face().add(new BarChart(values));
		nc0.inside(RectPosition.NW, 0.25, 0.1);

		TextEditRect ter = face().add(new TextEditRect());
		ter.inside(RectPosition.SW, 0.75, 0.1);
		
		//getFace().add(new IconButton("icon.magic").move(RectPosition.SW, 0.25, 0.1));
		
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				int minute = Calendar.getInstance().get(Calendar.MINUTE);
				int second = Calendar.getInstance().get(Calendar.SECOND);
				timeString.set( hour + ":" + minute + "." + second);
				return 0.5;
			}
		});
		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				double v = Maths.random(-1, 1);
				if (values.size() > 0) {
					v = (values.getLast().d() + v)/2.0;
				}
				values.push(new DoubleVar(v));
				return 0.3;
			}		
		});
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoFaceBorder());
	}
}
