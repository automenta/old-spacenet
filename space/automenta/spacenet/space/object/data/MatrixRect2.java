package automenta.spacenet.space.object.data;

import automenta.spacenet.Starts;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.var.map.MapVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfDoubleChanges;


/** displays a sub-matrix of a 2D matrix array of rectangles, useful for grid arrangements and (editable) text rectangles */
@Deprecated public class MatrixRect2 extends Rect implements Starts {


	private DoubleVar width;
	private DoubleVar height;
	private DoubleVar cx;
	private DoubleVar cy;
	DoubleVar minX = new DoubleVar(0);
	DoubleVar maxX = new DoubleVar(0);
	DoubleVar minY = new DoubleVar(0);
	DoubleVar maxY = new DoubleVar(0);
	
	private MapVar<Integer, MapVar<Integer,Rect>> grid = new MapVar();
	private Rect content;
	private Slider xSlider, ySlider;

	public MatrixRect2(DoubleVar width, DoubleVar height) {
		this(width, height, new DoubleVar((width.d()) / 2.0), new DoubleVar(height.d() / 2.0));
	}

	public MatrixRect2(DoubleVar width, DoubleVar height, DoubleVar cx, DoubleVar cy) {
		super();
		this.width = width;
		this.height = height;
		this.cx = cx;
		this.cy = cy;
	}

	public MatrixRect2(double width, double height) {
		this(new DoubleVar(width), new DoubleVar(height));
	}

	public DoubleVar getWidth() {
		return width;
	}
	public DoubleVar getHeight() {
		return height;
	}
	public DoubleVar getCx() {
		return cx;
	}
	public DoubleVar getCy() {
		return cy;
	}

	@Override public void start() {
		content = add(new Rect());
		
		xSlider = add(new Slider(getCx(), getMinX(), getMaxX(), new DoubleVar(0.1), SliderType.Horizontal));
		ySlider = add(new Slider(getCy(), getMinY(), getMaxY(), new DoubleVar(0.1), SliderType.Vertical));

		
		add(new IfDoubleChanges(getCx(), getCy()) {
			@Override public void afterDoubleChanges(DoubleVar doubleVar, Double previous, Double next) {
				layout();
			}			
		});
		
		layout();
	}

	@Override public void stop() {
		
	}

	public void removeAll() {
		content.clear();
		
		grid.clear();
		
		
		minX.set(0);
		minY.set(0);
		maxX.set(0);
		maxY.set(0);
		
		cx.set(0);
		cy.set(0);
	}

	public void put(int x, int y, Rect r) {
		MapVar<Integer, Rect> row = grid.get(y);
		if (row == null) {
			row =new MapVar<Integer,Rect>(); 
			grid.put(y, row);
		}
		
		if (row.get(x)!=null) {
			Rect removed = row.remove(x);
			content.remove(removed);
		}
		
		row.put(x, r);

		if (x < minX.i()) minX.set(x);
		if (x > maxX.i()) maxX.set(x);
		if (y < minY.i()) minY.set(y);
		if (y > maxY.i()) maxY.set(y);

		content.add(r);

		layout();
	}

	public Rect get(int x, int y) {
		try {
			return grid.get(y).get(x);
		}
		catch (Exception e) {
			return null;
		}
	}

	//	public void remove(int x, int y) {
	//		
	//	}

	
	protected void layout() {
		content.scale(0.9, 0.9);
		
	
		double dx = maxX.d() - minX.d();
		double dy = maxY.d() - minY.d();
		
		if (getWidth().d() < dx) {
			xSlider.span(-0.4, -0.45 , 0.4, -0.5);
			double kl = getWidth().d() / dx;
			xSlider.getKnobLength().set(kl);
		}
		else
			xSlider.scale(0);
		
		if (getHeight().d() <= dy) {
			ySlider.span(0.45, -0.4 , 0.5, 0.4);
			double kl = getHeight().d() / dy;
			ySlider.getKnobLength().set(kl);
		}
		else
			ySlider.scale(0);

		
		double height = 0;
		double width = 0;
		
		//calculate width and height to normalize
		for (int y = minY.i(); y <= maxY.i(); y++) {
			double yScale = getYScale(y);

			MapVar<Integer, Rect> row = grid.get(y);
			
			double w = 0;
			if (row!=null) {
				for (int x = minX.i(); x <= maxX.i(); x++) {
					double xScale = getXScale(x);					
					w += xScale;
				}
			}
			if (w > width)
				width = w;
						
			height += yScale;
		}

		double px;
		double py = -0.5;

		for (int y = minY.i(); y <= maxY.i(); y++) {
			px = -0.5;
			double yScale = getYScale(y)/height;
			MapVar<Integer, Rect> row = grid.get(y);
			
			if (row!=null) {
				for (int x = minX.i(); x <= maxX.i(); x++) {
					double xScale = getXScale(x)/width;
					Rect r = row.get(x);
					if (r!=null) {
						r.getPosition().set(px + xScale/2.0, py + yScale/2.0, 0);						
						r.getSize().set(xScale, yScale);
						
					}
					px += xScale;
				}
			}
			py += yScale;
		}

	}

//	private double getScale(int x, int y) {
//		double dx = Math.abs( x - getCx().d() );
//		double dy = Math.abs( y - getCy().d() );
//		
//		if (dx < getWidth().d()/2.0) {
//			if (dy < getHeight().d()/2.0)
//				return 1.0;
//		}
//	
//		double d = Math.sqrt ( dx*dx + dy*dy );
//
//		return 1.0 / d;
//	}

	protected double getXScale(double x) {
		double dx = Math.abs( x - getCx().d() );
		double dw = getWidth().d()/2.0;
		if (dx < dw) {
			return 1.0;
		}
		double sx = 1.0 / (dx - dw + 1.0);
		if (sx < getVisibleThreshold()) {
			return 0.0;
		}
		return sx;
	}

	protected double getYScale(double y) {
		double dy = Math.abs( y - getCy().d() );
		double dh = getHeight().d()/2.0;
		if (dy < dh) {
			return 1.0;
		}
		double sy = 1.0 / (dy - dh + 1.0);
		if (sy < getVisibleThreshold()) {
			return 0.0;
		}
		return sy;
	}
	
	public double getVisibleThreshold() {
		return 0.05;
	}

	public DoubleVar getMaxX() {	return maxX;	}
	public DoubleVar getMaxY() {	return maxY;	}
	public DoubleVar getMinX() {	return minX;	}
	public DoubleVar getMinY() {	return minY;	}

}
