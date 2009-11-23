package automenta.spacenet.space.control;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer.PointerButton;
import automenta.spacenet.space.geom3.Sketch;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

abstract public class Sketching extends Scope implements StartsIn<Space>, Stops {

	private Pointer pointer;
	private PointerButton sketchButton = PointerButton.Center;
	private boolean sketching = false;
	private Vector3 startRealPosition;
	private Vector2 lastPixelPosition;
	private Vector3 lastRealPosition;
	private Sketch sketch;
	private DoubleVar pixelThreshold;
	
	public Sketching(DoubleVar pixelThreshold) {
		super();
		
		this.pixelThreshold = pixelThreshold;
		
	}

	@Override public void start(Space c) {
		this.pointer = getThe(Pointer.class);
		
		add(new IfVector2Changes(this.pointer.getPixelPosition()) {
			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
				Vector2 pixelPosition = pointer.getPixelPosition();
				Vector3 realPosition = pointer.getTriangleIntersection();

				if (!sketching) {
					if (pointer.getButton(sketchButton).b()) {
						startSketching(pixelPosition, realPosition);
						sketching = true;
					}
				}
				
				if (sketching) {
					updateSketch(pixelPosition, realPosition);

					if (!pointer.getButton(sketchButton).b()) {
						stopSketching(pixelPosition, realPosition);
						sketching = false;
					}
				}
			}			
		});
	}

	protected void updateSketch(Vector2 pixelPosition, Vector3 realPosition) {
		double pixelDistance = pixelPosition.getDistance(lastPixelPosition);
		
		
		if (pixelDistance > getPixelThreshold().d()) {
			sketch.add(adjustPosition(new Vector3(realPosition)));
			
			this.lastPixelPosition.set(pixelPosition);
			this.lastRealPosition.set(realPosition);
		}
	}
	
	protected Vector3 adjustPosition(Vector3 v) {
		return v;
	}

	abstract public void startSketch(Sketch s);
	abstract public void stopSketch(Sketch s);

	private DoubleVar getPixelThreshold() {
		return pixelThreshold ;
	}

	
	protected void stopSketching(Vector2 pixelPosition, Vector3 realPosition) {

		Space startNode = (Space) pointer.getTouchedNode().get();
		if (startNode instanceof Space) {
			sketch.getStopNode().set(startNode);
		}

		sketch.add(adjustPosition(new Vector3(realPosition)));
		
		stopSketch(sketch);
		sketch = null;
	}

	protected void startSketching(Vector2 pixelPosition, Vector3 realPosition) {
		this.startRealPosition = new Vector3(realPosition);
		this.lastPixelPosition = new Vector2(pixelPosition);
		this.lastRealPosition = new Vector3(realPosition);
		
		
		sketch = new Sketch();

		Space startNode = (Space) pointer.getTouchedNode().get();
		if (startNode instanceof Space) {
			sketch.getStartNode().set(startNode);
		}

		sketch.add(adjustPosition(new Vector3(startRealPosition)));


		startSketch(sketch);
	}

	@Override public void stop() {	}
	
}
