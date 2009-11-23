/**
 * 
 */
package automenta.spacenet.space.object.widget.window;


import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

/** Rectangle that can be optionally moved and resized */
public class Window extends Panel {

	private Pointer cursor;
	private Video3D sight;
	private BooleanVar beingDragged = new BooleanVar(false);
	private Rect dragCue;

	public static enum DragMode { ResizeNE, ResizeSW, ResizeSE, ResizeNW, Move };

	public class WindowBevel extends PanelRect implements Draggable, Touchable {


		private Vector2 uv = new Vector2();

		private double dragStartZ;
		private Vector3 dragStartAbs = new Vector3();
		private Vector3 dragStartRelative = new Vector3();
		private Vector3 dragStartPosition = new Vector3();
		private Vector2 dragStartSize = new Vector2();
		private DragMode dragMode;

		private boolean dragging;

		private double alX;

		private double alY;

		private Vector2 dragStartAbsSize = new Vector2();

		public WindowBevel(Panel p, Surface surface) {
			super(p, surface);
		}

		@Override public void startTouch(Pointer c) {
			super.startTouch(c);
			updateDragCue(c);
		}

		@Override public void stopTouch(Pointer c) {
			super.stopTouch(c);
			removeDragCue();
		}

		@Override
		public void updateTouch(Pointer c, double timeTouched) {
			super.updateTouch(c, timeTouched);

			if (!dragging)
				updateDragCue(c);
		}


		private void removeDragCue() {
			dragCue.getSize().set(0,0);
		}

		private void updateDragCue(Pointer c) {
			c.getQuadIntersection(this, uv);

			double w = windowResizeEdgeWidth.d();
			double hw = w / 2.0;
			
			if (uv.x() > 0.5 - w) {
				if (uv.y() > 0.5 - w) {
					dragMode = DragMode.ResizeNE;
					dragCue.span(0.5 - hw, 0.5 - hw, 0.5, 0.5);
					return;
				}
				else if (uv.y() < -0.5 + w) {
					dragMode = DragMode.ResizeSE;					
					dragCue.span(0.5 - hw, -0.5 + hw, 0.5, -0.5);
					return;
				}
			}
			else if (uv.x() < -0.5 + w) {
				if (uv.y() > 0.5 - w) {
					dragMode = DragMode.ResizeNW;					
					dragCue.span(-0.5 + hw, 0.5 - hw, -0.5, 0.5);
					return;
				}
				else if (uv.y() < -0.5 + w) {
					dragMode = DragMode.ResizeSW;										
					dragCue.span(-0.5 + hw, -0.5 + hw, -0.5, -0.5);
					return;
				}				
			}

			dragMode = DragMode.Move;			
			dragCue.getSize().set(0,0);
		}

		@Override public void startDrag(Pointer c, Drag drag) {
			Window.this.startDrag(c, drag);

			updateDragCue(c);

			this.dragStartPosition.set(Window.this.getAbsolutePosition());
			this.dragStartSize.set(Window.this.getSize().x(), Window.this.getSize().y());
			this.dragStartAbsSize .set(getAbsoluteSize());
			this.dragStartZ = Window.this.getAbsolutePosition().z();
			this.dragStartRelative.set(Window.this.getAbsolutePosition());
			this.dragStartRelative.subtract(drag.getMeshIntersectStart());
			this.dragStartAbs.set(drag.getMeshIntersectStart());


			dragging = true;
		}

		@Override public void stopDrag(Pointer c, Drag drag) {
			Window.this.stopDrag(c, drag);
			dragCue.getSize().set(0,0);

			dragging = false;
		}

