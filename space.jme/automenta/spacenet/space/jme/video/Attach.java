package automenta.spacenet.space.jme.video;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

public class Attach {

	private Node existingParent;
	private Spatial newChild;

	public Attach(Node existingParent, Spatial newChild) {
		this.existingParent = existingParent;
		this.newChild = newChild;
	}

	public Node getParent() {
		return existingParent;
	}

	public Spatial getChild() {
		return newChild;
	}
	
}
