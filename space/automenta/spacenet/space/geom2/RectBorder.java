package automenta.spacenet.space.geom2;

import automenta.spacenet.Scope;
import automenta.spacenet.space.Surface;
import automenta.spacenet.space.object.widget.Widget;
import automenta.spacenet.var.number.DoubleVar;

public class RectBorder extends Widget {

	private Surface surface;
	private DoubleVar thickStart;
	private DoubleVar thickEnd;
	private Rect top;
	private Rect bottom;
	private Rect left;
	private Rect right;
	private Rect cTL;
	private Rect cTR;
	private Rect cBL;
	private Rect cBR;

	public static enum Part { Top, Bottom, Left, Right, NE, SE, SW, NW };
	
	public RectBorder(Surface surface, DoubleVar thickStart, DoubleVar thickEnd) {
		super();
		this.surface = surface;
		this.thickStart = thickStart;
		this.thickEnd = thickEnd;
		
	}
	
	@Override public void start() {
		super.start();

		top = add(newPartNode(Part.Top)); 
		bottom = add(newPartNode(Part.Bottom)); 
		left = add(newPartNode(Part.Left)); 
		right = add(newPartNode(Part.Right)); 
		
		cTL = add(newPartNode(Part.NW)); 
		cTR = add(newPartNode(Part.NE)); 
		cBL = add(newPartNode(Part.SW)); 
		cBR = add(newPartNode(Part.SE)); 

		updateBorder();

	}
	
	protected Rect newPartNode(Part p) {
		Rect r = getDefaultPart();
		r.tangible(false);
		return r;
	}

	private Rect getDefaultPart() { return new Rect(surface); }
	
	protected double getThickStart() { return thickStart.d(); }
	protected double getThickEnd() { return thickEnd.d(); }
	
	protected void updateBorder() {
		
		double tCenter = (getThickStart() + getThickEnd()) / 2.0;
		double thickness = thickEnd.d() - thickStart.d();

		double tHorizontal = thickness;
		double tVertical = thickness;

		double lHorizontal = 1.0 - thickness/2.0;
		double lVertical = 1.0 - thickness/2.0;
		
		double cHorizontal = thickness;
		double cVertical = thickness;
		
		//double tEx = (Math.max(getThickStart(), getThickEnd()) - 1.0)/2.0;

		top.move(0,0.5 * tCenter);
		top.scale(lHorizontal, tHorizontal);
		
		bottom.move(0, -0.5 * tCenter);
		bottom.scale(lHorizontal, tHorizontal);
		
		left.move(-0.5 * tCenter, 0);
		left.scale(tVertical, lVertical);
		
		right.move(0.5 * tCenter, 0);
		right.scale(tVertical, lVertical);
		
		cTL.move(-0.5 * tCenter, 0.5 * tCenter);
		cTL.scale(cHorizontal, cVertical);
		
		cTR.move(0.5 * tCenter, 0.5 * tCenter);
		cTR.scale(cHorizontal, cVertical);
		
		cBL.move(-0.5 * tCenter, -0.5 * tCenter);
		cBL.scale(cHorizontal, cVertical);
		
		cBR.move(0.5 * tCenter, -0.5 * tCenter);
		cBR.scale(cHorizontal, cVertical);

	}
	

	public void addCorners() {
		
	}

	
}
