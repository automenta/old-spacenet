package automenta.spacenet.space.jme.video;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections15.Predicate;
import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Scope;
import automenta.spacenet.space.HasPosition3;
import automenta.spacenet.space.HasSize3;
import automenta.spacenet.space.HasSurface;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.HasOrientation;
import automenta.spacenet.space.jme.geom.BoxNode;
import automenta.spacenet.space.jme.geom.RectNode;
import automenta.spacenet.space.jme.video.action.UpdateBitmapSurface;
import automenta.spacenet.space.jme.video.action.UpdateColorSurface;
import automenta.spacenet.space.jme.video.action.UpdateGLSLSurface;
import automenta.spacenet.space.surface.BitmapSurface;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.surface.GLSLSurface;
import automenta.spacenet.space.video2d.HasSize2;
import automenta.spacenet.var.IfChanges;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.map.WhenMapValuesChange;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfBoolChanges;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingVolume;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState.StateType;


/** after SpaceNode attaches to its parent, then it attempts to start its local space in nearest parent space.
 * 	if it has no local space, it executes actions in it's parents space, recursively.
 *
 * 	before SpaceNode detaches from parent, it attempts to stop its local space.
 * 
 */
public class JmeNode extends Node implements Disposable {
	private static final Logger logger = Logger.getLogger(JmeNode.class);

	public static BlendState getBlendState(JmeNode spatial) {
		BlendState bs = (BlendState) spatial.getRenderState(StateType.Blend);
		if (bs == null) {
			bs = spatial.getJme().getDisplaySystem().getRenderer().createBlendState();
		}
		return bs;		
	}
	public static MaterialState getMaterialState(JmeNode spatial) {        
		MaterialState bs = (MaterialState) spatial.getRenderState(StateType.Material);
		if (bs == null) {
			bs = spatial.getJme().getDisplaySystem().getRenderer().createMaterialState();
		}
		return bs;		
	}

	@Deprecated public static <S> S getParent(Spatial s, Class<? extends S> class1) {
		Spatial c = s;
		while (c!=null) {
			if (class1.isInstance(c))
				return (S)c;

			c = c.getParent();			
		}
		return null;
	}

	public static <S> S visitParent(Spatial s, Class<? extends S> class1, Predicate<S> continueVisit) {
		Spatial c = s;
		while (c!=null) {
			if (class1.isInstance(c)) {
				if (!continueVisit.evaluate((S)c)) {
					return (S)c;					
				}
			}
			c = c.getParent();			
		}
		return null;

	}

	protected boolean nodeVisible = true;

	private boolean started;

	private WhenMapValuesChange whenSpaceContentsChange;

	public final Quaternion rotQuat = new Quaternion();

	final public Space space;

	final float absAngles[] = new float[3];

	final public JmeNodeBuilder model;

	final public Jme jme;
    
	private JmeNodeBuilder parentModel;

    private Jme parentJme;

	//temporary vectors TODO allocate as necessary
	private final Vector3f vA = new Vector3f();
	private final Vector3f vB = new Vector3f();
	private final Vector3f vC = new Vector3f();
	private final Vector3f vD = new Vector3f();

	private IfChanges<Surface> whenSurfaceChanges;

	private boolean updateRequired;

	private IfVector3Changes whenPositionChanges;

	private IfVector3Changes whenSize3Changes;

	private IfVector2Changes whenSize2Changes;

	private IfBoolChanges whenVisiblityChanges;

	private IfVector3Changes whenOrientationChanges;

	private boolean setNextAbsolutePosition;
	private boolean setNextAbsoluteOrientation;
	private boolean setNextAbsoluteSize;

	public JmeNode(final Jme jme, Space space, JmeNodeBuilder model) {
		super();


		this.jme = jme;
		this.space = space;
		this.model = model;				

		setRenderQueueMode(getDefaultRenderQueue());

		Jme.setZOrderState(this);

	}

