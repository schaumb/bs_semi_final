package windroids.search;

import java.io.IOException;
import java.util.ArrayList;

import windroids.entities.User;
import windroids.storage.UserStorage;

public class SearchUser {
    public static ArrayList<User> searchByName(String s) throws IOException, ClassNotFoundException {
        ArrayList<User> allUser = UserStorage.readUsers();
        ArrayList<User> result = new ArrayList<>();
        for(User u: allUser)
        {
            if(u.getUserName().contains(s) || u.getFullName().contains(s))
            {
                result.add(u);
            }
        }
        return result;
    }

    public static ArrayList<User> searchByCityContains(String s) throws IOException, ClassNotFoundException {
        ArrayList<User> allUser = UserStorage.readUsers();
        ArrayList<User> result = new ArrayList<>();
        for(User u: allUser)
        {
            if(u.getCity().contains(s))
            {
                result.add(u);
            }
        }
        return result;
    }

    public static ArrayList<User> searchByCitySameAsUser(User s) throws IOException, ClassNotFoundException {
        ArrayList<User> allUser = UserStorage.readUsers();
        ArrayList<User> result = new ArrayList<>();
        for(User u: allUser)
        {
            if(u.getCity().equals(s.getCity()))
            {
                result.add(u);
            }
        }
        return result;
    }

}
