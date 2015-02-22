package windroids.semifinal.logic.pattern;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

public class Pattern implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private List<KeyEvent> events;

    private void decreaseTimeFrom(int from, long how_much)
    {
        for(ListIterator<KeyEvent> it2 = events.listIterator(from); it2.hasNext();)
        {
            KeyEvent next = it2.next();
            next.setTime( next.getTime() - how_much );
        }
    }

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
            else if(e.getCode().equals(KeyCode.CONTROL))
            {
                // nothing to do
            }
            else if(e.getCode().isLetterKey() && shift)
            {
                result += Character.toUpperCase((char)e.getCode().code);
            }
            else if(e.getCode().isLetterKey())
            {
                result += Character.toLowerCase((char)e.getCode().code);
            }
			else
			{
				result += (char)e.getCode().code;
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
            if (e.getCode().isModifierKey())
            {
                if(e.getCode().equals(KeyCode.CONTROL))
                {
                    long diff = -e.getTime();
                    iterator.remove();

                    iterator.next();
                    iterator.remove();
                    e  = iterator.next();
                    diff += e.getTime();

                    iterator.previous();
                    decreaseTimeFrom(iterator.nextIndex(), diff);
                }
                else if(e.getCode().equals(KeyCode.SHIFT))
                {
                    long diff = -e.getTime();
                    e = iterator.next();
                    e = iterator.next();
                    if(!e.getCode().isLetterKey())
                    {
                        diff += e.getTime();
                        iterator.previous();
                        iterator.remove();
                        iterator.previous();
                        iterator.remove();
                        decreaseTimeFrom(iterator.nextIndex(), diff);
                    }
                }
            }
            else if (e.getCode().equals(KeyCode.BACK_SPACE))
			{
                e = iterator.next(); // up event

                long diff = e.getTime();

                if(iterator.hasNext())
                {
                    e = iterator.next(); // next (if exist)
                    diff = e.getTime();
                    e = iterator.previous();
                }

                int t = 4; // remove backspace and the previous (up-down) character
                do
                {
                    iterator.remove();
                    if(!iterator.hasPrevious()) break;
                    KeyEvent prev = iterator.previous();

                    if(prev.getCode().isModifierKey())
                        ++t;

                    if(--t == 0)
                    {
                        diff -= prev.getTime();
                        break;
                    }
                }
                while(true);
                decreaseTimeFrom(iterator.nextIndex(), diff);
            }
		}
        // calculate to zero
        decreaseTimeFrom(0, events.get(0).getTime());

		return countOfBackSpace;
	}
}
