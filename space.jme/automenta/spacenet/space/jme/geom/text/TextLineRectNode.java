/**
 * 
 */
package automenta.spacenet.space.jme.geom.text;

import org.apache.log4j.Logger;

import automenta.spacenet.space.geom2.TextLineRect;
import automenta.spacenet.space.geom2.TextLineRect.TextType;
import automenta.spacenet.space.jme.geom.RectNode;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.video3d.VectorFont;

import com.jme.bounding.OrientedBoundingBox;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial.CullHint;
import com.jme.scene.TriMesh;
import com.jme.scene.state.CullState.Face;
import com.jme.scene.state.lwjgl.LWJGLCullState;
import com.jmex.font3d.Glyph3D;

public class TextLineRectNode extends RectNode {
	private static final Logger logger = Logger.getLogger(TextLineRectNode.class);


	private final TextLineRect tRect;


	static private LWJGLCullState textCullState;

	public TextLineRectNode(TextLineRect t) {
		super(t);
		this.tRect = t;
	}


	public VectorFont getFont() { return tRect.getFont(); }

	@Override protected void attachRectGeometry() {	}
	@Override protected void detachRectGeometry() {	}


	@Override
	public CullHint getCullHint() {
		return CullHint.Dynamic;
	}
	
	@Override protected void startJme(JmeNode parent) {
		super.startJme(parent);

		Jme.doLater(new Runnable() {
			@Override public void run() {
                if (tRect.getType() == TextType.Vector) {
                    createVector();
                }
                else if (tRect.getType() == TextType.Bitmap) {
                    createBitmap();
                }
			}			
		});
	}



	@Override public void dispose()  {

		super.dispose();
	}




	//	@Override public Vector3 getTranslationAdjustment() {
	//		if (getStyle()!=null)
	//			return getStyle().getTextAdjustment();
	//		return null;
	//	}


	/** 
	 * z-alignment: 0=center (for 3d characters, with sides drawn), 1=front (for flat characters)
	 * @return
	 */
	public float getZAlignment() {
		return 1.0f;
	}


	private void createBitmap() {
		Renderer renderer = getJme().getDisplaySystem().getRenderer();
		
		GFont gFont = GFont.get(getFont(), renderer);
		

		float size = 0.5f;
		String text = tRect.getText().s();
		ColorRGBA fill = Jme.asJMEColor(getFont().getColor());
		BitmapTextLineRectNode b = new BitmapTextLineRectNode(text, gFont, fill, size, 0.0f, renderer);
		b.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		attachChild(b);

	
		setModelBound(new OrientedBoundingBox());
		updateGeometricState(0.0f, true);
		updateModelBound();
		updateRenderState();

	}

	private void createVector() {
		Node charsNode = new Node();

		//			ImposterNode charsNode = new ImposterNode("x", 5.0f, 256, 256);
		//			charsNode.setRedrawRate(0.1f);

		attachChild(charsNode);

		Glyph3D glyph;


		MVectorFont fontMesh = MVectorFont.getFont(getJme(), getFont());

		double x = 0;
		double maxHeight = 0;

		double spaceWidth = fontMesh.getGlyph('-').getBounds().getWidth();

		for (char c : tRect.getText().s().toCharArray()) {

			glyph = fontMesh.getGlyph(c);
			if (glyph == null) {
				logger.warn(this + " missing glyph for : " + c + " with font: " + fontMesh);
				glyph = fontMesh.getGlyph('?');
			}

			if (glyph.getChildIndex() == -1) {
				x += spaceWidth;
				continue;
			}

			double charWidth = glyph.getBounds().getWidth();// * localscale;
			double charHeight = glyph.getBounds().getHeight();

			if (charHeight > maxHeight)
				maxHeight = charHeight;

			TriMesh targetMesh = (TriMesh)fontMesh.getRenderNode().getChild(glyph.getChildIndex());

			if (targetMesh == null) {
				x += spaceWidth;
				continue;
			}

							//SharedMesh charShape = new SharedMesh(targetMesh);
			TriMesh charShape = new TriMesh(""/*+c*/, targetMesh.getVertexBuffer(), targetMesh.getNormalBuffer(), 
					targetMesh.getColorBuffer(), targetMesh.getTextureCoords(0), targetMesh.getIndexBuffer());

			charsNode.attachChild(charShape);

			//				if (charHeight > charWidth) {
			//					charShape.getLocalScale().x = Math.min(maxStretch , ((float)(charHeight / charWidth)));
			//					charShape.getLocalScale().y = 1.0f;
			//				}
			//				else {
			//					charShape.getLocalScale().x = 1.0f;
			//					charShape.getLocalScale().y = Math.min(maxStretch, ((float)(charWidth / charHeight)));
			//				}

			//charShape.getLocalScale().z = 1.0f;


			//		float scaleToFit = (float)Math.min(2.0*scaleFactor/charWidth,	2.0*scaleFactor/charHeight);
			//		charShape.getLocalScale().set(scaleToFit, scaleToFit, scaleToFit);

			float vx = (float) (/*charShape.getLocalScale().getX()**/charWidth);
			float vy = (float) (/*charShape.getLocalScale().getY()**/charHeight);

			//		float vx = (float) (charShape.getLocalScale().getX()*(charWidth/charHeight));
			//		float vy = (float) (charShape.getLocalScale().getY());

			//		float vx = (float) (charShape.getLocalScale().getX());
			//		float vy = (float) (charShape.getLocalScale().getY()*(charHeight/charWidth));

			float vz = charShape.getLocalScale().getZ();

			//depends on alignment to rect
			float px = -vx/2.0f;

			float py = -vy/4.0f;

			//float pz = 0;//-vz/2.0f;
			float pz = -vz/2.0f * getZAlignment();

			charShape.getLocalTranslation().set((float)x, py, pz);

			//charShape.setModelBound(new OrientedBoundingBox());

			x += charWidth;

		}

//		charsNode.updateGeometricState(0.0f, true);
//		charsNode.setModelBound(new OrientedBoundingBox());
//		charsNode.updateModelBound();

		float nw = (float)(1.0/x);
		float nh = (float)(1.0 / maxHeight);
		charsNode.getLocalScale().set(nw, nh/2.0f, 1f);
		float w = (float) x * nw;		
		charsNode.getLocalTranslation().set(-w/2.0f, 0, 0);

		if (textCullState == null) {
			textCullState = new LWJGLCullState();
			textCullState.setCullFace(Face.Back);
			textCullState.setEnabled(true);
		}
		setRenderState(textCullState);
		setModelBound(new OrientedBoundingBox());
		updateGeometricState(0.0f, true);
		updateModelBound();
		updateRenderState();
	}


	@Override
	public double getMinVisibleProportion() {
		return getJme().getVideoState().getTextLineVisibleProportion();
	}

}