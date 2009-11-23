package automenta.spacenet.space.object.widget.button;

import automenta.spacenet.UURI;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.extern.ThreeDSMaxSpace;
import automenta.spacenet.space.surface.BitmapSurface;

public class IconButton extends Button {

	private String iconID;
	private UURI iconURI;

	public IconButton(String iconID) {
		this(iconID, false);
	}

	public IconButton(String iconID, boolean withBacking) {
		super(iconID);
		this.iconID = iconID;
		this.withBacking = withBacking;
	}
	
	public IconButton(UURI icon) {
		super(icon.toString());
		this.iconURI = icon;
		this.withBacking = false;
	}

	double dz = 0.01;
	private Space iconMesh;
	
	@Override protected void initButton() {
		
		if (iconURI == null) {
			iconURI = getMedia().get(iconID);
		}
		
		if (iconURI!=null) {
			if (iconURI.toString().endsWith(".3ds") || iconURI.toString().endsWith(".x3d")) {
				iconMesh = getPanelRect().add(new ThreeDSMaxSpace(iconURI).move(0,0,dz));
			}
			else if (iconURI.toString().endsWith(".png") || iconURI.toString().endsWith(".jpg") || iconURI.toString().endsWith(".bmp")) {
				iconMesh = getPanelRect().add(new Rect(new BitmapSurface(iconURI)));
			}
		}
	}


	@Override
	public Space getTouchable() {
		return iconMesh;
	}
	

}