	public JmeNode(Space contentNode) {
		this(contentNode, null);
	}


	public JmeNode(Space contentNode, JmeNodeBuilder model) {
		this(null, contentNode, model);		
	}


	//	private synchronized void startUnstarted() {
	//		if (unstarted!=null) {
	//			for (JmeNode j : unstarted) {
	//				j.startIn(this);
	//			}
	//			unstarted.clear();
	//			unstarted = null;
	//		}
	//		
	//	}

	public void attachAndDetach(final Collection attached, final Collection detached) {
		if ((attached==null) && (detached==null))
			return;

		Jme.doLater(new Runnable() {
			@Override public void run() {
				if (detached!=null) {
					for (Object o : detached) {
						if (o instanceof Spatial) {
							Spatial s = (Spatial) o;

							getChildContainer().detachChild(s);
						}
					}


				}			

				if (attached!=null) {
					for (Object o : attached) {
						if (o instanceof Spatial) {
							Spatial s = (Spatial) o;

							getChildContainer().attachChild(s);

							//s.updateGeometricState(0.0f, true);
							s.updateRenderState();
						}
					}



				}

				updateGeometricState(0.0f, true);
				updateModelBound();

			}

		});


	}


	protected void attachSpatials(final Spatial... spatials) {
		Jme.doLater(new Runnable() {
			@Override public void run() {
				for (Spatial s : spatials) {
					getChildContainer().attachChild(s);
					s.updateRenderState();
				}

				updateGeometricState(0.0f, true);
				updateModelBound();

			}			
		});

	}


	protected synchronized void requireUpdateGeometry() {		
		if (!updateRequired) {
			updateRequired = true;
			Jme.doLater(new Runnable() {
				@Override public void run() {
					if (updateRequired) {
						updateRequired = false;
						updateGeometricState(0.0f, true);
					}
				}				
			});
		}		
	}

	protected void beforeStartJme(JmeNode parent) {	
		if (getSpace()!=null) {
			whenVisiblityChanges = getSpace().add(new IfBoolChanges(getSpace().getVisible()) {
				@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
					requireUpdateGeometry();
				}				
			});
			whenSpaceContentsChange = getSpace().add(new WhenMapValuesChange(getSpace().getObjects()) {
				@Override public void afterMapAdds(Object... added) {
					whenSpaceChanges(added, null);					
				}
				@Override public void beforeMapRemoves(Object... removed) {
					whenSpaceChanges(null, removed);					
				}
				@Override public String toString() {
					return "updating " + JmeNode.this + " when " + getSpace() + " changes";
				}
			});			



			if (getSpace() instanceof HasPosition3) {
				final HasPosition3 h = (HasPosition3) getSpace();
				whenPositionChanges = getSpace().add(new IfVector3Changes(h.getPosition(), h.getNextAbsolutePosition()) {
					@Override public void afterVectorChanged(Vector3 v, double dx, 	double dy, double dz) {
						if (v == h.getNextAbsolutePosition()) {
							setNextAbsolutePosition = true;
						}
						requireUpdateGeometry();
					}					
				});				
			}
			if (getSpace() instanceof HasSize3) {
				final HasSize3 h = (HasSize3) getSpace();
				whenSize3Changes = getSpace().add(new IfVector3Changes(h.getSize(), h.getNextAbsoluteSize()) {
					@Override public void afterVectorChanged(Vector3 v, double dx, 	double dy, double dz) {
						if (v == h.getNextAbsoluteSize()) {
							setNextAbsoluteSize = true;
						}

						requireUpdateGeometry();
					}					
				});
			}
			else if (getSpace() instanceof HasSize2) {
				HasSize2 h = (HasSize2) getSpace();
				whenSize2Changes = getSpace().add(new IfVector2Changes(h.getSize()) {
					@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
						requireUpdateGeometry();
					}					
				});				
			}


