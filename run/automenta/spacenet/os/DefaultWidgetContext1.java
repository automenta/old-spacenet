/**
 * 
 */
package automenta.spacenet.os;

import java.awt.Font;
import java.io.File;

import automenta.spacenet.UURI;
import automenta.spacenet.os.style.BorderedSliderDecorator;
import automenta.spacenet.os.style.FlatPanelDecorator;
import automenta.spacenet.os.style.FlatTextEditRectDecorator;
import automenta.spacenet.os.style.GradientTextButtonDecorator;
import automenta.spacenet.space.object.widget.WidgetContext;
import automenta.spacenet.space.object.widget.button.TextButtonDecorator;
import automenta.spacenet.space.object.widget.panel.PanelDecorator;
import automenta.spacenet.space.object.widget.slider.SliderDecorator;
import automenta.spacenet.space.object.widget.text.TextEditRectDecorator;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.map.MapVar;

final class DefaultWidgetContext1 extends WidgetContext {
	
	protected String mediaPath;

	public DefaultWidgetContext1() {
		mediaPath  = new File("./media").toURI().toASCIIString();
		
	}
	
	@Override public double getZLayerDepth() {
		double widgetZLayerDepth = 0.02;
		return widgetZLayerDepth;
	}

	@Override protected VectorFont newDefaultFont() {
		//String defaultFontName = "OCRA"; //"Arial"; // "OCRA";
        String defaultFontName = "Arial"; //"Arial"; // "OCRA";
		VectorFont defaultFont = new VectorFont(defaultFontName, Font.PLAIN, Color.Gray, 5.0, false);
		return defaultFont;
	}

	@Override protected void initMedia() {
		registerIcon("hide", "magic");
		registerIcon("arrow.left", "arrow.right", "arrow.up", "arrow.down");
		registerIcon("arrow.nesw", "arrow.neswdiag", "move");
		registerIcon("bullet.left", "bullet.right");
		registerIcon("forward", "reverse", "play", "stop", "pause");
		registerIcon("plus", "minus");
	}

	@Override protected void initWidgetStyles() {
		setThe(PanelDecorator.class, FlatPanelDecorator.class);
		//setThe(PanelDecorator.class, RandomColorPanelDecorator.class);
		//setThe(PanelDecorator.class, GradientPanelDecorator.class);

        
		setThe(SliderDecorator.class, BorderedSliderDecorator.class);
		setThe(TextEditRectDecorator.class, FlatTextEditRectDecorator.class);
		
		setThe(TextButtonDecorator.class, GradientTextButtonDecorator.class);
	}
	

	protected void registerIcon(String... icons) {
		MapVar<String, UURI> media = getThe(WidgetContext.class).getMedia();
		for (String i : icons) {
			media.put("icon." + i, new UURI(mediaPath + "icon/" + i + ".3ds"));
		}
	}


}