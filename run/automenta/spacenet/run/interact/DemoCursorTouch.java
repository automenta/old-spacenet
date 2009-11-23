package automenta.spacenet.run.interact;






public class DemoCursorTouch  {
//
//	Quaternion q = new Quaternion();
//	Vector3f up = new Vector3f(0,1,0);
//	float[] angles = new float[3];
//	private Vector3f intersectOrientationF = new Vector3f();
//	private Vector3 intersectNormal = new Vector3();
//
//	public class RectMoveDrag implements StartsIn<Scope>, Stops {
//
//		private boolean running = true;
//		private Pointer cursor;
//		private Rect rect;
//		private RectDragging whenDraggingRect;
//		private Scope node;
//
//		Vector3 center = new Vector3();
//		Vector3 ul = new Vector3(), ur = new Vector3(), bl = new Vector3();
//		private Vector3 intersectPoint = new Vector3();
//		private Vector3 intersectOrientation = new Vector3();
//
//		public RectMoveDrag(Pointer c, Rect r) {
//			this.cursor = c;
//			this.rect = r;
//
//			getParent().add(new VectorBox(intersectPoint).scale(0.5));
//
//			getParent().add(new Box(ul, new Vector3(1,1,1)).scale(0.2));
//			getParent().add(new Box(ur, new Vector3(1,1,1)).scale(0.2));
//			getParent().add(new Box(bl, new Vector3(1,1,1)).scale(0.2));
//		}
//
//		public boolean isRunning() { return running; }
//
//		@Override public void start(Scope node) {
//			this.node = node;
//
////			rect.add(new WhenButtonChanges(rect, cursor, CursorButton.Left) {
////
////				@Override protected void whenPressed(int repeatClicks) {}
////
////				@Override protected void whenReleased() {
////					running = !running;
////				}
////
////			});
//			node.add(new Repeat() {
//				private Vector2 uv = new Vector2();
//
//				@Override public double repeat(double t, double dt) {
//					center.set(rect.getAbsolutePosition());
//
//					cursor.getQuadIntersection(rect, uv);
//					System.out.println(uv);
//
//					return 0;
//				}
//			});
//
////			whenDraggingRect = node.add(new WhenDraggingRect(rect, cursor, CursorButton.Left) {
////
////				@Override
////				protected void whenDragStarts(Vector2 dragStart) {
////					System.out.println("drag start: " + dragStart);
////				}
////
////				@Override
////				protected void whenDragStops(Vector2 dragStop) {
////					System.out.println("drag stop: " + dragStop);
////				}
////
////				@Override
////				protected void whenDragging(Vector2 dragCurrent) {
////					System.out.println("dragging: " + dragCurrent);
////				}
////
////			});
//
//		}
//
//		@Override public void stop() {
//			node.remove(whenDraggingRect);
//		}
//	}
//
//	@Override public void start(Scope c) {
//		super.start(c);
//
//		//add(new CursorRay(getCursor(), getSight()));
//
//		final RectX r = new RectX(-3,0,0,4,3).color(Color.Orange.alpha(0.7));
//		add(r);
//		final RectMoveDrag rr = add(new RectMoveDrag(pointer(), r));
//
//		final RectX s = (RectX) new RectX(3,0,0,4,4).color(Color.Purple.alpha(0.7)).orient(0, -0.5, 0);
//		add(s);
//		final RectMoveDrag ss = add(new RectMoveDrag(pointer(), s));
//
//
//		add(new RectMoveDrag(pointer(), add(new RectX(-3,-3,0,4,4).color(Color.Green))));
//
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				if (rr.isRunning())
//					r.getOrientation().add(0.01,0.01,0);
//
//				if (rr.isRunning())
//					s.getOrientation().add(0,0.05,0.0);
//
//				return 0.05;
//			}
//		});
//
//	}
//
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoCursorTouch());
//	}
}

