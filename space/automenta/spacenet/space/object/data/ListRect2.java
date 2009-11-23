package automenta.spacenet.space.object.data;

import java.util.Map;

import javolution.util.FastMap;
import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.var.list.CollectionVar;
import automenta.spacenet.var.list.IfCollectionChanges;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

/** displays a list of space objects in a row or column.  
 * if more items are present in the list than allowed to be displayed,
 * a scrollbar appears. */
public class ListRect2<X> extends Rect implements Starts, Stops {

	private ListVar<X> list;

	private Rect content;
	private RectBuilder<X> builder;
	
	private Slider slider;

	public static enum ListDirection { Vertical, Horizontal };
	
	private Map<X, Rect> objectRects = new FastMap();

	private DoubleVar center;

	private DoubleVar focusSize;

	private ListDirection direction;

	
	public static interface RectBuilder<Y> {
		public Rect newRect(Y y);
	}
	
	public ListRect2(ListVar<X> list, ListDirection direction, DoubleVar center, DoubleVar focusSize, RectBuilder<X> builder) {
		super();
		this.list = list;
		this.center = center;
		this.focusSize = focusSize;
		this.builder = builder;
		this.direction = direction;
	}
	
	public DoubleVar getCenter() {
		return center;
	}
	public DoubleVar getFocusSize() {
		return focusSize;
	}
	
	
	@Override public void start() {
		content = add(new Rect());	

		double sliderWidth = 0.15;



		if (direction == ListDirection.Vertical) {
			slider = add(new Slider(1,0,1,0.1, SliderType.Vertical));
			content.span(-0.5, -0.5, 0.5 - sliderWidth, 0.5);
			slider.span(0.5-sliderWidth, -0.5, 0.5, 0.5);
		}
		else if (direction == ListDirection.Horizontal) {
			slider = add(new Slider(0,0,1,0.1, SliderType.Horizontal));
			content.span(-0.5, -0.5+sliderWidth, 0.5, 0.5);
			slider.span(-0.5, -0.5, 0.5, -0.5+sliderWidth);			
		}
		add(new IfDoubleChanges(slider.getValue()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				onSliderChange();
			}
		});

		
		add(new IfCollectionChanges<X>(getList()) {
			@Override public void afterObjectsAdded(CollectionVar list, X[] added) {
				onListChanged();
			}
			@Override public void afterObjectsRemoved(CollectionVar list, X[] removed) {
				onListChanged();
			}			
		});
		add(new IfDoubleChanges(getCenter(), getFocusSize()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar,	Double previous, Double next) {
				layout();				
			}			
		});

		onSliderChange();

	}

	protected void onListChanged() {

		for (int i = 0; i < getList().size(); i++) {
			
			X x = getList().get(i);
			if (getRect(x) == null) {
				Rect s = builder.newRect(x);
				objectRects.put(x, s);				
				content.add(s);
			}
		}

		layout();
	}

	private Rect getRect(X x) {
		return objectRects.get(x);
	}

	protected void onSliderChange() {
//		int f;
//		if (arrange instanceof ArrangeColumn)
//			f = (int)((1.0 - slider.getValue().asDouble()) * (getNumItems() - getNumShown().asInt() + 1));
//		else
//			f = (int)((slider.getValue().asDouble()) * (getNumItems() - getNumShown().asInt() + 1));
		
		double s = slider.getValue().d();
		if (direction == ListDirection.Vertical) {
			s = 1.0 - s;
		}
		getCenter().set(s * (getNumItems() - getFocusSize().d()/2.0) );
	}

	protected synchronized void layout() {
		
		if (getList().size() == 0)
			return;

		double i = 0;
		double sw = 1.0;
		double ni = getNumItems();
		double sh = 1.0 / ni;
		
		double length = 0;
		for (X o : getList()) {
			length += getScale(i);			
			i++;
		}

		double x = 0, y = 0, z = 0;
		if (direction == ListDirection.Horizontal) {
			x = -0.5;
			y = 0;
		}
		else if (direction == ListDirection.Vertical) {
			y = 0.5;
			x = 0;
		}

		i = 0;

		double w = 0, h = 0;
		for (X o : getList()) {
			Rect r = getRect(o);
			
			if (direction == ListDirection.Horizontal) {
				x += w/2.0;
			}
			else {
				y -= h/2.0;
			}
			
			if (direction == ListDirection.Horizontal) {
				h = 1.0;
				w = getScale(i) / length;
			}
			else /*if (direction == ListDirection.Vertical)*/ {
				w = 1.0;
				h = getScale(i) / length;
			}

			if (r!=null) {
				r.getSize().set(w, h);
				r.getPosition().set(x, y, z);
			}

			if (direction == ListDirection.Horizontal) {
				x += w/2.0;
			}
			else {
				y -= h/2.0;
			}
			
			i++;	
		}

		
		//slider.getKnobLength().set( ((double)getNumShown().asInt()) / ((double)getNumItems()) );
	}
	
	private double getScale(double i) {
		double centerDelta = getCenter().d() - i;
		double centerDistance = Math.abs(centerDelta);
		
		double fw = getFocusSize().d()/2.0;
		if (centerDistance < fw) {
			return 1.0;
		}
		else {
			double s = (1.0 / (centerDistance-fw+1));
//			if (s < 0.2)
//				return 0;
			return s;
		}
	}

	private Rect newExceptionRect(Exception e) {
		return new TextRect(e.toString());
	}

	@Override public void stop() {	}


	private int getNumItems() {
		return getList().size();
	}
	
	public ListVar<X> getList() {
		return list;
	}
	
}

