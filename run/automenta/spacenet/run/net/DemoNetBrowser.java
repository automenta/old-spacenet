package automenta.spacenet.run.net;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Scheduler;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.dynamic.collection.ArrangeGrid;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.button.Button;
import automenta.spacenet.space.object.widget.button.TextButton;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.plugin.file.VirtualFile;

public class DemoNetBrowser  {

//	abstract public static class ButtonList<L> extends Rect implements Starts, Stops{
//
//		private ListVar<L> list;
//		private Rect space;
//
//		public ButtonList(ListVar<L> list) {
//			super();
//			this.list = list;
//		}
//
//		@Override public void start() {
//			this.space = add(new Rect());
//			add(new ArrangeColumn(space,0.05,0.05));
//
//			add(new IfCollectionChanges<L>(list) {
//				@Override public void afterObjectsAdded(CollectionVar list, L[] added) {
//					updateList();
//				}
//				@Override public void afterObjectsRemoved(CollectionVar list, L[] removed) {
//					updateList();
//				}
//			});
//
//			updateList();
//		}
//
//		protected synchronized void updateList() {
//			Scheduler.doLater(new Runnable() {
//				@Override public void run() {
//					space.clear();
//					for (Object o : list) {
//						Button b = new TextButton(o.toString()) {
//
//						};
//						space.add(b);
//					}
//				}
//			});
//		}
//
//		@Override public void stop() {
//
//		}
//
//
//		abstract public Runnable getButtonAction(Object o);
//	}
//
//	abstract public static class GridRect extends Rect implements Starts, Stops {
//
//		private ListVar list;
//
//		public GridRect(ListVar list) {
//			super();
//
//			this.list = list;
//		}
//
//		@Override public void start() {
//			add(new ArrangeGrid(this));
//
//			add(new IfCollectionChanges(list) {
//
//				@Override public void afterObjectsAdded(CollectionVar list, Object[] added) {
//					for (Object o : added) {
//						add(getSpace(o));
//					}
//				}
//
//				@Override public void afterObjectsRemoved(CollectionVar list, Object[] removed) {
//
//				}
//
//			});
//		}
//
//		abstract protected Object getSpace(Object o);
//
//		@Override public void stop() {
//
//		}
//	}
//
//
//	public static class NetBrowser extends Rect implements Starts, Stops {
//
//		private VirtualFile file;
//
//		public NetBrowser(VirtualFile file) {
//			super();
//			this.file = file;
//		}
//
//		@Override public void start() {
//			add(new ButtonList(file) {
//				@Override public Runnable getButtonAction(Object o) {
//					return null;
//				}
//			}.inside(RectPosition.W, 0.25, 1.0));
//
//			add(new GridRect(file) {
//				@Override protected Object getSpace(Object o) {
//					return new TextButton(o.toString());
//				}
//			}.inside(RectPosition.E, 0.75, 1.0));
//		}
//
//		@Override public void stop() {
//
//		}
//
//	}
//
//	@Override public void run() {
//		try {
//			add(new NetBrowser(new VirtualFile("file:///", 1)).scale(5));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		new StartJmeWindowOld(new DemoNetBrowser());
//	}
}
