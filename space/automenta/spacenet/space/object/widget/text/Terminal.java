/**
 * 
 */
package automenta.spacenet.space.object.widget.text;

import automenta.spacenet.Starts;
import automenta.spacenet.Stops;
import automenta.spacenet.space.geom2.Rect.RectPosition;
import automenta.spacenet.space.geom3.Box;
import automenta.spacenet.space.object.text.TextRect2;
import automenta.spacenet.space.object.widget.text.TextEditRect;
import automenta.spacenet.var.string.StringVar;

abstract public class Terminal extends Box implements Starts, Stops {

	private StringVar output;

	int charsWide;
	int outputLines;
	
	public Terminal(int charsWide, int charsTall) {
		super();
		
		this.charsWide = charsWide;
		this.outputLines = charsTall;
	}

	@Override public void start() {

		double outputProportion = 0.92;
		double gap = 0.05;


		output = new StringVar("");

		//			TextRect outputRect = add(new TextRect(output, charsWide, outputLines));
		final TextRect2 outputRect = add(new TextRect2(output) {
			@Override
			protected synchronized void updateText(boolean textChanged,
					boolean sizeChanged) {
				super.updateText(textChanged, sizeChanged);

				getVisWidth().set(80);
				getVisHeight().set(25);
				getVisCY().set(0);	//HACK 0 should mean top, but it currently means bottom
			}

			@Override protected void layout() {

				super.layout();
			}

		});
		outputRect.inside(RectPosition.N, 1.0, outputProportion - gap /2.0);

		final StringVar input = new StringVar("");
		TextEditRect inputRect = add(new TextEditRect(input, charsWide, 1) {

			@Override protected synchronized void enter() {
				super.enter();

				String output = getOutput(input.get());
				getOutput().append(output);				

				input.set("");

			}

		});
		inputRect.inside(RectPosition.S, 1.0, (1.0 - outputProportion) - gap/2.0);

		startTerminal();
	}

	public StringVar getOutput() {
		return output;
	}

	abstract protected void startTerminal();

	abstract protected String getOutput(String input);

	@Override public void stop() {

	}
}