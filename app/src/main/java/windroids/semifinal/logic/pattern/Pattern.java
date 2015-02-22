package windroids.semifinal.logic.pattern;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

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

	public String getTypedCode()
	{
		String result = "";
		boolean shift = false;
		for(KeyEvent e : events)
		{
			if(e.getType() == KeyEvent.Type.UP)
				continue;

            if(e.getCode().equals(KeyCode.BACK_SPACE))
            {
                result = result.substring(0, result.length() - 1);
            }
            else if(e.getCode().equals(KeyCode.SHIFT))
            {
                shift = true;
                continue;
            }
            else if(e.getCode().isLetterKey() && shift)
            {
                result += Character.toUpperCase(e.getCode().code);
            }
            else
            {
                result += e.getCode().code;
            }

			shift = false;
		}
		return result;
	}

	public int eraseBackSpaceEvents()
	{
        // WARNING MORE MODIFIER ELIMINATE!!!!!!!!
		int countOfBackSpace = 0;
		for (ListIterator<KeyEvent> iterator = events.listIterator(); iterator.hasNext();) 
		{
			KeyEvent e = iterator.next();
			if (e.getCode() == KeyCode.BACK_SPACE) 
			{
				if(e.getType() == KeyEvent.Type.UP)
				{
					++countOfBackSpace;
					iterator.remove();

					// change the time
					long diff = e.getTime();
					if(iterator.hasPrevious()) 
					{
						KeyEvent prev = iterator.previous();
						diff -= prev.getTime();
					}
					for(ListIterator<KeyEvent> it2 = events.listIterator(iterator.nextIndex()); it2.hasNext();)
					{
						KeyEvent next = it2.next();
						next.setTime( next.getTime() - diff );
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
						if(prev.getCode() == KeyCode.SHIFT)
							++t;
					}
					while(--t != 0);
				}
			}
		}
		return countOfBackSpace;
	}
}
