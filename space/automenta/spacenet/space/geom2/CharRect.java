package automenta.spacenet.space.geom2;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class CharRect extends Rect {
	private Character character;
	private VectorFont font;
	
	public CharRect(Character character, VectorFont font) {
		this(character, font, (Surface)null);
	}
	
	public CharRect(Character character, VectorFont font, Color charColor) {
		this(character, font, new ColorSurface(charColor));
	}
	
	public CharRect(Character character, VectorFont font, Surface surface) {
		this(new Vector3(), new Vector2(1,1), character, font, surface);
	}

	public CharRect(Vector3 position, Vector2 size, Character character, VectorFont font, Surface surface) {
		super(position, size, new Vector3());
		this.character = character;
		this.font = font;
		
		surface(surface);
		
		tangible(false);
		
	}
	
	public Character getCharacter() {
		return character;
	}
	public VectorFont getFont() {
		return font;
	}

}
