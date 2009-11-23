package automenta.spacenet.space;

import java.util.Set;

import javolution.util.FastSet;
import automenta.spacenet.var.ObjectVar;



public class Color extends ObjectVar<Double[]> {


	public static final Color White = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color Black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color Invisible = new Color(0.0f, 0.0f, 0.0f, 0.0f);

	public static final Color Gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color GrayPlus = new Color(0.6f, 0.6f, 0.6f, 1.0f);
	public static final Color GrayPlusPlus = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	public static final Color GrayMinus = new Color(0.4f, 0.4f, 0.4f, 1.0f);
	public static final Color GrayMinusMinus = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	public static final Color GrayMinusMinusMinus = new Color(0.2f, 0.2f, 0.2f, 1.0f);

	public static final Color GrayTransparent = new Color(0.5f, 0.5f, 0.5f, 0.5f);

	public static final Color Red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color Orange = new Color(1.0f, 0.75f, 0.0f, 1.0f);
	public static final Color Yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	public static final Color Green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color Blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color Purple = new Color(0.75f, 0.0f, 1.0f, 1.0f);
	public static final Color GrayMinusMinusMinusMinus = new Color(0.1, 0.1, 0.1, 1.0);
	
	private Set<IfColorChanges> whenChanges;

	double r, g, b, a;

	public Color(double r, double g, double b, double a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(double r, double g, double b) {
		this(r,g,b,1.0);
	}

	public Color(double grayIntensity, double a) {
		this(grayIntensity, grayIntensity, grayIntensity, a);
	}

	public Color() {
		this(0.5, 0.5, 0.5, 0.5);
	}

	public Color(Color c) {
		this(c.r(), c.g(), c.b(), c.a());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "{" + r() + "," + g() + "," + b() + "," + a() + "}";
	}

	public void set(double r, double g, double b) {		
		set(r, g, b, 1.0f);
	}

	public void set(double r2, double g2, double b2, double a2) {
		r2 = Math.max(Math.min(r2, 1.0), 0.0);
		g2 = Math.max(Math.min(g2, 1.0), 0.0);
		b2 = Math.max(Math.min(b2, 1.0), 0.0);
		a2 = Math.max(Math.min(a2, 1.0), 0.0);

		if ((r!=r2) || (g!=g2) || (b!=b2) || (a!=a2)) { 

			r = r2;
			g = g2;
			b = b2;
			a = a2;

			if (whenChanges!=null) {
				for (IfColorChanges w : whenChanges) {
					w.afterColorChanged(this);
				}
			}
		}
	}

	//TODO eventually make these get?() methods public, when sure they function correctly (w/ unit tests)

	public double r() { return r; }
	public double g() { return g; }
	public double b() { return b; }
	public double a() { return a; }



	public Color multiply(double d) {
		return new Color( r() * d, g() *d, b() * d, a() );
	}




	public Color alpha(double a) {
		return new Color(r(), g(), b(), a);
	}

	public boolean isInvisible() {
		return a()==0.0;
	}

	public static Color newRandom() {
		return new Color(Math.random(), Math.random(), Math.random(), Math.random());
	}
	public static Color newRandom(double a) {
		return new Color(Math.random(), Math.random(), Math.random(), a);
	}

	public static Color newRandomHSB(double saturation, double brightness) {
		//TODO remove dependency on AWT for HSB function
		float hue = (float) Math.random();
		java.awt.Color c = java.awt.Color.getHSBColor(hue, (float)saturation, (float)brightness);
		return fromAWTColor(c);
	}

	public static Color fromAWTColor(java.awt.Color c) {
		float f[] = new float[4];
		c.getComponents(f);
		return new Color(f[0], f[1], f[2], f[3]);
	}

	public java.awt.Color toAWTColor() {
		return new java.awt.Color((float)r(), (float)g(), (float)b(), (float)a());
	}

	public void set(double grayIntensity, double alpha) {
		set(grayIntensity, grayIntensity, grayIntensity, alpha);		
	}

	protected void whenStarted(IfColorChanges w) {
		if (whenChanges == null) {
			whenChanges = new FastSet();
		}
		whenChanges.add(w);		
	}
	protected void whenStopped(IfColorChanges w) {
		whenChanges.remove(w);
	}

	public void set(Color c) {
		set(c.r(), c.g(), c.b(), c.a());
	}


	//	public ColorRGBA asJMEColor() {
	//	return new ColorRGBA((float)r(), (float)g(), (float)b(), (float)a());
	//}


	@Override
	public Color clone() { return new Color(r(), g(), b(), a()); }

	public static Color gray(double brightness) {
		return new Color(brightness,brightness,brightness,1.0);
	}

	public static Color gray(double brightness, double alpha) {
		return new Color(brightness,brightness,brightness,alpha);
	}

	public static Color hsb(double hue, double saturation, double brightness) {
		//TODO remove dependency on AWT for HSB function
		java.awt.Color c = java.awt.Color.getHSBColor((float)hue, (float)saturation, (float)brightness);
		return fromAWTColor(c);
	}

	public void setA(double a) {
		set(r(), g(), b(), a);		
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Color) {
			Color o = (Color) obj;
			if ((o.r() == r()) && (o.g() == g()) && (o.b() == b()) && (o.a() == a()))
				return true;
			
		}
		return false;
	}
	
}
