/**
 * 
 */
package automenta.spacenet.space.video3d;

import java.awt.Font;

import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IntegerVar;



public class VectorFont {
	
	private DoubleVar charAspect = new DoubleVar(1.7);

	protected java.awt.Font font;
	protected Color color;
	private double flatness;
	private boolean sides;
	private IntegerVar maxLineChars = new IntegerVar(-1);
	private Color borderColor;
	
	public VectorFont(String fontName, Color c) {
		this(fontName, c, 1.0);
	}

	public VectorFont(String fontName) {
		this(fontName, null, 1.0);
	}
	
	public VectorFont(String fontName, double flatness) {
		this(fontName, null, flatness);
	}

	public VectorFont(String fontName, Color defaultColor, double flatness) {
		this(fontName, Font.PLAIN, defaultColor, flatness);
	}
	public VectorFont(String fontName, Color defaultColor, double flatness, int maxLineChars) {
		this(fontName, Font.PLAIN, defaultColor, flatness);
		this.maxLineChars.set(maxLineChars);
	}

	
	public VectorFont(String fontName, int fontAttrib, Color defaultColor, double flatness) {
		this(new Font(fontName, fontAttrib, 1), defaultColor, flatness, false);	
	}
	public VectorFont(String fontName, int fontAttrib, Color defaultColor, double flatness, boolean sides) {
		this(new Font(fontName, fontAttrib, 1), defaultColor, flatness, sides);
	}
	public VectorFont(String fontName, int fontAttrib, Color color, Color borderColor, double flatness, boolean sides) {
		this(new Font(fontName, fontAttrib, 1), color, borderColor, flatness, sides, -1);
	}

	
	public VectorFont(Font font, Color defaultColor, double flatness, boolean sides, int maxLineChars) {
		this(font, defaultColor, null, flatness, sides, maxLineChars);
	}
	
	public VectorFont(Font font, Color defaultColor, Color borderColor, double flatness, boolean sides, int maxLineChars) {
		super();
		this.font = font;
		this.color = defaultColor;
		this.flatness = flatness;
		this.sides = sides;
		this.borderColor = borderColor;
		
		this.maxLineChars.set(maxLineChars);
	}

	/**
	 * 
	 * @param font
	 * @param defaultColor
	 * @param flatness  the maximum allowable distance between the control points and the flattened curve
	 */
	public VectorFont(Font font, Color defaultColor, double flatness, boolean sides) {
		this(font, defaultColor, flatness, sides, -1);
	}
	
	public VectorFont(Font font, Color defaultColor, boolean sides) {
		this(font, defaultColor, 1.0, sides);
	}

	public VectorFont(Font font, Color defaultColor) {
		this(font, defaultColor, 1.0, false);
	}
	


	public java.awt.Font getFont() {
		return font;
	}
	public Color getDefaultColor() {
		return color;
	}

	public double getFlatness() {
		return flatness ;
	}

	public boolean hasSides() {
		return sides;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[" + getFont().getFontName() + ", " + getFlatness() + "]";
	}
	
	public IntegerVar getMaxLineChars() {
		return maxLineChars;
	}
	
	public VectorFont withMaxLineChars(int l) {
		return new VectorFont(getFont(), getColor(), getFlatness(), hasSides(), getMaxLineChars().i() );		
	}
	public Color getColor() {
		return color;
	}
	
	//involve all properties except color and linechars in computing hash
	@Override public int hashCode() {
		return getFont().hashCode() + (int)getFlatness() + (hasSides()?0:1);  
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VectorFont) {
			VectorFont v = (VectorFont) obj;
			if (!getFont().equals(v.getFont()))
				return false;
			if (getFlatness()!=v.getFlatness())
				return false;
			if (hasSides()!=v.hasSides())
				return false;
			return true;			
		}
		return false;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public DoubleVar getCharAspect() {
		return charAspect;
	}
	
	
}