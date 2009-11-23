package automenta.spacenet.space.jme.geom.text;

import org.apache.log4j.Logger;

import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.jme.geom.RectNode;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.video3d.VectorFont;

import com.jme.bounding.BoundingBox;
import com.jme.scene.TriMesh;
import com.jmex.font3d.Glyph3D;


public class CharRectNode extends RectNode {

	private static final Logger logger = Logger.getLogger(CharRectNode.class);

	private final CharRect charRect;

	private float maxStretch = 1.65f;

	public CharRectNode(CharRect c) {
		super(c);
		this.charRect = c;

	}

	@Override protected void attachRectGeometry() {	}

	@Override protected void detachRectGeometry() {	}


	@Override protected void startJme(JmeNode parent) {
		super.startJme(parent);

        double charWidth;
        double charHeight;
        TriMesh charShape;

		Glyph3D glyph;


		MVectorFont fontMesh = MVectorFont.getFont(getJme(), getFont());

		char c = getCharacter();

        try {
    		glyph = fontMesh.getGlyph(c);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            glyph = null;
        }

		if (glyph == null) {
			logger.warn(this + " missing glyph for : " + c + " with font: " + fontMesh);
			glyph = fontMesh.getGlyph('?');
		}

		if (glyph.getChildIndex() == -1) {
			return;
		}

		charWidth = glyph.getBounds().getWidth();// * localscale;
		charHeight = glyph.getBounds().getHeight();


		TriMesh targetMesh = (TriMesh)fontMesh.getRenderNode().getChild(glyph.getChildIndex());

		if (targetMesh == null) {
			return;
		}

						//charShape = new SharedMesh(targetMesh);

		charShape = new TriMesh(""+c, targetMesh.getVertexBuffer(), targetMesh.getNormalBuffer(),
				targetMesh.getColorBuffer(), targetMesh.getTextureCoords(0), targetMesh.getIndexBuffer());

		attachChild(charShape);

		if (charHeight > charWidth) {
			charShape.getLocalScale().x = Math.min(maxStretch , ((float)(charHeight / charWidth)));
			charShape.getLocalScale().y = 1.0f;
		}
		else {
			charShape.getLocalScale().x = 1.0f;
			charShape.getLocalScale().y = Math.min(maxStretch, ((float)(charWidth / charHeight)));
		}
		//charShape.getLocalScale().z = 1.0f;


		//		float scaleToFit = (float)Math.min(2.0*scaleFactor/charWidth,	2.0*scaleFactor/charHeight);
		//		charShape.getLocalScale().set(scaleToFit, scaleToFit, scaleToFit);

		float vx = (float) (charShape.getLocalScale().getX()*charWidth);
		float vy = (float) (charShape.getLocalScale().getY()*charHeight);

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

		charShape.getLocalTranslation().set(px, py, pz);



		charShape.setModelBound(new BoundingBox());
		charShape.updateModelBound();

		charShape.updateGeometricState(0.0f, true);
		charShape.updateRenderState();
	}




	public Character getCharacter() {
		return charRect.getCharacter();
	}

	public VectorFont getFont() {
		return charRect.getFont();
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


	@Override
	public double getMinVisibleProportion() {
		return getJme().getVideoState().getCharNodeVisibleProportion();
	}


}
