package automenta.spacenet.run.face;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.os.SkyUtil;
import automenta.spacenet.run.text.DemoGroovyTerminal.GroovyTerminal;

public class DemoFaceConsole extends ProcessBox {

	
	@Override public void run() {
		
		spacetime().sky().add(SkyUtil.newRainbowSkyBox());
		
		double x = 0;
		double y = -0.25;
		double z = 0;
		double w = 0.5;
		double h = 0.5;
		
		GroovyTerminal edit = new GroovyTerminal(40, 10);
		
		edit.move(x, y, z).size(w, h);
		spacetime().face().add(edit);
		
	}

	public static void main(String[] args) {
		new DefaultJmeWindow( new DemoFaceConsole() );
	}
	
}
