package automenta.spacenet.space.geom2;

import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.StringVar;

public class TextLineRect extends Rect {

	private final StringVar text;
	private final VectorFont font;
	
	private final TextType textType;
	
	
	public static enum TextType {
		Vector, Bitmap
	}

	public TextLineRect(StringVar text, VectorFont font, TextType textType) {
		super();
		this.text = text;
		this.font = font;
		this.textType = textType;
	}

	public TextLineRect(StringVar text, VectorFont font) {
		this(text, font, TextType.Vector);
	}
	
	public StringVar getText() {
		return text;
	}
	public VectorFont getFont() {
		return font;
	}

	public TextType getType() {
		return textType;
	}
	
	
}
