package automenta.spacenet.space.control.keyboard;

import automenta.spacenet.Scope;
import automenta.spacenet.StartsIn;
import automenta.spacenet.Stops;
import automenta.spacenet.space.control.Sensor;
import automenta.spacenet.space.control.Toggle;
import automenta.spacenet.var.list.ListVar;

public abstract class Keyboard implements Sensor {
	
	protected ListVar<IfKeyboardChanges> whenChange = new ListVar();
	
	public static abstract class IfKeyboardChanges implements StartsIn<Scope>, Stops {
		
		private Keyboard keyboard;

		@Override public void start(Scope n) {
			keyboard = n.getThe(Keyboard.class);
			keyboard.startKeyboardChange(this);
		}
		
		@Override public void stop() {
			keyboard.stopKeyboardChange(this);			
		}
		
		
		abstract public void onKey(char character, int code, boolean pressed);
	
		public Keyboard getKeyboard() { return keyboard; }
		
	}
	
	abstract public Toggle getKey(int keyCode);

	protected void startKeyboardChange(IfKeyboardChanges w) {
		whenChange.add(w);
	}
	
	protected void stopKeyboardChange(IfKeyboardChanges w) {
		whenChange.remove(w);
	}

	abstract protected void onKey(char character, int keyCode, boolean pressed);


