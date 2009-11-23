package automenta.spacenet.run.surface;

import java.net.URL;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.extern.ThreeDSMaxSpace;

public class DemoMesh extends ProcessBox {

	@Override public void run() {
		URL u = getClass().getResource("data/magic.3ds");
		try {
			add(new ThreeDSMaxSpace(u));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoMesh().scale(4));
	}
	
}
