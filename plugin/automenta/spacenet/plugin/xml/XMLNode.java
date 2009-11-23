package automenta.spacenet.plugin.xml;

import org.w3c.dom.NamedNodeMap;

import automenta.spacenet.var.net.memory.MemoryNode;

public class XMLNode extends MemoryNode<XMLNode, XMLLink> {

	private String name;
	private NamedNodeMap atts;
	private String content;

	public XMLNode(XMLNet net, String localName, NamedNodeMap atts, String content) {
		super(net, null);
		
		this.name = localName;
		this.atts = atts;
		this.content = content;
	}

	@Override public XMLNode getValue() { 	return this;	}

	@Override public String toString() {
		if (getName().equals("#text"))
			return getContent();
		
		String s = new String(name);
		
		if (content!=null)
			if (content.length() > 0)
				s += " { " + content + " }";
		return s;
	}

	private String getContent() {
		return content;
	}

	private String getName() {
		return name;
	}
}
