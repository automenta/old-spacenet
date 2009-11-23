package automenta.spacenet.run.data;

import java.net.URI;
import java.net.URISyntaxException;

import automenta.spacenet.UURI;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect2;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.FileString;
import automenta.spacenet.var.string.StringVar;

public class DemoTextMatrix extends ProcessBox {

	@Override public void run() {
		VectorFont f = getThe(VectorFont.class);
		StringVar t;
		try {
			URI u = getClass().getResource("media/text.txt").toURI();
			System.out.println(u);
			t = new FileString(new UURI(u));
			
			TextRect2 tm = add(new Window()).add(new TextRect2(t, f));
			tm.viewAll();
			
			//tm.getAutoAspectScale().set(1.0);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoTextMatrix().scale(6));
	}
	
}
