package windroids.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import windroids.entities.Message;

public class MessageStorage {

    private static String fileName = "message_storage";

    private static ArrayList<Message> readMessages() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<Message> result = (ArrayList<Message>)ois.readObject();

        ois.close();
        fis.close();

        return result;

    }

    private static void saveMessages(ArrayList<Message> users) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(users);

        oos.close();
        fos.close();
    }

    public static ArrayList<Message> getMessagesToMe(String userName) throws IOException, ClassNotFoundException {
        ArrayList<Message> list = readMessages();
        ArrayList<Message> result = new ArrayList<>();
        for(Message m: list)
        {
            if(userName.equals(m.getTo()))
            {
                result.add(m);
            }
        }
        return result;
    }

    public static ArrayList<Message> getMessagesFromMe(String userName) throws IOException, ClassNotFoundException {
        ArrayList<Message> list = readMessages();
        ArrayList<Message> result = new ArrayList<>();
        for(Message m: list)
        {
            if(userName.equals(m.getFrom()))
            {
                result.add(m);
            }
        }
        return result;
    }

    public static Integer getNewMessageId() throws IOException, ClassNotFoundException {
        return readMessages().size();
    }

    public static void saveThisMessageChanges(Message message) throws IOException, ClassNotFoundException {
        ArrayList<Message> list = readMessages();
        for (Message m : list) {
            if (message.equals(m)) {
                list.remove(m);
                list.add(message);
                break;
            }
        }
        saveMessages(list);
    }
}
