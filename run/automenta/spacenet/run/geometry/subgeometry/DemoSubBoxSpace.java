package automenta.spacenet.run.geometry.subgeometry;

import automenta.spacenet.Starts;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.vector.Vector3;

//TODO load grid shader once, when used
public class DemoSubBoxSpace extends Box implements Starts {

	private int remainingDepth;
	private Vector3 initialPosition;
	private Vector3 initialSize;

	double timeFactor = 0.1;
	double sizeFactor = 0.5;
	double positionFactor = 1.0;
	
	public DemoSubBoxSpace(double x, double y, double z, double w, double h, double d, Color c, int nextRemainingDepth) {			
		//super(superSpace, new Vector3(x,y,z), new Vector3(w,h,d), DemoGridShader.getGridSurface());
		super(new Vector3(x,y,z), new Vector3(w,h,d));

		color(c);
		
		this.remainingDepth = nextRemainingDepth;	

		this.initialPosition = new Vector3(getPosition());
		this.initialSize = new Vector3(getSize());

	}

	@Override public void start() {
		double s = 2.0;

		System.out.println(this + " " + remainingDepth);
		if (remainingDepth>0) {
			add(new DemoSubBoxSpace(s,0,0,0.5,0.5,0.5, Color.Red, remainingDepth-1));
			add(new DemoSubBoxSpace(-s,0,0,0.5,0.5,0.5, Color.Blue, remainingDepth-1));
			add(new DemoSubBoxSpace(0,0,-s,0.5,0.5,0.5, Color.Purple, remainingDepth-1));
			add(new DemoSubBoxSpace(0,0,s,0.5,0.5,0.5, Color.Orange, remainingDepth-1));
			add(new DemoSubBoxSpace(0,s,0,0.5,0.5,0.5, Color.Yellow, remainingDepth-1));
			add(new DemoSubBoxSpace(0,-s,0,0.5,0.5,0.5, Color.GrayPlus, remainingDepth-1));
		}

		add(new Repeat() {
			@Override public double repeat(double t, double dt) {
				t*=timeFactor;
				double r = t / (1+remainingDepth) + 0.5;
				getOrientation().set(r, 0, r);
				
				double s = (Math.sin(t * r) +1.0* 0.1) + (Math.cos(-t*r / 20.0)+1.0*0.5)   + 1.0;
				getSize().set(initialSize);
				getSize().multiply(s * sizeFactor);
				
				getPosition().set(initialPosition);
				getPosition().multiply(s * positionFactor);
				return 0.0;
			}			
			
			@Override
			public String toString() {	
				return "Rotate[" + DemoSubBoxSpace.this + "]";
			}
		});

//		startOld(new RepeatAction() {
//		@Override public double afterElapsed(double t, double dt) {
//			b.getOrientation().set((Math.cos(t)+1.0)+0.5, Math.sin(t), 0);
//			return 0.0;
//		}			
//	});


	}
	
	@Override public void stop() {
		
	}

	@Override
	public String toString() {
		return "DemoSubBoxSpace(" + remainingDepth + ")";
	}

	public DemoSubBoxSpace() {
		this(0,0,0,0.1,0.1,0.1,Color.Gray.alpha(0.5), 3);
	}
	public static void main(String[] args) {
		new DefaultJmeWindow(new DemoSubBoxSpace(0,0,0,3,3,3,Color.Gray.alpha(0.5), 3));
	}
}
