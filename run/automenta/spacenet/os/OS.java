package automenta.spacenet.os;

import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.space.Space;
import automenta.spacenet.var.index.MemoryIndex;

/** operating shell, or operating system interface */
public interface OS {

	/** procedures for representing arbitrary objects as spatials */
	public ActionIndex<Object,Space> views();

	/** procedures for operating upon and amongst objects */
	public ActionIndex actions();

	/** provides links according to procedures, heuristics, and inferences */
	public Linker linker();

	public MemoryIndex index();

}
