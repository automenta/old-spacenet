package automenta.spacenet.run.graph.depr;



public class DemoClassHierarchy  {
//	private static final Logger logger = Logger.getLogger(DemoClassHierarchy.class);
//	
//	public class ClassNet extends OldMemoryNet implements Starts, Stops {
//
//		@Override
//		public void start() {
//			
////			add(new Simultaneous() {
//
////				@Override public Object call() throws Exception {
//					
////					Package[] packages = Package.getPackages();					
////					for (Package p : packages) {
////						addPackage(p);
////					}
//			
//			getJme().doLater(new Runnable() {
//
//				@Override
//				public void run() {
//					addClass(AbstractPlaneExample.class);
//					addClass(OldMemoryNet.class);					
//				}
//				
//			});
//
////					return null;
////				}
////
////
////			});
//			
//		}
//
//		private void addPackage(Package p) {
//			addNode(new StringVar(p.toString()));
//		}
//
//		private void addClass(Class c) {
//			StringVar classNode = new StringVar(c.getSimpleName());
//			addNode(classNode);
//			
//			Class superClass = c.getSuperclass();
//			Method[] methods = c.getMethods();
//			
//			for (Method me : methods) {
//				StringVar methodNode = new StringVar(me.getName());
//				
//				addNode(methodNode);
//				addLink(classNode, new StringVar("method"), methodNode );
//				logger.info(classNode +  " -> " + methodNode);
//				
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//
//		@Override
//		public void stop() {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
//	
//	@Override protected OldMemoryNet<Object, Object> newNet() {
//		final OldMemoryNet m = new ClassNet();
//		
//
//		return m;
//	}
//	
//
//	public static void main(String[] args) {		
//		new StartJmeWindowOld(new DemoClassHierarchy());
//	}
}
