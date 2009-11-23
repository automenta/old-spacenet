package automenta.spacenet.space.jme.geom.scalar;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.geom3.Sphere;
import automenta.spacenet.space.jme.geom.BoxNode;
import automenta.spacenet.space.jme.video.JmeNode;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;

public class MetaballNode extends BoxNode {

	private MetaballBox metabox;

	private Map<Sphere, IfVector3Changes> spherePositions = new HashMap();
	private Map<Sphere, IfDoubleChanges> sphereRadii = new HashMap();

	private MetaballMesh mesh;

	
	public MetaballNode(MetaballBox box) {
		super(box);
		
		this.metabox = box;
		
	}
	
	@Override protected void beforeStartJme(JmeNode parent) {
		super.beforeStartJme(parent);

		mesh = new MetaballMesh(this.metabox);
		attachChild(mesh);
		
		metabox.add(new IfCollectionChanges<Sphere>(metabox.getPoints()) {
			@Override public void afterObjectsAdded(CollectionVar list, Sphere[] added) {
				for (Sphere s : added) {
					IfVector3Changes positionChange = metabox.add(new IfVector3Changes(s.getPosition()) {
						@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
							requireUpdateGeometry();
						}
					});
					
					IfDoubleChanges radiusChange = metabox.add(new IfDoubleChanges(s.getRadius()) {
						@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
							requireUpdateGeometry();
						}						
					});
					spherePositions.put(s, positionChange);
					sphereRadii.put(s, radiusChange);

					requireUpdateGeometry();
				}
			}

			@Override  public void afterObjectsRemoved(CollectionVar list, Sphere[] removed) {
				for (Sphere s : removed) {
					IfVector3Changes positionChange = spherePositions.remove(s);
					IfDoubleChanges radiusChange = sphereRadii.remove(s);
					metabox.remove(positionChange);
					metabox.remove(radiusChange);					
				}
				requireUpdateGeometry();
			}
		});
		
	}
	
	@Override
	protected void afterStopJme(JmeNode parent) {		
		for (Sphere s : spherePositions.keySet()) {
			IfVector3Changes positionChange = spherePositions.get(s);
			IfDoubleChanges radiusChange = sphereRadii.get(s);
			metabox.remove(positionChange);
			metabox.remove(radiusChange);			
		}
		spherePositions.clear();
		spherePositions = null;
		sphereRadii.clear();
		sphereRadii = null;

		super.afterStopJme(parent);
	}
	
}
