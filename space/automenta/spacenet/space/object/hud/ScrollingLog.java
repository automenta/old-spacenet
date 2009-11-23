package automenta.spacenet.space.object.hud;

import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.dynamic.SpaceFifo;
import automenta.spacenet.space.dynamic.collection.ArrangeColumn;
import automenta.spacenet.space.dynamic.vector.DynamicVectorSet;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.text.TextRect;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.Vector3;



public class ScrollingLog extends Rect implements StartsIn<Scope>, Stops {

	int maxLineChars = 30;

	private WriterAppender appender;
	private SpaceFifo msgFifo;
	private DynamicVectorSet vectorSet;

	@Override public void start(Scope c) {
		appender = new WriterAppender() {
			@Override public void append(LoggingEvent event) {
				appendLog(event);
			}
		};
		
		vectorSet = add(new DynamicVectorSet(0.05));

		
		Logger.getRootLogger().addAppender(appender);
		
		Rect text = add(new Rect().color(Color.GrayPlus.alpha(0.3)));

		msgFifo = add(new SpaceFifo(text, 8, true));

		add(new ArrangeColumn(text, 0.1, 0));
	}

	protected void appendLog(LoggingEvent event) {
		String msg = event.getLoggerName() + " - " + event.getRenderedMessage();

		System.out.println(msg);	
		
		Vector3 pos = vectorSet.newVector3(0, -0.3, 0, 0.2);
		TextRect msgRect = new TextRect(pos, new StringVar(msg), maxLineChars, Color.newRandomHSB(0.6, 1.0));
		
		msgFifo.push(msgRect);
	}

	@Override public void stop() {
		if (appender!=null) {
			Logger.getRootLogger().removeAppender(appender);
			appender = null;
		}
	}

}