    /**
     * escape key.
     */
    public static final int KEY_ESCAPE = 0x01;
    /**
     * 1 key.
     */
    public static final int KEY_1 = 0x02;
    /**
     * 2 key.
     */
    public static final int KEY_2 = 0x03;
    /**
     * 3 key.
     */
    public static final int KEY_3 = 0x04;
    /**
     * 4 key.
     */
    public static final int KEY_4 = 0x05;
    /**
     * 5 key.
     */
    public static final int KEY_5 = 0x06;
    /**
     * 6 key.
     */
    public static final int KEY_6 = 0x07;
    /**
     * 7 key.
     */
    public static final int KEY_7 = 0x08;
    /**
     * 8 key.
     */
    public static final int KEY_8 = 0x09;
    /**
     * 9 key.
     */
    public static final int KEY_9 = 0x0A;
    /**
     * 0 key.
     */
    public static final int KEY_0 = 0x0B;
    /**
     * - key.
     */
    public static final int KEY_MINUS = 0x0C;
    /**
     * = key.
     */
    public static final int KEY_EQUALS = 0x0D;
    /**
     * back key.
     */
    public static final int KEY_BACK = 0x0E;
    /**
     * tab key.
     */
    public static final int KEY_TAB = 0x0F;
    /**
     * q key.
     */
    public static final int KEY_Q = 0x10;
    /**
     * w key.
     */
    public static final int KEY_W = 0x11;
    /**
     * e key.
     */
    public static final int KEY_E = 0x12;
    /**
     * r key.
     */
    public static final int KEY_R = 0x13;
    /**
     * t key.
     */
    public static final int KEY_T = 0x14;
    /**
     * y key.
     */
    public static final int KEY_Y = 0x15;
    /**
     * u key.
     */
    public static final int KEY_U = 0x16;
    /**
     * i key.
     */
    public static final int KEY_I = 0x17;
    /**
     * o key.
     */
    public static final int KEY_O = 0x18;
    /**
     * p key.
     */
    public static final int KEY_P = 0x19;
    /**
     * [ key.
     */
    public static final int KEY_LBRACKET = 0x1A;
    /**
     * ] key.
     */
    public static final int KEY_RBRACKET = 0x1B;
    /**
     * enter (main keyboard) key.
     */
    public static final int KEY_RETURN = 0x1C;
    /**
     * left control key.
     */
    public static final int KEY_LCONTROL = 0x1D;
    /**
     * a key.
     */
    public static final int KEY_A = 0x1E;
    /**
     * s key.
     */
    public static final int KEY_S = 0x1F;
    /**
     * d key.
     */
    public static final int KEY_D = 0x20;
    /**
     * f key.
     */
    public static final int KEY_F = 0x21;
    /**
     * g key.
     */
    public static final int KEY_G = 0x22;
    /**
     * h key.
     */
    public static final int KEY_H = 0x23;
    /**
     * j key.
     */
    public static final int KEY_J = 0x24;
    /**
     * k key.
     */
    public static final int KEY_K = 0x25;
    /**
     * l key.
     */
    public static final int KEY_L = 0x26;
    /**
     * ; key.
     */
    public static final int KEY_SEMICOLON = 0x27;
    /**
     * ' key.
     */
    public static final int KEY_APOSTROPHE = 0x28;
    /**
     * ` key.
     */
    public static final int KEY_GRAVE = 0x29;
    /**
     * left shift key.
     */
    public static final int KEY_LSHIFT = 0x2A;
    /**
     * \ key.
     */
    public static final int KEY_BACKSLASH = 0x2B;
    /**
     * z key.
     */
    public static final int KEY_Z = 0x2C;
    /**
     * x key.
     */
    public static final int KEY_X = 0x2D;
    /**
     * c key.
     */
    public static final int KEY_C = 0x2E;
    /**
     * v key.
     */
    public static final int KEY_V = 0x2F;
    /**
     * b key.
     */
    public static final int KEY_B = 0x30;
    /**
     * n key.
     */
    public static final int KEY_N = 0x31;
    /**
     * m key.
     */
    public static final int KEY_M = 0x32;
    /**
     * , key.
     */
    public static final int KEY_COMMA = 0x33;
    /**
     * . key (main keyboard).
     */
    public static final int KEY_PERIOD = 0x34;
    /**
     * / key (main keyboard).
     */
    public static final int KEY_SLASH = 0x35;
    /**
     * right shift key.
     */
    public static final int KEY_RSHIFT = 0x36;
    /**
     * * key (on keypad).
     */
    public static final int KEY_MULTIPLY = 0x37;
    /**
     * left alt key.
     */
    public static final int KEY_LMENU = 0x38;
    /**
     * space key.
     */
    public static final int KEY_SPACE = 0x39;
    /**
     * caps lock key.
     */
    public static final int KEY_CAPITAL = 0x3A;
    /**
     * F1 key.
     */
    public static final int KEY_F1 = 0x3B;
    /**
     * F2 key.
     */
    public static final int KEY_F2 = 0x3C;
    /**
     * F3 key.
     */
    public static final int KEY_F3 = 0x3D;
    /**
     * F4 key.
     */
    public static final int KEY_F4 = 0x3E;
    /**
     * F5 key.
     */
    public static final int KEY_F5 = 0x3F;
    /**
     * F6 key.
     */
    public static final int KEY_F6 = 0x40;
    /**
     * F7 key.
     */
    public static final int KEY_F7 = 0x41;
    /**
     * F8 key.
     */
    public static final int KEY_F8 = 0x42;
    /**
     * F9 key.
     */
    public static final int KEY_F9 = 0x43;
    /**
     * F10 key.
     */
    public static final int KEY_F10 = 0x44;
    /**
     * NumLK key.
     */
    public static final int KEY_NUMLOCK = 0x45;
    /**
     * Scroll lock key.
     */
    public static final int KEY_SCROLL = 0x46;
    /**
     * 7 key (num pad).
     */
    public static final int KEY_NUMPAD7 = 0x47;
    /**
     * 8 key (num pad).
     */
    public static final int KEY_NUMPAD8 = 0x48;
    /**
     * 9 key (num pad).
     */
    public static final int KEY_NUMPAD9 = 0x49;
    /**
     * - key (num pad).
     */
    public static final int KEY_SUBTRACT = 0x4A;
    /**
     * 4 key (num pad).
     */
    public static final int KEY_NUMPAD4 = 0x4B;
    /**
     * 5 key (num pad).
     */
    public static final int KEY_NUMPAD5 = 0x4C;
    /**
     * 6 key (num pad).
     */
    public static final int KEY_NUMPAD6 = 0x4D;
    /**
     * + key (num pad).
     */
    public static final int KEY_ADD = 0x4E;
    /**
     * 1 key (num pad).
     */
    public static final int KEY_NUMPAD1 = 0x4F;
    /**
     * 2 key (num pad).
     */
    public static final int KEY_NUMPAD2 = 0x50;
    /**
     * 3 key (num pad).
     */
    public static final int KEY_NUMPAD3 = 0x51;
    /**
     * 0 key (num pad).
     */
    public static final int KEY_NUMPAD0 = 0x52;
    /**
     * . key (num pad).
     */
    public static final int KEY_DECIMAL = 0x53;
    /**
     * F11 key.
     */
    public static final int KEY_F11 = 0x57;
    /**
     * F12 key.
     */
    public static final int KEY_F12 = 0x58;
    /**
     * F13 key.
     */
    public static final int KEY_F13 = 0x64;
    /**
     * F14 key.
     */
    public static final int KEY_F14 = 0x65;
    /**
     * F15 key.
     */
    public static final int KEY_F15 = 0x66;
    /**
     * kana key (Japanese).
     */
    public static final int KEY_KANA = 0x70;
    /**
     * convert key (Japanese).
     */
    public static final int KEY_CONVERT = 0x79;
    /**
     * noconvert key (Japanese).
     */
    public static final int KEY_NOCONVERT = 0x7B;
    /**
     * yen key (Japanese).
     */
    public static final int KEY_YEN = 0x7D;
    /**
     * = on num pad (NEC PC98).
     */
    public static final int KEY_NUMPADEQUALS = 0x8D;
    /**
     * circum flex key (Japanese).
     */
    public static final int KEY_CIRCUMFLEX = 0x90;
    /**
     * &#064; key (NEC PC98).
     */
    public static final int KEY_AT = 0x91;
    /**
     * : key (NEC PC98)
     */
    public static final int KEY_COLON = 0x92;
    /**
     * _ key (NEC PC98).
     */
    public static final int KEY_UNDERLINE = 0x93;
    /**
     * kanji key (Japanese).
     */
    public static final int KEY_KANJI = 0x94;
    /**
     * stop key (NEC PC98).
     */
    public static final int KEY_STOP = 0x95;
    /**
     * ax key (Japanese).
     */
    public static final int KEY_AX = 0x96;
    /**
     * (J3100).
     */
    public static final int KEY_UNLABELED = 0x97;
    /**
     * Enter key (num pad).
     */
    public static final int KEY_NUMPADENTER = 0x9C;
    /**
     * right control key.
     */
    public static final int KEY_RCONTROL = 0x9D;
    /**
     * , key on num pad (NEC PC98).
     */
    public static final int KEY_NUMPADCOMMA = 0xB3;
    /**
     * / key (num pad).
     */
    public static final int KEY_DIVIDE = 0xB5;
    /**
     * SysRq key.
     */
    public static final int KEY_SYSRQ = 0xB7;
    /**
     * right alt key.
     */
    public static final int KEY_RMENU = 0xB8;
    /**
     * pause key.
     */
    public static final int KEY_PAUSE = 0xC5;
    /**
     * home key.
     */
    public static final int KEY_HOME = 0xC7;
    /**
     * up arrow key.
     */
    public static final int KEY_UP = 0xC8;
    /**
     * PgUp key.
     */
    public static final int KEY_PRIOR = 0xC9;
    /**
     * PgUp key.
     */
    public static final int KEY_PGUP = KEY_PRIOR;

