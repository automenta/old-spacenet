package automenta.spacenet.space.jme.video.swing.depr;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import automenta.spacenet.Disposable;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.math.Plane;
import com.jme.math.Ray;
import com.jme.math.Triangle;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Geometry;
import com.jme.scene.TriMesh;
import com.jmex.awt.input.AWTMouseInput;


public class SwingPointer {

}

//public class SwingPointer implements Repeat<SwingJmeOLD>, Pointer, MouseListener, MouseMotionListener, MouseWheelListener, Disposable {
//
//	private SwingJmeOLD jme;
//	private Canvas canvas;
//	private MouseEvent updateNecessary;
//
//	private PickResults pickResults;
//
//	private double minDistance;
//
//	private int currentPickResult;
//	private Vector2f cursorPixelF = new Vector2f();
//
//	private Geometry touchedMesh;
//
//	private Plane touchedPlane;
//
//
//	//picking re-used / cached variables
//	Vector3f[] triVertices = new Vector3f[3];
//
//	Vector3f worldLoc = new Vector3f();
//
//	Vector3f clickStartPos = new Vector3f();
//
//	Plane p = new Plane();
//
//	Plane defaultPlane = new Plane(new Vector3f(0,0,1), 0.5f);
//
//	private Triangle touchedTriangle;
//
//	private Vector3f currentTriangleIntersection = new Vector3f();
//
//
//	private Vector3f pickedScreenPos = new Vector3f();
//
//	private float pickedScreenZ;
//
//	private float touchedDistance;
//
//	public SwingPointer() {
//		super();
//
//	}
//
//	@Override public void mouseClicked(MouseEvent e) {
//		//cursorState.resetState();
//
//		int px = e.getX();
//		int py = e.getY();
//
//		int clickCount = e.getClickCount();
//
//		int buttonIndex;
//		switch (e.getButton()) {
//		case MouseEvent.BUTTON1:
//			buttonIndex = 0;
//			break;
//		case MouseEvent.BUTTON2:
//			buttonIndex = 1;
//			break;
//		case MouseEvent.BUTTON3:
//			buttonIndex = 2;
//			break;
//		default:
//			return;
//		}
//
//		cursor.getKey( buttonIndex ).setLastClickCount(e.getClickCount());
//
//		recordButtonState();
//	}
//
//	@Override
//	public void mouseDragged(MouseEvent e) {
//		mouseMoved(e);
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		//controlsEnabled.setValue(true);
//	}
//
//	@Override
//	public void mouseExited(MouseEvent e) {
//		//controlsEnabled.setValue(false);
//	}
//
//	@Override public void mouseMoved(MouseEvent e) {
//		updateNecessary = e;
//	}
//
//	@Override public void mousePressed(MouseEvent e) {
//		updateButton(e, true);
//	}
//
//	@Override public void mouseReleased(MouseEvent e) {
//		updateButton(e, false);
//	}
//
//	@Override
//	public void mouseWheelMoved(MouseWheelEvent e) {
//		resetState();
//		cursor.setWheelAbsolute(e.getWheelRotation());
//		cursor.setWheelDelta(e.getWheelRotation());
//		resetClickCounts();
//		recordButtonState();
//	}
//
//
//	@Override
//	public MapVar<Object, BooleanVar> getButtons() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public MapVar<Space, Vector3> getIntersectedSpatials() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Vector2 getPixelPosition() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Vector3 getSourcePosition() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Vector2 getTouchedSurfacePosition() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public IntegerVar getWheelRotation() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private void recordButtonState() {
//		//System.out.println("button change, picked=" + picked + ", latched=" + cursorState.getLatch());
//
//		updateCursorButtons(ambientNode);
//
//
//		if (cursor.getLatch()!=null) {
//			cursor.getLatch().onCursorButtonChange(cursor);
//		}
//		else {
//			if (touchedNode!=null) {
//				updateCursorButtons(touchedNode);
//			}
//			else {
//				updateCursorButtons(rootNode);
//			}
//		}
//		//
//		//		if (picked!=null) {
//		//			picked.foreachState(CursorAware.class, new ObjectVisitor<CursorAware>() {
//		//				@Override public void onVisit(CursorAware c) {
//		//					c.onCursorButtonChange(cursorState);
//		//				}
//		//			});
//		//		}
//	}
//
//	@Override
//	public double afterElapsed(double dt) {
//		updateCursorMoved();
//
//		forward(dt);
//
//		return 0;
//	}
//
//	@Override public void start(SwingJmeOLD jme) {
//		this.jme = jme;
//		this.canvas = jme.getPanel().getCanvas();
//
//		canvas.addMouseListener(this);
//		canvas.addMouseMotionListener(this);
//		canvas.addMouseWheelListener(this);
//		AWTMouseInput.setup(canvas, false);
//	}
//
//	@Override public void whenStopped(N context) {
//		canvas.removeMouseListener(this);
//		canvas.removeMouseMotionListener(this);
//		canvas.removeMouseWheelListener(this);
//		this.jme = null;
//	}
//
//	@Override
//	public void dispose() {
//		jme.whenStopped(this);
//	}
//
//	protected void updateCursorMoved() {
//		if (updateNecessary == null)
//			return;
//
//		if (getJMEDisplay() == null)
//			return;
//
//		Vector2 pp = cursor.getPixelPosition();
//
//		int px = updateNecessary.getX();
//		int py = getHeight() - updateNecessary.getY();
//
//		cursor.getLastPixelPosition().set(pp.x(), pp.y());
//		cursor.getPixelPosition().set(px, py);
//		cursor.getVelocity().set( cursor.getPixelPosition().x() - cursor.getLastPixelPosition().x(), cursor.getPixelPosition().y() - cursor.getLastPixelPosition().y() );
//
//		AbstractNode previousTouched = touchedNode;
//
//		updateNextTouched();
//
//
//		if (previousTouched!=touchedNode) {
//
////			logger.info("t:" + previousTouched + " -> " + touchedNode + "(dist=" + touchedDistance + ")");
////			if (touched!=null)
////				logger.info("  " + touchedMesh + " [ " + (touched.getParent() == null ? "(no parent)" : touched.getParent()));
//
//			if (previousTouched!=null) {
//				previousTouched.foreachState(CursorAware.class, new ObjectVisitor<CursorAware>() {
//					@Override public void onVisit(CursorAware c) {
//						c.onCursorExited(cursor);
//						c.onCursorButtonChange(cursor);
//					}
//				});
//
//			}
//			if (touchedNode!=null) {
//				touchedNode.foreachState(CursorAware.class, new ObjectVisitor<CursorAware>() {
//					@Override public void onVisit(CursorAware c) {
//						c.onCursorButtonChange(cursor);
//						c.onCursorEntered(cursor);
//					}
//				});
//			}
//		}
//		//
//		//		//		Vector3f zeroPickLocation = updatePickLocation(mouseRay.getOrigin(), mouseRay.getDirection(), new Vector3f(0,0,0));
//		//		//		Vector3f pickLocation;
//		//		//		if (closestPickedMesh!=null) {
//		//		//			pickLocation = updatePickLocation(closestPickData, closestPickedMesh, next);
//		//		//		}
//		//		//		else {
//		//		//			pickLocation = zeroPickLocation;
//		//		//		}
//		//
//		//		//		next.cursorWorld = new Vector2( pickLocation.getX(), pickLocation.getY() );
//		//
//		//
//		updateCursorMoved(ambientNode);
//
//		if (cursor.getLatch()!=null) {
//			cursor.getLatch().onCursorMoved(cursor);
//		}
//		else {
//			if (touchedNode!=null) {
//				updateCursorMoved(touchedNode);
//			}
//			else {
//				updateCursorMoved(rootNode);
//			}
//		}
//
//		//updateNecessary = null;
//	}
//
//	public void updateNextTouched() {
//		if (display == null)
//			return;
//
//
//		try {
//			cursorPixelF.set((float)cursor.getPixelPosition().x(), (float)cursor.getPixelPosition().y());
//			display.getWorldCoordinates(cursorPixelF, 1f, clickStartPos);
//		}
//		catch (ArithmeticException ae) {
//			System.out.println(ae);
//			return;
//		}
//
//
//		Vector3f camLoc = display.getRenderer().getCamera().getURI();
//		Ray pickRay = cursor.getPickRay();
//		pickRay.setOrigin(camLoc);
//
//		pickRay.setDirection(clickStartPos.subtractLocal(camLoc).normalizeLocal());
//
//		//pickRay.getDirection().normalizeLocal();
//
//
//		pickResults = cursor.getPickResults();
//		pickResults.clear();
//		pickResults.setCheckDistance(true);
//		getVolumeNode().findPick(pickRay, pickResults);
//
//
//		touchedNode = null;
//		touchedMesh = null;
//		touchedPlane = defaultPlane;
//
//		//		if (pickedPlane!=null)
//		//			pickRay.intersectsWherePlane(pickedPlane, worldLoc);
//
//		float minDist = Float.MAX_VALUE;
//
//		if (pickResults.getNumber() > 0) {
//
////			for (int j = 0; j < pickResults.getNumber(); j++) {
//			int j = 0;
//			{
//				PickData pickData = pickResults.getPickData(j);
//				Geometry mesh = pickData.getTargetMesh();
//				ArrayList<Integer> tris = pickData.getTargetTris();
//
//				if (mesh instanceof TriMesh) {
//
//					AbstractNode s = getTouchableParentSpaceNodeOf(mesh);
//
//					if (s!=null) {
//
//						TriMesh m = (TriMesh) mesh;
//						for (Integer i : tris) {
//							m.getTriangle(i, triVertices );
//							touchedTriangle = new Triangle(triVertices[0],triVertices[1], triVertices[2]);
//							p.setPlanePoints(triVertices[0],triVertices[1], triVertices[2]);
//
//							pickRay.intersectWhere(touchedTriangle, currentTriangleIntersection );
//							currentTriangleIntersection.add(s.getWorldTranslation());
//
//							float dist = currentTriangleIntersection.distanceSquared(camLoc);
//
//							if (dist < minDist) {
//								minDist = dist;
//
//
//								touchedDistance = dist;
//								touchedPlane = p;
//								touchedMesh = mesh;
//
//								touchedNode = s;
//
//
//								display.getScreenCoordinates( touchedNode.getWorldTranslation(), pickedScreenPos  );
//								pickedScreenZ = pickedScreenPos.z;
//
//
//							}
//						}
//					}
//
//				}
//				else {
//					logger.info("not touchable: ");
//					logger.info(mesh);
//				}
//
//			}
//		}
//
//
//
//		if (cursor.getLatch()==null) {
//			cursor.setTouched(touchedNode);
//
//			if (touchedMesh!=null) {
//				cursor.setTouchedShape(getParentAbstractNode(touchedMesh));
//			}
//			else
//				cursor.setTouchedShape(null);
//
//			cursor.setTouchedPlane(touchedPlane);
//			cursor.setTouchedDistance(touchedDistance);
//		}
//
//		display.getWorldCoordinates( cursorPixelF, pickedScreenZ, worldLoc  );
//		cursor.getSpacePosition().set(worldLoc.getX(), worldLoc.getY(),	worldLoc.getZ());
//	}
//
//
//	protected void updateCursorMoved() {
//		if (updateNecessary == null)
//			return;
//
//		if (getJMEDisplay() == null)
//			return;
//
//		Vector2 pp = cursor.getPixelPosition();
//
//		int px = updateNecessary.getX();
//		int py = getHeight() - updateNecessary.getY();
//
//		cursor.getLastPixelPosition().set(pp.x(), pp.y());
//		cursor.getPixelPosition().set(px, py);
//		cursor.getVelocity().set( cursor.getPixelPosition().x() - cursor.getLastPixelPosition().x(), cursor.getPixelPosition().y() - cursor.getLastPixelPosition().y() );
//
//		AbstractNode previousTouched = touchedNode;
//
//		updateNextTouched();
//
//
//		if (previousTouched!=touchedNode) {
//
////			logger.info("t:" + previousTouched + " -> " + touchedNode + "(dist=" + touchedDistance + ")");
////			if (touched!=null)
////				logger.info("  " + touchedMesh + " [ " + (touched.getParent() == null ? "(no parent)" : touched.getParent()));
//
//			if (previousTouched!=null) {
//				previousTouched.foreachState(CursorAware.class, new ObjectVisitor<CursorAware>() {
//					@Override public void onVisit(CursorAware c) {
//						c.onCursorExited(cursor);
//						c.onCursorButtonChange(cursor);
//					}
//				});
//
//			}
//			if (touchedNode!=null) {
//				touchedNode.foreachState(CursorAware.class, new ObjectVisitor<CursorAware>() {
//					@Override public void onVisit(CursorAware c) {
//						c.onCursorButtonChange(cursor);
//						c.onCursorEntered(cursor);
//					}
//				});
//			}
//		}
//		//
//		//		//		Vector3f zeroPickLocation = updatePickLocation(mouseRay.getOrigin(), mouseRay.getDirection(), new Vector3f(0,0,0));
//		//		//		Vector3f pickLocation;
//		//		//		if (closestPickedMesh!=null) {
//		//		//			pickLocation = updatePickLocation(closestPickData, closestPickedMesh, next);
//		//		//		}
//		//		//		else {
//		//		//			pickLocation = zeroPickLocation;
//		//		//		}
//		//
//		//		//		next.cursorWorld = new Vector2( pickLocation.getX(), pickLocation.getY() );
//		//
//		//
//		updateCursorMoved(ambientNode);
//
//		if (cursor.getLatch()!=null) {
//			cursor.getLatch().onCursorMoved(cursor);
//		}
//		else {
//			if (touchedNode!=null) {
//				updateCursorMoved(touchedNode);
//			}
//			else {
//				updateCursorMoved(rootNode);
//			}
//		}
//
//		//updateNecessary = null;
//	}
//
//	public void updateNextTouched() {
//		if (display == null)
//			return;
//
//
//		try {
//			cursorPixelF.set((float)cursor.getPixelPosition().x(), (float)cursor.getPixelPosition().y());
//			display.getWorldCoordinates(cursorPixelF, 1f, clickStartPos);
//		}
//		catch (ArithmeticException ae) {
//			System.out.println(ae);
//			return;
//		}
//
//
//		Vector3f camLoc = display.getRenderer().getCamera().getURI();
//		Ray pickRay = cursor.getPickRay();
//		pickRay.setOrigin(camLoc);
//
//		pickRay.setDirection(clickStartPos.subtractLocal(camLoc).normalizeLocal());
//
//		//pickRay.getDirection().normalizeLocal();
//
//
//		pickResults = cursor.getPickResults();
//		pickResults.clear();
//		pickResults.setCheckDistance(true);
//		getVolumeNode().findPick(pickRay, pickResults);
//
//
//		touchedNode = null;
//		touchedMesh = null;
//		touchedPlane = defaultPlane;
//
//		//		if (pickedPlane!=null)
//		//			pickRay.intersectsWherePlane(pickedPlane, worldLoc);
//
//		float minDist = Float.MAX_VALUE;
//
//		if (pickResults.getNumber() > 0) {
//
////			for (int j = 0; j < pickResults.getNumber(); j++) {
//			int j = 0;
//			{
//				PickData pickData = pickResults.getPickData(j);
//				Geometry mesh = pickData.getTargetMesh();
//				ArrayList<Integer> tris = pickData.getTargetTris();
//
//				if (mesh instanceof TriMesh) {
//
//					AbstractNode s = getTouchableParentSpaceNodeOf(mesh);
//
//					if (s!=null) {
//
//						TriMesh m = (TriMesh) mesh;
//						for (Integer i : tris) {
//							m.getTriangle(i, triVertices );
//							touchedTriangle = new Triangle(triVertices[0],triVertices[1], triVertices[2]);
//							p.setPlanePoints(triVertices[0],triVertices[1], triVertices[2]);
//
//							pickRay.intersectWhere(touchedTriangle, currentTriangleIntersection );
//							currentTriangleIntersection.add(s.getWorldTranslation());
//
//							float dist = currentTriangleIntersection.distanceSquared(camLoc);
//
//							if (dist < minDist) {
//								minDist = dist;
//
//
//								touchedDistance = dist;
//								touchedPlane = p;
//								touchedMesh = mesh;
//
//								touchedNode = s;
//
//
//								display.getScreenCoordinates( touchedNode.getWorldTranslation(), pickedScreenPos  );
//								pickedScreenZ = pickedScreenPos.z;
//
//
//							}
//						}
//					}
//
//				}
//				else {
//					logger.info("not touchable: ");
//					logger.info(mesh);
//				}
//
//			}
//		}
//
//
//
//		if (cursor.getLatch()==null) {
//			cursor.setTouched(touchedNode);
//
//			if (touchedMesh!=null) {
//				cursor.setTouchedShape(getParentAbstractNode(touchedMesh));
//			}
//			else
//				cursor.setTouchedShape(null);
//
//			cursor.setTouchedPlane(touchedPlane);
//			cursor.setTouchedDistance(touchedDistance);
//		}
//
//		display.getWorldCoordinates( cursorPixelF, pickedScreenZ, worldLoc  );
//		cursor.getSpacePosition().set(worldLoc.getX(), worldLoc.getY(),	worldLoc.getZ());
//	}
//
////	@Override public CursorSpace newCursorSpace() {
////	if (cursorSpace == null) {
////		cursorSpace = (CursorSpace)new CursorSpace(getCamera(), getCursorState()).attachTo(foreNode);
////
////		videoWatch = new Watch(getControlsEnabled()) {
////			@Override public void onChange(Watched w, Serializable message) {
////				if (!getControlsEnabled().isTrue()) {
////					rootNode.detach(cursorSpace);
////				}
////				else {
////					rootNode.attach(cursorSpace);
////				}
////			}
////		};
////	}
////	return cursorSpace;
////}
//
//}
