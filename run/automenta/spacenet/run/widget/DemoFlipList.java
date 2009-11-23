package automenta.spacenet.run.widget;

import java.util.HashMap;
import java.util.Map;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.RectBuilder;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;

public class DemoFlipList extends ProcessBox {

	public static class FlipList<I> extends Box implements Starts, Stops {
		double contentProportion = 0.9;
		DoubleVar pos = new DoubleVar(0.5);
		private ListVar<I> list;
		private RectBuilder<I> builder;
		private Box content;
		private Map<I, Rect> objectRect = new HashMap();
		
		public FlipList(ListVar<I> list, RectBuilder<I> rectBuilder) {
			super();
			
			this.list = list;
			this.builder = rectBuilder;
		}

		@Override public void start() {
			double midY = -0.5 + (1.0 - contentProportion);
			add(new Slider(pos, new DoubleVar(0.0), new DoubleVar(1.0), 0.1, SliderType.Horizontal)).span(-0.5, midY, 0.5, -0.5);
			
			content = add(new Box().span(-0.5, 0.5, 0.5, midY));
			
			add(new IfDoubleChanges(pos) {
				@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
					updatePosition();
				}				
			});
			update();
		}

		protected void update() {
			content.clear();
			objectRect.clear();
			
			for (I i : list) {
				Rect r = content.add(builder.newRect(i));
				objectRect.put(i, r);
			}
			
			updatePosition();
		}
		
		protected void updatePosition() {
			int j = 0;
			for (I i : list) {
				Rect r = objectRect.get(i);
				r.move(getX(j), 0, 0);
				r.orient(0, getOrientation(j), 0);
				
				double s = getSize(j);
				r.scale( s, s );
				j++;
			}
			
		}
		

		public int getNum() { return list.size(); }
		
		double sqr(double x) { return x * x;}
		
		/**
		 * @see http://en.wikipedia.org/wiki/Lorentzian_function
		 */
		public double lorentzian(double x) {
			double y = 1.0;
			return 1.0 / (y * (1 + sqr(x)));
		}
		
		private double getOrientation(int j) {
			return (1.0 - lorentzian( Math.abs(  pos.d() - ((double)j) / ((double)getNum()))) ) * Math.PI;
		}

		double getSize(int j) {
			return 0.5;
		}

		private double getX(int j) {
			double x = 0, t = 0;
			for (int i = 0; i < getNum(); i++) {
				t += Math.abs( Math.cos(Math.PI - getOrientation(i)) / Math.PI / (getNum()) );
				if (i == j)
					x = t;
			}
			x /= t;
			x -= 0.5;
			return x;
		}

		@Override public void stop() {
			
		}
		
	}
	
	@Override 	public void run() {
		
		ListVar list = new ListVar();
		for (int i = 0; i < 32; i++) {
			list.add(Integer.toString(i));
		}
		
		add(new FlipList(list, new RectBuilder() {

			@Override public Rect newRect(Object y) {
				Rect r = new Rect(Color.newRandom());
				r.add(new TextRect(y.toString()));
				return r;
			}
			
		}) );
		
	}
	
	public static void main(String[] args) {
		
		new DefaultJmeWindow(new DemoFlipList().scale(4));
	}
	
}
