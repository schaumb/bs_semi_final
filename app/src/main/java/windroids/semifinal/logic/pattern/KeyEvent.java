package windroids.semifinal.logic.pattern;

import java.io.Serializable;
import android.util.Pair;

public class KeyEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private Type type;
	private long time;
	private KeyCode code;
	private Pair<Double, Double> pos;

	public KeyEvent(Type type, long time, KeyCode code, double posX, double posY) {
		this.type = type;
		this.time = time;
		this.code = code;
		this.pos = new Pair<>(posX, posY);
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

	public Pair<Double, Double> getPos() {
		return pos;
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
		stringBuilder.append(pos.first);
		stringBuilder.append("\n");
		stringBuilder.append("PosY: ");
		stringBuilder.append(pos.second);
		stringBuilder.append("\n");
		return stringBuilder.toString();
	}
}