		double px, py, pz, dx, dy, dxR, dyR;

//		@Override public void updateDrag(Pointer c, Drag drag) {
//
//			pz = dragStartZ;
//
//			if (dragMode == DragMode.Move) {
//				px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
//				py = drag.getRealPositionCurrent().y() + dragStartRelative.y();					
//			}
//			else {
//				px = dragStartPosition.x();
//				py = dragStartPosition.y();
//
//				dx = drag.getRealPositionCurrent().x() - dragStartAbs.x();
//				dy = drag.getRealPositionCurrent().y() - dragStartAbs.y();
//
////				alX = Window.this.getAbsoluteSize().x() / Window.this.getSize().x();
////				alY = Window.this.getAbsoluteSize().y() / Window.this.getSize().y();
////
//////				dxR = dx / alX;
//////				dyR = dy / alY;
//
//
//				if (dragMode == DragMode.ResizeNE) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() + dx/2.0, dragStartAbsSize.y() + dy/2.0, dragStartAbsSize.z());
//				}
//				else if (dragMode == DragMode.ResizeNW) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() - dx/2.0, dragStartAbsSize.y() + dy/2.0, dragStartAbsSize.z());
//				}
//				else if (dragMode == DragMode.ResizeSE) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() + dx/2.0, dragStartAbsSize.y() - dy/2.0, dragStartAbsSize.z());
//				}
//				else if (dragMode == DragMode.ResizeSW) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() - dx/2.0, dragStartAbsSize.y() - dy/2.0, dragStartAbsSize.z());
//				}
//
//
//			}
//
//			Window.this.setNextAbsolutePosition(px, py, pz);
//		}

		@Override public void updateDrag(Pointer c, Drag drag) {

			pz = dragStartZ;

			if (dragMode == DragMode.Move) {
				px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
				py = drag.getRealPositionCurrent().y() + dragStartRelative.y();					
			}
			else {
				px = dragStartPosition.x();
				py = dragStartPosition.y();

				
				dx = drag.getRealPositionCurrent().x() - dragStartAbs.x();
				dy = drag.getRealPositionCurrent().y() - dragStartAbs.y();

				alX = getAbsoluteSize().x() / Window.this.getSize().x();
				alY = getAbsoluteSize().y() / Window.this.getSize().y();

				dxR = dx / alX;
				dyR = dy / alY;
				

				double winZ = Window.this.getSize().z();

				if (dragMode == DragMode.ResizeNE) {
					px += dx/2.0;
					py += dy/2.0;
					Window.this.getSize().set(dragStartSize.x() + dxR/2.0, dragStartSize.y() + dyR/2.0, winZ);
				}
				else if (dragMode == DragMode.ResizeNW) {
					px += dx/2.0;
					py += dy/2.0;
					Window.this.getSize().set(dragStartSize.x() - dxR/2.0, dragStartSize.y() + dyR/2.0, winZ);
				}
				else if (dragMode == DragMode.ResizeSE) {
					px += dx/2.0;
					py += dy/2.0;
					Window.this.getSize().set(dragStartSize.x() + dxR/2.0, dragStartSize.y() - dyR/2.0, winZ);
				}
				else if (dragMode == DragMode.ResizeSW) {
					px += dx/2.0;
					py += dy/2.0;
					Window.this.getSize().set(dragStartSize.x() - dxR/2.0, dragStartSize.y() - dyR/2.0, winZ);
				}


			}

			Window.this.setNextAbsolutePosition(px, py, pz);
		}


	}

	public Window(Box b) {
		super(b.getPosition(), b.getSize(), b.getOrientation());
		tangible(false);
	}


	public Window() {
		super();
		tangible(false);
	}

	protected void startDrag(Pointer c, Drag drag) {
		beingDragged.set(true);
	}

	protected void stopDrag(Pointer c, Drag drag) {
		beingDragged.set(false);
	}

	public BooleanVar getBeingDragged() {
		return beingDragged ;
	}



	@Override protected Rect newPanelRect() {
		return new WindowBevel(this, new ColorSurface(getBackColor()));
	}

	@Override public void start() {
		super.start();
		
		cursor = getThe(Pointer.class);
		sight = getThe(Video3D.class);

		dragCue = add(new Rect(0,0));
		dragCue.color(Color.Red.alpha(0.5));
		dragCue.tangible(false);
	}





}





