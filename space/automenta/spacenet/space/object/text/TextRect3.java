package automenta.spacenet.space.object.text;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import automenta.spacenet.Starts;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.geom2.TextLineRect;
import automenta.spacenet.space.geom2.TextLineRect.TextType;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.vector.IfVector2Changes;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;


/**
 * displays multiple lines of text in a rectangle, with correct character aspect ratio.
 * creates a TextLineRect's for each line of text.  this is more efficient than TextRect, which creates a CharRect for each character  
 */
public class TextRect3 extends Rect implements Starts {
	//TODO refactor & cleanup constructors
	
	private static final Logger logger = Logger.getLogger(TextRect3.class);

	private StringVar text;
	private VectorFont font;

	private List<String> lines = new LinkedList();
	private List<TextLineRect> lineNodes = new LinkedList();

	private IfVector2Changes whenSizeChanges;

	private Color textColor;

	private int charsWide = -1;
	private int charsTall = -1;

	private Rect charRect;

	private IntegerVar maxLines = new IntegerVar(-1);

//	/** TODO simplify and un-redundant constructors */
//	public TextRect(Rect r, StringVar text, Color textColor) {
//		super(r.getPosition(), r.getSize(), r.getOrientation());
//		this.text = text;
//		this.font = null;
//
//		getTangible().set(false);
//		getSurface().set(new ColorSurface(textColor));
//		this.textColor = textColor;
//
//	}
	
	public TextRect3(Vector3 pos, StringVar text, int lineChars, Color textColor) {
		super(pos, new Vector2(1,1), new Vector3());
		this.text = text;
		this.font = null;
		this.charsWide  = lineChars;
		
		getSurface().set(new ColorSurface(textColor));
		this.textColor = textColor;

	}

	public TextRect3(Vector3 pos, StringVar text, int lineChars) {
		this(pos, text, lineChars, null);
	}

	public TextRect3(StringVar text, VectorFont font, int lineChars) {
		this(null, text, lineChars, null);
		this.font = font;
	}

	public TextRect3(String s) {		this(new StringVar(s));	}

	
	public TextRect3(StringVar s, int maxLineChars) {	
		this(s, null, maxLineChars);	
	}

	public TextRect3(StringVar s, int maxLineChars, int maxLines) {	
		this(s, maxLineChars);
		getMaxLines().set(maxLines);
	}

	private IntegerVar getMaxLines() {
		return maxLines ;
	}

	public TextRect3(StringVar s) {	this(s, null, -1);	}
	
	public TextRect3(StringVar s, Color color) {	
		this(s);

		getSurface().set(new ColorSurface(color));
		this.textColor = color;
	}

	
	public TextRect3(String string, VectorFont font) {	this(new StringVar(string), font, -1);	}


	public TextRect3(StringVar s, VectorFont vectorFont, int lineChars, Color c) {
		this(s, vectorFont, lineChars);

		getSurface().set(new ColorSurface(c));
		this.textColor = c;
	}

	public TextRect3(StringVar text, int maxLineChars, Color textColor) {
		this(text, textColor);
		this.charsWide = maxLineChars;
		
	}


	public TextRect3(StringVar str, Color color, VectorFont font) {
		this(str, color);
		this.font = font;
	}


	public TextRect3(String string, Color color) {
		this(new StringVar(string), color);
	}

	public TextRect3(String string, int lineChars) {
		this(new StringVar(string), lineChars);
	}

	public TextRect3() {
		this(new StringVar());
	}

	public void alignText(double ax, double ay) {
		charRect.align(ax, ay);
	}
	
	@Override public void start() {
		tangible(false);

		charRect = add(new Rect());
		
		//charRect.getAspectXY().set(1.0);
		charRect.align(-1, 1);
		charRect.tangible(false);

		if (font == null) {
			font = getThe(VectorFont.class);
			
			if (charsWide!=-1) {
				font = font.withMaxLineChars(charsWide);
			}
			
			if (font == null) {
				logger.error("no default font exists, unable to create TextRect");
				return;
			}
		}


		add(new IfStringChanges(text) {
			@Override public void afterTextChanged(StringVar t, String previous, String current) {
				updateTextRect(true, true);
			}			
		});
//		whenSizeChanges = add(new IfVector2Changes(getSize()) {
//			
//			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
//				updateTextRect(true, true);
//			}			
//		});

		updateTextRect(true, true);
		
	}



	@Override public void stop() {
	}

	
	int maxLineLength = 0;

