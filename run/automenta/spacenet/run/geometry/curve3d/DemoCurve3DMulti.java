package automenta.spacenet.run.geometry.curve3d;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.collection.ArrangeGrid;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoCurve3DMulti extends ProcessBox {

	final static double velocity = 0.01;
	
	public static class RandomCurve extends Box implements StartsIn<Scope> {

		private int numPoints;
		public RandomCurve(int numPoints) {
			super();
			this.numPoints = numPoints;
		}
		
		public void stop() {}
		
		@Override public void start(Scope c) {
			final ListVar<Vector3> points = new ListVar();
			for (int i = 0; i < numPoints; i++) {
				points.add(Vector3.newRandom(-0.5, 0.5));				
			}
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					/*points.startBatching();*/ {
						for (int i = 0; i < numPoints; i++) {
							double nx = points.get(i).x() + DoubleVar.random(-velocity, velocity);
							double ny = points.get(i).y() + DoubleVar.random(-velocity, velocity);
							double nz = points.get(i).z() + DoubleVar.random(-velocity, velocity);
							points.get(i).set(nx, ny, nz);
						}
					}
					/*points.stopBatching();*/
					return 0;
				}				
			});
			
			StringVar t = new StringVar("P: " + numPoints);
			VectorFont font = getThe(VectorFont.class);
			add(new TextRect(t, font, 16).scale(0.5, 0.5).move(0,-0.5));
			add(new Curve3D(points, new DoubleVar(0.05), 3, 2).color(Color.Orange).move(0, 0, 0));
			add(new Curve3D(points, new DoubleVar(0.05), 4, 2).color(Color.Purple).move(0, 0, -1));
			add(new Curve3D(points, new DoubleVar(0.05), 10, 4).move(0, 0, -2));
			add(new Curve3D(points, new DoubleVar(0.05), 4, 2, true).move(0, 0, -3));
		}
		
	}
	
	@Override
	public void run() {
		for (int i = 2; i < 8; i++) {
			add(new RandomCurve(i));
		}
		add(new ArrangeGrid(this, 0.5,0));
	}
	
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoCurve3DMulti().scale(20));
	}
}
