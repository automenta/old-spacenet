package automenta.spacenet.space.object.data;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.dynamic.collection.ArrangeGrid;
import automenta.spacenet.space.dynamic.collection.ArrangeRow;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;
import automenta.spacenet.var.number.IfIntegerChanges;
import automenta.spacenet.var.number.IntegerVar;

/** displays a list of space objects in a row or column.  
 * if more items are present in the list than allowed to be displayed,
 * a scrollbar appears. */
public class ListRect<X> extends Rect implements Starts, Stops {

	private IntegerVar maxItems;
	private IntegerVar firstShown;
	private ListVar<X> list;
	private ArrangeGrid arrange;
	private Rect content;
	private RectBuilder<X> builder;
	private Slider slider;


	double sliderWidth = 0.1;
	private Map<X, Rect> cachedRects = new HashMap();

	public ListRect(ListVar<X> list, ArrangeGrid arrange, IntegerVar maxItems, RectBuilder<X> builder) {
		super();
		this.list = list;
		this.arrange = arrange;
		this.maxItems = maxItems;
		this.firstShown = new IntegerVar(0);
		this.builder = builder;
	}
	
	@Override public void start() {
		content = add(new Rect());	

		if (arrange instanceof ArrangeColumn) {
			slider = add(new Slider(1,0,1,0.1, SliderType.Vertical));
		}
		else if (arrange instanceof ArrangeRow) {
			slider = add(new Slider(0,0,1,0.1, SliderType.Horizontal));
		}
		
		add(new IfDoubleChanges(slider.getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				onSliderChange();
			}
		});

		
		add(new IfCollectionChanges<X>(getList()) {
			@Override public void afterObjectsAdded(CollectionVar list, X[] added) {
				layout();
			}
			@Override public void afterObjectsRemoved(CollectionVar list, X[] removed) {
				layout();
			}			
		});
		add(new IfIntegerChanges(firstShown, maxItems) {
			@Override public void afterValueChange(ObjectVar o, Integer previous, Integer next) {
				layout();
			}			
		});
		

	}

	protected void onSliderChange() {
		int f;
		if (arrange instanceof ArrangeColumn) {
			double p = 1.0 - slider.getValue().d();
			f = (int)(p * (getNumItems() - getNumShown().i() + 1));
		}
		else
			f = (int)((slider.getValue().d()) * (getNumItems() - getNumShown().i() + 1));
		getFirstShown().set(f);		
	}

	protected synchronized void layout() {
		content.clear();


		if (list.size() > getNumShown().i()) {
			if (arrange instanceof ArrangeColumn) {
				content.span(-0.5, -0.5, 0.5 - sliderWidth, 0.5);
				slider.span(0.5-sliderWidth, -0.5, 0.5, 0.5);
			}
			else if (arrange instanceof ArrangeRow) {
				content.span(-0.5, -0.5+sliderWidth, 0.5, 0.5);
				slider.span(-0.5, -0.5, 0.5, -0.5+sliderWidth);			
			}
		}
		else {
			content.span(-0.5, -0.5, 0.5, 0.5);
			slider.scale(0);
		}

		
		if (list.size() == 0)
			return;
		
		for (int i = getFirstShown().i(); i < getLastShown(); i++) {
			X x = getList().get(i);
			
			Rect s;
			if (isCachingSpatials()) {
				s = cachedRects.get(x);
				if (s == null) {
					s = builder.newRect(x);
					cachedRects.put(x, s);
				}
			}
			else {
				s = builder.newRect(x);
			}
			
			try {
				content.add(s);
			}
			catch (Exception e) {
				content.add(newExceptionRect(e));
			}
		}
	
		
		slider.getKnobLength().set( ((double)getNumShown().i()) / ((double)getNumItems()) );
		arrange.arrange(content);
	}
	
	/** TODO: enable this.  does not work currently because when the list is cleared, the spatials contents are destroyed by default */
	private boolean isCachingSpatials() {
		return false;
	}

	private Rect newExceptionRect(Exception e) {
		return new TextRect(e.toString());
	}

	@Override public void stop() {	}

	public IntegerVar getNumShown() {
		return maxItems;
	}
	public IntegerVar getFirstShown() {
		return firstShown;
	}

	/** actually, first non-shown */
	public int getLastShown() { 
		int l = getFirstShown().i() + getNumShown().i();
		l = Math.min(l, getNumItems());
		return l;
	}

	private int getNumItems() {
		return getList().size();
	}
	
	public ListVar<X> getList() {
		return list;
	}
	
}

