package automenta.spacenet.space.geom2;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.vector.Vector2;



public class OverlayRect extends Rect implements StartsIn<Scope>, Stops {

	private Vector2 screenPixelSize = new Vector2(1,1);

	public OverlayRect() {
		super();
	}

	public OverlayRect(Color c) {
		super(c);		
	}

	public Vector2 getScreenPixelSize() { return screenPixelSize; }

	@Override public void start(Scope c) {
		final Video3D video = c.getThe(Video3D.class);
		
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				getPosition().set(video.getPosition());
//
//				return 0;
//			}
//		});
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}


}
