package windroids.search;

import java.io.IOException;
import java.util.ArrayList;

import windroids.entities.Message;
import windroids.storage.MessageStorage;

public class SearchMessage {
    public static ArrayList<Message> searchByFromName(String s) throws IOException, ClassNotFoundException {
        ArrayList<Message> allMessage = MessageStorage.readMessages();
        ArrayList<Message> result = new ArrayList<>();
        for(Message u: allMessage)
        {
            if(u.getFrom().contains(s))
            {
                result.add(u);
            }
        }
        return result;
    }

    public static ArrayList<Message> searchByMessage(String s) throws IOException, ClassNotFoundException {
        ArrayList<Message> allMessage = MessageStorage.readMessages();
        ArrayList<Message> result = new ArrayList<>();
        for(Message u: allMessage)
        {
            if(u.getMessage().contains(s))
            {
                result.add(u);
            }
        }
        return result;
    }

}