    /**
     * left arrow key.
     */
    public static final int KEY_LEFT = 0xCB;
    /**
     * right arrow key.
     */
    public static final int KEY_RIGHT = 0xCD;
    /**
     * end key.
     */
    public static final int KEY_END = 0xCF;
    /**
     * down arrow key.
     */
    public static final int KEY_DOWN = 0xD0;
    /**
     * PgDn key.
     */
    public static final int KEY_NEXT = 0xD1;
    /**
     * PgDn key.
     */
    public static final int KEY_PGDN = KEY_NEXT;

    /**
     * insert key.
     */
    public static final int KEY_INSERT = 0xD2;
    /**
     * delete key.
     */
    public static final int KEY_DELETE = 0xD3;
    public static final int KEY_LMETA            = 0xDB; /* Left Windows/Option key */
    /**
     * The left windows key, mapped to KEY_LMETA
     *
     * @Deprecated Use KEY_LMETA instead
     */
    public static final int KEY_LWIN            = KEY_LMETA; /* Left Windows key */
    public static final int KEY_RMETA            = 0xDC; /* Right Windows/Option key */
    /**
     * The right windows key, mapped to KEY_RMETA
     *
     * @Deprecated Use KEY_RMETA instead
     */
    public static final int KEY_RWIN            = KEY_RMETA; /* Right Windows key */
    public static final int KEY_APPS = 0xDD;
    /**
     * power key.
     */
    public static final int KEY_POWER = 0xDE;
    /**
     * sleep key.
     */
    public static final int KEY_SLEEP = 0xDF;
    
	public static boolean isNumPad(int code) {
		return (
				(code == KEY_NUMPAD0) ||
				(code == KEY_NUMPAD1) ||
				(code == KEY_NUMPAD2) ||
				(code == KEY_NUMPAD3) ||
				(code == KEY_NUMPAD4) ||
				(code == KEY_NUMPAD5) ||
				(code == KEY_NUMPAD6) ||
				(code == KEY_NUMPAD7) ||
				(code == KEY_NUMPAD8) ||
				(code == KEY_NUMPAD9) ||
				(code == KEY_NUMPADCOMMA) ||
				(code == KEY_NUMPADENTER) ||
				(code == KEY_NUMPADEQUALS) ||
				(code == KEY_ADD) ||
				(code == KEY_SUBTRACT) ||
				(code == KEY_MULTIPLY) ||
				(code == KEY_DIVIDE)
				);
	}


}

