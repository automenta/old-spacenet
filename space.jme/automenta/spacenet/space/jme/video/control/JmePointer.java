package automenta.spacenet.space.jme.video.control;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Drag;
import automenta.spacenet.space.control.DragTarget;
import automenta.spacenet.space.control.Draggable;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.Pressable;
import automenta.spacenet.space.control.Toggle;
import automenta.spacenet.space.control.Touchable;
import automenta.spacenet.space.control.Drag.DragState;
import automenta.spacenet.space.jme.fMaths;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.space.video3d.Video3D;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.time.TimePoint;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingVolume;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.input.MouseInput;
import com.jme.input.MouseInputListener;
import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Plane;
import com.jme.math.Ray;
import com.jme.math.Triangle;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.system.DisplaySystem;


public class JmePointer extends Scope implements Pointer<Geometry>, Starts {
	private static final Logger logger = Logger.getLogger(JmePointer.class);

	public static enum Button {
		Left, Right, Center
	};

	private MouseInputListener absoluteMouse;
	private MapVar<Object, Toggle> buttons = new MapVar();
	private Vector2f cursorPixelF = new Vector2f();
	private Vector3f cursorWorldZOne = new Vector3f();
	private Jme jme;

	final PointerButton PressButton = PointerButton.Left;

	private Vector2 pixelPosition = new Vector2();

	private Vector2 pixelPositionRelativeToScreen = new Vector2();

	private Vector3f sightPosF = new Vector3f();


	private Vector3 touchDirection = new Vector3();
	private JmeNode touchedJmeNode;
	private Geometry touchedMesh;
	private ObjectVar<Space> touchedNode = new ObjectVar();
	private Plane touchedPlane = new Plane();
	private Triangle touchedTriangle = new Triangle(new Vector3f(), new Vector3f(), new Vector3f());
	private Vector3 triangleIntersect = new Vector3();
	private Vector3f touchedTriangleIntersectionF = new Vector3f();
	private Vector3f[] touchedTriangleVertices = new Vector3f[3];
	private Ray touchRay = new Ray();

	//private PickResults boundResults = new BoundingPickResults();
	private PickResults triResults = new TrianglePickResults();

	private DoubleVar wheel = new DoubleVar(0);

	private Pressable currentlyPressed;
	private TimePoint timeWhenPressed;

	private Space nowTouched;
	private TimePoint timeWhenTouched;

	private Draggable currentlyDragged;

	private Vector2 dragStartUV = new Vector2(); 
	private Vector2 dragCurrentUV = new Vector2();

	private Drag currentDrag;
	private PickData pickData;
	private Space nextTouched;

	public JmePointer(Jme jme) {
		super();
		this.jme = jme;
	}
	
