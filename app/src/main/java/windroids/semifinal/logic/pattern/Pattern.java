package windroids.semifinal.logic.pattern;

import java.io.Serializable;
import java.util.List;

public class Pattern implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<KeyEvent> events;
	
	public Pattern(List<KeyEvent> events) {
		this.events = events;
	}

	public List<KeyEvent> getEvents() {
		return events;
	}
}
