package automenta.spacenet.space.object.widget.icon;

import automenta.spacenet.UURI;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Represents;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.text.TextRect3;
import automenta.spacenet.space.object.widget.button.IconButton;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;

abstract public class AbstractIcon extends Window implements Represents {

	private Object object;
	private StringVar iconName;

	private Vector3 startPosition = new Vector3();
	protected HasPosition3 glyph;
	protected TextRect text;
	private UURI imageURL;

	public AbstractIcon(Object object) {
		this(object, object.toString());
	}

	public AbstractIcon(Object object, String name) {
		super();
		
		if (object instanceof Found) {
			Found f = (Found) object;
			this.object = f.getObject();
			this.iconName = new StringVar(f.getName());
			
			String url = f.getUURI().toString();
			if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".bmp")) {
				this.imageURL = f.getUURI();
			}
			
		}
		else {
			this.object = object;
			this.iconName = new StringVar(name);
		}
	}
 	
	@Override public void start() {
		super.start();
	
		tangible(false);
		
		if (imageURL!=null) {
			glyph = add(new Rect(new BitmapSurface(imageURL)));
		}
		else {
			glyph = add(new IconButton("icon.magic"));
		}
		
		text = add(new TextRect(getIconName()));
		
		
		layoutIcon();
	}
	abstract protected void layoutIcon();
	

	public StringVar getIconName() {
		return iconName;
	}
	

	@Override
	protected void startDrag(Pointer c, Drag drag) {
		this.startPosition.set(getPosition());
		
		super.startDrag(c, drag);
	}
	
	@Override
	protected void stopDrag(Pointer c, Drag drag) {
		super.stopDrag(c, drag);
		
		getPosition().set(startPosition);
	}

	/** the object associated with this icon */
	public Object getObject() {
		return object;
	}

}
