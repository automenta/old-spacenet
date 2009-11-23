package automenta.spacenet.var.string;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;

import automenta.spacenet.UURI;
import automenta.spacenet.act.Scheduler;

/** the contents of a file (UUID=URL=URI) as a string */
public class FileString extends StringVar {
	private static final Logger logger = Logger.getLogger(FileString.class);
	
	private UURI uri;

	public FileString(UURI uri) {
		super();
		this.uri = uri;

		Scheduler.doLater(new Runnable() {
			@Override public void run() {
				refresh();
			}			
		});
	}

	protected void refresh() {
		try {
			File file = new File(getURI().toURI());
			StringBuffer fileData = new StringBuffer(1000);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			char[] buf = new char[1024];
			int numRead=0;
			while((numRead=reader.read(buf)) != -1){
				String readData = String.valueOf(buf, 0, numRead);
				fileData.append(readData);
				buf = new char[1024];
			}
			reader.close();
			
			set(fileData.toString());
		}
		catch (Exception e) {
			logger.warn(e);
			set("");
		}

	}
	
	public UURI getURI() {
		return uri;
	}
	

}
