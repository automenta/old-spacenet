package automenta.spacenet.space.jme;

import automenta.spacenet.Root;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.Spacetime;
import automenta.spacenet.space.audio.Audio;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.jme.video.JmeVideoState;
import automenta.spacenet.space.object.widget.WidgetContext;
import automenta.spacenet.space.video3d.Video3D;

/** an instance of an interactive SpaceTime session, implemented via Jme */
public class JmeSpacetime extends Root implements Spacetime {

	private Box face;
	private Space volume;
	private Space sky;


	private Audio audio;
	private Video3D video;
	private Scheduler scheduler;
	private Pointer pointer;
	private Keyboard keyboard;
	private Jme jme;

//    private Class<? extends Jme> jmeClass;
//    private Class<? extends Pointer> pointerClass;
//    private Class<? extends Keyboard> keyboardClass;
//    private Class<? extends Scheduler> schedulerClass;
//    private Class<? extends Audio> audioClass;
//    private Video3D video3D;
//    private WidgetContext widgetContext;

	public JmeSpacetime(Class<? extends Jme> jmeClass, Class<? extends Pointer> pointerClass, Class<? extends Keyboard> keyboardClass, Class<? extends Scheduler> schedulerClass, Class<? extends Audio> audioClass, Video3D video3D, WidgetContext widgetContext, JmeVideoState videoState) {
		super();
		
		face = add(new Box() {
            @Override public String toString() {
                return "Face(Box)";
            }
        });
		//face.setRenderOrder(RenderOrder.Face);
		
		volume = add(new Space() {
            @Override public String toString() {
                return "Volume(Space)";
            }
        });

		sky = add(new Space() {
            @Override public String toString() {
                return "Sky(Space)";
            }
        });
		sky.tangible(false);	//cant touch the sky

		//Class<? extends Audio> audioClass = getAudioClass();
		if (audioClass!=null)
			audio = addThe(Audio.class, audioClass);
		else
			audio = null;

		scheduler = addThe(Scheduler.class, schedulerClass);
		
		video = addThe(Video3D.class, video3D);

		addThe(WidgetContext.class, widgetContext);
		
		addThe(JmeVideoState.class, videoState);

		jme = addThe(Jme.class, jmeClass);

		keyboard = addThe(Keyboard.class, keyboardClass);
		pointer = addThe(Pointer.class, pointerClass);

		setThe(Spacetime.class, this);

	}

    
    
//	protected Class<? extends Jme> getJmeClass() {      return jmeClass;    }
//	protected WidgetContext newWidgetContext() { return widgetContext; }
//
//	protected Class<? extends Pointer> getPointerClass() { return pointerClass; }
//	protected Class<? extends Keyboard> getKeyboardClass() { return keyboardClass; }
//	protected Video3D newVideo3D() { return video3D; }
//	protected JmeVideoState newJmeVideoState()  { return videoState; }
//	public Class<? extends Scheduler> getSchedulerClass() { return schedulerClass; }

	/** note: if returns null, audio is disabled */
//	protected Class<? extends Audio> getAudioClass() { return audioClass; }

	@Override public Audio audio() { return audio;	}
	@Override public Box face() { 	return face;	}
	@Override public Space sky() {	return sky;	}
	@Override public Space volume() { return volume;	}
	@Override public Keyboard keyboard() {	return keyboard;	}
	@Override public Pointer pointer() {	return pointer;	}
	@Override public Video3D video() {	return video;	}
	@Override public Scheduler time() {	return scheduler;}
		
//	public JmeVideoState getJmeVideoState() {
//		if (videoState == null)
//			videoState = newJmeVideoState();
//		return videoState;
//	}

	public Jme getJme() {
		return jme;
	}
	
}
