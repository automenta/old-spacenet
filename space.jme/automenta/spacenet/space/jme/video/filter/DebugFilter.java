package automenta.spacenet.space.jme.video.filter;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;
import com.jme.util.geom.Debugger;


public class DebugFilter implements RenderAction {

	private Node rootNode;

	public DebugFilter(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	@Override public void start(Jme c) {

	}
	
	@Override	public void forward(float tpf) {	}

	@Override public void render(DisplaySystem display) {
		
		Renderer renderer = display.getRenderer();
        /**
         * If showing bounds, draw rootNode's bounds, and the bounds of all its
         * children.
         */
        if ( showBounds() ) {
            Debugger.drawBounds( rootNode, renderer, true );
        }

        if ( showNormals() ) {
            Debugger.drawNormals( rootNode, renderer );
            Debugger.drawTangents( rootNode, renderer );
        }

	}

	private boolean showNormals() {
		return true;
	}

	private boolean showBounds() {
		return true;
	}


	@Override
	public void dispose() {
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
