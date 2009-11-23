package automenta.spacenet.run.text;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.object.text.TextRect3;

public class DemoText2DParagraph extends ProcessBox {

	@Override public void run() {
		String string = "";
		string += "Abcdefghjiklklkmlsdfk;jk\n";
		string += "Abcdefghjiklklkmlsdfk;jk\n";
		string += "Abcdefghjiklklkmlsdfk;jk\n";
		string += "Abcdefghjiklklkmlsdfk;jk";
		
		add(new TextRect3(string));
	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoText2DParagraph());
	}
	
}
