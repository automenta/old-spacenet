package automenta.spacenet.run.geometry.curve3d;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.geom3.Sketch;
import automenta.spacenet.space.object.measure.GridRect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.Vector3;

public class DemoCurve3DEdit extends ProcessBox {

	public static class Curve3DEdit extends Curve3D implements Starts, Stops {
		
		private Map<Vector3, Space> vectorControls = new HashMap();
		
		private Sketch sketch;

		public Curve3DEdit(Sketch sketch, DoubleVar radius, int numEdges, int numSegments, boolean closed) {
			super(sketch, radius, numEdges, numSegments, closed);
			this.sketch = sketch;
			
		}
		
		
		@Override public void start() {
			add(new IfCollectionChanges(sketch) {

				@Override public void afterObjectsAdded(CollectionVar list, Object[] added) {
					for (Object v : added) {
						addControlPoint((Vector3)v);
					}
				}

				@Override public void afterObjectsRemoved(CollectionVar list, Object[] removed) {
					for (Object v : removed) {
						removeControlPoint((Vector3)v);
					}					
				}
				
			});
		}
		
		@Override public void stop() {
			vectorControls.clear();
		}

		protected void addControlPoint(Vector3 v) {
			double s = getControlPointRadius();
			
			Box b = new Box(v, new Vector3(s, s, s), new Vector3());			
			Window w = new Window(b);
			add(w);
			
			vectorControls.put(v, w);
		}
		
		private double getControlPointRadius() {
			return getRadius().d() * 4.0;
		}

		protected void removeControlPoint(Vector3 v) {
			Space so = vectorControls.get(v);
			if (so!=null) {
				vectorControls.remove(v);
				remove(so);
			}
		}
	}
	
	public static class DemoCurveWindow extends Rect implements Starts, Stops {
		private int numPoints;
		private boolean closed;

		public DemoCurveWindow(int numPoints, boolean closed) {
			super();
			tangible(false);
			this.numPoints = numPoints;
			this.closed = closed;
		}
		
		@Override
		public void start() {
			Sketch s = new Sketch();
			for (int i = 0; i < numPoints; i++) {
				double v = ((double)i) / ((double)numPoints);
				
				double nx = Math.cos( v * Math.PI*2 ) * (0.5 * v);
				double ny = Math.sin( v * Math.PI*2) * (0.5 * v);
				s.add(nx, ny);
			}

			double dz = 0.1;

			GridRect grid = add(new GridRect(Color.Invisible, Color.Gray, 8, 8, 0.1));
			grid.tangible(false);

			DoubleVar radius = new DoubleVar(0.02);
			int numEdges = 8;
			int numSegments = 12;
			
			final Curve3DEdit curve = add(new Curve3DEdit(s, radius, numEdges, numSegments, closed));
			curve.color(Color.Orange);
			curve.moveDelta(0,0,dz);
			curve.tangible(false);
			
			add(new Slider(radius, new DoubleVar(0.005), new DoubleVar(0.25), 0.1, SliderType.Vertical).span(0.5, -0.25, 0.6, 0.25));

			final DoubleVar edges = new DoubleVar(numEdges);
			add(new Slider(edges, new DoubleVar(2), new DoubleVar(16), 0.1, SliderType.Vertical).span(0.7, -0.25, 0.8, 0.25));
			
			add(new IfDoubleChanges(edges) {
				@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
					curve.getNumEdges().set(edges.i());
				}			
			});
		}
		
		@Override	public void stop() {	}
		
	}
	
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoCurve3DEdit());
	}

	@Override public void run() {
//		add(new DemoCurveWindow(2, false).move(2, 2));
//		add(new DemoCurveWindow(3, false).move(2, 1));
//		
		add(new DemoCurveWindow(7, false));
//		add(new DemoCurveWindow(7, true).move(-2, 2));

//		add(new DemoCurveWindow(4, false).move(-2, -1));
//		add(new DemoCurveWindow(4, true).move(-2, -2));
	}
}
