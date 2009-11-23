package automenta.spacenet.run.text;

import automenta.spacenet.space.object.widget.text.Terminal;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.run.text.DemoGroovyTerminal.GroovyTerminal;
import automenta.spacenet.space.object.widget.window.Window;

public class DemoTerminal extends ProcessBox {


//	abstract protected void initTerminal();
//	abstract protected String getOutput(String input);
	
	@Override public void run() {
		//initTerminal();
		
		Window w = add(new Window());
		
		final Terminal terminal = w.add(new GroovyTerminal(40, 20));
		terminal.scale(0.95); 
	}

}
