/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.plugin.xml;

/**
 *
 * @author seh
 */
public abstract class XMLAgent {

//    private XMLNode rootNode;
//
//    public XMLAgent(URI u) throws Exception {
//        super();
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(u.toURL().openStream()));
//
//
//        InputSource is = new InputSource(in);
//
//        NonValidatingConfiguration config = new NonValidatingConfiguration();
//
//        DOMParser p = new DOMParser(config) {
//        };
//        p.parse(is);
//
//        refresh(p.getDocument());
//    }
//
//    protected void refresh(Document document) {
//        //TODO remove all nodes and links
//
//
//        refresh((Node) document);
//
//
//
//    }
//
//    protected void refresh(Node n) {
//        //onNode(n.getNodeName(), n.getAttributes(), n.getTextContent() );
//        onNode(n);
//
//        //add Attributes
//        NamedNodeMap atts = n.getAttributes();
//        if (atts != null) {
//            for (int i = 0; i < atts.getLength(); i++) {
//                Node a = atts.item(i);
//                refresh(a);
//            }
//        }
//
//        //add Children
//        NodeList children = n.getChildNodes();
//        for (int i = 0; i < children.getLength(); i++) {
//            refresh(children.item(i));
//        }
//
//    }
//
//    abstract protected void onNode(Node node);
}
