package automenta.spacenet.var.string;

import java.text.NumberFormat;
import java.util.Arrays;

import automenta.spacenet.var.ObjectVar;



public class StringVar extends ObjectVar<String> {

	public StringVar(String text) {
		super();
		set(text);
	}

	public StringVar() {
		this("");
	}

	@Override public String toString() {
		return "'" + get() + "'";
	}


	public static String padToLength(String l, int maxLineLength) {
		if (l.length() < maxLineLength) {
			int padLength = maxLineLength - l.length();
			char[] ch = new char[padLength];
			Arrays.fill(ch, ' ');
			return l.concat(new String(ch));
		}
		else
			return l;
	}

	public String s() {
		return get();
	}

	public void append(char character) {
		set(s().concat(new Character(character).toString()));		
	}

	public int length() {
		return s().length();
	}

	public void insert(int pos, char character) {
		String current = s();
		String pre = current.substring(0, pos);
		String next= pre + character;
		
		if (pos < current.length())
			next += current.substring(pos, current.length());
		
		set( next );
	}

	public void set(StringVar t) {
		set(t.s());		
	}

	public void append(String string) {
		set(s().concat(string));		
	}

	public void set(double d, int decimalPlaces) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(decimalPlaces);
		
		set( nf.format(d) );
	}

	
}
