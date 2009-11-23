package automenta.spacenet.run.data;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.dynamic.vector.DynamicVectorSet;
import automenta.spacenet.space.object.data.MatrixRect;
import automenta.spacenet.space.object.widget.window.Window;

public class DemoMatrixRect3AutoAspect extends DemoMatrixRect3 {


	@Override public void run() {
		dvs = add(new DynamicVectorSet(0));

		Window w = add(new Window());
		w.scale(2.0);
		final MatrixRect m = w.add(new MatrixRect());

		m.getAutoAspectScale().set(4);
		
		add(new MatrixInformation(m).move(-2.0, 0));


		generate(m, 12, 12);
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMatrixRect3AutoAspect().scale(4));
	}
}
