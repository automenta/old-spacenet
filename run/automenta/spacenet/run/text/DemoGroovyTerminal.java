package automenta.spacenet.run.text;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.plugin.groovy.GroovyScope;
import automenta.spacenet.space.object.widget.text.Terminal;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;

public class DemoGroovyTerminal extends ProcessBox {


	public static class GroovyTerminal extends Terminal {
		GroovyScope groovy;		
		Map<String, Object> preBind = new HashMap();
		
		public GroovyTerminal(int w, int h) {
			super(w, h);
		}

		@Override protected void startTerminal() {
			groovy = add(new GroovyScope());
			
			for (String s : preBind.keySet())
				groovy.bind(s, preBind.get(s));
			preBind.clear();
			
		}
		
		public void bind(String name, Object object) {
			if (groovy!=null)
				groovy.bind(name, object);
			else
				preBind.put(name, object);
		}

		@Override protected String getOutput(String string) {
			if (string.endsWith("\n"))
				string = string.substring(0, string.length()-1);

			Object o = groovy.evaluate(string);
			return "> " + string + "\n" + o.toString() + "\n\n";
		}
		
	}
	
	@Override public void run() {
		add(new GroovyTerminal(40,20));
	}
	

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoGroovyTerminal());		
	}

}
