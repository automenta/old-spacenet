package automenta.spacenet.space.object.widget.menu;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.data.ListRect;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.IfBoolChanges;
import automenta.spacenet.var.number.IntegerVar;

/** list of menu items and sub-menus, analogous to swing's JPopupMenu */
public class ListMenu extends Panel  {

	public interface MenuComponent {
		public void setMenu(ListMenu menu);
	}
	
	public static class MenuButton extends Rect implements Starts, MenuComponent {
		private String label;
		private Runnable whenClicked;
		ListMenu container;
		protected TextButton button;

		public MenuButton(String label) {
			this(label, null);
		}


		public MenuButton(String label, final Runnable whenClicked) {
			super();
			
			this.label = label;
			this.whenClicked = whenClicked;
			
			tangible(false);
			
		}


		@Override public void start() {
			button = add(new TextButton(label) {
				@Override public void updateTouch(Pointer c, double timeTouched) {
					super.updateTouch(c, timeTouched);
					MenuButton.this.updateTouch(c, timeTouched);
				}
				@Override public void stopTouch(Pointer c) {
					super.stopTouch(c);
					MenuButton.this.stopTouch(c);
				}
				@Override
				public void onFocusChange(boolean focused) {
					super.onFocusChange(focused);
					MenuButton.this.onFocusChange(focused);
				}
				
			});
			
			if (whenClicked!=null) {
				add(new IfBoolChanges(button.getPressed()) {
					@Override public void afterValueChanged(BooleanVar b, boolean nextValue) {
						Scheduler.doLater(whenClicked);
					}				
				});
			}
			
		}

		protected void onFocusChange(boolean focused) {
			
		}


		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}
		
		protected void stopTouch(Pointer c) {	}

		protected void updateTouch(Pointer c, double timeTouched) {		}


		@Override public void setMenu(ListMenu menu) {
			this.container = menu;			
		}

	}
	
	public class SubMenuButton extends MenuButton  {

		private boolean opened = false;
		private ListVar<Space> subMenu;
		private ListMenu openedMenu;


		public SubMenuButton(String label, ListVar<Space> subMenu) {
			super(label + " >", null );
			this.subMenu = subMenu;
		}

		@Override protected void updateTouch(Pointer c, double timeTouched) {
			super.updateTouch(c, timeTouched);
			if (timeTouched > getPopupThreshold()) {
				if (!isOpen())
					open();
			}
		}

		@Override
		protected void stopTouch(Pointer arg0) {
			super.stopTouch(arg0);
		}
		
		public boolean isOpen() { return opened ; }
		
		public void open() {
			final double popupDZ = 0.1;
			
			if (!isOpen()) {
//				Scheduler.doLater(new Runnable() {
//					@Override public void run() {
						openedMenu = add(new ListMenu(getSubMenu()) {
						});
						
						
						openedMenu.moveDelta(1.0, 0, popupDZ);

						openedMenu.requestFocus();
	
						opened = true;					
//					}				
//				});
			}
						
		}
		
		@Override public void onFocusChange(boolean f) {
			if (!f) {
				Space focused = widgets().getFocus().get();
				
				System.out.println("closing because of focused: "+ focused);
				
				//if (!container.getPopupStack().contains(openedMenu))
				if (!focused.hasParent(this))
					close();
			}
//			else {
//				if (container.getPopupStack().getLast()!=openedMenu)
//					container.getPopupStack().add(openedMenu);
//			}
			
		}
		
		private ListVar<Space> getSubMenu() {
			return subMenu;
		}

		public void close() {
			
			if (isOpen()) {
//				Scheduler.doLater(new Runnable() {
//					@Override public void run() {
						remove(openedMenu);

						openedMenu = null;
						opened = false;
//					}
//				});
			}
			
		}
		

		private double getPopupThreshold() {
			return 0.1;
		}
		
	}
	
	private ListVar<Space> list;
	private IntegerVar shownItems = new IntegerVar(8);

	public ListMenu() {
		this(new ListVar<Space>());
	}

	public ListMenu(ListVar<Space> menuList) {
		super();
		this.list = menuList;
		
	}
	
	@Override public void start() {
		super.start();
		
		tangible(false);

		ListRect<Space> listRect = add(new ListRect<Space>(getList(), new ArrangeColumn(0.05, 0.05), getShownItems(), new RectBuilder<Space>() {
			@Override public Rect newRect(Space s) {
				Rect r  = new Rect();
				r.tangible(false);
				r.add(s);
				
				if (s instanceof MenuComponent) {
					((MenuComponent)s).setMenu(ListMenu.this);
				}
				return r;
			}			
		}));
		listRect.tangible(false);
		
		double dz = 0.05;
		listRect.moveDelta(0,0,dz);
		listRect.scale(0.9, 0.9);
	}
	

	private IntegerVar getShownItems() {
		return shownItems ;
	}

	private void addButton(Object o) {
		add(new TextButton(o.toString()));		
	}
	
	public ListVar<Space> getList() {
		return list;
	}

	
}
