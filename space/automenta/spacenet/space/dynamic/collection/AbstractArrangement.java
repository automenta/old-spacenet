package automenta.spacenet.space.dynamic.collection;

abstract public class AbstractArrangement/*<S extends SpaceNode> implements SpaceState<S>*/ {

//	protected S space;
//
//	//protected Map<SpaceNode, Animation> rectProcesses = new HashMap();
//	protected ListValue<SpaceNode> nodes = new ArrayListValue();
//	private boolean isInitialized = false;
//	private CollectionWatch rectsWatch;
//
//
//	public AbstractArrangement() {
//	}
//
//
//	public S getSpaceNode() { return space; }
//
//	/** nodes managed by this arrangement */
//	public ListValue<SpaceNode> getArrangedNodes() {
//		return nodes;
//	}
//
//
//	abstract protected void onArrangedNodeAdded(SpaceNode e);
//	abstract protected void onArrangedNodeRemoved(SpaceNode e);
//
//	protected void initializeArrangement() {
//		rectsWatch = new CollectionWatch(space.getChildrenList()) {
//
//			@Override
//			public void onCollectionAdded(Object e) {
//				if (e instanceof SpaceNode) {
//					if (acceptsNode((SpaceNode)e)) {
//						nodes.add((SpaceNode)e);
//						onArrangedNodeAdded((SpaceNode)e);
//					}
//				}
//			}
//
//			@Override
//			public void onCollectionRemoved(Object e) {
//				if (e instanceof SpaceNode) {
//					if (nodes.contains((SpaceNode)e)) {
//						nodes.remove((SpaceNode)e);
//						onArrangedNodeRemoved((SpaceNode)e);
//					}
//				}
//			}
//
//		};
//	}
//
//	protected boolean acceptsNode(SpaceNode e) {
//		return true;
//	}
//
//
////	@Override public double animate(double dt) {
////		if (!isInitialized) {
////			initializeArrangement();
////			isInitialized = true;
////		}
////
////		List<Object> finished = new LinkedList();
////
////		for (Object k : rectProcesses.keySet()) {
////			Animation a = rectProcesses.get(k);
////			double d = a.animate(dt);
////			if (d == -1)
////				finished.add(k);
////		}
////
////		for (Object o : finished) {
////			rectProcesses.remove(o);
////		}
////
////		if (rectProcesses.size() == 0)
////			return 0;
////		return updatePeriod;
////	}
//
//
//
//	@Override public void onDisabled() {
//
//	}
//
//	@Override
//	public void onEnabled(S s) {
//		this.space = s;
//		initializeArrangement();
//	}
//
//	public SpaceNode getSpace() { return space; }
}
