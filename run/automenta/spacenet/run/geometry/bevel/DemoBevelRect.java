package automenta.spacenet.run.geometry.bevel;

import automenta.spacenet.os.DefaultJmeWindow;
import automenta.spacenet.os.ProcessBox;
import automenta.spacenet.space.geom3.BevelRect;
import automenta.spacenet.space.object.measure.GeometryViewer;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.slider.Slider;
import automenta.spacenet.space.object.widget.slider.Slider.SliderType;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.DoubleVar;

public class DemoBevelRect extends ProcessBox {

	public static class BevelControlPanel extends Panel {

		private BevelRect bevel;

		public BevelControlPanel(BevelRect br) {
			super();
			this.bevel = br;
		}
		
		@Override public void start() {
			super.start();
			
			double dz = 0.05;
			
			final Slider bz = add(new Slider(bevel.getBevelZ(), new DoubleVar(-0.5), new DoubleVar(0.5), new DoubleVar(0.1), SliderType.Vertical));
			bz.span(-0.45, -0.45, -0.15, 0.45);
			bz.moveDelta(0,0,dz);
			
			final Slider bxy = add(new Slider(bevel.getBevelBorderX(), new DoubleVar(0), new DoubleVar(0.5), new DoubleVar(0.1), SliderType.Vertical));
			bxy.span(0.45, -0.45, 0.15, 0.45);
			bxy.moveDelta(0,0,dz);

		}
		
		
	}
	

	@Override public void run() {
				
		BevelRect br = (BevelRect) new BevelRect(Color.Gray.alpha(0.75)).scale(0.5);
		GeometryViewer w = add(new GeometryViewer(br));
		
		w.add(new BevelControlPanel(br).move(0.5+0.25/2.0,0,0).scale(0.25, 0.5));

	}

	public static void main(String[] args) {		
		new DefaultJmeWindow(new DemoBevelRect().scale(4.0));
	}
	
}
