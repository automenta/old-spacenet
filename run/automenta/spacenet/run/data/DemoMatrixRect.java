package automenta.spacenet.run.data;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.MatrixRect2;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.number.DoubleVar;

public class DemoMatrixRect extends ProcessBox {

	int w = 20;
	int h = 20;
	int fw = 8;
	int fh = 8;

	@Override public void run() {
		
		
		final MatrixRect2 m = add(new MatrixRect2(new DoubleVar(fw), new DoubleVar(fh)));
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Rect r = new Rect(Color.newRandom(1.0));
				r.add(new CharRect(new Character((char)('a' + (char)(Maths.random(0,26)))), getThe(VectorFont.class), Color.Black).aspect(1,1));
				m.put(i, j, r);				
			}
		}
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMatrixRect().scale(4));		
	}
}
