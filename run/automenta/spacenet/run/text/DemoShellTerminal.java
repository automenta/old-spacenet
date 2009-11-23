package automenta.spacenet.run.text;

import automenta.spacenet.os.DefaultJmeWindow;

public class DemoShellTerminal extends DemoTerminal {



//	@Override protected void initTerminal() {
//	}
//
//	@Override
//	protected String getOutput(String string) {
//		if (string.endsWith("\n"))
//			string = string.substring(0, string.length()-1);
//
//		Object o = StringViaShell.executeShell(string);
//		return "> " + string + "\n" + o.toString() + "\n\n";
//	}

	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoShellTerminal());		
	}

}
