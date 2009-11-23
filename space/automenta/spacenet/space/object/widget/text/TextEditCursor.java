package automenta.spacenet.space.object.widget.text;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.act.Repeat;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.var.ObjectVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IfIntegerChanges;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;

public class TextEditCursor extends Rect implements Starts, Stops {
	
	private TextContainer textEdit;
	private IntegerVar tstart;
	private IntegerVar tstop;
	private double cursorBlinkFrequency = 8.0;
	double cursorBlinkUpdatePeriod = 0.05;

	public TextEditCursor(TextContainer textEdit, IntegerVar start, IntegerVar stop) {
		super();
		this.textEdit = textEdit;
		this.tstart = start;
		this.tstop = stop;		
		tangible(false);
	}
	
	@Override public void start() {
		add(new IfIntegerChanges(tstart, tstop) {
			@Override public void afterValueChange(ObjectVar o, Integer previous,	Integer next) {
				updateCursor();
			}			
		});
		add(new IfStringChanges(textEdit.getText()) {
			@Override public void afterTextChanged(StringVar t, String previous, String current) {
				updateCursor();
			}			
		});
		
		if (cursorBlinkFrequency > 0) {
			add(new Repeat() {
				@Override public double repeat(double t, double dt) {
					double a = (1.0 + Math.sin(cursorBlinkFrequency * t))/2.0;
					getOpacity().set(a);
					return cursorBlinkUpdatePeriod;
				}			
			});
		}
		
		updateCursor();
	}

	protected void updateCursor() {
		int start = tstart.i();
		int stop = tstop.i();
		
		double baseWidth = 0.01;
		
		Rect r = textEdit.getCharacterRect(start);
		
		if (r == null)
			r = new Rect();
		
		if (r.getSize().x() == 0) {
			r.getSize().setX(baseWidth);
		}
		if (r.getSize().y() == 0) {
			r.getSize().setY(0.7);
		}
		
		double xCharProp = 0.1;
		
		double w = r.getSize().x() * xCharProp;
		double h = r.getSize().y();

		double x = r.getPosition().x() - r.getSize().x() / 2.0 + w;
		double y = r.getPosition().y();
		double z = r.getPosition().z();
		
		
		x -= w/2.0;
		
		getPosition().set(x, y, z);
		getSize().set(w, h);
	}

	@Override public void stop() {
		
	}


	
}
