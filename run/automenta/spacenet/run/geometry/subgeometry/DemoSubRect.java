package automenta.spacenet.run.geometry.subgeometry;

import automenta.spacenet.Scope;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;

public class DemoSubRect extends Rect {

//	double s = 10.0;
//
//	@Override
//	public void whenStarted(final Scope superNode) {
//
//		final Rect q = (Rect)whenStarted(new Rect(0,0,1,1, new ColorSurface(Color.Blue)));
//
//		final Rect r = (Rect)whenStarted(new Rect(1,1,1,1, new ColorSurface(Color.Red)));
//
//		final Rect t = (Rect)whenStarted(new Rect(this, -1,-1,1,1, new ColorSurface(Color.Green)));
//
//
//		whenStarted(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				getSize().set(s * (Math.cos(t)+1.0)+1.5, s * (Math.sin(t)+1.0)+1.5);
//				return 0.0;
//			}
//		});
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoSubRect());
//	}
}
