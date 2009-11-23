package automenta.spacenet.act;

import automenta.spacenet.plugin.java.act.MethodActions;
import automenta.spacenet.plugin.java.act.StaticMethodsActions;
import automenta.spacenet.var.string.act.ToUppercase;

public class CoreActions {

	public static void addCoreActions(ActionIndex index) {
		index.addAction(new ToUppercase());
		index.addActionGenerator(new MethodActions());
		index.addActionGenerator(new StaticMethodsActions(Math.class));
		index.addActionGenerator(new StaticMethodsActions(Double.class));			
	}
	
}
