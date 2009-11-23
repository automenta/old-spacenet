package automenta.spacenet.plugin.xml;

import automenta.spacenet.var.net.Link;
import automenta.spacenet.var.net.Node;

public class XMLLink implements Link<XMLNode, XMLLink> {

	private XMLNode from;
	private XMLNode to;

	XMLLink(XMLNode from, XMLNode to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Node<XMLNode, XMLLink> getFirst() {
		return from;
	}

	@Override
	public Node<XMLNode, XMLLink> getLast() {
		return to;
	}

	@Override
	public Node<XMLNode, XMLLink>[] getNodes() {
		return new Node[] { from, to };
	}

	@Override public XMLLink getValue() {
		return this;
	}

}
