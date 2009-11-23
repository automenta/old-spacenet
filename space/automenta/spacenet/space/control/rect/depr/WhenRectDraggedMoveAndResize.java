/**
 * 
 */
package automenta.spacenet.space.control.rect.depr;


public class WhenRectDraggedMoveAndResize /*extends RectDragging*/ {
//		private DragMode dragMode = null;
//		final String moveAndResize = "moveAndResize";
//		protected Vector3 dragStart = new Vector3();
//		protected Vector3 dragDelta = new Vector3();
//
//		private Vector2 dragLocalDelta = new Vector2();
//		private Vector2 lastDragLocalXY = new Vector2();
//		private Vector2 startDragLocal = new Vector2();
//		private Vector2 startSize = new Vector2();
//
//		protected Vector3 startPos = new Vector3();
//		protected Vector3 startAbsPos = new Vector3();
//		private Ray pickRay = new Ray();
//		//private Triangle draggedTriangle;
//		private fVector3 triIntersectF = new fVector3();
//		private Vector3 intersectPoint = new Vector3();
//		private Plane dragPlane = new Plane();
//		private double centralProportion;
//		private Video3D sight;
//
//
//		public WhenRectDraggedMoveAndResize(Rect rect, Pointer cursor, Video3D sight, PointerButton cursorButton, double centralProportion) {
//			super(rect, cursor, cursorButton);
//			this.sight = sight;
//			this.centralProportion = centralProportion;
//		}
//
//		@Override protected void whenDragStarts(Vector2 dragStart) {
//			this.dragStart.set(cursor.getTriangleIntersection());
//			startPos.set(getRect().getPosition());
//			startAbsPos.set(getRect().getAbsolutePosition());
//
//			dragPlane.setPlanePoints(cursor.getTouchedTriangle());
//
//			startSize.set(getRect().getSize());
//			startDragLocal.set(dragStart);
//			lastDragLocalXY.set(dragStart);
//
//			dragMode = getDragMode(dragStart, 1.0 - centralProportion);
//
//		}
//
//		@Override protected void whenDragStops(Vector2 dragStop2) {
//			dragMode = null;
//		}
//
//		@Override protected void whenDragging(Vector2 dragRectXY) {
//			pickRay.setOrigin(Maths.toFloat(sight.getPosition()));
//			pickRay.setDirection(Maths.toFloat(cursor.getTouchDirection()));
//
//			pickRay.intersectsWherePlane(dragPlane, triIntersectF);
//			Maths.toDouble(triIntersectF, intersectPoint);
//
//			this.dragDelta.set(intersectPoint);
//			dragDelta.subtract(dragStart);
//
//			dragLocalDelta.set(dragRectXY);
//			dragLocalDelta.subtract(startDragLocal);
//
//			lastDragLocalXY.set(dragRectXY);
//
//
//			if (dragMode == DragMode.DragMove) {
//				whenDragMove(dragRectXY, dragDelta);
//			}
//			else {
//				whenDragResize(dragRectXY, dragDelta, dragMode);
//			}
//
//		}
//
//		protected void whenDragResize(Vector2 dragRectXY, Vector3 dragDelta, DragMode dragMode) {
//			//TODO this is not yet correct for absolute coordinates, and sub-spaces
//			getRect().getPosition().set(startPos);
//			getRect().getSize().set(startSize);
//			double dx = dragDelta.x() * getRect().getAbsoluteSize().x() / getRect().getSize().x();
//			double dy = dragDelta.y() * getRect().getAbsoluteSize().y() / getRect().getSize().y();
//
//			double sx = startPos.x();
//			double sy = startPos.y();
//			double sz = startPos.z();
//
//			if (dragMode == DragMode.DragResizeNW) {
//				getRect().getSize().add(-dx*2, dy*2);
//				getRect().getPosition().set(sx + dx, sy + dy, sz);
//			}
//			else if (dragMode == DragMode.DragResizeNE) {
//				getRect().getSize().add(dx*2, dy*2);
//				getRect().getPosition().set(sx+ dx, sy + dy, sz);
//			}
//			else if (dragMode == DragMode.DragResizeSW) {
//				getRect().getSize().add(-dx*2, -dy*2);
//				getRect().getPosition().set(sx + dx, sy + dy, sz);
//			}
//			else if (dragMode == DragMode.DragResizeSE) {
//				getRect().getSize().add(dx*2, -dy*2);
//				getRect().getPosition().set(sx + dx, sy + dy, sz);
//			}
//
//		}
////		protected void whenDragResize(Vector2 dragRectXY, Vector2 dragDelta, DragMode dragMode) {
////			System.out.println("drag reszie");
////			if (dragMode == DragMode.DragResizeNW) {
////				//double factor =  getAbsoluteSize().getMagnitude() / getSize().getMagnitude();
////				//System.out.println(getSize() + " " + getAbsoluteSize());
////				double dx = dragDelta.x();
////				double dy = dragDelta.y();
////				getRect().getSize().set(startSize);
////				getRect().getSize().add(-dx*4, dy*4);
////				getRect().getPosition().set(startPos);
////				getRect().getPosition().add(dx, dy, 0);
////			}
////		}
//
//		protected void whenDragMove(Vector2 dragRectXY, Vector3 dragDelta) {
//			double x = startAbsPos.x() + dragDelta.x();
//			double y = startAbsPos.x() + dragDelta.y();
//			double z = startAbsPos.z();
//			getRect().setNextAbsolutePosition(x,y,z);
//		}
//
//		static protected DragMode getDragMode(Vector2 v, double resizeBorder) {
//			double x = v.x() * 2;	//upscale from 0.5 to 1.0 range
//			double y = v.y() * 2;
//
//			double p = resizeBorder;
//
//			int dx, dy;
//			if (x > 1.0 - p)		dx = 1;	//right
//			else if (x < -1.0 + p)	dx = -1; //left
//			else					dx = 0; //center
//			if (y > 1.0 - p)		dy = 1;	//right
//			else if (y < -1.0 + p)	dy = -1; //left
//			else					dy = 0; //center
//
//			if ((dx == -1) && (dy == -1)) return DragMode.DragResizeSW;
//			else if ((dx == -1) && (dy == 1)) return DragMode.DragResizeNW;
//			else if ((dx == 1) && (dy == 1)) return DragMode.DragResizeNE;
//			else if ((dx == 1) && (dy == -1)) return DragMode.DragResizeSE;
//			else /*if ((dx == 0) && (dy == 0))*/ {
//				//Central
//				return DragMode.DragMove;
//			}
//		}
//
}