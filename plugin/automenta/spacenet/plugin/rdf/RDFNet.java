package automenta.spacenet.plugin.rdf;




public class RDFNet {

}

///** Net interface to an RDF model */
//public class RDFNet<N extends RDFNode, L extends RDFLink> extends RDFEntityModel<N, L> implements Net<N, L> {
//	private static final Logger logger = Logger.getLogger(RDFNet.class);
//
//	private BidiMap<Value, Node<N,L>> nodes = new DualHashBidiMap<Value, Node<N,L>>();
//	private BidiMap<Statement, Link<N,L>> links = new DualHashBidiMap<Statement, Link<N,L>>();
//
//	public RDFNet() throws RepositoryException {
//		super();
//	}
//
//	@Override public void dispose() {
//		super.dispose();
//
//		if (nodes!=null) {
//			nodes.clear();
//			nodes = null;
//		}
//		if (links!=null) {
//			links.clear();
//			links = null;
//		}
//	}
//
////	protected void addSubject(ID subject) {
////		subjects.put(subject.getUURI(), subject);
////	}
//
//	//	public void addLink(OldLink l) {
//	//		SailRepositoryConnection con = rep.getConnection();
//	//		Resource subject = getResource(l.getSubject());
//	//		Resource predicate = getResource(l.getPredicate());
//	//		Value object = getResource(l.getObject());
//	//		con.add(subject, predicate, object, contexts);
//	//		con.close();
//	//	}
//
//	//	private Resource getResource(ID subject) {
//	//		Resource r = resources.get(subject);
//	//		if (r != null) {
//	//			return r;
//	//		}
//	//
//	//		try {
//	//			URI uri = rep.getValueFactory().createURI(subject.getUURI().toURI().toString());
//	//			resources.put(subject.getUURI(), uri);
//	//			return uri;
//	//		} catch (URISyntaxException e) {
//	//			logger.error(e);
//	//			e.printStackTrace();
//	//		}
//	//
//	//		return null;
//	//	}
//
//	@Override
//	public void addFile(URL u, String baseURI) throws Exception {
//		super.addFile(u, baseURI);
//
//		updateAll();
//	}
//
//
//	protected void updateAll() {
//		updateNodes();
//		updateLinks();
//	}
//
//	protected void updateNodes() {
//		try {
//			foreachResource(new ResourceVisitor() {
//				@Override public void onResource(RDFModel model, Resource r) {
//					Node<N, L> rdfNode = nodes.get(r);
//					if (rdfNode == null) {
//						rdfNode = newRDFNode(r);
//						nodes.put(r, rdfNode);
//					}
//					else {
//						//update node?
//					}
//				}
//
//				@Override
//				public void onValue(RDFModel model, Statement s, Value v) {
//					Node<N, L> rdfNode = nodes.get(v);
//					if (rdfNode == null) {
//						Link<N, L> rdfLink = links.get(s);
//						if (rdfLink == null) {
//							rdfLink = newRDFLink(s);
//							links.put(s, rdfLink);
//						}
//						else {
//							//update node?
//						}
//
//						rdfNode = newRDFNode(v);
//						nodes.put(v, rdfNode);
//					}
//					else {
//						//...
//					}
//
//				}
//			});
//		} catch (RepositoryException e) {
//			logger.error(e);
//		}
//	}
//
//	protected void updateLinks() {
//		try {
//			foreachStatement(new StatementVisitor() {
//				@Override public void onStatement(RDFModel model, Statement s) {
//					Link<N,L> rdfLink = links.get(s);
//					if (rdfLink == null) {
//						rdfLink = newRDFLink(s);
//						links.put(s, rdfLink);
//					}
//					else {
//						//update link?
//					}
//				}
//			});
//		} catch (RepositoryException e) {
//			logger.error(e);
//		}
//	}
//
//	protected Link<N, L> newRDFLink(final Statement s) {
//		return new RDFLink(s) {
//
//			@Override public RDFNode[] getContexts() {
//				//TODO
//				return null;
//			}
//
//			@Override public RDFNode getObject() {
//				return RDFNet.this.getNode(s.getObject());
//			}
//
//			@Override public RDFNode getPredicate() {
//				return RDFNet.this.getNode(s.getPredicate());
//			}
//
//			@Override public RDFNode getSubject() {
//				return RDFNet.this.getNode(s.getSubject());
//			}
//
//			@Override public Node<RDFNode, RDFLink> getFirst() {
//				return getSubject();
//			}
//
//			@Override public Node<RDFNode, RDFLink> getLast() {
//				return getPredicate();
//			}
//
//			@Override
//			public Node<RDFNode, RDFLink>[] getNodes() {
//				Node<RDFNode, RDFLink>[] array = (Node<RDFNode,RDFLink>[]) Array.newInstance(Node.class, 2);
//				array[0] = getSubject();
//				array[1] = getObject();
//				return array;
//			}
//
//			@Override public RDFLink getValue() {
//				return this;
//			}
//
//		};
//
//	}
//
//	@Override
//	public void clear() {
//		super.clear();
//
//		updateAll();
//	}
//
//	protected N getNode(Value object) {
//		return nodes.get(object).getValue();
//	}
//	protected L getLink(Statement statement) {
//		return links.get(statement).getValue();
//	}
//
//	protected Node<N, L> newRDFNode(Value v) {
//		return new RDFNode(v) {
//
//			private ListVar<Class> entityClasses;
//
//			@Override public String getComment() {
//				String s = "";
//				try {
//					List<L> labelLinks = RDFNet.this.getLinks(getValue(), "rdf-schema", "comment");
//					for (L l : labelLinks)
//						s += l.getObject() + " ; ";
//				}
//				catch (Exception e) {
//					logger.error(e);
//				}
//				return s;
//			}
//
//			@Override
//			public Object getEntity() {
//				if (getRDFValue() instanceof Resource) {
//					Resource r = (Resource)getRDFValue();
//					return RDFNet.this.getEntity(r);
//				}
//				return null;
//			}
//
//			@Override public ListVar getEntityClasses() {
//				if (entityClasses == null) {
//					entityClasses = new ListVar<Class>();
//				}
//
//				if (getRDFValue() instanceof Resource) {
//					Resource r = (Resource)getRDFValue();
//
//					entityClasses.clear();
//					for (Class i : RDFNet.this.getEntity(r).getClass().getInterfaces()) {
//						entityClasses.add(i);
//					}
//
//				}
//
//				return entityClasses;
//			}
//
//			@Override public ListVar getInLinks() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override  public String getLabel() {
//				String s = "";
//				try {
//					List<L> labelLinks = RDFNet.this.getLinks(getValue(), "rdf-schema", "label");
//					for (L l : labelLinks)
//						s += l.getObject() + " ; ";
//				}
//				catch (Exception e) {
//					logger.error(e);
//				}
//				return s;
//			}
//
//			@Override public ListVar getOutLinks() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//
//			@Override public RDFNode getValue() {
//				return this;
//			}
//
//			@Override
//			public Iterator iterateLinksIn() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public Iterator iterateLinksOut() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//		};
//	}
//
//
//
//	protected List<L> getLinks(RDFNode subject, final String predNamespaceFilter, final String predLocalFilter) throws RepositoryException {
//		final LinkedList<L> matches = new LinkedList();
//
//		Value v = subject.getRDFValue();
//		if (v instanceof Resource) {
//			Resource res = (Resource)v;
//
//			foreachStatement(new StatementVisitor() {
//				@Override public void onStatement(RDFModel model, Statement s) {
//					URI pred = s.getPredicate();
//
//					if (predLocalFilter!=null) {
//						if (!pred.getLocalName().contains(predLocalFilter)) {
//							return;
//						}
//					}
//					if (predNamespaceFilter!=null) {
//						if (!pred.getNamespace().contains(predNamespaceFilter)) {
//							return;
//						}
//					}
//
//					matches.add(getLink(s));
//				}
//			}, res, null, null);
//		}
//
//		return matches;
//	}
//
//	@Override public boolean containsLink(Link<N, L> l) {
//		return links.containsValue(l);
//	}
//
//	@Override public boolean containsNode(Node<N, L> n) {
//		return nodes.containsValue(n);
//	}
//
//	@Override public Iterator<? extends Link<? extends N, ? extends L>> iterateLinks() {
//		return links.values().iterator();
//	}
//
//	@Override public Iterator<Link<N, L>> iterateLinksInTo(Node<N, L> n) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Iterator<Link<N, L>> iterateLinksOutOf(Node<N, L> n) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Iterator<Node<N, L>> iterateNodes() {
//		return nodes.values().iterator();
//	}
//
//	/** if 2 nodes are supplied, creates a single statement.  if 3 or more nodes, then multiple statements connecting them with the given L (predicate) are created. */
//	@Override public Link<N, L> addLink(L value, Node<N, L>... nodes) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override public Node<N, L> addNode(N value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override public boolean removeLink(Link<N, L> l) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override public boolean removeNode(Node<N, L> n) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//}
