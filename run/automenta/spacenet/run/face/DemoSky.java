package automenta.spacenet.run.face;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.os.SkyUtil;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.Color;

public class DemoSky extends ProcessBox {

	
	@Override
	public void run() {
		Jme jme = getThe(Jme.class);
	
		add(new Box(Color.Blue));
		
		jme.getSky().add(SkyUtil.newRainbowSkyBox());
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoSky());
	}
}
