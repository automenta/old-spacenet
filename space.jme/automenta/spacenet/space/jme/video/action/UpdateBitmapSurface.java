package automenta.spacenet.space.jme.video.action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.jme.geom.RectNode;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;

import com.jme.image.Texture;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.RenderState.StateType;
import com.jme.util.TextureManager;


public class UpdateBitmapSurface implements Starts, Stops {
	private static final Logger logger = Logger.getLogger(UpdateColorSurface.class);

	private JmeNode spatial;
	private BitmapSurface bitmapSurface;

	private IfChanges locationChange;

	private TextureState textureState;

	private static Map<String, Texture> textureCache = new HashMap();
	
	public UpdateBitmapSurface(JmeNode spatial, BitmapSurface s) {
		this.spatial = spatial;
		this.bitmapSurface = s;
	}

	@Override public void start() {
		locationChange = spatial.getSpace().add(new IfChanges(bitmapSurface.getURI()) {
			@Override public void afterValueChange(ObjectVar o, Object previous, Object next) {
				update();				
			}
		});

		update();
	}

	@Override public void stop() {
		if (locationChange!=null) {
			spatial.getSpace().remove(locationChange);
			locationChange = null;
		}

		Jme.doLater(new Runnable() {
			@Override public void run() {

				if (textureState!=null) {
						spatial.clearRenderState(StateType.Texture);
						spatial.updateRenderState();
				}
			}
			
		});

	}

	protected void update() {
		
		//TODO use a threadpool
		new Thread( new Runnable() {

			@Override public void run() {
				// TODO Auto-generated method stub
				URL textURL;
				try {
					textURL = bitmapSurface.getURI().toURL();
				} catch (MalformedURLException e) {
					logger.error("unable to apply texture to: " + spatial + " <- " + "(" + e + ")");
					return;
				}

				textureState = newTextureState();

				final Texture texture = getTexture(textURL); 

				//texture.setApply(ApplyMode.Replace);
				
				
				double aspect = ((double)texture.getImage().getHeight()) / ((double)texture.getImage().getWidth());
				bitmapSurface.getAspectXY().set(aspect);
				
				if (spatial instanceof RectNode) {
					((RectNode)spatial).getRect().getAspectXY().set(aspect);
				}

				
				Jme.doLater(new Runnable() {
					@Override public void run() {

						textureState.setTexture(texture);
						

						spatial.setRenderState(textureState);
						
						//HACK
						Jme.setImageState(spatial);
						

						//spatial.updateRenderState();						

					}					
				});
				
			}
			
		}).start();
		
	}

	private Texture getTexture(URL textURL) {
		Texture texture = textureCache.get(textURL.toExternalForm());
		if (texture == null) {
			if (logger.isInfoEnabled())
				logger.info(this + " loading bitmap: " + textURL);
	
			texture = TextureManager.loadTexture(
					textURL,
					Texture.MinificationFilter.BilinearNearestMipMap,
					Texture.MagnificationFilter.Bilinear);	
				
			textureCache.put(textURL.toExternalForm(), texture);
		}
		
		return texture;
	}

	private TextureState newTextureState() {
//		TextureState ts = (TextureState)spatial.getRenderState(RenderState.RS_TEXTURE);
//		if (ts == null) {
//			ts = spatial.getJme().getDisplaySystem().getRenderer().createTextureState();
//		}
//		return ts;
		return spatial.getJme().getDisplaySystem().getRenderer().createTextureState();
	}

	//	private BlendState getBlendState() {
	//		BlendState bs = (BlendState) spatial.getRenderState(BlendState.RS_BLEND);
	//		if (bs == null) {
	//			bs = spatial.getJme().getDisplaySystem().getRenderer().createBlendState();
	//		}
	//		return bs;
	//	}
	//
	//	private MaterialState getMaterialState() {
	//		MaterialState ms = (MaterialState) spatial.getRenderState(MaterialState.RS_MATERIAL);
	//		if (ms == null) {
	//			ms = spatial.getJme().getDisplaySystem().getRenderer().createMaterialState();
	//		}
	//		return ms;
	//	}


}
