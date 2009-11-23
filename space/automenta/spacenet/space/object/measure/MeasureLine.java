package automenta.spacenet.space.object.measure;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.geom3.Line3D;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.IfVector3Changes;
import automenta.spacenet.var.vector.Vector3;

public class MeasureLine extends Line3D implements Starts, Stops{

	private IfVector3Changes whenSizeChanges;
	private StringVar label;
	private Vector3 a;
	private Vector3 b;
	private IfVector3Changes whenPointsChange;
	private TextRect text;

	public MeasureLine(Vector3 a, Vector3 b) {
		super(a, b, new DoubleVar(0.2), 4); 
		this.a = a;
		this.b = b;
	}
	

	@Override
	public void start() {
		
		final Box aBox = (Box)add(new Box(a, new Vector3(0.1, 0.1, 0.1), new Vector3()).surface(new ColorSurface(Color.White.alpha(0.5))));
		final Box bBox = (Box)add(new Box(b, new Vector3(0.1, 0.1, 0.1), new Vector3()).surface(new ColorSurface(Color.White.alpha(0.5))));
		
		VectorFont font = getThe(VectorFont.class);
		
		label = new StringVar("???");
		
		whenSizeChanges = add(new IfVector3Changes(aBox.getAbsolutePosition(), bBox.getAbsolutePosition()) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				Vector3 aAbs = aBox.getAbsolutePosition();
				Vector3 bAbs = bBox.getAbsolutePosition();
				double dist = Vector3.subtract(aAbs, bAbs).getMagnitude();
				label.set(Double.toString(dist));
			}			
		});
		whenPointsChange = add(new IfVector3Changes(a, b) {
			@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
				updateTextPosition();
			}			
		});
		
		text = add(new TextRect(label, font, 30));
		
		updateTextPosition();
	}

	protected void updateTextPosition() {
		text.getPosition().set(new Vector3(a, b, 0.5));		
		text.getPosition().add(0,1,0);		
	}
	
	@Override public void stop() {
		
	}
	


}
