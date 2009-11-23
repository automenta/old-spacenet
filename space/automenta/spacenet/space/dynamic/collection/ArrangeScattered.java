package automenta.spacenet.space.dynamic.collection;

import automenta.spacenet.Maths;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.var.vector.Vector3;

public class ArrangeScattered /*extends AbstractArrangement<SpaceNode>*/ {

//	private double maxVelocity;
//	private double fillDensity;
//	private final static String transformKey = new String(ArrangeScattered.class.toString());
//	private Vector3 scatterVolume;
//
//	public ArrangeScattered(SpaceNode space, double fillDensity, final double maxVelocity) {
//		this(space, new Vector3(1,1,1), fillDensity, maxVelocity);
//	}
//
//	public ArrangeScattered(SpaceNode space, Vector3 scatterVolume, double fillDensity, final double maxVelocity) {
//		super();
//		this.scatterVolume = scatterVolume;
//		this.maxVelocity = maxVelocity;
//		this.fillDensity = fillDensity;
//	}
//
//	@Override protected void onArrangedNodeAdded(SpaceNode e) {
//		if (isArrangeable(e)) {
//			e.addState(transformKey, new TransformBox(e, nextTargetBox(e), maxVelocity, maxVelocity*4.0));
//		}
//	}
//
//	@Override public void onDisabled() {
//		for (SpaceNode s : nodes) {
//			s.removeState(transformKey);
//		}
//		super.onDisabled();
//	}
//
//	protected boolean isArrangeable(SpaceNode e) {
//		return true;
//	}
//
//	@Override protected void onArrangedNodeRemoved(Space e) {
//		e.removeState(transformKey);
//	}
//
//	protected Box nextTargetBox(Space e) {
//		double dim = fillDensity;
//
//		//TODO apply fillDensity
//		double w = 0.5 * Maths.random(0, dim) * scatterVolume.x();
//		double h = 0.5 * Maths.random(0, dim) * scatterVolume.y();
//		double d = 0.5 * Maths.random(0, dim) * scatterVolume.z();
//
//		double x = 0.5 * Maths.random(-1.0, 1.0) * scatterVolume.x() /** (1.0-w)*/;
//		double y = 0.5 * Maths.random(-1.0, 1.0) * scatterVolume.y() /** (1.0-h)*/;
//		double z = 0.5 * Maths.random(-1.0, 1.0) * scatterVolume.z() /**(1.0-d)*/;
//
//		Box b = new Box(x, y, z, w, h, d);
//		return b;
//	}
//
//

}
