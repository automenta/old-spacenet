package automenta.spacenet.os;


import automenta.spacenet.act.ParallelScheduler;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.jme.JmeSpacetime;
import automenta.spacenet.space.jme.video.JmeVideoState;
import automenta.spacenet.space.jme.video.WindowJme;
import automenta.spacenet.space.jme.video.control.JmeKeyboard;
import automenta.spacenet.space.jme.video.control.JmePointer;
import automenta.spacenet.space.object.widget.WidgetContext;
import automenta.spacenet.space.video3d.SmoothSkeetVideo3D;


public class DefaultJmeWindow extends JmeSpacetime {	
	
	public DefaultJmeWindow() {
		this(null);
	}

	public DefaultJmeWindow(Space volumeContent) {
		super(WindowJme.class, JmePointer.class, JmeKeyboard.class, ParallelScheduler.class, null, newVideo3D(), newWidgetContext(), newJmeVideoState());

        add(new DefaultZoomControls());		

        if (volumeContent!=null)
            volume().add(volumeContent);
	}



	static protected WidgetContext newWidgetContext() {
		return new DefaultWidgetContext1();
	}
	
		
	static protected SmoothSkeetVideo3D newVideo3D() {
		double cameraSpeed = 0.05;
		double skeetFactor = 0.1;
		return new SmoothSkeetVideo3D(cameraSpeed, skeetFactor);
	}


	static protected JmeVideoState newJmeVideoState() {
		JmeVideoState v = new JmeVideoState();

		v.getSubsamples().set(1);
		v.getUpdatePeriod().set(1.0 / 50.0);
		v.getWindowTitle().set("SpaceNet");

		v.getAlphaBits().set(16);
		v.getDepthBits().set(16);
		v.getStencilBits().set(16);
		v.getFullScreen().set(false);
		v.getLightsEnabled().set(true);
		v.getAmbientLightLevel().set(0.6);
		
		//TODO use ObjectVar to access & set these
		//v.getTextLineVisibleProportion().set(0.0025);
		//v.getCharNodeVisibleProportion().set(0.002);
		
		return v;
	}

}