	@Override public void forward(double dt) {
		updateCursorPosition();

		if (currentlyDragged!=null) {
			nextTouched = (Space) currentlyDragged;
		}

		if (nextTouched!=nowTouched) {

			if (nowTouched!=null) {
				if (nowTouched instanceof Touchable) {
					//STOP TOUCH
					//System.out.println("STOP TOUCH: " + nowTouched + " -> " + nextTouched);
					((Touchable)nowTouched).stopTouch(this);
					timeWhenTouched = null;
				}
			}

			setTouchedSpace(nextTouched);

			if (nowTouched!=null) {
				if (nowTouched instanceof Touchable) {
					//START TOUCH
					//System.out.println("START TOUCH: " + nowTouched);

					Touchable t = ((Touchable)nowTouched);
					timeWhenTouched = new TimePoint();
					t.startTouch(this);
				}
			}			
		}
		else if (nowTouched!=null) {
			if (nowTouched instanceof Touchable) {
				((Touchable)nowTouched).updateTouch(this, timeWhenTouched.getSecondsToNow());
			}
		}

		boolean isPressed = getButton(PressButton).b();
		int pressChange = getButton(PressButton).getChange();

		//PRESS (DOWN)
		if (pressChange == +1) {
			if (nowTouched instanceof Draggable) {
				//START DRAG
				currentlyDragged = (Draggable)nowTouched;
				this.getQuadIntersection((HasPosition3)currentlyDragged, dragStartUV );

				currentDrag = new Drag(currentlyDragged) {

				};
				currentDrag.getRealPositionCurrent().set(triangleIntersect);
				currentDrag.getMeshIntersectStart().set(triangleIntersect);
				currentDrag.getPixelStart().set(pixelPositionRelativeToScreen);
				currentDrag.getTimeStart().setNow();
				currentlyDragged.startDrag(this, currentDrag);
			}

			if (nowTouched instanceof Pressable) {
				//START PRESS
				((Pressable)nowTouched).pressStart(this, triangleIntersect, null);
				currentlyPressed = (Pressable)nowTouched;
				timeWhenPressed = new TimePoint();
			}

		}

		//PRESSED
		if (isPressed) {
			if (currentlyDragged!=null) {
				//UDPATE DRAG
				this.getQuadIntersection((HasPosition3)currentlyDragged, dragCurrentUV);
				//touchedTriangleIntersection, dragCurrentUV);
				currentDrag.setState(DragState.Dragging);
				currentDrag.getRealPositionCurrent().set(triangleIntersect);
				currentDrag.getPixelCurrent().set(pixelPositionRelativeToScreen);
				currentlyDragged.updateDrag(this, currentDrag);
			}			

			if (currentlyPressed!=null) {
				//UPDATE PRESS
				currentlyPressed.pressUpdate(this, timeWhenPressed.getSecondsToNow());
			}

		}
		//NOT PRESSED
		else  {
			if (currentlyDragged!=null) {
				//STOP DRAG
				currentDrag.setState(DragState.Stopped);
				currentDrag.getPixelStop().set(pixelPositionRelativeToScreen);
				currentDrag.getTimeStop().setNow();
				currentlyDragged.stopDrag(this, currentDrag);
				
				if (currentDrag.getScreenDistance() > getDragThreshold()) {
					DragTarget dragTarget = (DragTarget) findTouched(getJme().getVolumeNode(), new TouchValidator() {
						@Override  public boolean isValid(Space s) {
							return (s instanceof DragTarget);
						}
					});
					if (dragTarget!=null) {
						if (dragTarget!=currentlyDragged)
							dragTarget.onDraggableDropped(this, currentlyDragged, new Vector2());
					}
					
				}
				
				currentlyDragged = null;
				currentDrag = null;
			}

			if (currentlyPressed!=null) {
				//STOP PRESS
				currentlyPressed.pressStopped(this);
				currentlyPressed = null;
				timeWhenPressed = null;
			}

		}

		for (Toggle t : buttons.values())
			t.clear();
	}

	private double getDragThreshold() {
		return 0.01;
	}

	public Toggle getButton(PointerButton key) {
		Toggle b = buttons.get(key);
		if (b == null) {
			b = new Toggle(false);
			buttons.put(key, b);
		}

		return b;
	} 


	public BooleanVar getButton(int button) {
		PointerButton key;

		if (button == 0)	key = PointerButton.Left;
		else if (button == 2) key = PointerButton.Center;
		else if (button == 1) key = PointerButton.Right;
		else {
			return null;
		}

		return getButton(key);
	}

	@Override
	public MapVar<Object, Toggle> getButtons() {
		return buttons;
	}

	@Override public Vector2 getConsumedVisionProportion(Space s) {
		// TODO Auto-generated method stub
		return null;
	}

	public DisplaySystem getDisplay() {
		return getJme().getDisplaySystem();
	}

	public Jme getJme() { return jme; }

	@Override public Vector2 getPixelPosition() {
		return pixelPosition ;
	}

	@Override public Vector2 getPositionRelativeToScreen() {
		return pixelPositionRelativeToScreen;
	}

	@Override public Vector2 getQuadIntersection(HasPosition3 rect, Vector2 uv) {		
		Vector3 ul = new Vector3(), bl = new Vector3(), ur = new Vector3();

		double upscale = 5.0;

		rect.getAbsolutePosition(-upscale, upscale, ul);
		rect.getAbsolutePosition(-upscale, -upscale, bl);
		rect.getAbsolutePosition(upscale, upscale, ur);

		Vector3f loc = new Vector3f();

		touchRay.intersectWherePlanarQuad(fMaths.fromDouble(ul), fMaths.fromDouble(bl), fMaths.fromDouble(ur), loc);
		uv.set(loc.getZ() - 0.5, 0.5 - loc.getY());		
		uv.multiply(upscale);


		//System.out.println(uv);

		return uv;

		//		rect.getAbsolutePosition(-1, 1, ul);
		//		rect.getAbsolutePosition(-1, -1, bl);
		//		rect.getAbsolutePosition(1, 1, ur);
		//		
		//		getQuadIntersection(ul, bl, ur, uv);
		//		return uv;

	}