	/** arranges character nodes */
	protected synchronized void updateTextRect(boolean textChanged, boolean sizeChanged) {
		textChanged = sizeChanged = true;

		lines.clear();
		maxLineLength = 0;

		List<String> newLines = tokenizeLines(getText().s());

		for (String l : newLines) {
			if (newLines.size() > 1)
				if (!l.endsWith("\n"))
					l += "\n";
			
			if (getMaxLineChars() > 1) {
				int c = 0;
				while (c < l.length()) {
					int nextC = c + Math.min(l.length()-c, getMaxLineChars());
					String ls = l.substring(c, nextC );
					lines.add( ls );
					if (ls.length() > maxLineLength) {
						maxLineLength = ls.length();
					}
					c+=getMaxLineChars();
				}
			}
			else {
				lines.add(l);
				if (l.length() > maxLineLength)
					maxLineLength = l.length();
			}
		}

		if (getMaxLineChars() > 1)
			maxLineLength = getMaxLineChars();

		
		int firstLine = 0;
		int lastLine = lines.size();
		
		if (getMaxLines().i() != -1) {
			if ((lastLine - firstLine) > (getMaxLines().i())) {
				firstLine = lines.size() - getMaxLines().i();
				lastLine = firstLine + getMaxLines().i();
			}
		}

		int numLines = lastLine - firstLine;
		
		double rw = getSize().x();
		double rh = getSize().y();

		double ra = rh/rw;

		int i = 0;
		double x, y, w, h;

		double a = getCharAspect().get();


		
		//charRect.removeAll(charNodes.values());
		
		

		double cw = maxLineLength;
		double ch = Math.max(1, numLines)*a;
		h = a / ch;
		w = 1.0 / cw;
		
		x = 0;
		y = 0.5-h/2;
		
		lineNodes.clear();
		for (int k = firstLine; k < lastLine; k++) {
			String l = lines.get(k);
			
			x = 0;
			
			if (l.length() < maxLineLength) {
				l = StringVar.padToLength(l, maxLineLength);
			}
			StringVar sl = new StringVar(l);
			
			TextLineRect tn = new TextLineRect(sl, font, TextType.Bitmap);
			tn.tangible(false);
			tn.move(x, y);
			
			lineNodes.add(tn);
			y -= h;
		}

		
		charRect.aspect(cw, ch*a);
		charRect.clear();
		charRect.addAll(lineNodes);
		
	}


	/** separates a string by '\n'.  if multiple '\n', adds a blank string.  this is different from String.split()'s functionailty when handling consecutive '\n' */
	private List<String> tokenizeLines(String s) {
		List<String> l = new LinkedList();
		
		int p = 0;
		int i;
		for (i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\n') {
				l.add(s.substring(p, i));
				p = i + 1;
			}
		}
		l.add(s.substring(p, i));
		
		return l;
	}

	private boolean isEachCharacterColor() {
		return false;
	}

	public Color getColor() {
		if (textColor!=null)
			return textColor;
		return font.getDefaultColor();
	}

	protected Color getColor(CharRect cn, int p) {
		if (textColor!=null)
			return textColor;
		return font.getDefaultColor();
	}

//	public Rect getCharacterRect(int position) {
//		if (getText().length() == 0) {
//			double a = getCharAspect().d();
//			double cw = maxLineLength;
//			double ch = 1*a;
//
//			double h = a / ch;
//			double w = 1.0 / cw;
//
//			double y = 0.5-h/2;
//			double x = -0.5+w/2;
//
//			return new Rect(x, y, 0, w, h);
//		}
//		
//		Rect r = lineNodes.get(position);
//		if (r!=null) {
//			return r;
//		}
//		
//		if ((position == getText().length()) && (getText().length() > 0)) {
//			Rect a = lineNodes.get(position-1);
//			return new Rect(a.x() + a.w(), a.y(), a.z(), a.w(), a.h());
//		}
//
//		return null;
//		
//
//		//		int whichChar = -1;
//		//		int whichLine = -1;
//		//		int lineLength;
//		//		TextLineRect whichRect = null;
//		//		
//		//		int c = 0;
//		//		int l = 0;
//		//		
//		//		double x, y, w, h;
//		//		if (position < getText().length()) {
//		//			for (l = 0; l < lineRects.size(); l++) {
//		//				TextLineRect tr = lineRects.get(l);
//		//				String text = lines.get(l);
//		//				
//		//				lineLength = text.length();
//		//				
//		//				if ((position >= c) && (position < c + lineLength)) {
//		//					whichRect = tr;
//		//					whichChar = position - c;
//		//					whichLine = l;
//		//					
//		//					break;
//		//				}
//		//				c += lineLength;
//		//				l++;
//		//			}
//		//			
//		//			if ((whichChar == -1) || (whichLine == -1)) {
//		//				logger.warn("character at position " + position + " not found");
//		//				return r;
//		//			}
//		//			
//		//			x = (((double)whichChar) / ((double)whichRect.getText().length()) - 0.5);
//		//			y = 0; //tODO get right 'y'
//		//			w = 0;
//		//			h = 0;
//		//		}
//		//		else {
//		//			x = 0.5;
//		//			y = 0;
//		//			w = h = 0;
//		//		}
//		//		
//		//		r.move(x, y);
//		//		r.scale(w, h);
//
//		//		return r;
//	}

	public VectorFont getFont() {
		return font;
	}

	private int getMaxLineChars() {
		int l = getFont().getMaxLineChars().i();
		if (l == -1) {
			return charsWide;
		}
		else
			return l;
	}

	public StringVar getText() {
		return text;
	}

	public DoubleVar getCharAspect() { return getFont().getCharAspect(); }



}
