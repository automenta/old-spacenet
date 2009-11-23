package automenta.spacenet.space.jme.video.filter;

import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;

import com.jme.system.DisplaySystem;


/** assumes responsibilities formerly held by JME's "BasicPassManager" to support
 *  dynamic reconfiguration of the render pipeline.  'flow' refers to 'dataflow' which
 *  suggests the possibility of non-linearity and branching/merging, rather than 
 *  'pass' which seems more linear/sequential.
 */
public class RenderFlow  {

	private ListVar<RenderAction> procedure = new ListVar(); //ListVar.newLinkedList(RenderAction.class);
	private Jme jme;
	private IfCollectionChanges<RenderAction> whenProcedureChanges;
	
	public RenderFlow(final Jme jme) {
		super();
		this.jme = jme;
		
		whenProcedureChanges = jme.add(new IfCollectionChanges<RenderAction>(procedure) {
			@Override public void afterObjectsAdded(CollectionVar list, RenderAction[] added) {
				for (RenderAction ra : added) {
					ra.start(jme);
				}
			}

			@Override public void afterObjectsRemoved(CollectionVar list, RenderAction[] removed) {
				for (RenderAction ra : removed) {
					ra.dispose();
				}				
			}

			@Override public String toString() {
				return "noticing changes in rendering";
			}
		});
	}
	
	public void render(DisplaySystem display) {
		for (RenderAction ra : procedure) {
			ra.render(display);
		}
	}

	public void forward(float tpf) {
		for (RenderAction ra : procedure) {
			ra.forward(tpf);
		}
	}

	/** the rendering procedure, as an editable sequence of RenderActions that are 
	    performed each video frame */
	public ListVar<RenderAction> getProcedure() {
		return procedure;
	}

	public void dispose() {
		if (procedure!=null) {
			for (RenderAction ra : procedure) {
				ra.dispose();			
			}
			procedure.clear();
			procedure = null;
		}
		
	}
}
