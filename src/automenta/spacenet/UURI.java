package automenta.spacenet;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

import automenta.spacenet.var.string.StringVar;


/** universally unique resource identifier (UURI).
 *  consists of a URL, URI, or a UUID -- stored as a string
 *  */
public class UURI extends StringVar implements ID {

	private String type = "";

	public UURI(String s) {
		super(s);
	}

	public UURI(String uri, String type) {
		super(uri);
		this.type = type;
	}

	public UURI(UUID u) {
		this(u.toString());
	}
	
	public UURI(URI u) {
		this(u.toString());
	}
	
	public UURI(URL url) {
		this(url.toExternalForm());
	}

	public UURI() {
		this(UUID.randomUUID());
	}
	

	@Override public String toString() { return get(); }
	
	@Override public int hashCode() {
		return get().hashCode();
	}
	
	@Override public boolean equals(Object obj) {
		if (obj instanceof UURI) {
			return toString().equals(((UURI)obj).toString());
		}
		return false;
	}

	@Override
	public UURI getUURI() {
		return this;
	}

	public URL toURL() throws MalformedURLException {
		return new URL(s());
	}

	public URI toURI() throws URISyntaxException {
		return new URI(s());
	}

	public void set(URL u) {
		set(u.toString());
	}
	
	public String getType() {
		return type ;
	}
}
