package windroids.semifinal.util;

import java.security.InvalidParameterException;

public class KeyCodeParser {
	
	public static KeyCode parse(String rawValue) {
		rawValue = rawValue.toLowerCase();
		switch (rawValue) {
			case "1":
				return KeyCode.KEY_1;
			case "2":
				return KeyCode.KEY_2;
			case "3":
				return KeyCode.KEY_3;
			case "4":
				return KeyCode.KEY_4;
			case "5":
				return KeyCode.KEY_5;
			case "6":
				return KeyCode.KEY_6;
			case "7":
				return KeyCode.KEY_7;
			case "8":
				return KeyCode.KEY_8;
			case "9":
				return KeyCode.KEY_9;
			case "0":
				return KeyCode.KEY_0;
			case "q":
				return KeyCode.KEY_Q;
			case "w":
				return KeyCode.KEY_W;
			case "e":
				return KeyCode.KEY_E;
			case "r":
				return KeyCode.KEY_R;
			case "t":
				return KeyCode.KEY_T;
			case "z":
				return KeyCode.KEY_Z;
			case "u":
				return KeyCode.KEY_U;
			case "i":
				return KeyCode.KEY_I;
			case "o":
				return KeyCode.KEY_O;
			case "p":
				return KeyCode.KEY_P;
			case "a":
				return KeyCode.KEY_A;
			case "s":
				return KeyCode.KEY_S;
			case "d":
				return KeyCode.KEY_D;
			case "f":
				return KeyCode.KEY_F;
			case "g":
				return KeyCode.KEY_G;
			case "h":
				return KeyCode.KEY_H;
			case "j":
				return KeyCode.KEY_J;
			case "k":
				return KeyCode.KEY_K;
			case "l":
				return KeyCode.KEY_L;
			case "shift":
				return KeyCode.KEY_SHIFT;
			case "y":
				return KeyCode.KEY_Y;
			case "x":
				return KeyCode.KEY_X;
			case "c":
				return KeyCode.KEY_C;
			case "v":
				return KeyCode.KEY_V;
			case "b":
				return KeyCode.KEY_B;
			case "n":
				return KeyCode.KEY_N;
			case "m":
				return KeyCode.KEY_M;
			case "backspace":
				return KeyCode.KEY_BACKSPACE;
			case "change":
				return KeyCode.KEY_CHANGE;
			case "comma":
				return KeyCode.KEY_COMMA;
			case "space":
				return KeyCode.KEY_SPACE;
			case "dot":
				return KeyCode.KEY_DOT;
			case "enter":
				return KeyCode.KEY_ENTER;
			default:
				throw new InvalidParameterException("Given key does not exist!");
		}
	}
}
