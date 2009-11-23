package automenta.spacenet.run.widget;


import automenta.spacenet.space.Space;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.run.widget.DemoWindow.PointerWindow;
import automenta.spacenet.space.object.widget.menu.ListMenu;
import automenta.spacenet.space.object.widget.menu.ListMenu.MenuButton;
import automenta.spacenet.space.object.widget.menu.ListMenu.SubMenuButton;
import automenta.spacenet.var.list.ListVar;

public class DemoListMenu extends ProcessBox {

	@Override public void run() {
//		ListVar menu = new ListVar();
//
//
//		menu.add(new MenuButton("Start", new Runnable() {
//			@Override public void run() {
//
//			}
//		}));
//		menu.add(new MenuButton("Refresh", new Runnable() {
//			@Override public void run() {
//
//			}
//		}));
//
//		ListVar<Space> subSubMenu = new ListVar();
//		subSubMenu.add(new MenuButton("123"));
//		subSubMenu.add(new MenuButton("456"));
//
//		ListVar<Space> subMenu = new ListVar();
//		subMenu.add(new MenuButton("Abc"));
//		subMenu.add(new MenuButton("Xyz"));
//		subMenu.add(new SubMenuButton("Fde", subSubMenu));
//
//		ListVar<Space> subMenu2 = new ListVar();
//		subMenu2.add(new MenuButton("Abc"));
//		subMenu2.add(new MenuButton("Xyz"));
//
//		menu.add(new SubMenuButton("SubMenu", subMenu));
//		menu.add(new SubMenuButton("Xy Menu", subMenu2));
//
//		menu.add(new MenuButton("Exit", new Runnable() {
//			@Override public void run() {
//
//			}
//		}));
//
//		ListMenu m = add(new ListMenu(menu));
//
//		add(new PointerWindow().move(-2,0,0));

	}
	
	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoListMenu().scale(3));
	}
}
