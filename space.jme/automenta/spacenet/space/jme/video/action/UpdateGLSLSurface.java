package automenta.spacenet.space.jme.video.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.jme.fMaths;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

import com.jme.scene.state.GLSLShaderObjectsState;
import com.jme.scene.state.RenderState.StateType;



public class UpdateGLSLSurface implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(UpdateGLSLSurface.class);
	private GLSLSurface surface;
	private JmeNode spatial;
	private GLSLShaderObjectsState shaderState;

	public UpdateGLSLSurface(JmeNode spatial, GLSLSurface s) {
		this.spatial = spatial;
		this.surface = s;
	}

	@Override public void stop() {
		Jme.doLater(new Runnable() {
			@Override public void run() {
				spatial.clearRenderState(StateType.GLSLShaderObjects);
				spatial.updateRenderState();
			}			
		});
	}

	@Override public void start() {

		if (!GLSLShaderObjectsState.isSupported()) {
			logger.warn("video device does not support GLSL shader programs");
			return;
		}

		final URL vertURL;
		final URL fragURL;
		try {
			vertURL = surface.getVert().toURL();
			fragURL = surface.getFrag().toURL();
		}
		catch (MalformedURLException e) {
			logger.warn(this + " has malformed shader URL: " + e);
			return;
		}

		shaderState = getShaderState(surface, vertURL, fragURL);

		//TODO add remaining shader variable type converters
		MapVar<String,Object> params = surface.getVars();
		for (String varName : params.keySet()) {
			Object val = params.get(varName);
			if (val instanceof Vector2)
				shaderState.setUniform(varName, fMaths.toFloat((Vector2)val));
			else if (val instanceof Vector3) 
				shaderState.setUniform(varName, fMaths.toFloat((Vector3)val));
			else if (val instanceof DoubleVar)
				shaderState.setUniform(varName, ((DoubleVar)val).f());
			else if (val instanceof Color) {
				shaderState.setUniform(varName, Jme.asJMEColor((Color)val));
			}
			else {
				logger.warn(surface + " has unsupported shader parameter type: " + varName + "=" + val);
			}
		}

		
		//TODO does this need BlendState applied here?
		Jme.doLater(new Runnable() {
			@Override public void run() {
				spatial.setRenderState(shaderState);
				spatial.updateRenderState();				
			}			
		});


	}

	//	public static class URLPair  {
	//		URL a, b;
	//
	//		public URL getA() {
	//			return a;
	//		}
	//
	//		public URL getB() {
	//			return b;
	//		}
	//
	//		public URLPair(URL a, URL b) {
	//			super();
	//			this.a = a;
	//			this.b = b;
	//		}
	//		
	//		@Override  public int hashCode() {
	//			return a.hashCode() + b.hashCode();
	//		}
	//		@Override public boolean equals(Object obj) {
	//			if (obj instanceof URLPair) {
	//				URLPair u = (URLPair) obj;
	//				return (getA().equals(u.getA()) && getB().equals(u.getB()));
	//			}
	//			return false;
	//		}
	//		
	//	}

	private static Map<GLSLSurface, GLSLShaderObjectsState> surfaceStates = new HashMap();

	private GLSLShaderObjectsState getShaderState(GLSLSurface surface, URL vertURL, URL fragURL) {
		GLSLShaderObjectsState s = surfaceStates.get(surface);
		if (s == null) {
			
			//s = (GLSLShaderObjectsState) spatial.getRenderState(RenderState.RS_SHADE);
	
			s = spatial.getJme().getDisplaySystem().getRenderer().createGLSLShaderObjectsState();

			if (logger.isInfoEnabled())
				logger.info(surface + " loading shader: " + vertURL + ", " + fragURL);

			s.load(vertURL, fragURL);

			s.setEnabled(true);
			
			surfaceStates.put(surface, s);

		}


		return s;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + spatial + " <- " + surface + "]";
	}
}
