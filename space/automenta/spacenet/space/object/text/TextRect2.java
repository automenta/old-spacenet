package automenta.spacenet.space.object.text;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import automenta.spacenet.space.Surface;
import automenta.spacenet.space.geom2.CharRect;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.data.MatrixRect;
import automenta.spacenet.space.object.widget.text.TextContainer;
import automenta.spacenet.space.Color;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.space.video3d.VectorFont;
import automenta.spacenet.var.number.BooleanVar;
import automenta.spacenet.var.number.DoubleVar;
import automenta.spacenet.var.string.IfStringChanges;
import automenta.spacenet.var.string.StringVar;
import automenta.spacenet.var.string.WordWrap;
import automenta.spacenet.var.vector.Vector2;
import automenta.spacenet.var.vector.Vector3;

public class TextRect2 extends MatrixRect implements TextContainer {
	private static final Logger logger = Logger.getLogger(TextRect2.class);

	private StringVar text;

	public TextRect2(StringVar t, VectorFont font) {
		super();
		this.text = t;
		this.font = font;
	}
	
	public TextRect2(StringVar t) {
		this(t, null);
	}

	public StringVar getText() {
		return text;
	}


	public VectorFont getFont() {
		return font;
	}
	

	private VectorFont font;

	private List<String> lines = new LinkedList();
	private Map<Integer,CharRect> charNodes = new HashMap();
	

	private Color textColor;

	private int charsWide = -1;
	private int charsTall = -1;

	private int maxLineLength;

	private BooleanVar wordWrap = new BooleanVar(true);

	protected boolean needsTextUpdated;

	
	@Override public void start() {
		needsTextUpdated = true;

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
				needsTextUpdated = true;
			}			
		});
//		add(new IfVector2Changes(getSize()) {			
//			@Override public void afterVectorChanged(Vector2 v, double dx, double dy) {
//				updateTextRect(true, true);
//			}			
//		});



		super.start();

		content.align(-1, 1);

		
	}

	


	@Override public void stop() {
		super.stop();
	}

	public BooleanVar getWordWrap() {
		return wordWrap ;
	}

	/** arranges character nodes */
	protected synchronized void updateText(boolean textChanged, boolean sizeChanged) {
		textChanged = sizeChanged = true;

		lines.clear();
		maxLineLength = 0;

		String t;
		
		if (getWordWrap().b()) {
			int vw = getOptimalWrapChars();
			t = getWrappedString(getText().s(), vw);
		}
		else {
			t = getText().s();
		}
		
		List<String> newLines = tokenizeLines(t);

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
		
		int numLines = lastLine - firstLine;
		
		double rw = getSize().x();
		double rh = getSize().y();

		double ra = rh/rw;

		int i = 0;

		double a = getCharAspect().get();


		
		charNodes.clear();

		
		removeAll();
		
		for (int k = firstLine; k < lastLine; k++) {
			String l = lines.get(k);
			
			
			for (int p = 0; p < l.length(); p++) {
				CharRect cn = charNodes.get(i);
				if (isEachCharacterColor()) {
					cn = new CharRect(newPositionVector(), newSizeVector(), l.charAt(p), font, new ColorSurface(getColor(cn, p)));
				}
				else {
					cn = new CharRect(newPositionVector(), newSizeVector(), l.charAt(p), font, (Surface)null);						
				}
				charNodes.put(i, cn);
				put(p, k, cn);
				i++;
			}
		}

		
	}

	private int getOptimalWrapChars() {
		double a = getAbsoluteSize().y() / getAbsoluteSize().x();
		return 40;
	}

	private String getWrappedString(String s, int charsWide) {
		return WordWrap.wordWrap(s, charsWide, "\n", "\n");
	}

	public void viewAll() {
		getVisCX().set((getMaxX().d() + getMinX().d())/2.0);
		getVisCY().set((getMaxY().d() + getMinY().d())/2.0);
		getVisWidth().set(getMaxX().i() - getMinX().i());
		getVisHeight().set(getMaxY().i() - getMinY().i());
	}
	
	@Override
	protected void updateMatrix() {
		if (needsTextUpdated) {
			updateText(true, true);
			needsTextUpdated = false;
		}

		super.updateMatrix();
	}
	
	@Override
	protected void layout() {
		
		super.layout();
		
		double cw = getVisWidth().d();
		double ch = getVisHeight().d();
		double h = getCharAspect().get() * ch;
		double w = 1.0 * cw;
		content.getAspectXY().set(h / w);


	}
	

	private Vector3 newPositionVector() {
		return new Vector3();
	}

	private Vector2 newSizeVector() {
		return new Vector2(1,1);
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

	public Rect getCharacterRect(int position) {
		if (getText().length() == 0) {
			double a = getCharAspect().d();
			double cw = maxLineLength;
			double ch = 1*a;

			double h = a / ch;
			double w = 1.0 / cw;

			double y = 0.5-h/2;
			double x = -0.5+w/2;

			return new Rect(x, y, 0, w, h);
		}
		
		Rect r = charNodes.get(position);
		if (r!=null) {
			return r;
		}
		
		if ((position == getText().length()) && (getText().length() > 0)) {
			Rect a = charNodes.get(position-1);
			if (a == null)
				return null;
			return new Rect(a.x() + a.w(), a.y(), a.z(), a.w(), a.h());
		}

		return null;
		

		//		int whichChar = -1;
		//		int whichLine = -1;
		//		int lineLength;
		//		TextLineRect whichRect = null;
		//		
		//		int c = 0;
		//		int l = 0;
		//		
		//		double x, y, w, h;
		//		if (position < getText().length()) {
		//			for (l = 0; l < lineRects.size(); l++) {
		//				TextLineRect tr = lineRects.get(l);
		//				String text = lines.get(l);
		//				
		//				lineLength = text.length();
		//				
		//				if ((position >= c) && (position < c + lineLength)) {
		//					whichRect = tr;
		//					whichChar = position - c;
		//					whichLine = l;
		//					
		//					break;
		//				}
		//				c += lineLength;
		//				l++;
		//			}
		//			
		//			if ((whichChar == -1) || (whichLine == -1)) {
		//				logger.warn("character at position " + position + " not found");
		//				return r;
		//			}
		//			
		//			x = (((double)whichChar) / ((double)whichRect.getText().length()) - 0.5);
		//			y = 0; //tODO get right 'y'
		//			w = 0;
		//			h = 0;
		//		}
		//		else {
		//			x = 0.5;
		//			y = 0;
		//			w = h = 0;
		//		}
		//		
		//		r.move(x, y);
		//		r.scale(w, h);

		//		return r;
	}

	private int getMaxLineChars() {
		int l = getFont().getMaxLineChars().i();
		if (l == -1) {
			return charsWide;
		}
		else
			return l;
	}

	public DoubleVar getCharAspect() { return getFont().getCharAspect(); }



}
