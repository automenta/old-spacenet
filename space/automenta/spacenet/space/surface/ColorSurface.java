package automenta.spacenet.space.surface;

import automenta.spacenet.space.Color;
import automenta.spacenet.space.Surface;

//@IncompleteFeature("ambient, shininess, emissive, and specular as variables in ColorSurface")
public class ColorSurface implements Surface {

	private final Color color;

	public ColorSurface(Color c) {
		super();
		this.color = c;
	}
	
	public Color getColor() { return color; }

	
}
