package automenta.spacenet.os.widget;

import org.apache.log4j.Logger;

import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.act.PossibleAction;
import automenta.spacenet.os.Linker;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.geom3.Box;

/** metawindow containing an object panel */
public class ObjectWindow<I> extends MetaWindow  {
	static final Logger logger = Logger.getLogger(ObjectWindow.class);
	



	private ObjectPanel objectPanel;





	private I object;




	private ActionIndex<I, Object> actions;




	private ActionIndex<I, Space> views;




	private Linker linker;

	public ObjectWindow(I i, ActionIndex<I,Object> actions, ActionIndex<I,Space> views, Linker linker) {
		super();			

		this.object = i;
		this.actions = actions;
		this.views = views;
		this.linker = linker;
	}
	
	public ObjectWindow(Box box, I o, ActionIndex<I,Object> actions, ActionIndex<I, Space> views, Linker linker) {
		super(box);
		
		this.object = o;
		this.actions= actions;
		this.views = views;
		this.linker = linker;
	}

	public ObjectWindow(String title, I o, ActionIndex<I, Object> actions, ActionIndex<I, Space> views, Linker linker) {
		this(o, actions, views, linker);
		getName().set(title);
	}

	@Override public void start() {
		super.start();
		
		if (getName().length() == 0)		
			getName().set(getObject().toString());
		
		objectPanel = getContent().add(new ObjectPanel(object, actions, views, linker));
		//objectPanel.scale(contentScaleX, contentScaleY);

		
		setView(objectPanel.getDefaultView());
	}	


	public void setView(final PossibleAction j) {
		objectPanel.setView(j);
		
	}

	public ActionIndex<I, Object> getActions() {	return actions;	}
	public ActionIndex<I, Space> getViews() {	return views;	}
	public I getObject() {	return object;	}

	public ObjectPanel getObjectPanel() {
		return objectPanel;
	}

	
}
