package automenta.spacenet.run.perf;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;

public class CharGeometryMeter extends AbstractGeometryMeter {

	private VectorFont font;

	public CharGeometryMeter() {
		super(1500, 50, 6.0);
		
	}

	@Override
	public void run() {
		this.font = getThe(VectorFont.class);
		super.run();
	}
	
	@Override public void addGeometry(Box s) {
		char c = (char) ((char)(Math.random() * 26.0) + 'A');
		Color color = Color.newRandom();
		double x = Maths.random(-1, 1);
		double y = Maths.random(-1, 1);
		s.add(new CharRect(c, font, color).move(x, y));
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new CharGeometryMeter());
	}
}
