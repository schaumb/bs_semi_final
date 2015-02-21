package windroids.semifinal.logic.pattern;

import java.io.Serializable;
import java.util.List;

import windroids.semifinal.util.KeyCode;

public class Pattern implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private List<KeyEvent> events;
	
	public Pattern(List<KeyEvent> events) 
	{
		this.events = events;
	}

	public List<KeyEvent> getEvents() 
	{
		return events;
	}

	String getTypedCode()
	{
		String result = "";
		boolean shift = false;
		for(KeyEvent e : events)
		{
			if(e.getType() == KeyEvent.Type.UP) 
				continue;

			switch(e.getCode())
			{
			case KeyCode.BACK_SPACE:
				result = result.substring(0, result.lenth() - 1);
				break;
			case KeyCode.SHIFT:
				shift = true;
				continue;
			default:
				if(e.isLetterKey() && shift)
				{
					result.append(Character.toUpperCase(e.getCode()));
				}
				else
				{
					result.append(e.getCode());
				}
			}
			shift = false;
		}
		return result;
	}

	int eraseBackSpaceEvents()
	{
		int countOfBackSpace = 0;
		for (ListIterator<KeyEvent> iterator = events.listIterator(); iterator.hasNext();) 
		{
			KeyEvent event = iterator.next();
			if (e.getCode() == KeyCode.BACK_SPACE) 
			{
				if(e.getType() == KeyEvent.Type.UP)
				{
					++countOfBackSpace;
					iterator.remove();

					// change the time
					long diff = event.getTime();
					if(iterator.hasPrevious()) 
					{
						KeyEvent prev = iterator.previous();
						diff -= prev.getTime();
					}
					for(ListIterator<KeyEvent> it2 = events.listIterator(iterator.nextIndex()); it2.hasNext();)
					{
						KeyEvent nexts = it2.next();
						nexts.setTime( nexts.getTime() - diff );						
					}
				}
				else
				{
					int t = 3; // remove backspace and the previous (up-down) character
					do
					{
						iterator.remove();
						if(!iterator.hasPrevious()) break;
						KeyEvent prev = iterator.previous();
						if(prev.getCode() == KeyEvent.SHIFT)
							++t;
					}
					while(--t != 0);
				}
			}
		}
		return countOfBackSpace;
	}
}
