package windroids.semifinal.logic.pattern;

import java.io.Serializable;

import windroids.semifinal.config.KeyCode;

public class KeyEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Type type;
	private long time;
	private KeyCode code;
	private double posX;
	private double posY;

	public KeyEvent(Type type, long time, KeyCode code, double posX, double posY) {
		this.type = type;
		this.time = time;
		this.code = code;
		this.posX = posX;
		this.posY = posY;
	}

	public enum Type {
		UP,
		DOWN
	}

	public Type getType() {
		return type;
	}

	public long getTime() {
		return time;
	}

	public KeyCode getCode() {
		return code;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
}