//For Reference:
//public class DemoRectDrag extends AbstractPlaneDemo {
//
//	
//	public class RectMoveDrag implements StartsIn<Node>, Stops {
//		
//		private Cursor cursor;
//		private Rect rect;
//		private WhenDraggingRect whenDraggingRect;
//		private Node node;
//
//		Vector3 center = new Vector3();
//		Vector3 ul = new Vector3(), ur = new Vector3(), bl = new Vector3(); 
////		Line3D cul = new Line3D(center, ul, new DoubleVar(0.05), 4);
////		Line3D cur = new Line3D(center, ur, new DoubleVar(0.05), 4);;
////		Line3D cbl = new Line3D(center, bl, new DoubleVar(0.05), 4);;
//		private Vector3 intersectPoint = new Vector3();
//		private Vector3 intersectOrientation = new Vector3();
//
//		public RectMoveDrag(Cursor c, Rect r) {
//			this.cursor = c;
//			this.rect = r;
//
//			
//			getParent().add(new AxisBox(intersectPoint , new Vector3(1,1,1), intersectOrientation).scale(0.8));
//			getParent().add(new VectorBox(intersectPoint).scale(0.5));
//			//getParent().add(new AxisBox(center, new Vector3(1,1,1)).scale(0.8));
//			getParent().add(new Box(ul, new Vector3(1,1,1)).scale(0.2));
//			getParent().add(new Box(ur, new Vector3(1,1,1)).scale(0.2));
//			getParent().add(new Box(bl, new Vector3(1,1,1)).scale(0.2));
//			//getParent().add(new Box(center, new Vector3(1,1,1)).scale(0.2));
////			getParent().add(cul);
////			getParent().add(cur);
////			getParent().add(cbl);
//
//		}
//
//		Vector3f up = new Vector3f(0,1,0);
//		float[] angles = new float[3];
//		private Vector3f intersectOrientationF = new Vector3f();
//
//		@Override public void start(Node node) {
//			this.node = node;
//
//			node.add(new Repeat() {
//
//
//				@Override public double repeat(double t, double dt) {
//					
////					Vector3f ur = MathAction.fromDouble(rect.getAbsolutePosition());
////					Vector3f bl = MathAction.fromDouble(rect.getAbsolutePosition());
//					
//					//MathAction.fromDouble(rect.getAbsolutePosition(), center);
//					
//					Triangle tri = cursor.getTouchedTriangle();
//					if (tri!=null) {
//						intersectPoint.set(cursor.getTriangleIntersection());
//						MathAction.toDouble( cursor.getTouchedTriangle().getNormal(), intersectOrientation );
//						
//						Quaternion q = new Quaternion();
//						
//						
//						q.lookAt(cursor.getTouchedTriangle().getNormal(), up);
//						q.toAngles(angles);
//						intersectOrientation.set(angles[0], angles[1], angles[2]);
//						
//						//intersectOrientation.cartesianToSpherical();
//						//rect.getAbsoluteOrientation().set(intersectOrientation);
//						System.out.println("tri normal: " + intersectOrientation);
//					}
//					
//					center.set(rect.getAbsolutePosition());				
//					
//					rect.getAbsolutePosition(-1, 1, ul);
//					rect.getAbsolutePosition(-1, -1, bl);
//					rect.getAbsolutePosition(1, 1, ur);
//
//					
//					//System.out.println(cursor.getPositionRelativeTo(rect) + " " + cursor.getPixelPosition() + " " + cursor.getButtons());
//					return 0;
//				}
//				
//			});
//
////			whenDraggingRect = node.add(new WhenDraggingRect(rect, cursor, CursorButton.Left) {
////
////				@Override
////				protected void whenDragStarts(Vector2 dragStart) {
////					System.out.println("drag start: " + dragStart);
////				}
////
////				@Override
////				protected void whenDragStops(Vector2 dragStop) {
////					System.out.println("drag stop: " + dragStop);					
////				}
////
////				@Override
////				protected void whenDragging(Vector2 dragCurrent) {
////					System.out.println("dragging: " + dragCurrent);										
////				}
////				
////			});
//
//		}
//
//		@Override public void stop() {
//			node.remove(whenDraggingRect);
//		}
//	}
//	
//	@Override public void start(Node c) {
//		super.start(c);
//		
//		//add(new CursorRay(getCursor(), getSight()));
//		
//		final Rect r = new Rect(-3,0,0,4,3).withSurface(Color.Blue.deriveAlpha(0.7));
//		add(r);
//		add(new RectMoveDrag(getCursor(), r));
//
//		final Rect s = (Rect) new Rect(3,0,0,4,4).withSurface(Color.Blue.deriveAlpha(0.7)).withOrientation(0, -0.5, 0);
//		add(s);
//		add(new RectMoveDrag(getCursor(), s));
//		
//		add(new Repeat() {
//			@Override public double repeat(double t, double dt) {
//				r.getOrientation().add(0.01,0.01,0);
//				//r.getPosition().add(Math.sin(t)*0.2,Math.cos(t)*0.2,0);
//				
//				s.getOrientation().add(0,0.05,0.0);
//
//
//				return 0.05;
//			}			
//		});
//		
//	}
//	
//
//	public static void main(String[] args) {
//		new RunJme(new DemoRectDrag());
//	}
//}
