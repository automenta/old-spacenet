package automenta.spacenet.os;

import automenta.spacenet.Starts;
import automenta.spacenet.os.widget.DraggableBackground;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Sketching;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.geom3.Sketch;
import automenta.spacenet.space.object.measure.GridDotRect;
import automenta.spacenet.space.object.measure.GridRect;
import automenta.spacenet.space.object.widget.window.Window;
import automenta.spacenet.space.dynamic.physics.PhyBox;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** self-contained 2D surface upon which windows can be created, and re-positioned */
public class ObjectCanvas extends Box implements Starts {
	//TODO derive a subclass that has physics features, leaving ObjectCanvas without physics support
	
	//TODO remove physics rects when windows are destroyed. maybe involve Space.dispose() method

	double w = 12;
	double h = 8;
	//private PhySpace phy;

	Color gridColor = new Color(Color.GrayMinusMinusMinusMinus);
	private Rect dragBackground;
	

	
	@Override public void start() {
		
		
		dragBackground = add(new DraggableBackground() {
			@Override protected void onClicked(Vector3 p) {
				super.onClicked(p);

				//ObjectCanvas.this.add(new ObjectWindow(new StringVar(""), os.actions(), os.views(), os.linker()).move(p.x(), p.y(), 0).scale(1.0, 0.5));
			}

			@Override public void onDraggableDropped(Pointer p, Draggable d, Vector2 localPosition) {
				super.onDraggableDropped(p, d, localPosition);

				//HACK
				localPosition.set(p.getTriangleIntersection().x(), p.getTriangleIntersection().y());

				Object o = getRepresentedObject((Space)d);

//				if (o!=null) {
//					ObjectCanvas.this.add(newObjectWindow(o)).move(localPosition.x(), localPosition.y(), 0);
//				}

			}

		}.scale(w, h).moveDelta(0,0, -1.25) );


		add(new Sketching(new DoubleVar(4)) {

			private Curve3D currentSketch;

			@Override public void startSketch(Sketch k) {
				if (currentSketch!=null) {
					ObjectCanvas.this.remove(currentSketch);
					currentSketch = null;
				}

				double dz = 0;

				currentSketch = ObjectCanvas.this.add(new Curve3D(k, new DoubleVar(0.05), 3, 4, false));
				currentSketch.color(Color.Orange);
			}


			@Override public void stopSketch(Sketch s) {
				//				System.out.println(s);
				//				System.out.println(" " + s.getStartNode());
				//				System.out.println(" " + s.getStopNode());
			}			
		});


        addGridBackground();

	}

	public void addDotBackground() {
		double gridDZ = 1.2;
		double gridSize = 32;
		double gridAspect = 1.0 / 1.6;

		double gridDensity = 0.9;

		dragBackground.add(new GridDotRect(new ColorSurface(Color.Invisible), new ColorSurface(gridColor), new Vector2(gridSize * gridDensity, gridSize * gridAspect * gridDensity), new DoubleVar(0.15)).
					moveDelta(0,0,-gridDZ)).tangible(false);
		
		
	}
	
	public void addGridBackground() {
		double gridDZ = 1.2;
		double gridSize = 32;
		double gridAspect = 1.0 / 1.6;

		double gridDensity = 0.5;

		dragBackground.add(new GridRect(Color.Invisible, gridColor, gridSize * gridDensity, gridSize * gridAspect * gridDensity, 0.05).moveDelta(0,0,-gridDZ)).tangible(false);
	}


	public void removeWindow(PhyBox b, Window w) {
		//phy.removeBox(b);
	}

//	public ObjectWindow newObjectWindow(final Object o) {
//		//final Box box = newPhysicsBox();
//		final Box box = new Box();
//		return newObjectWindow(o, box);
//	}
	
//	public ObjectWindow newObjectWindow(final Object o, final Box box) {
//		return new ObjectWindow(box, o, os.actions(), os.views(), os.linker()) {
//			@Override
//			public void hide() {
//				super.hide();
//				box.dispose();
//
////				if (o instanceof Scope) {
////					Scope s = (Scope) o;
////					s.getParent().remove(o);
////				}
//			}
//
//		};
//	}


	@Override public void stop() {
	}

}
