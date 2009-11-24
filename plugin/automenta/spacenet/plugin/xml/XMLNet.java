package automenta.spacenet.plugin.xml;


/** net containing XML data.  interfaces to Javolution SAX reader */
public class XMLNet  {
//	private static final Logger logger = Logger.getLogger(XMLNet.class);
//	private automenta.spacenet.var.net.Node rootNode;
//
////	public class XMLNetContentHandler extends DefaultHandler {
////
////		Stack<XMLNode> stack = new Stack();
////
////
////		@Override public void endDocument() throws SAXException {
////			super.endDocument();
////			logger.info("end");
////		}
////
////
////		@Override public void startDocument() throws SAXException {
////			super.startDocument();
////			logger.info("start");
////		}
////
////		@Override public void startElement(CharArray uri, CharArray localName, CharArray qName, Attributes atts) throws SAXException {
////			super.startElement(uri, localName, qName, atts);
////
////			XMLNode e = new XMLNode(XMLNet.this, uri, localName, qName, atts );
////			XMLNet.this.addNode(e);
////
////			stack.push(e);
////		}
////
////
////	}
//
//	public XMLNet(URI u) throws IOException, SAXException {
//		super();
//
//
//		InputStream i = new FileInputStream(new File(u));
//		InputSource is = new InputSource( i );
//
//		NonValidatingConfiguration config = new NonValidatingConfiguration();
//		DOMParser p = new DOMParser(config ) {
//
//		};
//		p.parse(is);
//
//		refresh(p.getDocument());
//	}
//
//	private void refresh(Document document) {
//		//TODO remove all nodes and links
//
//		rootNode = refresh((Node)document);
//
////		NodeList children = document.getChildNodes();
////		for (int i = 0; i < children.getLength(); i++) {
////			refresh(children.item(i));
////		}
//
//		System.out.println(this);
//
//
//	}
//
//	private XMLNode refresh(Node n) {
//		XMLNode e = new XMLNode(XMLNet.this, n.getNodeName(), n.getAttributes(), n.getTextContent() );
//		addNode(e);
//
//		//add Attributes
//		NamedNodeMap atts = n.getAttributes();
//		if (atts!=null) {
//			for (int i = 0; i < atts.getLength(); i++) {
//				Node a = atts.item(i);
//				XMLNode an = refresh(a);
//				addLink(new XMLLink(e, an), e, an);
//			}
//		}
//
//		//add Children
//		NodeList children = n.getChildNodes();
//		for (int i = 0; i < children.getLength(); i++) {
//			XMLNode c = refresh(children.item(i));
//			addLink(new XMLLink(e, c), e, c);
//		}
//
//
//		return e;
//	}
//
//	public XMLNet(URL resource) throws IOException, SAXException, URISyntaxException {
//		this(resource.toURI());
//	}
//
//	public XMLNet(String url) throws MalformedURLException, IOException, SAXException, URISyntaxException {
//		this(new URL(url));
//	}
//
//	public automenta.spacenet.var.net.Node getRoot() {
//		return rootNode;
//	}
//
}
