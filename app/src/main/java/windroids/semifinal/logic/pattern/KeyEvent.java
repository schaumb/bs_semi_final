package windroids.semifinal.logic.pattern;

import java.io.Serializable;

import windroids.semifinal.util.KeyCode;

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

	public void setTime(long time) {
		this.time = time;
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

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("// KeyEvent\n");
		stringBuilder.append("Type: ");
		stringBuilder.append(type);
		stringBuilder.append("\n");
		stringBuilder.append("Time: ");
		stringBuilder.append(time);
		stringBuilder.append("\n");
		stringBuilder.append("Code: ");
		stringBuilder.append(code);
		stringBuilder.append("\n");
		stringBuilder.append("PosX: ");
		stringBuilder.append(posX);
		stringBuilder.append("\n");
		stringBuilder.append("PosY: ");
		stringBuilder.append(posY);
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}
