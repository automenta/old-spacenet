package automenta.spacenet.run.data;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.vector.DynamicVectorSet;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.MatrixRect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoMatrixRect3 extends ProcessBox {

	public static class MatrixInformation extends Rect implements Starts {

		private MatrixRect matrix;
		protected double updatePeriod = 0.3;

		public MatrixInformation(MatrixRect matrix) {
			super();
			this.matrix = matrix;
		}

		@Override public void start() {
			final StringVar s = new StringVar("");
			add(new TextRect(s));

			add(new Repeat() {
				@Override  public double repeat(double t, double dt) {
					String x = "";
					x += "#: " + matrix.getNumCells().i() + "\n";
					x += matrix.getMinX().i() + ".." + matrix.getMaxX().i() + ", " +matrix.getMinY().i() + ".." + matrix.getMaxY().i() + "\n";
					x += getThe(Pointer.class).getTouchedNode().get() + "\n";
					s.set(x);
					return updatePeriod;
				}
			});
		}


		@Override public void stop() {

		}


	}

	protected DynamicVectorSet dvs;

	public void generate(final MatrixRect m, final int writeW, final int writeH) {
		m.removeAll();

		m.getVisWidth().set(writeW/2);
		m.getVisHeight().set(writeH/2);
		
		for (int x = 0; x < writeW; x++) {
			for (int y = 0; y < writeH; y++) {
				m.put(x, y, newRect(x, y));
			}
		}

	}

	@Override public void run() {
		dvs = add(new DynamicVectorSet(0));
		
		Window w = add(new Window());
		w.scale(2.0);
		w.move(0.2, 0.2);
		final MatrixRect m = w.add(new MatrixRect());

		add(new MatrixInformation(m).move(-1.5, 1.5));

		add(new TextButton("1x1") {
			@Override public void pressStopped(Pointer c) {
				super.pressStopped(c);
				generate(m, 1, 1);
			}			
		}.span(-2.0, -1.0, -1.0, -1.1));
		add(new TextButton("0x0") {
			@Override public void pressStopped(Pointer c) {
				super.pressStopped(c);
				generate(m, 0, 0);
			}			
		}.span(-2.0, -1.1, -1.0, -1.2));
		add(new TextButton("1x10") {
			@Override public void pressStopped(Pointer c) {
				super.pressStopped(c);
				generate(m, 1, 10);
			}			
		}.span(-2.0, -1.2, -1.0, -1.3));
		add(new TextButton("10x10") {
			@Override public void pressStopped(Pointer c) {
				super.pressStopped(c);
				generate(m, 10, 10);
			}			
		}.span(-2.0, -1.3, -1.0, -1.4));


		generate(m, 10, 10);
	}

	public Rect newRect(int x, int y) {
		double s = 0.05;

		final double hue = (x)/10.0;
		final double saturation = (y)/10.0;
		final double brightness = 0.5;

		Rect r = new Rect(dvs.newVector3(0, 0, 0, s), dvs.newVector2(1,1, s), new Vector3()) {
			@Override
			public String toString() {
				return "Hue: " + hue + "\nSat: " + saturation + "\nBrightness: " + brightness;
			}					
		};
		r.color(Color.hsb( hue, saturation, brightness).alpha(0.6));
		r.add(new TextRect("[" + x + "," + y + "]", Color.Black) {
		}.scale(brightness).moveDZ(0.05));
		return r;
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMatrixRect3().scale(4));
	}
}
