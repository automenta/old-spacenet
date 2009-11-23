package automenta.spacenet.run.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import automenta.spacenet.UURI;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.var.string.StringVar;

public class DemoFileEdit extends ProcessBox {

	public static class FileString extends StringVar {
	
		public FileString(UURI path) throws URISyntaxException {
			super();
			
			File file = new File(path.toURI());

			StringBuffer sb = new StringBuffer();
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));

				String line;
				do {
					line = reader.readLine();
					if (line!=null) {
						sb.append( line );
					}
				} while (line!=null);
				
				reader.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			set(sb.toString());
		}
		
	}
	
	@Override public void run() {
//		
//		URL r = new URL("file://../");
//		System.out.println( r.getPath() ); 
//		
//		UURI uri = new UURI(r);
//		
//		FileString fs;
//		try {
//			
//			fs = new FileString( uri );
//			
//			add(new TextEditRect(fs));
//			
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
//		
	}

	public static void main(String[] args) {
		URL r;
		try {
			r = new URL("file://../");
			System.out.println( r.getPath() ); 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		//new DefaultJmeWindow(new DemoFileEdit());		
	}
}
