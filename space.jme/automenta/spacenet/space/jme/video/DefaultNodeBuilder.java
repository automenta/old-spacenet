package automenta.spacenet.space.jme.video;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.TextLineRect;
import automenta.spacenet.space.geom3.BevelRect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Curve3D;
import automenta.spacenet.space.geom3.FlatLine3D;
import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.jme.geom.BevelRectNode;
import automenta.spacenet.space.jme.geom.BoxNode;
import automenta.spacenet.space.jme.geom.Curve3DNode;
import automenta.spacenet.space.jme.geom.FlatLine3DNode;
import automenta.spacenet.space.jme.geom.RectNode;
import automenta.spacenet.space.jme.geom.scalar.MetaballNode;
import automenta.spacenet.space.jme.geom.text.CharRectNode;
import automenta.spacenet.space.jme.geom.text.TextLineRectNode;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.text.TextRect3;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;


public class DefaultNodeBuilder implements JmeNodeBuilder {
	private static final Vector3f defaultRotationalAxisDirection = new Vector3f(0,0,1);

	private static Logger logger = Logger.getLogger(DefaultNodeBuilder.class);

	protected Map<Object, Object> objectSpatials = new HashMap();

	@Override	public Object getAddedSpatial(final JmeNode jmeNode, Object s) {
		Object created = create(jmeNode, s);
		if (created!=null) {
			objectSpatials.put(s, created);
		}
		return created;
	}

	@Override public Object getRemovedSpatial(JmeNode jmeNode, Object v) {
		Object spatial = objectSpatials.remove(v);

		return spatial;
	}

	protected Object create(final JmeNode jmeNode, Object s) {
		//These must be in order of most specific first to least specific last
		final Jme jme = jmeNode.getJme();

		if (s instanceof Spatial) {
			return (s);
		}

		if (s instanceof MetaballBox) {
			return new MetaballNode((MetaballBox)s);
		}
//		else if (s instanceof FlatLine3D) {
//			return new FlatLine3DNode((FlatLine3D)s);
//		}
		else if (s instanceof CharRect) {
			return new CharRectNode((CharRect)s);
		}
		else if (s instanceof TextLineRect) {
			return new TextLineRectNode((TextLineRect)s) {
				@Override public boolean isRectSurfaced() {
					return false;
				}				
			};
		}
		else if ((s instanceof TextRect) || (s instanceof TextRect3)) {
			return new RectNode((Rect)s) {
				@Override public boolean isRectSurfaced() {
					return false;
				}
			};
		}
		else if (s instanceof Curve3D) {
			return new Curve3DNode((Curve3D)s);
		}
		else if (s instanceof BevelRect) {
			return new BevelRectNode((BevelRect)s);
		}
		else if (s instanceof Box) {
			return new BoxNode((Box)s);
		}
		else if (s instanceof Rect) {
			return new RectNode((Rect)s);
		}
		else if (s instanceof Space) {
			return new JmeNode((Space)s, new DefaultNodeBuilder());
		}

		return null;
	}

//	private DynamicPhysicsNode getDynamicNode(PhysicsSpace jmePhysicsSpace, SpaceObject s) {
//		for (PhysicsNode pn : jmePhysicsSpace.getNodes()) {
//			Spatial d = pn.getChild(0);
//			if (d instanceof DynamicBoxNode) {
//				if (((DynamicBoxNode)d).getBox() == s) {
//					return ((DynamicBoxNode)d).getPhysicsNode();
//				}
//			}
//		}
//		return null;
//	}
//
//	private DynamicPhysicsNode getDynamicNode(SpaceObject source) {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
