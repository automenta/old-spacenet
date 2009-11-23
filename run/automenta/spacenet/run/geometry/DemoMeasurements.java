package automenta.spacenet.run.geometry;

public class DemoMeasurements  {

//	int num = 4;
//	double maxWidth = 10.0;
//
//	@Override public void start(Scope context) {
//		super.start(context);
//
//
//		Vector3 a = new Vector3(0,0,0);
//		Vector3 b = new Vector3(10,0,0);
//		Vector3 c = new Vector3(0,10,0);
//		final Vector3 d = new Vector3(-3,10,0);
//
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				double a = 10+Math.cos(t*3);
//				d.set(a*Math.sin(t), a*Math.cos(t), 0);
//				return 0;
//			}
//		});
//		add(new MeasureLine(a, b));
//		add(new MeasureLine(a, c));
//		add(new MeasureLine(a, d));
//
//
//		addGrid(10, 2, 0);
//
////		RectSpace cp = add(new MeasureRect(5,5,-0.5,0.1,0.1));
////		cp.add(new MeasureRect(0.5, 0.5, 0.5, 0.5));
//
//	}
//
//	private void addGrid(double maxWidth, int num, double z) {
//		for (int i = 0; i < num; i++) {
//			for (int j = 0; j < num; j++) {
//				double s = maxWidth / num;
//				double x = i * s * 2;
//				double y = j * s * 2;
//				add(new MeasureRect(x,y,z,s,s)).add(new MeasureRect(0.5,0.5,0,0.25,0.25));
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoMeasurements());
//	}
}
