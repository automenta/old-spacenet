package automenta.spacenet.space.control.video;

import automenta.spacenet.Scope;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class ClickZoomPress extends ClickZoom {

	Vector2 startPos = new Vector2();
	Vector2 deltaPos = new Vector2();
	Vector3 sightDelta = new Vector3();
	
	boolean clicked = false;
	protected boolean rightClickZoom;
	protected double rightClickZoomStart;
	
	public ClickZoomPress(Spacetime s, PointerButton button) {
		super(s, button);
	}
	
	@Override public void start(Scope scope) {
		scope.add(new IfBoolChanges(spaceTime.pointer().getButton(PointerButton.Right)) {
			@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
				if (nextValue == false) {
					updateDelta();
					
					if (deltaPos.getMagnitude() < getDragThreshold()) {
						Space touchedNode = (Space)spaceTime.pointer().getTouchedNode().get();
						if (touchedNode!=null) {
							if (isZoomable(touchedNode))
								zoomToFocused();
						}
						else {
							//pressMove = 1 * zoomButtonZoomOutSpeed;
						}
					}
					
					clicked = false;
				}
				else {
					startPos.set(spaceTime.pointer().getPositionRelativeToScreen());
					clicked = true;
				}
			}				
		});
		
		scope.add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				if (spaceTime.pointer().getButton(PointerButton.Right).b()) {		
					Object touchedNode = spaceTime.pointer().getTouchedNode().get();
					if (touchedNode == null)
						startRightClickZoom(t);
					else if (touchedNode instanceof ClickZoomExcepted)
						startRightClickZoom(t);					
				}
				else {
					rightClickZoom = false;
				}

				if (rightClickZoom) {
					sightDelta.set(spaceTime.video().getTarget());
					sightDelta.subtract(spaceTime.video().getPosition());
					sightDelta.normalize();
				
					double maxZoomOutSpeed = getZoomOutSpeed();
					double fullSpeedTime = 1.0;
					double rightClickZoomTime = t - rightClickZoomStart;
					double zoomOutSpeed = Math.min(maxZoomOutSpeed, maxZoomOutSpeed * (rightClickZoomTime / fullSpeedTime));
					sightDelta.multiply(-zoomOutSpeed * dt);


					spaceTime.video().getPosition().add(sightDelta);
					spaceTime.video().getTarget().add(sightDelta);					
				}
				
//				if (clicked) {
//					updateDelta();
//				
//					sightDelta.set(spaceTime.video().getTarget());
//					sightDelta.subtract(spaceTime.video().getPosition());
//					sightDelta.normalize();
//					
//					double dy = deltaPos.y();
//					double d = Math.signum(dy) * dy * dy *getDragSpeed()*dt; 
//					sightDelta.multiply(d);
//					
//					spaceTime.video().getPosition().add(sightDelta);
//					spaceTime.video().getTarget().add(sightDelta);					
//				}				
				return 0;
			}

			private void startRightClickZoom(double t) {
				if (!rightClickZoom) {
					rightClickZoom = true;
					rightClickZoomStart = t;
				}				
			}
		});

	}

	protected double getZoomOutSpeed() {
		return 150.0;
	}

	protected void updateDelta() {
		deltaPos.set(spaceTime.pointer().getPositionRelativeToScreen());
		deltaPos.subtract(startPos);							
	}

	protected double getDragThreshold() {
		return 0.02;
	}


}