	@Override public Vector2 getQuadIntersection(Vector3 a, Vector3 b, Vector3 c, Vector2 result) {
		Vector3f loc = new Vector3f();

		touchRay.intersectWherePlanarQuad(fMaths.fromDouble(a), fMaths.fromDouble(b), fMaths.fromDouble(c), loc);
		result.set(loc.getZ() - 0.5, 0.5 - loc.getY());		

		return result;
	}

	private Video3D getSight() {
		return getJme().getVideo();
	}

	@Override
	public Vector3 getSourcePosition() {
		// TODO Auto-generated method stub
		return null;
	}

//	private <Y> Y getNode(TriMesh mesh, Class<Y> x) {
//		Spatial s = mesh;
//		Space t = null;
//		while (s!=null) {
//			if (s instanceof JmeNode) {
//				t = ((JmeNode)s).getSpace();
//				System.out.println("  possible: " + t + " a " + x + "?");
//				
//				Class<?>[] ii = t.getClass().getInterfaces();
//				for (Class c : ii) {
//					System.out.println("    test: " + c);
//					if (c.equals(x))
//						return (Y)t;
//				}
//				
////				if (t instanceof DragTarget) {
////					return (X)t;
////				}
////				if (t.getClass().asSubclass(x)!=null) {
////					return (X)t;
////				}
//			}
//			else {
//				t = null;
//			}
//			
//			s = s.getParent();
//		}
//		return null;
//	}

	private DragTarget getDragTarget(TriMesh mesh) {
		Spatial s = mesh;
		Space t = null;
		while (s!=null) {
			if (s instanceof JmeNode) {
				t = ((JmeNode)s).getSpace();
				
				if (t instanceof DragTarget) {
					return (DragTarget)t;
				}
			}
			else {
				t = null;
			}
			
			s = s.getParent();
		}
		return null;
	}


	private JmeNode getTangibleNode(TriMesh mesh) {
		Spatial s = mesh;
		while (s!=null) {
			if (s instanceof JmeNode) {
				JmeNode j = (JmeNode) s;
				if (j.isTangible()) {
					return j;
				}
				else
					return null;
			}
			s = s.getParent();
		}
		return null;
	}

	@Override public Vector3 getTouchDirection() { return touchDirection; }

	@Override public ObjectVar<Space> getTouchedNode() {
		return touchedNode;
	}

	public Geometry getTouchedSpatial() {
		return touchedMesh;
	}

	public Triangle getTouchedTriangle() {
		return touchedTriangle;
	}

	@Override public Vector3 getTriangleIntersection() {
		return triangleIntersect;
	}

	@Override public DoubleVar getWheel() {
		return wheel; 
	}

	private void setTouchedSpace(Space n) {
		if (timeWhenTouched == null) {
			timeWhenTouched = new TimePoint();
		}
		if (touchedNode.set( n )) {
			if (n!=null)
				if (logger.isDebugEnabled()) {
					logger.debug("Touched: " + n);
				}
		}
		nowTouched = n;
	}

