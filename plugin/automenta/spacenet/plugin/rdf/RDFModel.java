package automenta.spacenet.plugin.rdf;


/** basic interface to OpenRDF sesame graphs */
abstract public class RDFModel {

//	private static final Logger logger = Logger.getLogger(RDFNet.class);
//	protected SailRepository rep;
//
//	public RDFModel(SailRepository rep)  {
//		super();
//
//		this.rep = rep;
//
//		init();
//	}
//
//	public RDFModel() throws RepositoryException {
//		super();
//
//		rep = new SailRepository(
//				new ForwardChainingRDFSInferencer(
//						new MemoryStore()));
//
//		rep.initialize();
//
//		init();
//	}
//
//	@Override public void dispose() {
//		try {
//			rep.shutDown();
//		} catch (RepositoryException e) {
//			logger.error(e);
//		}
//	}
//
//	protected void init() {
//
//	}
//
//	public void addFile(URL u, String baseURI) throws Exception {
//
//		try {
//			RepositoryConnection con = newRepositoryConnection();
//			try {
//				con.add(u, baseURI, RDFFormat.RDFXML);
//			}
//			finally {
//				con.close();
//			}
//		}
//		catch (Exception e) {
//			throw e;
//		}
//
//	}
//
//	private RepositoryConnection newRepositoryConnection() throws RepositoryException {
//		return getRepository().getConnection();
//	}
//
//	private SailRepository getRepository() {
//		return rep;
//	}
//
//	public void foreachResource(final ResourceVisitor resourceVisitor) throws RepositoryException {
//		final Map<Value, Statement> visitedResources = new HashMap();
//		foreachStatement(new StatementVisitor() {
//			@Override public void onStatement(RDFModel model, Statement s) {
//				updateResource(s.getSubject(), s);
//				updateResource(s.getPredicate(), s);
//				if (s.getObject() instanceof Resource) {
//					updateResource((Resource)s.getObject(), s);
//				}
//				else if (s.getObject() instanceof Value) {
//					updateValue((Value)s.getObject(), s);
//				}
//				if (s.getContext()!=null)
//					updateResource(s.getContext(), s);
//			}
//
//			private void updateValue(Value v, Statement s) {
//				if (visitedResources.get(v) == null) {
//					resourceVisitor.onValue(RDFModel.this, s, v);
//					visitedResources.put(v, s);
//				}
//
//			}
//
//			private void updateResource(Resource r, Statement s) {
//				if (visitedResources.get(r) == null) {
//					resourceVisitor.onResource(RDFModel.this, r);
//					visitedResources.put(r, s);
//				}
//			}
//		});
//		visitedResources.clear();
//	}
//
////	public interface LiteralVisitor {
////		public void onLiteral(RDFModel model, Statement s, Literal value);
////	}
////
////	public void foreachLiteral(final LiteralVisitor literalVisitor) throws RepositoryException {
////		foreachResource(new ResourceVisitor() {
////			@Override public void onResource(RDFModel model, Resource r) {
////
////			}
////		});
////	}
//
//	public void foreachStatement(StatementVisitor<N, L> v) throws RepositoryException {
//		foreachStatement(v, null, null, null);
//	}
//
//	public void foreachStatement(StatementVisitor<N, L> v, Resource subject, URI predicate, Value object /* contexts */) throws RepositoryException {
//
//		RepositoryConnection con = newRepositoryConnection();
//		try {
//
//			RepositoryResult<Statement> result = con.getStatements(subject, predicate, object, true);
//
//			while (result.hasNext()) {
//				v.onStatement(this, result.next());
//			}
//		}
//		finally {
//			con.close();
//		}
//
//	}
//
//	public List<Statement> getStatements(Object object, Object object2, Object object3) throws RepositoryException {
//		final LinkedList<Statement> statements = new LinkedList<Statement>();
//
//		foreachStatement(new StatementVisitor<N, L>() {
//			@Override public void onStatement(RDFModel model, Statement s) {
//				statements.add(s);
//			}
//		});
//
//		return statements;
//
//	}
//
//	private void closeConnection(RepositoryConnection con) throws RepositoryException {
//		con.close();
//	}
//
//	public void clear() {
//
//		try {
//			RepositoryConnection con = newRepositoryConnection();
//
//			con.remove((Resource)null, null, null);
//
//			con.close();
//		}
//		catch (Exception e) {
//			logger.error(e);
//		}
//
//	}
//
//	public String toN3() {
//		StringWriter sw = new StringWriter();
//
//		try {
//			RepositoryConnection con = newRepositoryConnection();
//
//			RDFHandler rdfxmlWriter = new N3Writer(sw);
//
//			con.export(rdfxmlWriter);
//
//			con.close();
//		}
//		catch (Exception e) {
//			return e.toString();
//		}
//
//		return sw.toString();
//	}
//
//	static public <X> List<? extends X> toList(Iterator<? extends X> iterateNodes) {
//		return IteratorUtils.toList(iterateNodes);
//	}

}