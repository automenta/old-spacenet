package automenta.spacenet.run.widget;

import automenta.spacenet.os.SkyUtil;

public class DemoSubLayout extends SkyUtil {

//	double b = 0.2;
//
//	public class RecursePanel extends Panel {
//		private int l;
//
//		public RecursePanel(int l) {
//			this.l = l;
//
//		}
//
//		@Override
//		public void start(Scope c) {
//			super.start(c);
//
//			getPosition().setZ(getPosition().z() + 0.1);
//
//			if (l == 0) {
//				addAll(new Button("C").inside(RectPosition.Center, 0.5, 0.25),
//						   new Button("SW").inside(RectPosition.SW, b, b),
//						   new Button("NW").inside(RectPosition.NW, b, b),
//						   new Button("NE").inside(RectPosition.NE, b, b),
//						   new Button("SE").inside(RectPosition.SE, b, b),
//						   new Button("S").inside(RectPosition.S, b, b),
//						   new Button("W").inside(RectPosition.W, b, b),
//						   new Button("N").inside(RectPosition.N, b, b),
//						   new Button("E").inside(RectPosition.E, b, b));
//			}
//			else {
//				addAll(
//						   new RecursePanel(l-1).inside(RectPosition.S, b*2, b),
//						   new RecursePanel(l-1).inside(RectPosition.W, b, b*2),
//						   new RecursePanel(l-1).inside(RectPosition.N, b*2, b),
//						   new RecursePanel(l-1).inside(RectPosition.E, b, b*2),
//						   new RecursePanel(l-1).inside(RectPosition.Center, b*2, b*2)
//				);
//			}
//
//			((ColorSurface)getBackSurface()).getColor().set(Color.newRandomHSB(0.5, 1.0).alpha(0.25));
//
//		}
//
//	}
//
//	@Override public void run() {
//
//		add(new Window().move(0.5, 0.5));
//		add(new Window().addAll(new RecursePanel(2).scale(0.8,0.8)));
//
//	}
//
//	@Override protected Surface getMainRectSurface() {
//		return DemoGridShader.newGridSurface();
//	}
//
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoSubLayout());
//	}
}