	@Override public void start() {

		int pw = (int) jme.getPixelSize().x();
		int ph = (int) jme.getPixelSize().y();

		absoluteMouse = new MouseInputListener() {

			@Override public void onButton(int button, boolean pressed, int x, int y) {
				getButton(button).set(pressed);
			}

			@Override public void onMove(int delta, int delta2, int newX, int newY) {
				double px = newX;
				double py = newY;
				double pw = jme.getPixelSize().x();
				double ph = jme.getPixelSize().y();

                //TODO SwingJme special handler
//				if (getJme() instanceof SwingJme) {
//					//invert mouse y if swingJME used
//					py = ph - py;
//				}

				pixelPosition.set(px, py);
				pixelPositionRelativeToScreen.set( 2.0 * (px - pw / 2.0)/pw,  2.0 * (py - ph / 2.0)/ph);

				//updateCursorPosition();
			}

			@Override public void onWheel(int wheelDelta, int x, int y) {
				wheel.set(wheel.get() + wheelDelta);
			}

		};

		Jme.doLater(new Runnable() {
			@Override public void run() {
				MouseInput.get().addListener(absoluteMouse);				
			}			
		});

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				JmePointer.this.forward(dt);
				return 0;
			}
		});

	}

	@Override public void stop() {
		if (absoluteMouse!=null) {
			Jme.doLater(new Runnable() {
				@Override public void run() {
					MouseInput.get().removeListener(absoluteMouse);
					absoluteMouse = null;
				}			
			});
		}

	}

	@Override public String toString() {
		return "noticing cursor (Jme, mouse)";
	}

	protected void updateCursorPosition() {

		fMaths.toFloat(getPixelPosition(), cursorPixelF);

		fMaths.toFloat(getSight().getPosition(), sightPosF);

		touchedMesh = null;
		touchedJmeNode = null;
		nextTouched = null;

		//traverse scenegraph picking in the order: face, volume, sky (not from root-down only)
		if (!findTouched(getJme().getFaceNode())) {
			findTouched(getJme().getVolumeNode());
		}

	}

	public interface TouchValidator {
		public boolean isValid(Space s);
	}
	
	public Space findTouched(Node rootNode, TouchValidator tv) {
		DisplaySystem display = getDisplay();

		try {
			display.getWorldCoordinates(cursorPixelF, 1f, cursorWorldZOne );
		}
		catch (ArithmeticException ae) {
			logger.error(ae);
			return null;
		}


		Vector3f camLoc = display.getRenderer().getCamera().getLocation();

		touchRay.setOrigin(camLoc);

		touchRay.setDirection(cursorWorldZOne.subtractLocal(sightPosF).normalizeLocal());

		fMaths.toDouble(touchRay.getDirection(), touchDirection);

		triResults.clear();
		triResults.setCheckDistance(true);
		rootNode.findPick(touchRay, triResults);


		if (triResults.getNumber() > 0) {

			//touch the closest node that is tangible

			int j = -1;

			float closestDistance = -1;
			float smallestVolume = -1;

			while ((++j) < triResults.getNumber()) {

				pickData = triResults.getPickData(j);
				Geometry geom = pickData.getTargetMesh();
				TriMesh mesh = (TriMesh)geom;

				if (closestDistance!=-1)
					if (closestDistance < pickData.getDistance()) {
						continue;
					}
				

				Spatial s = mesh;
				Space u = null;
				while (s!=null) {
					if (s instanceof JmeNode) {
						u = ((JmeNode)s).getSpace();
						
						if (tv.isValid(u)) {
							return u;
						}
					}
					else {
						u = null;
					}
					
					s = s.getParent();
				}

//				ArrayList<Integer> tris = pickData.getTargetTris();
//				if (tris==null)
//					continue;				
//
//				if (tris.size() < 1)
//					continue;
//
//				
//				float vol = getVolume(mesh);
//
//				if ((vol < smallestVolume) || (smallestVolume == -1)) {
//					smallestVolume = vol;
//
//					touchedMesh = mesh;
//					touchedJmeNode = jNode;
//					nextTouched = touchedJmeNode.getSpace();
//
//
//
//					int i = 0;	//just use first triangle to determine plane
//
//					/*for (int i = 0; i < tris.size(); i++)*/ 
//					{
//						//int triIndex = tris.get(i);
//						int triIndex = tris.get(0);
//
//						//do not change touchedTriangle if already dragging
//						if (currentDrag == null) {
//							mesh.getTriangle(triIndex, touchedTriangleVertices);
//	
//							int tv = 0;
//							for (Vector3f v : touchedTriangleVertices) {
//								v.multLocal(mesh.getWorldScale());
//								mesh.getWorldRotation().mult(v, v);
//								v.addLocal(mesh.getWorldTranslation());
//								touchedTriangle.set(tv++, v);
//							}
//						}
//						
//
//
//						pickData.getRay().intersectWhere(
//								touchedTriangleVertices[0], 
//								touchedTriangleVertices[1], 
//								touchedTriangleVertices[2], touchedTriangleIntersectionF  );
//
//						fMaths.toDouble( touchedTriangleIntersectionF, triangleIntersect);
//
//					}
//
//
//				}

			}
		}

		return null;
	}
	
	private boolean findTouched(Node rootNode) {
		DisplaySystem display = getDisplay();

		try {
			display.getWorldCoordinates(cursorPixelF, 1f, cursorWorldZOne );
		}
		catch (ArithmeticException ae) {
			logger.error(ae);
			return false;
		}


		Vector3f camLoc = display.getRenderer().getCamera().getLocation();

		touchRay.setOrigin(camLoc);

		touchRay.setDirection(cursorWorldZOne.subtractLocal(sightPosF).normalizeLocal());

		fMaths.toDouble(touchRay.getDirection(), touchDirection);

		triResults.clear();
		triResults.setCheckDistance(true);
		rootNode.findPick(touchRay, triResults);


		if (triResults.getNumber() == 0) {
			if (currentlyDragged == null) {
				nextTouched = null;
				//setTouchedSpace(null);
			}
			else {
				//dragging, but not touching anything.  so find the UV plane intersection of the last triangle touched

				touchedPlane.setNormal(touchedTriangle.getNormal());

				//plane's constant == tri's projection
				touchedPlane.setConstant(touchedTriangle.getProjection());

				touchRay.intersectsWherePlane(touchedPlane, touchedTriangleIntersectionF);

				fMaths.toDouble( touchedTriangleIntersectionF, triangleIntersect);
			}
		}
		else /*if (triResults.getNumber() > 0)*/ {

			//touch the closest node that is tangible

			int j = -1;

			float closestDistance = -1;
			float smallestVolume = -1;

			while ((++j) < triResults.getNumber()) {

				pickData = triResults.getPickData(j);
				Geometry geom = pickData.getTargetMesh();
				TriMesh mesh = (TriMesh)geom;

				if (closestDistance!=-1)
					if (closestDistance < pickData.getDistance()) {
						continue;
					}
				
				JmeNode jNode = getTangibleNode(mesh);
				if (jNode==null) {
					continue;
				}

				ArrayList<Integer> tris = pickData.getTargetTris();
				if (tris==null)
					continue;				

				if (tris.size() < 1)
					continue;

				
				float vol = getVolume(mesh);

				if ((vol < smallestVolume) || (smallestVolume == -1)) {
					smallestVolume = vol;

					touchedMesh = mesh;
					touchedJmeNode = jNode;
					nextTouched = touchedJmeNode.getSpace();



					int i = 0;	//just use first triangle to determine plane

					/*for (int i = 0; i < tris.size(); i++)*/ 
					{
						//int triIndex = tris.get(i);
						int triIndex = tris.get(0);

						//do not change touchedTriangle if already dragging
						if (currentDrag == null) {
							mesh.getTriangle(triIndex, touchedTriangleVertices);
	
							int tv = 0;
							for (Vector3f v : touchedTriangleVertices) {
								v.multLocal(mesh.getWorldScale());
								mesh.getWorldRotation().mult(v, v);
								v.addLocal(mesh.getWorldTranslation());
								touchedTriangle.set(tv++, v);
							}
						}
						


						pickData.getRay().intersectWhere(
								touchedTriangleVertices[0], 
								touchedTriangleVertices[1], 
								touchedTriangleVertices[2], touchedTriangleIntersectionF  );

						fMaths.toDouble( touchedTriangleIntersectionF, triangleIntersect);

					}


				}

			}
		}

		return nextTouched!=null;
	}

	private float getVolume(TriMesh mesh) {
		BoundingVolume b = mesh.getWorldBound();
		if (b instanceof OrientedBoundingBox) {
			return ((OrientedBoundingBox)b).getExtent().length();
		}
		else if (b instanceof BoundingBox) {
			BoundingBox bb = ((BoundingBox)b);
			return bb.xExtent * bb.yExtent * bb.zExtent; 

		}

		// TODO Auto-generated method stub
		return 0;
	}

}
