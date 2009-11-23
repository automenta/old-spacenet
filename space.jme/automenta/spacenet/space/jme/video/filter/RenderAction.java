package automenta.spacenet.space.jme.video.filter;

import automenta.spacenet.Disposable;
import automenta.spacenet.StartsIn;

import automenta.spacenet.space.jme.video.Jme;
import com.jme.system.DisplaySystem;


public interface RenderAction extends StartsIn<Jme>, Disposable {

	void render(DisplaySystem display);

	void forward(float tpf);

}
