package automenta.spacenet.space.object.widget.text;

import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.string.StringVar;

public interface TextContainer {

	public StringVar getText();

	public Rect getCharacterRect(int start);
}
