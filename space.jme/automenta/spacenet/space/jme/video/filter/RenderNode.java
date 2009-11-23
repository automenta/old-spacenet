package automenta.spacenet.space.jme.video.filter;

import com.jme.renderer.pass.Pass;
import com.jme.renderer.pass.RenderPass;
import com.jme.scene.Node;

public class RenderNode extends PassAction {

	private RenderPass renderNode;
	private Node[] nodes;

	public RenderNode(Node... nodes) {
		super();
		
		this.nodes = nodes;
		renderNode = new RenderPass();
		for (Node n : nodes)
			renderNode.add(n);
		
	}

	@Override
	public Pass getPass() {
		return renderNode;
	}

	
	@Override public void forward(float tpf) {
		//node.updateGeometricState(tpf, true);

		super.forward(tpf);
	}

	@Override public void stop() {
		
	}



}
