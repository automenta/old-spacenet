package automenta.spacenet.var.list;

import java.util.Collection;

import automenta.spacenet.var.number.IntegerVar;


public class FifoVar<O> extends ListVar<O>  {

	private IntegerVar fifoSize;
	public FifoVar(int size) {
		super();
		
		this.fifoSize = new IntegerVar(size);
		
	}
//
//	@Override
//	public void start(Object context) {
//		whenFifoSizeChanges = new WhenIntChanges(fifoSize) {
//			@Override public void afterValueChanged(ObjectVal o, Integer previous, Integer next) {
//				ensureSize();				
//			}
//
//		};
//	}
//
//	@Override
//	public void stop(N context) {
//		if (whenFifoSizeChanges!=null) {
//			whenFifoSizeChanges.stop(this);
//			whenFifoSizeChanges = null;
//		}
//		
//	}

		
	public IntegerVar getFifoSize() {
		return fifoSize;
	}

	@Override
	public void add(int index, O element) {
		super.add(index, element);
		ensureSize();
	}
	
	@Override
	public boolean add(O e) {
		boolean b = super.add(e);
		ensureSize();
		return b;
	}
	
	@Override
	public boolean addAll(Collection<? extends O> c) {
		boolean b = super.addAll(c);
		ensureSize();
		return b;
	}
	@Override
	public boolean addAll(int index, Collection<? extends O> c) {
		boolean b = super.addAll(index, c);
		ensureSize();
		return b;
	}
	@Override
	public void addFirst(O e) {
		super.addFirst(e);
		ensureSize();		
	}
	@Override
	public void addLast(O e) {
		super.addLast(e);
		ensureSize();
	}

	public void push(O o) { 
		add(o);
		onIn(o);
	}

	protected void ensureSize() {
		if (size() > getFifoSize().get()) {
			O removed = removeFirst();
			onOut(removed);
		}
	}

	protected <X extends O> void onIn(O o) {	}
	protected <X extends O> void onOut(O o) {	}
	
}
