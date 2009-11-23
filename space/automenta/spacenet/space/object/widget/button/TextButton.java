package automenta.spacenet.space.object.widget.button;

import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.Space;
import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.object.widget.Focusable;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.string.StringVar;

public class TextButton extends Button implements Focusable {

	private TextRect label;
	private VectorFont font;
	private double buttonZ;


	
//	public static class VectorPlus extends Vector3 implements StartsIn<Scope> {
//		private Vector3 a;
//		private Vector3 b;
//
//		double abx;
//		double aby;
//		double abz;
//		
//		public VectorPlus(Vector3 a, Vector3 b) {
//			super();
//			this.a = a;
//			this.b = b;
//			update();
//		}
//
//		@Override public void start(Scope node) {
//			node.add(new IfVector3Changes(getA(), getB()) {
//				@Override public void afterVectorChanged(Vector3 v, double dx, double dy, double dz) {
//					update();
//				}				
//			});
//			
//			update();			
//		}
//		
//		private void update() {
//			abx = getA().x() + getB().x();
//			aby = getA().y() + getB().y();
//			abz = getA().z() + getB().z();
//			set(abx, aby, abz);
//		}
//		
//		public Vector3 getA() {
//			return a;
//		}
//		
//		public Vector3 getB() {
//			return b;
//		}
//		
//	}
	
	public TextButton(String label) {
		this(new StringVar(label));		
	}

	public TextButton(StringVar label) {
		super(label);		
	}

	public TextButton(String str, VectorFont font) {
		this(str);
		this.font = font;
	}

	
	@Override protected void initButton() {
		
//		captionRect = add(new TextRect(getCaption(), getTextColor(), font).scale(getSize().x(), getSize().y()).move(0,0,textButtonTextDZ));
//		captionRect.setTangibility(false);


	}
	
	public TextRect getLabel() {
		return label;
	}
	
//	@Override public Vector3 getPosition() {
//		//return super.getPosition();
//		return buttonPosition;
//	}
	
	@Override public void pressUpdate(Pointer c, double timePressed) {
		super.pressUpdate(c, timePressed);

		double dz = -buttonDepressionDepth * Math.pow(Math.min(1.0, timePressed/buttonDepressionTime ), 0.5);
		setButtonZ(dz);

	}	

	private void setButtonZ(double bz) {
		this.buttonZ = bz;
		
		double z = Math.min(buttonZ, 0);
		panelRect.move(0,0,z);
		
		if (label!=null) {
			label.move(0,0,0);
			widgets().pullDZ(label);
			label.moveDZ(z);
		}	
	}

	@Override public void pressStopped(Pointer c) {
		super.pressStopped(c);

		try {
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					double dz = -buttonDepressionDepth * Math.pow(Math.min(1.0, (1.0) - (t/buttonDepressionTime) ), 0.5);
					if (dz < 0) {
						setButtonZ(dz);
					}
					else {
						setButtonZ(0);
						return -1;
					}								
					return 0;
				}
			});
		}
		//the button may have been removed as a result of being clicked.  so trap a NullPointerException
		catch (NullPointerException e) { }
	}
		
	
	
	@Override protected void updateWidget() {
		super.updateWidget();

		if (label == null) {
			label = add(new TextRect(getCaption(), Color.White, font));
			//label = add(new TextRect3(getCaption(), Color.White, font));
			label.move(0,0,0);
			widgets().pullDZ(label);
			label.tangible(false);
		}

		
		TextButtonDecorator tbd = getThe(TextButtonDecorator.class);
		if (tbd!=null) {
			tbd.decorateTextButton(this, isTouched(), isPressed());
		}
		

//		if (!isPressed()) {
//			panelRect.move(0,0,0);
//			if (label!=null) {
//				label.move(0,0,0);
//				widgets().pullDZ(label);
//			}
//		}

	}

	@Override public Space getTouchable() {
		return panelRect;
	}

	@Override
	public void onFocusChange(boolean focused) {
		// TODO Auto-generated method stub
		
	}





}