			if (getSpace() instanceof HasOrientation) {
				final HasOrientation h = (HasOrientation)getSpace();
				whenOrientationChanges = getSpace().add(new IfVector3Changes(h.getOrientation()/*, h.getNextAbsoluteOrientation()*/) {
					@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
//						if (v == h.getNextAbsoluteOrientation()) {
//							setNextAbsoluteOrientation = true;
//						}
						requireUpdateGeometry();
					}					
				});
			}

			if (getSpace() instanceof HasSurface) {
				HasSurface h = ((HasSurface)getSpace());

				whenSurfaceChanges = getSpace().add(new IfChanges<Surface>(h.getSurface()) {
					@Override public void afterValueChange(ObjectVar o, Surface previous, Surface next) {
						setSurface(next);
					}			
				});

				setSurface(h.getSurface().get());
			}
		}


	}

	protected void afterStopJme(JmeNode parent) {
		if (whenSpaceContentsChange!=null) {
			getSpace().remove(whenSpaceContentsChange);
			whenSpaceContentsChange = null;
		}
		if (whenVisiblityChanges!=null) {
			getSpace().remove(whenVisiblityChanges);
			whenVisiblityChanges = null;
		}
		if (whenSurfaceChanges!=null) {
			getSpace().remove(whenSurfaceChanges);
			whenSurfaceChanges = null;
		}
		if (whenPositionChanges!=null) {
			getSpace().remove(whenPositionChanges);
			whenPositionChanges = null;
		}
		if (whenOrientationChanges!=null) {
			getSpace().remove(whenOrientationChanges);
			whenOrientationChanges = null;
		}
		if (whenSize3Changes!=null) {
			getSpace().remove(whenSize3Changes);
			whenSize3Changes = null;
		}
		if (whenSize2Changes!=null) {
			getSpace().remove(whenSize2Changes);
			whenSize2Changes = null;
		}
	}


	protected void detachSpatials(final Spatial... spatials) {
		Jme.doLater(new Runnable() {
			@Override public void run() {
				for (Spatial s : spatials) {
					getChildContainer().detachChild(s);
				}

				updateGeometricState(0.0f, true);
				updateModelBound();

				//not necessary to update render state for removed spatials
				//updateRenderState();

			}			
		});

	}


	@Override public void dispose() {
	}

	//	protected void whenSpaceChanges(Entry[] added, Entry[] removed) {
	//		List<Object> toDetach = null;
	//		List<Object> toAttach = null;
	//
	//		if (added!=null) {
	//			if (added.length > 0) {
	//				if (toAttach==null)	toAttach = new LinkedList();
	//				for (Entry e : added) {
	//
	//					Object v = e.getValue();
	//
	//					Object s = getModel().getAddedSpatial(this, v);
	//
	//					if (s!=null) 
	//						toAttach.add(s);
	//					else  {
	//						if (logger.isDebugEnabled())
	//							logger.debug("added " + v + " was not spatial-able");
	//					}
	//				}
	//			}
	//		}
	//
	//		if (removed!=null) {
	//			if (removed.length > 0) {
	//				if (removed!=null) {
	//					if (toDetach==null)	toDetach = new LinkedList();
	//					for (Entry e : removed) {
	//
	//						Object v = e.getValue();
	//
	//						Object s = getModel().getRemovedSpatial(this, v);
	//
	//						if (s!=null) 
	//							toDetach.add(s);
	//						else {
	//							if (logger.isDebugEnabled())
	//								logger.debug("removed " + v + " was not spatial-able");
	//						}
	//					}
	//				}
	//			}
	//		}
	//
	//		attachAndDetach(toAttach, toDetach);
	//
	//	}

	public BlendState getBlendState() {
		return getBlendState(this);
	}

	/** where added spatials are inserted/removed.  default=this node */
	protected Node getChildContainer() {
		return this;
	}

	public int getDefaultRenderQueue() {
		//return Renderer.QUEUE_SKIP;
		return Renderer.QUEUE_INHERIT;
		//return Renderer.QUEUE_OPAQUE;
	}

	//	private final void stopAttach(final Attach s) {
	//		s.getParent().detachChild(s.getChild());				
	//	}
	//
	//	private final void startAttach(final Attach s) {
	//		s.getParent().attachChild(s.getChild());				
	//	}

	public Jme getJme() {
		if (this.jme!=null)
			return this.jme;
        if (this.parentJme!=null)
            return this.parentJme;

		JmeNode j = getParent(getParent(), JmeNode.class);
		if (j!=null) {
			parentJme = j.getJme();
			return parentJme;
		}
		logger.error(this + " needs reference to Jme, but is not available in existing ancestry");
		return null;
	}

	public MaterialState getMaterialState() {
		return getMaterialState(this);
	}

	public JmeNodeBuilder getModel() {
		if (model == null) {			
			JmeNode jmeParent = getParent(getParent(), JmeNode.class);
			if (jmeParent!=null) {
				parentModel = jmeParent.getModel();
				return parentModel;
			}
			if (parentModel!=null) {
				return parentModel;
			}
			return null;
		}
		return model;
	}



	@Override
	public String getName() {
		String n = getClass().getSimpleName() + "@" + hashCode();
		if (getSpace()!=null) {
			n += "[" + getSpace().toString() + "]";
		}
		return n;

	}

	public Space getParentSpace() {	
		JmeNode j = getParent(getParent(), JmeNode.class);
		if (j!=null)
			return j.getSpace();
		return null;
	}

	public Space getSpace() {
		Space localSpace = this.space;
		if (localSpace!=null)
			return localSpace;
		return getParentSpace();
	}

	protected boolean isOrientationAdjustingRotation() {
		return false;
	}

	protected boolean isPositionAdjustingTranslation() {
		return false;
	}

	protected boolean isSizeAdjustingScale() {
		return false;
	}

	public boolean isStarted() { return started; }

	public boolean isTangible() {
		return getSpace().getTangible().b();
	}

	protected void nodeToSpace() {
		Scope s = getSpace();

		if ((s instanceof HasSize3) || (s instanceof HasSize2)) {
			BoundingVolume wb = getWorldBound();
			double ex=0, ey=0, ez=0;

			if (wb!=null) {
				if (wb instanceof OrientedBoundingBox) {
					OrientedBoundingBox b = (OrientedBoundingBox) wb;
					Vector3f e = b.getExtent();
					ex = e.x;
					ey = e.y;
					ez = e.z;

					if (s instanceof HasSize3)
						((HasSize3)s).getAbsoluteSize().set(ex, ey, ez);
					else if (s instanceof HasSize2)
						((HasSize2)s).getAbsoluteSize().set(ex, ey);
				}
				else if (wb instanceof BoundingBox) {
					BoundingBox b = (BoundingBox) wb;
					ex = b.xExtent;
					ey = b.yExtent;
					ez = b.zExtent;					

					if (s instanceof HasSize3)
						((HasSize3)s).getAbsoluteSize().set(ex, ey, ez);
					else if (s instanceof HasSize2)
						((HasSize2)s).getAbsoluteSize().set(ex, ey);

				}
				else {
					//logger.warn(this + " has un-handled bounding type: " + wb);
					//return;
				}

				if (s instanceof HasOrientation) {
					HasOrientation h = (HasOrientation) s;
					//getWorldRotation().toAngles(absAngles);

					Quaternion q = getWorldRotation();
					q.toAngles(absAngles);

					h.getAbsoluteOrientation().set(absAngles[0], absAngles[1], absAngles[2]);

				}
			}


		}
		if (getSpace() instanceof HasPosition3) {
			HasPosition3 hp = (HasPosition3) getSpace();
			Vector3f wt = getWorldTranslation();
			hp.getAbsolutePosition().set(wt.x, wt.y, wt.z);			
		}

	}

	@Override public void setParent(final com.jme.scene.Node parent) {		

		com.jme.scene.Node previousParent = getParent();
		com.jme.scene.Node nextParent = parent;

		if (previousParent == nextParent) {
			logger.error(this + " unnecessary setParent() to its current parent");
			return;
		}

		if (previousParent!=null) {
			JmeNode jParent;

			if (previousParent instanceof JmeNode)
				jParent = ((JmeNode)previousParent);			
			else 
				jParent = null;

			stopJme(jParent);
			afterStopJme(jParent);			
			started = false;

			if (logger.isDebugEnabled()) logger.debug(this + " stopped in " + previousParent);
		}

		JmeNode.super.setParent(nextParent);

		if (nextParent!=null) {
			if (logger.isDebugEnabled()) logger.debug(this + " starting in " + nextParent);



			final JmeNode jParent;
			if (nextParent instanceof JmeNode) {
				jParent = ((JmeNode)nextParent);
			}
			else {
				jParent = null;
			}


			if (getJme()!=null) {
				startIn(jParent);
			}
			else {
				logger.error(this + " started in parent without Jme reference: " + jParent);
			}
			//			else {
			//				if (jParent.unstarted==null)
			//					jParent.unstarted = new FastList();
			//				
			//				jParent.unstarted.add(this);
			//			}

		}


	}


	protected void setSurface(Surface s) {
		if (s instanceof Surface) {
			if (s instanceof ColorSurface) {
				getSpace().add(Surface.class, new UpdateColorSurface(this, ((ColorSurface)s)));
			}
			else if (s instanceof GLSLSurface) {
				getSpace().add(Surface.class, new UpdateGLSLSurface(this, ((GLSLSurface)s)));
			}
			else if (s instanceof BitmapSurface) {
				getSpace().add(Surface.class, new UpdateBitmapSurface(this, ((BitmapSurface)s)));
			}
		}		
	}

	protected void spaceToNode() {
		Scope s = getSpace();
		updateVisibility();


		if (setNextAbsolutePosition) {
			if ((s instanceof Rect) || (s instanceof Box)) {
				Vector3  ap = null;

				if (s instanceof Rect)
					ap = ((Rect)s).getNextAbsolutePosition();
				else if (s instanceof Box)
					ap = ((Box)s).getNextAbsolutePosition();


				Vector3f world = vA;
				vA.set((float)ap.x(), (float)ap.y(), (float)ap.z());
				getParent().worldToLocal(world, getLocalTranslation());
				Vector3f lt = getLocalTranslation();

				((HasPosition3)s).getPosition().set(lt.getX(), lt.getY(), lt.getZ());
				((HasPosition3)s).getAbsolutePosition().set(ap);

			}
			setNextAbsolutePosition = false;
		}

		if (setNextAbsoluteSize) {
			if (s instanceof Box) {
				Vector3 absSize = ((Box)s).getNextAbsoluteSize();

				//getParent().updateWorldVectors(false);


				Vector3f world = vA;
				Vector3f worldTR = vB;

				//compute distance from center to top right
				world.set(getWorldTranslation().x, 
						getWorldTranslation().y, 
						getWorldTranslation().z);

				worldTR.set(getWorldTranslation().x + (float)absSize.x()/1.0f, 
						getWorldTranslation().y + (float)absSize.y()/1.0f, 
						getWorldTranslation().z + (float)absSize.z()/1.0f);

				Vector3f local = vC;
				Vector3f localTR = vD;


				getParent().worldToLocal(world, local);
				getParent().worldToLocal(worldTR, localTR);


				double sx = 2.0 * (localTR.x - local.x);
				double sy = 2.0 * (localTR.y - local.y);
				double sz = 2.0 * (localTR.z - local.z);

				((Box)s).getSize().set(sx, sy, sz);
				((HasSize3)s).getAbsoluteSize().set(absSize);

			}

			setNextAbsoluteSize = false;
		}

		if (setNextAbsoluteOrientation) {
			//TODO implement
			setNextAbsoluteOrientation = false;
		}
			

		if (isOrientationAdjustingRotation()) {
			if (s instanceof HasOrientation) {
				Vector3 rot = ((HasOrientation)s).getOrientation();
				rotQuat.fromAngles((float)rot.x(), (float)rot.y(), (float)rot.z());
				getLocalRotation().set(rotQuat);
			}
		}


		if (isPositionAdjustingTranslation()) {
			if (s instanceof HasPosition3) {
				Vector3 position = ((HasPosition3)s).getPosition();

				getLocalTranslation().set((float)position.x(), (float)position.y(), (float)position.z());
			}
		}

		if (isSizeAdjustingScale()) {

			if (s instanceof HasSize2) {
				Vector2 size = ((HasSize2)s).getSize();
				//				getLocalScale().set(
				//						FastMath.abs((float)size.x()),
				//						FastMath.abs((float)size.y()), 
				//						FastMath.abs((float)((Math.max(size.x(), size.y())))));
				getLocalScale().set(
						FastMath.abs((float)size.x()),
						FastMath.abs((float)size.y()), 
						1.0f);

			}
			else if (s instanceof HasSize3) {

				Vector3 size = ((HasSize3)s).getSize();


				getLocalScale().set(
						FastMath.abs((float)size.x()), 
						FastMath.abs((float)size.y()), 
						FastMath.abs((float)size.z()));
			}

		}


//		if (s instanceof HasRenderOrder) {
//			HasRenderOrder h = (HasRenderOrder) s;
//			if (renderOrder != h.getRenderOrder()) {
//				renderOrder = h.getRenderOrder();
//				if (renderOrder == RenderOrder.Depth) {
//					Jme.setZOrderState(this);
//				}
////				else if (renderOrder == RenderOrder.Order) {
////					Jme.setDrawOrderState(this);					
////				}
//				else if (renderOrder == RenderOrder.Skip) { }
//			}
//		}
	}
	
	private void updateVisibility() {
		Space ss = getSpace();
		boolean v = ss.getVisible().b() && nodeVisible;
		if (!v) {
			setCullHint(CullHint.Always);
		}
		else if (v) {
			setCullHint(CullHint.Inherit);
		}

	}


	private void startIn(final JmeNode jParent) {
		//		Jme.doLater(new Runnable() {	
		//			@Override public void run() {
		started = true;


		beforeStartJme(jParent);

		startJme(jParent);


		//			}					
		//		});		
	}
	/** called after the node has been readied to participate in the spacenet */
	protected void startJme(JmeNode parent) { }

	/** called before the node is ready to be removed from the spacenet */
	protected void stopJme(JmeNode parent) {	}

	@Override
	public String toString() {
		String prefix = getClass().getSimpleName();
		if (getSpace()!=null) {
			return prefix + "[" + getSpace().toString() + "]";
		}
		else {
			return prefix + "[?]";
		}
	}

	//	protected void setOpacity(final double o) {
	//		Jme.doLater(new Runnable() {
	//			@Override public void run() {
	//				MaterialState bs = getMaterialState(JmeNode.this);
	//				
	//				bs.setMaterialFace(MaterialFace.FrontAndBack);
	//				
	//				ColorRGBA c = bs.getDiffuse();
	//				
	//				c.a = Math.max(c.a, (float)o);
	//				bs.setDiffuse(c);
	//				
	//				bs.setEnabled(true);
	//				
	//				setRenderState(bs);
	//				updateRenderState();
	//			}			
	//		});
	//	}

	//	public DoubleVar getOpacity() {
	//		return opacity;
	//	}

	@Override
	public void updateGeometricState(float time, boolean initiator) {
		spaceToNode();

		super.updateGeometricState(time, initiator);

		nodeToSpace();
	}


	//	@Override public void updateWorldVectors() {
	//		super.updateWorldVectors();
	//
	//
	//	}

	/** minimum visible screen proportion threshold before culling/unculling.  if =0, this is not checked  */
	public double getMinVisibleProportion() {
		return 0;
	}

	@Override
	public void onDraw(Renderer arg0) {
		if (getMinVisibleProportion() > 0) {
			if ((getSpace() instanceof HasPosition3) && (getSpace() instanceof HasSize2)) { 
				Vector3 p = ((HasPosition3)getSpace()).getAbsolutePosition();
				double distToCam = getJme().getVideo().getPosition().getDistance(p);

				//assumes maximal area as if CharNode was facing camera directly.  actual area will be lesser
				double nodeRad = ((HasSize2)getSpace()).getAbsoluteSize().getMinRadius();

				double visRad = getJme().getVisibleRadius(distToCam);

				double visRatio = nodeRad / visRad;

				if (visRatio < getMinVisibleProportion()) {
					if (nodeVisible) {
						nodeVisible = false;

						//requireUpdateGeometry();
					}
                    return;
				}
				else {
					if (!nodeVisible) {
						nodeVisible = true;
						
						requireUpdateGeometry();
					}
				}
			}
		}

		super.onDraw(arg0);
	}

	@Override public void updateWorldVectors() {


		super.updateWorldVectors();

		DoubleVar d = null;
		double v = 0;
		double alX = 0, alY = 0;
		if (this instanceof RectNode) {
			d = (((RectNode)this).getRect()).getAspectXY();
			alX = (((RectNode)this).getRect()).getAlignment().x();
			alY = (((RectNode)this).getRect()).getAlignment().y();
		}
		else if (this instanceof BoxNode) {
			d = (((BoxNode)this).getBox()).getAspectXY();
		}

		if (d == null)
			return;

		v = d.f();

		if (v == 0)
			return;

		//		float wx = worldScale.getX();
		//		float wy = worldScale.getY();

		double sx = worldScale.getX();
		double sy = worldScale.getY();
		double sz = worldScale.getZ();

		//original values
		double ox = sx;
		double oy = sy;
		double oz = sz;

		double px = 0, py = 0, pz = 0;

		double ex = 0, ey = 0, ez = 0;
		//constraint sx, sy according to aspect
		double n = sy/sx;
		if (v > n) {
			//visually taller, so shrink width & preserve height
			ex = 1.0 - (n/v);
			sx = (n/v) * sx;
		}
		else {
			//visually wider, so shrink height & preserve width
			ey = 1.0 - (v/n);
			sy = (v/n) * sy;
		}

		px = alX * (ox - sx)/2.0;
		py = alY * (oy - sy)/2.0;


		//rotate the delta vector by the absolute orientation
		vA.set((float)px, (float)py, (float)pz);		
		Quaternion q = worldRotation;
		q.multLocal(vA);
		px = vA.x;
		py = vA.y;
		pz = vA.z;

		//"correct" the scale
		worldScale.set((float)sx, (float)sy, (float)sz);		

		worldTranslation.addLocal((float)px, (float)py, (float)pz);

	}

	protected void whenSpaceChanges(Object[] added, Object[] removed) {
		List<Object> toDetach = null;
		List<Object> toAttach = null;

		if (added!=null) {
			if (added.length > 0) {
				if (toAttach==null)	toAttach = new LinkedList();
				for (Object v : added) {

					Object s = getModel().getAddedSpatial(this, v);

					if (s!=null) 
						toAttach.add(s);
				}
			}
		}

		if (removed!=null) {
			if (removed.length > 0) {
				if (removed!=null) {
					if (toDetach==null)	toDetach = new LinkedList();
					for (Object v : removed) {

						Object s = getModel().getRemovedSpatial(this, v);

						if (s!=null) 
							toDetach.add(s);
					}
				}
			}
		}

		attachAndDetach(toAttach, toDetach);

	}

}
