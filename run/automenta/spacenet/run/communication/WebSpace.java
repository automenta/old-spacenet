/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automenta.spacenet.run.communication;

import automenta.spacenet.space.object.net.NetSpace;
import automenta.spacenet.plugin.comm.Message;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.plugin.web.HTMLAgent;
import automenta.spacenet.var.net.Node;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WebSpace extends NetSpace {

    private HTMLAgent htmlAgent;
    private final String url;
    int minTextLength = 16;
    int maxTextLength = 50;
    int maxNodes = 64;
    int numNodes = 0;

    public WebSpace(String url) {
        super();

        this.url = url;
    }
    Map<org.w3c.dom.Node, Node> domToNode = new HashMap();

    @Override public void start() {
        super.start();

        try {
//            htmlAgent = new HTMLAgent(new URI(url)) {
//
//                public Node getDomNode(org.w3c.dom.Node n) {
//                    return domToNode.get(n);
//                }
//
//                @Override protected void onText(org.w3c.dom.Node textNode, String text) {
//                    if (text.length() < minTextLength) {
//                        return;
//                    }
//
//                    if (text.length() >= maxTextLength) {
//                        text = text.substring(0, maxTextLength);
//                    }
//
//                    if (numNodes++ < maxNodes) {
//                        Node n = addNode(new Message(text));
//
//                        registerNode(textNode, n);
//                    }
//                }
//
//                @Override protected void onImage(org.w3c.dom.Node imgNode, String url) {
//                    if (numNodes++ < maxNodes) {
//                        try {
//                            Node n = addNode(new Message(url, new URL(url)));
//                            registerNode(imgNode, n);
//                        } catch (MalformedURLException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }
//
//                protected void registerNode(org.w3c.dom.Node textNode, Node n) {
//                    domToNode.put(textNode, n);
//
//                    Node parentNode = getDomNode(textNode.getParentNode());
//                    if (parentNode != null) {
//                        addLink("in", parentNode, n);
//                    }
//                }
//            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DefaultJmeWindow(new WebSpace("http://automenta.com"));
    }
}
