package automenta.spacenet.plugin.rdf;



public class RDFEntityModel {

}

///** adds entity management support (via OpenRDF Elmo) */
//public class RDFEntityModel<N extends RDFNode, L extends RDFLink> extends RDFModel<N,L> {
//	private static final Logger logger = Logger.getLogger(RDFEntityModel.class);
//
//	private ElmoModule module;
//	private SesameManagerFactory factory;
//	private SesameManager manager;
//
//	public RDFEntityModel() throws RepositoryException {
//		super();
//	}
//
//	@Override protected void init() {
//		super.init();
//
//		module = new ElmoModule();
//		module.addConcept(Thing.class);
//
//		module.addConcept(org.openrdf.concepts.rdfs.Class.class);
//
//		module.addConcept(Person.class);
//		module.addConcept(Agent.class);
//
//		factory = new SesameManagerFactory(module, rep);
//		manager = factory.createElmoManager();
//
//
//	}
//
//	@Override
//	public void dispose() {
//		manager.close();
//		factory.close();
//
//		super.dispose();
//	}
//
//
//	public Object getEntity(Resource r) {
//		return manager.find(r);
//	}
//
//	public Object getEntity(RDFNode n) {
//		Value v = n.getRDFValue();
//		if (v instanceof Resource) {
//			return getEntity((Resource) n.getRDFValue());
//		}
//		else {
//			logger.error(n + " is not resource");
//			//create a value entity?
//		}
//		return null;
//	}
//
//	public <X> List<? extends X> getEntities(Class<? extends X> c) {
//		LinkedList<X> l = new LinkedList<X>();
//		Iterable<? extends X> m = manager.findAll(c);
//		Iterator<? extends X> f = m.iterator();
//		return toList(f);
//	}
//
//}
