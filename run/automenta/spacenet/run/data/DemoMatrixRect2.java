package automenta.spacenet.run.data;

import automenta.spacenet.Maths;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.MatrixRect2;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;

public class DemoMatrixRect2 extends ProcessBox {

	int w = 20;
	int h = 20;
	int fw = 8;
	int fh = 8;

	@Override public void run() {
		
		
		final MatrixRect2 vert = add(new MatrixRect2(2, 4));
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				vert.put(i, j, newRect());				
			}
		}
		vert.move(-1, 0);
		
		final MatrixRect2 hor = add(new MatrixRect2(4, 2));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				hor.put(i, j, newRect());				
			}
		}
		hor.move(1, 0);
		
		
	}
	
	private Rect newRect() {
		Rect r = new Rect(Color.newRandom(1.0));
		r.add(new CharRect(new Character((char)('a' + (char)(Maths.random(0,26)))), getThe(VectorFont.class), Color.Black).aspect(1,1));
		return r;
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMatrixRect2().scale(4));		
	}
}
