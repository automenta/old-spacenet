package automenta.spacenet.os;

import automenta.spacenet.Disposable;
import automenta.spacenet.Scope;
import automenta.spacenet.var.index.Found;
import automenta.spacenet.var.index.MemoryIndex;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;

public class Linker extends Scope {

	public class StringLinks extends ListVar implements Disposable {

		public int maxResults = 40;
		private StringVar text;
		private IfStringChanges ifStringChanges;
		
		public StringLinks(StringVar text) {
			super();
		
			this.text = text;
			
			ifStringChanges = Linker.this.add(new IfStringChanges(text) {
				@Override public void afterTextChanged(StringVar t, String previous, String current) {
					updateStringLinks();
				}				
			});
			
			updateStringLinks();
		}
		
		public StringVar getText() {
			return text;
		}
		
		protected void updateStringLinks() {
			clear();
			
			int displayedResults = 7;
			
			String i = getText().s();
			ListVar<Found> results = getIndex().getResults(i, maxResults);
			
			for (Found f : results) {
				add(f);
			}

		}
		
		@Override public void dispose() {
			Linker.this.remove(ifStringChanges);
		}
		
		
	}


	private MemoryIndex index;

	public Linker(MemoryIndex index) {
		super();
		this.index = index;
	}
	
	public ListVar newLinkList(Object o) {
		if (o instanceof StringVar) {
			return new StringLinks((StringVar)o);
		}
		return newEmptyLinks();
	}
	

	private ListVar newEmptyLinks() {
		ListVar l = new ListVar();
		l.add(new StringVar("No links"));
		return l;
	}

	public MemoryIndex getIndex() {
		return index;
	}
	
}
