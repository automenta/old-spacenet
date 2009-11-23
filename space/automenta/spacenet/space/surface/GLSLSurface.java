package automenta.spacenet.space.surface;

import java.net.URL;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Surface;
import automenta.spacenet.var.map.MapVar;


//@IncompleteFeature("update variable 'uniform' parameters when they change")
public class GLSLSurface implements Surface {

	private final UURI vert;
	private final UURI frag;
	private final MapVar<String, Object> parameters = new MapVar();

	public GLSLSurface(URL vert, URL frag) {
		this( new UURI(vert), new UURI(frag) );
	}

	public GLSLSurface(UURI vert, UURI frag) {
		super();
		this.vert = vert;
		this.frag = frag;
	}

	public UURI getVert() {
		return vert;
	}

	public UURI getFrag() {
		return frag;
	}

	public MapVar<String, Object> getVars() {
		return parameters ;
	}

	
	
}
