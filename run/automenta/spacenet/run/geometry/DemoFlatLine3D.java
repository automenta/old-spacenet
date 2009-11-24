package automenta.spacenet.run.geometry;

import automenta.spacenet.Starts;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.FlatLine3D;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector3;

public class DemoFlatLine3D extends Box implements Starts {

    @Override public void start() {

        double s = 0.5;
        double r = 0.05;

        Box box = new Box();

//		for (double x : new double[] { 0, 1 } ) {
//			for (double y : new double[] { 0, 1 } ) {
//				for (double z : new double[] { 0, 1 } ) {
//					if ((x==0) && (y==0) && (z == 0))
//						continue;
//					Vector3 a = new Vector3(0, 0, 0);
//					Vector3 b = new Vector3(x*s,y*s,z*s);
//					FlatLine3D fl = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
//					fl.surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5)));
//				}
//			}			
//		}

        addAxes(box, r);

//        {
//            Vector3 a = new Vector3(0, 0, 0);
//
//            final Vector3 b = new Vector3(1, 0, 0);
//            FlatLine3D fb = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
//            fb.color( Color.newRandomHSB(0.5, 0.5) );
//
//            final Vector3 c = new Vector3(0, 1, 0);
//            FlatLine3D fc = box.add(new FlatLine3D(a, c, new DoubleVar(r)));
//            fc.color( Color.newRandomHSB(0.5, 0.5) );
//
//            add(new Repeat() {
//                @Override public double repeat(double t, double dt) {
//                    b.set( Math.cos(t), Math.sin(t), 0);
//                    c.set( 0, Math.sin(t), Math.cos(t) );
//                    return 0.1;
//                }
//            });
//        }


        add(new GeometryViewer(box));
    }

    @Override public void stop() {
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new DemoFlatLine3D().scale(4));
    }

    private void addT1(Box box, double r) {
        {
            Vector3 a = new Vector3(-0.5, -0.5, 0);
            Vector3 b = new Vector3(0.5, 0.5, 0);
            FlatLine3D fl = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
            fl.surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5)));
        }

        {
            Vector3 a = new Vector3(0.5, -0.5, 0);
            Vector3 b = new Vector3(-0.5, 0.5, 0);
            FlatLine3D fl = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
            fl.surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5)));
        }

        {
            Vector3 a = new Vector3(0, 0, 0);
            Vector3 b = new Vector3(0, 0, 0.5);
            FlatLine3D fl = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
            fl.surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5)));
        }

        {
            Vector3 a = new Vector3(0, 0, 0);
            Vector3 b = new Vector3(0, 0, -0.5);
            FlatLine3D fl = box.add(new FlatLine3D(a, b, new DoubleVar(r)));
            fl.surface(new ColorSurface(Color.newRandomHSB(0.5, 0.5)));
        }
    }

    private void addAxes(Box box, double r) {
        {
            Vector3 a = new Vector3(0, 0, 0);

            box.add(new FlatLine3D(a, new Vector3(1, 0, 0), new DoubleVar(r))).color(Color.Red);
            box.add(new FlatLine3D(a, new Vector3(0, 1, 0), new DoubleVar(r))).color(Color.Green);
            box.add(new FlatLine3D(a, new Vector3(0, 0, 1), new DoubleVar(r))).color(Color.Blue);

            box.add(new FlatLine3D(a, new Vector3(-1, 0, 0), new DoubleVar(r))).color(Color.Purple);
            box.add(new FlatLine3D(a, new Vector3(0, -1, 0), new DoubleVar(r))).color(Color.Orange);
            box.add(new FlatLine3D(a, new Vector3(0, 0, -1), new DoubleVar(r))).color(Color.Yellow);
        }
    }
}
