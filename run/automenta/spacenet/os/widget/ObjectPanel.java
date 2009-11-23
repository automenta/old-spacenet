/**
 * 
 */
package automenta.spacenet.os.widget;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import automenta.spacenet.Disposable;
import automenta.spacenet.Starts;
import automenta.spacenet.act.ActionIndex;
import automenta.spacenet.act.PossibleAction;
import automenta.spacenet.os.Linker;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.data.ListRect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.ButtonAction;
import automenta.spacenet.space.object.widget.button.IconButton;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.IntegerVar;

/** panel that represents an object, with buttons for adjusting views and selecting actions applicable to a certain object */
public class ObjectPanel<I> extends Box implements Starts {

	private static final Logger logger = Logger.getLogger(ObjectPanel.class);

	private IconButton actionButton;
	private IconButton linkButton;

	private ActionIndex<I, Object> actions;
	private ActionIndex<I, Space> views;
	private ListRect actionsList;


	private IconButton viewButton;

	//private Box nextView;
	private I object;
	private Space viewContent;
	private Rect viewHolder;

	private MetaWindow actionsListWindow;

	private ListRect viewList;

	private MetaWindow viewListWindow;

	private ObjectWindow linkWindow;

	private Linker linker;

	private ListVar linkList;

	@Override
	public void start() {
		tangible(false);
		
		actionButton = add(new IconButton("icon.magic", false));
		actionButton.addButtonAction(new ButtonAction() {
			@Override public void onButtonPressed(Button b) {
				toggleActions();
			}
		});

		linkButton = add(new IconButton("icon.arrow.neswdiag", false));
		linkButton.addButtonAction(new ButtonAction() {
			@Override public void onButtonPressed(Button b) {
				toggleLinks();
			}
		});


		viewButton = add(new IconButton("icon.arrow.left", false));
		viewButton.addButtonAction(new ButtonAction() {
				@Override public void onButtonPressed(Button b) {
					toggleViews();
				}
		});

		//		nextView = add(new IconButton("icon.arrow.right", false) {
		//			@Override public void pressStopped(Pointer c) {
		//				super.pressStopped(c);
		//				setView(currentView + 1);
		//			}
		//		});

		viewHolder = add(new Rect());
		viewHolder.tangible(false);


		setView(getDefaultView());
	}



	@Override
	public void stop() {
		clearLinkList();

	}

	public void setLinksVisible(boolean b) {
		if (b) {
			clearLinkList();

			linkList = getLinker().newLinkList(getObject());
			linkWindow = add(new ObjectWindow("Links", linkList, getActions(), getViews(), getLinker()));
			linkWindow.span(-0.35,-0.5,0.35,-1.5);			
		}
		else {
			if (linkWindow!=null) {
				remove(linkWindow);
				linkWindow = null;
				clearLinkList();
			}			
		}
		
	}

	protected void toggleLinks() {
		clearLinkList();
		
		if (linkWindow == null) {
			setLinksVisible(true);
		}
		else {
			setLinksVisible(false);
		}

		
	}


	private void clearLinkList() {
		if (linkList!=null) {
			if (linkList instanceof Disposable)
				((Disposable)linkList).dispose();
			
			linkList = null;
		}
	}



	public Linker getLinker() {
		return linker;
	}
	
	PossibleAction<I, Space> getDefaultView() {
		ListVar<PossibleAction<I, Space>> l = getViews().getPossibleActions(getObject());
		if (l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	
	protected void toggleViews() {
		if (viewListWindow == null) {
			ListVar<PossibleAction<I, Space>> l = getViews().getPossibleActions(getObject());
			viewList = new ListRect(l, new ArrangeColumn(0,0), new IntegerVar(6), new RectBuilder<PossibleAction>() {
				@Override public Rect newRect(final PossibleAction y) {
					Rect r = new Rect();
					r.tangible(false);
					TextButton button = r.add(new TextButton(y.getName()));
					
					button.addButtonAction(new ButtonAction() {
						@Override public void onButtonPressed(Button b) {
							setView(y);
						}												
					});

					return r;
				}				
			});
			//viewList.scale(0.9);
			viewListWindow = add(new MetaWindow("Views"));
			viewListWindow.span(-0.5,-0.25,-0.8,0.25);
			viewListWindow.rotate(0, Math.PI/4, 0);
			viewListWindow.getContent().add(viewList);
		}
		else {
			remove(viewListWindow);
			viewListWindow = null;
		}
	}

	public void toggleActions() {
		if (actionsListWindow == null) {
			ListVar<PossibleAction<I,Object>> l = getActions().getPossibleActions(getObject());
			actionsList = new ListRect(l, new ArrangeColumn(0,0), new IntegerVar(6), new RectBuilder<PossibleAction>() {
				@Override public Rect newRect(final PossibleAction y) {
					Rect r = new Rect();
					r.tangible(false);
					TextButton button = r.add(new TextButton(y.getName()));
					
					button.addButtonAction(new ButtonAction() {
						@Override public void onButtonPressed(Button b) {						
							try {
								y.run();
								Object output = y.getOutputValue();								
								setObject((I)output);
							} catch (Exception e) {
								logger.error(e);
								e.printStackTrace();
							}							
						}												
					});

					return r;
				}				
			});
			//actionsList.scale(0.9);
			actionsListWindow = add(new MetaWindow("Actions"));
			actionsListWindow.span(0.5,-0.25,0.8,0.25);
			actionsListWindow.getContent().add(actionsList);
		}
		else {
			remove(actionsListWindow);
			actionsListWindow = null;
		}
	}

	public void setObject(I newObject) {
		//TODO 1. hide actions, views, and links
		
		this.object = newObject;
		
		
		setView(getDefaultView());
		
	}
	
	public ObjectPanel(I object, ActionIndex<I, Object> actions, ActionIndex<I, Space> views, Linker linker) {
		super();
		this.object = object;
		this.actions = actions;
		this.views = views;
		this.linker = linker;
	}

	public I getObject() {
		return object;
	}

	public ActionIndex<I, Space> getViews() {
		return views;
	}

	public ActionIndex<I, Object> getActions() {
		return actions;
	}

	public void setView(PossibleAction<I, Space> view) {

		if (viewContent!=null) {
			viewHolder.clear();
			viewContent = null;
		}

		layoutButtons();

		if (view!=null) {	
			try {
				view.run();
				Space v = view.getOutputValue();
				viewContent = viewHolder.add(v);
				return;
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		else {	
			viewContent = viewHolder.add(new TextRect("?"));
		}


	}

	public void layoutButtons() {

		double contentButtonBottomY = -0.42;
		double buttonTopY = -0.45;

		viewHolder.span(-0.5, 0.5, 0.5, contentButtonBottomY);


		List<IconButton> visibleButtons = new LinkedList();
		if (actionButton!=null)
			visibleButtons.add(viewButton);
		if (actionButton!=null)
			visibleButtons.add(linkButton);
		if (actionButton!=null)
			visibleButtons.add(actionButton);
		
		double x = 0.5;
		for (IconButton ib : visibleButtons) {
			ib.span(x, buttonTopY, x-0.05, -0.5, true);
			x -= 0.05;
		}
		
	}
//
//	public boolean hasActionButton() { return actionButtonEnabled; }  
//	public boolean hasLinkButton() { return linkButtonEnabled; }  
//	public boolean hasViewButton() { return viewButtonEnabled; }  



	
	
}