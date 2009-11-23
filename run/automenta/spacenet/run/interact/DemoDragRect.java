package automenta.spacenet.run.interact;

import org.apache.log4j.Logger;

import automenta.spacenet.Maths;
import automenta.spacenet.plugin.java.ObjectSnapshot;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.dynamic.FaceVector;
import automenta.spacenet.space.dynamic.LockZPosition;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.MapTable;
import automenta.spacenet.space.dynamic.physics.PhySpace;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.vector.Vector3;


/** move and resize a rect with arbitrary orientation and space-embedded-ness */
public class DemoDragRect extends ProcessBox {

	private int numRects = 14;
	ObjectSnapshot dragSnap = new ObjectSnapshot();

	
	public class DraggableRectSurface extends Rect implements Draggable {
		private final Logger logger = Logger.getLogger(DraggableRectSurface.class);
		
		private Vector3 dragStartRelative = new Vector3();

		private double dragStartZ;

		public DraggableRectSurface() {
			super();
			
			getSurface().set(new ColorSurface(Color.newRandom().alpha(0.5)));
		}
		
		@Override public void startDrag(Pointer c, Drag drag) {
			logger.info("drag start");
			this.dragStartZ = getAbsolutePosition().z();
			this.dragStartRelative.set(getAbsolutePosition());
			this.dragStartRelative.subtract(drag.getMeshIntersectStart());
		}

		@Override public void stopDrag(Pointer c, Drag drag) {
			logger.info("drag stop");			
		}

		@Override public void updateDrag(Pointer c, Drag drag) {
			//logger.info("drag abs " + absPos);									
			//logger.info("  drag rect rel " + rectPos);

			double px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
			double py = drag.getRealPositionCurrent().y() + dragStartRelative.y();
			double pz = dragStartZ;
			
			setNextAbsolutePosition(px, py, pz);

			dragSnap.put(drag, true);
		}
		
	}
	
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoDragRect());		
	}

	
	@Override
	public void run() {
		MapTable mapTable = add(new MapTable(dragSnap));
		mapTable.scale(10.0);
		mapTable.tangible(false);
		
		PhySpace space = add(new PhySpace());
		
		//space.getTimeRate().set(0.001);
		
		for (int i = 0; i < numRects; i++) {

			
			DraggableRectSurface r = add(new DraggableRectSurface());
			
			double x = Maths.random(-15, 15);
			double y = Maths.random(-14, 14);
			double w = Maths.random(2.0, 2.5);
			double h = Maths.random(1.0, 1.5);
			r.move(x, y);
			r.size(w, h);
			
			r.add(new FaceVector(r, new Vector3(0,0,1)));
			r.add(new LockZPosition(0));
			
//			final PhyBox pb = space.newBox(r, 1.0);
//			pb.add(new Box(Color.Invisible));			

		}
		
	}
}
