package automenta.spacenet.space.geom2;


import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.geom3.Curve3D;


public class TubeBorder /*extends Curve3D implements Starts, Stops*/ {

//	private Rect rect;
//	private DoubleVar thickStart;
//	private DoubleVar thickEnd;
//
//
//	public TubeBorder(Rect r, double thickStart, double thickEnd) {
//		super(thickEnd - thickStart, 3);
//
//		this.rect = r;
//		this.thickStart = new DoubleVar(thickStart);
//		this.thickEnd = new DoubleVar(thickEnd);
//		getClosed().set(true);
//
//		getTangible().set(false);
//	}
//
//	//	@Override public Vector3 getPosition() {
//	//		return rect.getPosition();
//	//	}
//	//
//	//	@Override public Vector3 getOrientation() {
//	//		return rect.getOrientation();
//	//	}
//
//	@Override public void start() {
//		updateBorder();
//	}
//
//	private void updateBorder() {
//		double cx = 0;
//		double cy = 0;
//		double cz = 0;
//
//		double w = (1.0 + getThickCenter()/2.0);
//		double h = (1.0 + getThickCenter()/2.0);
//
//		double t = getThickness();
//
//
//
//		getPoints().startBatching(); {
//			createPoints(cx, cy, cz, w, h, t);
//		}
//		getPoints().stopBatching();
//
//	}
//
//	private void createPoints(double cx, double cy, double cz, double w, double h, double t) {
//		int p = 0;
//		w/=2;
//		h/=2;
//		double tw = t/2 * (w / (w+h));
//		double th = t/2 * (h / (w+h));
//
//		double x;
//		double y;
//
//
//		x = cx - w;
//		y = cy - h;
//		getPoints().add(new Vector3(x - tw, y, cz));
//		getPoints().add(new Vector3(x, y - th, cz));
//
//		x = cx + w;
//		y = cy - h;
//		getPoints().add(new Vector3(x, y - th, cz));
//		getPoints().add(new Vector3(x + tw, y, cz));
//
//		x = cx + w;
//		y = cy + h;
//		getPoints().add(new Vector3(x + tw, y, cz));
//		getPoints().add(new Vector3(x, y + th, cz));
//
//		x = cx - w;
//		y = cy + h;
//		getPoints().add(new Vector3(x, y + th, cz));
//		getPoints().add(new Vector3(x - tw, y, cz));
//
//		x = cx - w;
//		y = cy - h;
//		getPoints().add(new Vector3(x - th, y, cz));
//		getPoints().add(new Vector3(x, y - th, cz));
//
//	}
//
//
////	private void createPointsOld(double cx, double cy, double cz, double w, double h) {
////		int p = 0;
////		getPoints().add(new Vector3(cx - w/2, cy + h/2, cz); {
//////			getPoints().get(1).set(cx - w/2 - t, cy + h/2 - t, cz);
//////			getPoints().get(2).set(cx - w/2 - t, cy - h/2 + t, cz);
////		}
////		getPoints().add(new Vector3(cx - w/2, cy - h/2, cz); {
//////			getPoints().get(4).set(cx - w/2 + t, cy - h/2 - t, cz);
//////			getPoints().get(5).set(cx + w/2 - t, cy - h/2 - t, cz);
////		}
////		getPoints().add(new Vector3(cx + w/2, cy - h/2, cz); {
//////			getPoints().get(7).set(cx + w/2 + t, cy - h/2 + t, cz);
//////			getPoints().get(8).set(cx + w/2 + t, cy + h/2 - t, cz);
////		}
////		getPoints().add(new Vector3(cx + w/2, cy + h/2, cz); {
//////			getPoints().get(10).set(cx + w/2 - t, cy + h/2 + t, cz);
//////			getPoints().get(11).set(cx - w/2 + t, cy + h/2 + t, cz);
////		}
////		getPoints().add(new Vector3(cx - w/2, cy + h/2, cz);
////
////	}
//
//	private double getThickness() {
//		return thickEnd.d() - thickStart.d();
//	}
//
//	private double getThickCenter() {
//		return 0.5 * (thickStart.d() + thickEnd.d());
//	}
//
//	@Override public void stop() {
//
//	}
//
//	@Override public boolean isSpline() {
//		return false;
//	}
//
//	@Override public Line getCrossSection() {
//		//quarter circle
//		Line l = newArc(getRadius().f()*2.0f, (float)(0.5 * Math.PI), (float)(1.0 * Math.PI), 0, 0, getNumEdges(), false);
//		return l;
//
//	}
//
	
}
