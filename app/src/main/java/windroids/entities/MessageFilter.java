package windroids.entities;

import java.util.ArrayList;

public class MessageFilter {
    public static ArrayList<Message> filterMessagesToMeFrom(ArrayList<Message> messagesToMe, String userName) {
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

    public static ArrayList<Message> filterMessagesFromMeTo(ArrayList<Message> messagesFromMe, String userName) {
        ArrayList<Message> result = new ArrayList<>();
        for(Message m : messagesFromMe)
        {
            if(m.getTo().equals(userName))
            {
                result.add(m);
            }
        }
        return result;
    }


}