//
//
///** Rectangle that can be optionally moved and resized */
//public class Window extends Panel {
//
//	private Pointer cursor;
//	private Video3D sight;
//	private BooleanVar beingDragged = new BooleanVar(false);
//	private Rect dragCue;
//
//	public static enum DragMode { ResizeNE, ResizeSW, ResizeSE, ResizeNW, Move };
//
//	public class WindowBevel extends PanelRect implements Draggable, Touchable {
//
//
//		private Vector2 uv = new Vector2();
//
//		private double dragStartZ;
//		private Vector3 dragStartAbs = new Vector3();
//		private Vector3 dragStartRelative = new Vector3();
//		private Vector3 dragStartPosition = new Vector3();
//		private Vector3 dragStartSize = new Vector3();
//		private DragMode dragMode;
//
//		private boolean dragging;
//
//		private double alX;
//
//		private double alY;
//
//		private Vector3 dragStartAbsSize = new Vector3();
//
//		public WindowBevel(Panel p, Surface surface) {
//			super(p, surface);
//		}
//
//		@Override public void startTouch(Pointer c) {
//			super.startTouch(c);
//			updateDragCue(c);
//		}
//
//		@Override public void stopTouch(Pointer c) {
//			super.stopTouch(c);
//			removeDragCue();
//		}
//
//		@Override
//		public void updateTouch(Pointer c, double timeTouched) {
//			super.updateTouch(c, timeTouched);
//
//			if (!dragging)
//				updateDragCue(c);
//		}
//
//
//		private void removeDragCue() {
//			dragCue.getSize().set(0,0);
//		}
//
//		private void updateDragCue(Pointer c) {
//			c.getQuadIntersection((HasPosition3)Window.this, uv);
//
//			double w = windowResizeEdgeWidth.d();
//			double hw = w / 2.0;
//			
//			if (uv.x() > 0.5 - w) {
//				if (uv.y() > 0.5 - w) {
//					dragMode = DragMode.ResizeNE;
//					dragCue.span(0.5 - hw, 0.5 - hw, 0.5, 0.5);
//					return;
//				}
//				else if (uv.y() < -0.5 + w) {
//					dragMode = DragMode.ResizeSE;					
//					dragCue.span(0.5 - hw, -0.5 + hw, 0.5, -0.5);
//					return;
//				}
//			}
//			else if (uv.x() < -0.5 + w) {
//				if (uv.y() > 0.5 - w) {
//					dragMode = DragMode.ResizeNW;					
//					dragCue.span(-0.5 + hw, 0.5 - hw, -0.5, 0.5);
//					return;
//				}
//				else if (uv.y() < -0.5 + w) {
//					dragMode = DragMode.ResizeSW;										
//					dragCue.span(-0.5 + hw, -0.5 + hw, -0.5, -0.5);
//					return;
//				}				
//			}
//
//			dragMode = DragMode.Move;			
//			dragCue.getSize().set(0,0);
//		}
//
//		@Override public void startDrag(Pointer c, Drag drag) {
//			Window.this.startDrag(c, drag);
//
//			updateDragCue(c);
//
//			this.dragStartPosition.set(Window.this.getPosition());
//			this.dragStartSize.set(Window.this.getSize());
//			this.dragStartAbsSize .set(Window.this.getAbsoluteSize());
//			this.dragStartZ = Window.this.getAbsolutePosition().z();
//			this.dragStartRelative.set(Window.this.getAbsolutePosition());
//			this.dragStartRelative.subtract(drag.getMeshIntersectStart());
//			this.dragStartAbs.set(drag.getMeshIntersectStart());
//
//
//			dragging = true;
//		}
//
//		@Override public void stopDrag(Pointer c, Drag drag) {
//			Window.this.stopDrag(c, drag);
//			dragCue.getSize().set(0,0);
//
//			dragging = false;
//		}
//
//		double px, py, pz, dx, dy, dxR, dyR;
//
////		@Override public void updateDrag(Pointer c, Drag drag) {
////
////			pz = dragStartZ;
////
////			if (dragMode == DragMode.Move) {
////				px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
////				py = drag.getRealPositionCurrent().y() + dragStartRelative.y();					
////			}
////			else {
////				px = dragStartPosition.x();
////				py = dragStartPosition.y();
////
////				dx = drag.getRealPositionCurrent().x() - dragStartAbs.x();
////				dy = drag.getRealPositionCurrent().y() - dragStartAbs.y();
////
//////				alX = Window.this.getAbsoluteSize().x() / Window.this.getSize().x();
//////				alY = Window.this.getAbsoluteSize().y() / Window.this.getSize().y();
//////
////////				dxR = dx / alX;
////////				dyR = dy / alY;
////
////
////				if (dragMode == DragMode.ResizeNE) {
////					px += dx/2.0;
////					py += dy/2.0;
////					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() + dx/2.0, dragStartAbsSize.y() + dy/2.0, dragStartAbsSize.z());
////				}
////				else if (dragMode == DragMode.ResizeNW) {
////					px += dx/2.0;
////					py += dy/2.0;
////					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() - dx/2.0, dragStartAbsSize.y() + dy/2.0, dragStartAbsSize.z());
////				}
////				else if (dragMode == DragMode.ResizeSE) {
////					px += dx/2.0;
////					py += dy/2.0;
////					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() + dx/2.0, dragStartAbsSize.y() - dy/2.0, dragStartAbsSize.z());
////				}
////				else if (dragMode == DragMode.ResizeSW) {
////					px += dx/2.0;
////					py += dy/2.0;
////					Window.this.setNextAbsoluteSize(dragStartAbsSize.x() - dx/2.0, dragStartAbsSize.y() - dy/2.0, dragStartAbsSize.z());
////				}
////
////
////			}
////
////			Window.this.setNextAbsolutePosition(px, py, pz);
////		}
//
//		@Override public void updateDrag(Pointer c, Drag drag) {
//
//			pz = dragStartZ;
//
//			if (dragMode == DragMode.Move) {
//				px = drag.getRealPositionCurrent().x() + dragStartRelative.x();
//				py = drag.getRealPositionCurrent().y() + dragStartRelative.y();					
//			}
//			else {
//				px = dragStartPosition.x();
//				py = dragStartPosition.y();
//
//				
//				dx = drag.getRealPositionCurrent().x() - dragStartAbs.x();
//				dy = drag.getRealPositionCurrent().y() - dragStartAbs.y();
//
//				alX = Window.this.getAbsoluteSize().x() / Window.this.getSize().x();
//				alY = Window.this.getAbsoluteSize().y() / Window.this.getSize().y();
//
//				dxR = dx / alX;
//				dyR = dy / alY;
//
//
//				if (dragMode == DragMode.ResizeNE) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.getSize().set(dragStartSize.x() + dxR/2.0, dragStartSize.y() + dyR/2.0, dragStartSize.z());
//				}
//				else if (dragMode == DragMode.ResizeNW) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.getSize().set(dragStartSize.x() - dxR/2.0, dragStartSize.y() + dyR/2.0, dragStartSize.z());
//				}
//				else if (dragMode == DragMode.ResizeSE) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.getSize().set(dragStartSize.x() + dxR/2.0, dragStartSize.y() - dyR/2.0, dragStartSize.z());
//				}
//				else if (dragMode == DragMode.ResizeSW) {
//					px += dx/2.0;
//					py += dy/2.0;
//					Window.this.getSize().set(dragStartSize.x() - dxR/2.0, dragStartSize.y() - dyR/2.0, dragStartSize.z());
//				}
//
//
//			}
//
//			Window.this.setNextAbsolutePosition(px, py, pz);
//		}
//
//
//	}
//
//	public Window(Box b) {
//		super(b.getPosition(), b.getSize(), b.getOrientation());
//		tangible(false);
//	}
//
//
//	public Window() {
//		super();
//		tangible(false);
//	}
//
//	protected void startDrag(Pointer c, Drag drag) {
//		beingDragged.set(true);
//	}
//
//	protected void stopDrag(Pointer c, Drag drag) {
//		beingDragged.set(false);
//	}
//
//	public BooleanVar getBeingDragged() {
//		return beingDragged ;
//	}
//
//
//
//	@Override protected Rect newPanelRect() {
//		return new WindowBevel(this, new ColorSurface(getBackColor()));
//	}
//
//	@Override public void start(Scope c) {
//		super.start(c);
//
//		cursor = getThe(Pointer.class);
//		sight = getThe(Video3D.class);
//
//		dragCue = add(new Rect(0,0));
//		dragCue.color(Color.Red.alpha(0.5));
//		dragCue.tangible(false);
//	}
//
//
//
//}
