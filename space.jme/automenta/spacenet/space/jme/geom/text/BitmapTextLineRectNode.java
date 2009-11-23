package automenta.spacenet.space.jme.geom.text;

import java.util.LinkedList;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.BlendState.DestinationFunction;
import com.jme.scene.state.BlendState.SourceFunction;
import com.jme.scene.state.lwjgl.LWJGLZBufferState;

/**
 * 
 * @author Victor Porof, blue_veek@yahoo.com
 */
public class BitmapTextLineRectNode extends Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GFont gFont;
	private String text;
	private ColorRGBA fill;
	private LinkedList<Quad> charQuads = new LinkedList<Quad>();
	private float size;
	private float kerneling;
	private float scale;
	private float spacing;
	private float width;
	private float height;

	public BitmapTextLineRectNode(String text, GFont gFont, ColorRGBA fill, float size, float kerneling, Renderer renderer) {
		super();
		
		this.fill = fill;
		this.gFont = gFont;
		this.size = size;
		this.kerneling = kerneling;

		BlendState bs = renderer.createBlendState();
		bs.setBlendEnabled(true);
		bs.setSourceFunction(SourceFunction.SourceAlpha);
		bs.setDestinationFunction(DestinationFunction.OneMinusSourceAlpha);
		bs.setEnabled(true);
		setRenderState(bs);

		setText(text);
	}

	private void construct() {
		Jme.doLater(new Runnable() {

			@Override public void run() {
				scale = size / gFont.getMetricsHeights();
				spacing = 0;
				for (int i = 0; i < charQuads.size(); i++) {
					if (i < text.length()) {
						float positionX = spacing * scale
								+ gFont.getMetricsWidths()[text.charAt(i)] * scale / 2f;
						float positionY = gFont.getTextDescent() * scale;
						charQuads.get(i).getLocalTranslation().setX(positionX);
						charQuads.get(i).getLocalTranslation().setY(positionY);

						float sizeX = size;
						float sizeY = size;
						charQuads.get(i).getLocalScale().setX(sizeX);
						charQuads.get(i).getLocalScale().setY(sizeY);

						if (fill != null) {
							charQuads.get(i).setSolidColor(fill);
						}
						attachChild(charQuads.get(i));

						charQuads.get(i).setRenderState(gFont.getChar(text.charAt(i)));
						spacing += gFont.getMetricsWidths()[text.charAt(i)] + kerneling;
					}
				}
				for (int i = charQuads.size(); i < text.length(); i++) {
					Quad quad = new Quad(String.valueOf(text.charAt(i)), 1f, 1f);

					float positionX = spacing * scale
							+ gFont.getMetricsWidths()[text.charAt(i)] * scale / 2f;
					float positionY = gFont.getTextDescent() * scale;
					quad.getLocalTranslation().setX(positionX);
					quad.getLocalTranslation().setY(positionY);

					float sizeX = size;
					float sizeY = size;
					quad.getLocalScale().setX(sizeX);
					quad.getLocalScale().setY(sizeY);

					if (fill != null) {
						quad.setSolidColor(fill);
					}
					attachChild(quad);

					quad.setRenderState(gFont.getChar(text.charAt(i)));
					spacing += gFont.getMetricsWidths()[text.charAt(i)] + kerneling;

					charQuads.add(quad);
				}
				for (int j = text.length(); j < charQuads.size(); j++) {
					detachChild(charQuads.get(j));
				}
				for (int j = text.length(); j < charQuads.size(); j++) {
					charQuads.remove(j);
				}

				width = spacing * scale;
				height = size;
				
				getLocalTranslation().set(-0.5f, -0.25f, 0);
				if ((width > 0.0) && (height > 0.0))
					getLocalScale().set(1.0f / width, 0.5f / height, 1.0f);

				LWJGLZBufferState zb = new LWJGLZBufferState();
				zb.setWritable(false);
				zb.setEnabled(true);
				setRenderState(zb);

				updateRenderState();
				
			}
			
		});
	}

	public void setText(Object text) {
		this.text = String.valueOf(text);
		construct();
	}

	public String getText() {
		return text;
	}

	public void setFill(ColorRGBA fill) {
		this.fill = fill;
		construct();
	}

	public ColorRGBA getFill() {
		return fill;
	}
	
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	
}