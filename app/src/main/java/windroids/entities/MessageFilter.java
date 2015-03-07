package windroids.entities;

import java.util.ArrayList;

public class MessageFilter {
    public static ArrayList<Message> filterMessagesFrom(ArrayList<Message> messagesToMe, String userName) {
        ArrayList<Message> result = new ArrayList<>();
        for(Message m : messagesToMe)
        {
            if(m.getFrom().equals(userName))
            {
                result.add(m);
            }
        }
        return result;
    }
}
