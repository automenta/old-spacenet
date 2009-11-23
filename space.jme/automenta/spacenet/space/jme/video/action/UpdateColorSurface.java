package automenta.spacenet.space.jme.video.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.IfColorChanges;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.MaterialState.MaterialFace;
import com.jme.scene.state.RenderState.StateType;


public class UpdateColorSurface implements Starts, Stops {

	public MaterialState materialState;

	private class DoColorUpdate implements Runnable {

		private DoColorUpdate() {
		}


		@Override public void run() {
			ColorRGBA c = nextColor;

			//try {
			materialState = spatial.getMaterialState();

			if (spatial.getSpace() instanceof Space) {
				double o;
				if ( ( (spatial.getSpace()).getOpacity()) != null) {
					o = (spatial.getSpace()).getOpacity().d();
				}
				else {
					o = 1.0;
				}

				c.a = (float)Math.min(c.a, o);
			}

			Jme.setBlendState(jme, spatial, false);

			materialState.setMaterialFace(MaterialFace.FrontAndBack);
			materialState.setDiffuse(c);
			materialState.setAmbient(defaultAmbient);
			//				materialState.setSpecular(defaultSpecular);
			//				materialState.setEmissive(defaultEmissive);
			materialState.setShininess(defaultShinineess);
			materialState.setEnabled(true);

			//					BlendState blendState = spatial.getBlendState(); {
			//						blendState.setBlendEnabled(true);
			//						blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
			//						blendState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
			//						blendState.setTestEnabled(true);
			//					}
			//
			//spatial.clearRenderState(MaterialState.RS_MATERIAL);
			//spatial.clearRenderState(MaterialState.RS_BLEND);
			spatial.setRenderState(materialState);
			//					spatial.setRenderState( blendState );		
			spatial.updateRenderState();

			//			}
			//			catch (Exception e) {
			//				logger.error("unable to apply surface color to: " + spatial + " <- " + "(" + e + ")");
			//				return;
			//			}
		}
	}


	private static final Logger logger = Logger.getLogger(UpdateColorSurface.class);

	private static final Color defaultColor = new Color(0.5, 0.5, 0.5, 0.5);

	private JmeNode spatial;
	private ColorSurface colorSurface;

	private IfColorChanges watchingColor;

	private Jme jme;

	private IfDoubleChanges watchingOpacity;

	final ColorRGBA defaultAmbient = new ColorRGBA(0.5f,0.5f,0.5f,0.5f);
	//	final ColorRGBA defaultSpecular = new ColorRGBA(0.2f,0.2f,0.2f,0.8f);
	//	final ColorRGBA defaultEmissive = new ColorRGBA(0.2f,0.2f,0.2f,0.8f);

	private float defaultShinineess = 0.5f;

	private DoColorUpdate doColorUpdate;

	private ColorRGBA nextColor;

	private Color lastColor = new Color();

	private double lastOpacity;


	private static Map<Space,UpdateColorSurface> spaceMap = new HashMap();

	public UpdateColorSurface(JmeNode spatial, ColorSurface s) {
		this.spatial = spatial;
		this.colorSurface = s;
		this.jme = spatial.getJme();

	}

	@Override public void start() {

		watchingColor = jme.add(new IfColorChanges(colorSurface.getColor()) {			
			@Override public void afterColorChanged(Color c) {
				update();
			}
			@Override public String toString() {			
				return "updating when " + spatial + " color changes";
			}
		});

		DoubleVar op = (spatial.getSpace()).getOpacity();
		watchingOpacity = jme.add(new IfDoubleChanges(op ) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, 	Double previous, Double next) {
				update();
				updateChildOpacity(spatial.getSpace());
			}				
			@Override public String toString() {			
				return "updating when " + spatial + " opacity changes";
			}
		});

		spaceMap.put(spatial.getSpace(), this);

		update();

	}

	protected static void updateChildOpacity(Space s) {
		for (Object o : s) {
			if (o instanceof Space)
				updateChildOpacity((Space)o);

			UpdateColorSurface x = spaceMap.get(o);
			if (x!=null) {
				x.update();
			}
		}
	}

	@Override public void stop() {
		spaceMap.remove(spatial.getSpace());

		if (watchingColor!=null) {
			jme.remove(watchingColor);
			watchingColor = null;
		}
		if (watchingOpacity!=null) {
			jme.remove(watchingOpacity);
			watchingOpacity = null;
		}
		if (doColorUpdate != null) {
			doColorUpdate = null;
		}

		Jme.doLater(new Runnable() {
			@Override public void run() {
				spatial.clearRenderState(StateType.Material);
				spatial.updateRenderState();				
			}			
		});
	}


	protected void update() {

		boolean colorChanged = false;
		boolean opacityChanged = false;

		Color currentColor = colorSurface.getColor();
		if (currentColor == null)
			currentColor = defaultColor;

		if (!lastColor.equals(currentColor)) {
			colorChanged = true;
			lastColor.set(currentColor);
		}

		double opacity = getOpacity();

		if (lastOpacity != opacity) {
			opacityChanged = true;
			lastOpacity = opacity;
		}

		if (colorChanged || opacityChanged) {
			nextColor = Jme.asJMEColor(lastColor);
			nextColor.set( nextColor.r, nextColor.g, nextColor.b, ((float)opacity) * nextColor.a );

			if (doColorUpdate == null) {
				doColorUpdate = new DoColorUpdate();			
			}

			Jme.doLater(doColorUpdate);

		}
	}

	private double getOpacity() {
		double o = 1.0;

		//TODO fold this first iteration into the bottom loop

		DoubleVar d = (spatial.getSpace()).getOpacity();
		if (d != null) {
			o = d.d();
		}

		Space parent = spatial.getParentSpace();
		while (parent!=null) {
			o *= parent.getOpacity().d();
			Object x = parent.getParent();
			if (x instanceof Space)
				parent = (Space)x;
			else
				parent = null;
		}
		return o;
	}


}
