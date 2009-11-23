package automenta.spacenet.space.control;

import automenta.spacenet.Scope;
import automenta.spacenet.var.list.ListVar;

/** contains all information about a cursor's touch (pick intersections, what is mouseOver'd) */
abstract public class Touch {
	/** sorted list of nodes intersected, lowest index = closest */
	abstract public ListVar<Scope> getTouched();
}
