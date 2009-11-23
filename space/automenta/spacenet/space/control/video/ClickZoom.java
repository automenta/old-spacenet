package automenta.spacenet.space.control.video;

import org.apache.log4j.Logger;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.HasSize3;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.video2d.HasSize2;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;
import automenta.spacenet.var.vector.Vector3;
import automenta.spacenet.var.vector.jme.fQuaternion;
import automenta.spacenet.var.vector.jme.fVector3;

@Deprecated public class ClickZoom implements StartsIn<Scope>, Stops {
	private static final Logger logger = Logger.getLogger(ClickZoom.class);

	private static final double defaultZoomBoundsFactor = 1.5;

	Vector3 vNormal = new Vector3();

	private static fVector3 qRot[] = new fVector3[3];

	protected Spacetime spaceTime;
	private PointerButton button;

	/** usually > 1.0, to determine "padding" for viewing zoom-to-focused objects appropriately */ 
	private double zoomBoundsFactor = 1.4;

	
	fQuaternion q = new fQuaternion();
	
	public ClickZoom(Spacetime s, PointerButton button) {
		super();

		this.spaceTime = s;
		this.button = button;
	}

	@Override public void start(Scope scope) {
		scope.add(new IfBoolChanges(spaceTime.pointer().getButton(PointerButton.Right)) {
			@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
				if (nextValue == false) {
					//release
					//pressMove = 0;
				}
				else {
					Space touchedNode = (Space) spaceTime.pointer().getTouchedNode().get();
					if (touchedNode!=null) {
						if (isZoomable(touchedNode))
							zoomToFocused();
					}
					else {
						//pressMove = 1 * zoomButtonZoomOutSpeed;
					}
				}
			}				
		});

	}
	
	/** only zoom to nodes that are children of the volume space (not face or sky) */
	protected boolean isZoomable(Space touchedNode) {
		Scope p = touchedNode;
		while (p!=null) {
			if (p.getParent() == spaceTime.face()) {
				return false;
			}
			if (p instanceof ClickZoomExcepted)
				return false;
//			if (p.getParent() == spaceTime.sky()) {
//				return false;
//			}
			p = p.getParent();
		}
		return true;
	}

	public void zoomTo(Spacetime spaceTime, Space spatial, double zoomBoundsFactor) {		
		if (spatial instanceof HasPosition3) { 
			double x = ((HasPosition3)spatial).getAbsolutePosition().x();
			double y = ((HasPosition3)spatial).getAbsolutePosition().y();
			double z = ((HasPosition3)spatial).getAbsolutePosition().z();

			double r;
			if ((spatial instanceof HasSize3)) {
				double w = ((HasSize3)spatial).getAbsoluteSize().x();
				double h = ((HasSize3)spatial).getAbsoluteSize().y();
				double d = ((HasSize3)spatial).getAbsoluteSize().z();

				//r = Math.max(w, Math.max(h, d));
				r = Math.max(w, h);
			}
			else if (spatial instanceof HasSize2) {
				double w = ((HasSize2)spatial).getAbsoluteSize().x();
				double h = ((HasSize2)spatial).getAbsoluteSize().y();

				r = Math.max(w, h);						
			}
			else {
				r = 1;
			}
			
			if (spatial instanceof HasOrientation) {
				HasOrientation ho = (HasOrientation) spatial;
				double tilt = ho.getAbsoluteOrientation().z();
				
				
				q.fromAngles(ho.getAbsoluteOrientation().x(), ho.getAbsoluteOrientation().y(), ho.getAbsoluteOrientation().z());
				if (qRot[0] == null) {
					qRot[0] = new fVector3();
					qRot[1] = new fVector3();
					qRot[2] = new fVector3();
				}
				q.toAxes(qRot);
				
				spaceTime.video().getUp().set(qRot[1].getX(), qRot[1].getY(), qRot[1].getZ());				
			}

			double viewAngle = Math.toRadians(spaceTime.video().getFocusAngle().get());

			double viewingDistance = 2 * r * zoomBoundsFactor * Math.sin(Math.PI/4 - viewAngle/2) / Math.sin(viewAngle/2);

//			logger.info("Zoom to focused: " + spatial + " pos=" + x + "," + y + ", " + z);
//			logger.info("  r=" + r);
//			logger.info("  dist=" + viewingDistance);

			//nextSightPosition.set(x, y, z + viewingDistance);

			((HasPosition3)spatial).getAbsoluteNormal(vNormal);
			vNormal.multiply(viewingDistance);
			
			Vector3 p = new Vector3(x + vNormal.x(), y + vNormal.y(), z + vNormal.z());
			Vector3 t = new Vector3(x,y,z);


			spaceTime.video().getPosition().set(p);		
			spaceTime.video().getTarget().set(t);	

		}

	}
	
	protected void zoomToFocused() {
		Space n = (Space) spaceTime.pointer().getTouchedNode().get();		
		if (n!=null) {		
			zoomTo(spaceTime, n, zoomBoundsFactor);				
		}
	}

	@Override public void stop() {

	}

}
