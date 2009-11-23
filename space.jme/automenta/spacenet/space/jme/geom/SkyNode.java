package automenta.spacenet.space.jme.geom;

import com.jme.scene.Skybox;

public class SkyNode extends Skybox {
	
	public SkyNode(double w, double h, double d) {
		super("Sky", (float)w, (float)h, (float)d);
	}
}
