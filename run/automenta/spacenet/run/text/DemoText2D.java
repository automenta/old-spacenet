package automenta.spacenet.run.text;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.TextLineRect;
import automenta.spacenet.space.geom2.TextLineRect.TextType;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.StringVar;

public class DemoText2D extends ProcessBox {

	@Override public void run() {
		StringVar string = new StringVar("Abcdefghij");
		VectorFont font = getThe(VectorFont.class);
		
		Rect rb = add(new Rect(Color.Orange.alpha(1.0))).move(0,1,0);
		TextLineRect tb = rb.add(new TextLineRect(string, font, TextType.Bitmap));
		tb.moveDZ(0.05);
		tb.color(Color.White);
		
		Rect rv = add(new Rect(Color.Purple.alpha(1.0))).move(0,-1,0);
		TextLineRect tv = rv.add(new TextLineRect(string, font, TextType.Vector));
		tv.color(Color.White);
		tv.moveDZ(0.05);

	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoText2D());		
	}
}
