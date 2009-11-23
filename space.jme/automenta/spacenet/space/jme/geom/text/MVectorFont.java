/**
 * 
 */
package automenta.spacenet.space.jme.geom.text;

import java.util.Map;
import java.util.logging.Level;

import javolution.util.FastMap;

import org.apache.log4j.Logger;

import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.video3d.VectorFont;

import com.jme.renderer.ColorRGBA;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.effects.Font3DBorder;
import com.jmex.font3d.math.TriangulationVertex;
import com.jmex.font3d.math.Triangulator;

public class MVectorFont extends Font3D {
	private static final Logger logger = Logger.getLogger(MVectorFont.class);

	private ColorRGBA col;

	final private VectorFont vectorFont;

	protected boolean drawSides = false;

	private final Jme jme;
	protected final static boolean drawFront = true;
	protected final static boolean drawBack = false;

	protected static Map<VectorFont, MVectorFont> fonts = new FastMap<VectorFont, MVectorFont>();

	static {
		java.util.logging.Logger.getLogger(Font3D.class.getName()).setLevel(Level.OFF);
		java.util.logging.Logger.getLogger(Triangulator.class.getName()).setLevel(Level.OFF);
		java.util.logging.Logger.getLogger(TriangulationVertex.class.getName()).setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("com.jmex.font3d.math.Triangulator$SweepLineComparer").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("com.jmex.font3d.math.Triangulator$1SweepLineStatus").setLevel(Level.OFF);	
	}
	
	protected MVectorFont(Jme jme, final VectorFont font) {
		super(font.getFont(), font.getFlatness(), font.hasSides(), drawFront,  drawBack );

		logger.info(this + " loaded font: " + font + " -> [ " + getRenderNode() + " ]");
		this.jme = jme;
		this.vectorFont = font;


		setColor(Jme.asJMEColor( font.getDefaultColor() ) );				
	}

	public void setColor(final ColorRGBA c) {
		this.col = c;				

		//				ColorRGBA cStart = new ColorRGBA(1,0,0,1);
		//				ColorRGBA cStop = new ColorRGBA(0,1,0,0f);
		//				Font3DGradient gradient = new Font3DGradient(Vector3f.UNIT_X, cStart, cStop);
		//				gradient.applyEffect(this);

		if (getVectorFont().getBorderColor()!=null) {
			ColorRGBA innerColor = c;
			ColorRGBA borderColor = Jme.asJMEColor(getVectorFont().getBorderColor());

			Font3DBorder fontborder = new Font3DBorder(getBorderWidth(), innerColor, borderColor, this);
			fontborder.applyEffect(this);
		}

		//this.enableBlendState();
		//this.enableDiffuseMaterial(); 
		//
		//
		this.getRenderNode().updateGeometricState(0.0f, true);
		this.getRenderNode().updateRenderState();

	}

	private float getBorderWidth() {
		return 0.02f;
	}

	public VectorFont getVectorFont() {
		return vectorFont;
	}


	public static MVectorFont getFont(Jme jme, VectorFont font) {
		MVectorFont f;
		f = fonts.get(font);
		if (f == null) {
			f = new MVectorFont(jme, font);
			fonts.put(font, f);		
		}
		return f;
	}
}