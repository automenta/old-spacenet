package automenta.spacenet.space;



import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.audio.Audio;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.video3d.Video3D;

/** audiovisual multimedia space experience */
public interface Spacetime {
	
	public Video3D video();
	public Audio audio();	
	public Space volume();
	public Box face();
	public Space sky();
	public Pointer pointer();
	public Keyboard keyboard();
	public Scheduler time();

}
