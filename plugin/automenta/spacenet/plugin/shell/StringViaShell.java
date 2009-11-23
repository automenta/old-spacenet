package automenta.spacenet.plugin.shell;

import java.io.InputStream;

import automenta.spacenet.act.ObjectVarAction;
import automenta.spacenet.var.ObjectVar;

public class StringViaShell implements ObjectVarAction<String,String> {

	@Override public String getName(String i) {
		return "Via Shell";
	}

	@Override public double getStrength(String i) {
		return 0.5;
	}

	@Override public void run(String cmd, ObjectVar<String> o) throws Exception {

		String output = executeShell(cmd);
		

		o.set(output);
		  
	}
	
	public static String executeShell(String command) {
		Process x;
		try {
			x = Runtime.getRuntime().exec(command);

			InputStream ix = x.getInputStream();
			
			StringBuffer sb = new StringBuffer();

			int ch;
			while ( ( ch = ix.read() ) != -1 ) {
				sb.append((char) ch); 
			}

			return sb.toString();

		} catch (Exception e) {
			return e.toString();
		}
		
	}

}
