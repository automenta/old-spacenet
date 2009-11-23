/**
 * 
 */
package automenta.spacenet.var.string.act;

import automenta.spacenet.act.ObjectVarAction;
import automenta.spacenet.var.ObjectVar;

public class ToUppercase implements ObjectVarAction<String,String> {

	@Override public String getName(String i) {
		return "uppercase";
	}

	@Override public double getStrength(String i) {
		for (int x = 0; x < i.length(); x++) {
			char c = i.charAt(x);
			
			//at least 1 lower case letter
			if (Character.isLowerCase(c)) {
				return 1.0;
			}
		}
		return 0.0;
	}

	@Override public void run(String i, ObjectVar<String> o) throws Exception {
		o.set( i.toUpperCase() );
	}
	
}