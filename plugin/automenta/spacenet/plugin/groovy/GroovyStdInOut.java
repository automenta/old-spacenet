package automenta.spacenet.plugin.groovy;

import groovy.ui.InteractiveShell;

import java.io.IOException;

public class GroovyStdInOut {

	public GroovyStdInOut() {
		
		InteractiveShell shell;
		try {
			shell = new InteractiveShell(System.in, System.out, System.err);
			shell.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		new GroovyStdInOut();
	}
}
