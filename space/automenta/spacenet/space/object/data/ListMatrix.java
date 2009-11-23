package automenta.spacenet.space.object.data;

import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;

public class ListMatrix<L> extends MatrixRect {

	private ListVar<L> list;
	private RectBuilder<L> builder;
	protected boolean listChanged;
	private ListMode mode;

	public static enum ListMode {
		Column, Row, Square
	};
	
	public ListMatrix(ListVar<L> list, RectBuilder<L> builder, ListMode mode) {
		super();
		this.mode = mode;
		this.list = list;
		this.builder = builder;
		//getAutoAspectScale().set(6);
	}

	@Override
	public void start() {
		listChanged = true;
		
		super.start();
		
		add(new IfCollectionChanges<L>(getList()) {
			@Override public void afterObjectsAdded(CollectionVar list, L[] added) {
				//updateMatrix();
				listChanged = true;
				needsRefresh = true;
			}
			
			@Override public void afterObjectsRemoved(CollectionVar list, L[] removed) {
				//updateMatrix();
				listChanged = true;
				needsRefresh = true;
			}			
		});
		
	}

	@Override
	protected void layout() {
		if (listChanged == true) {
			updateListMatrix();
			listChanged = false;
		}
		
		super.layout();
	}
	
	protected void updateListMatrix() {
		removeAll();
		
		int n = getList().size();
		int width = getCellsWide(n); //(int) Math.ceil( Math.sqrt( n ) );
		int height = width > 0 ? (int) Math.ceil( n / width ) : 1;
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < n; i++) {
			L o = getList().get(i);
			put(x, y, builder.newRect(o));
			x++;
			if (x == width) {
				x = 0;
				y ++;
			}
		}
	}
	
	public int getCellsWide(int n) {
		if (mode == ListMode.Column) {
			return 1;
		}
		else if (mode == ListMode.Square) {
			return (int) Math.ceil( Math.sqrt( n ) );
		}
		else if (mode == ListMode.Row) {
			return n;
		}
		return 1;
	}

	public ListVar<L> getList() {
		return list;
	}
	
}
