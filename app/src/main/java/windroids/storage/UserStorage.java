package windroids.storage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import windroids.entities.User;

public class UserStorage {

    public class UniqueException extends RuntimeException{
        public UniqueException(){}
        public UniqueException(String s) {
            super(s);
        }
    };
    public class WrongPasswordException extends UniqueException{
        public WrongPasswordException(){}
        public WrongPasswordException(String s) {
            super(s);
        }
    };
    public class NotExistUserException extends UniqueException{
        public NotExistUserException(){}
        public NotExistUserException(String s) {
            super(s);
        }
    };
    public class DuplicatedUserNameException extends UniqueException{
        public DuplicatedUserNameException(){}
        public DuplicatedUserNameException(String s) {
            super(s);
        }
    };
    public class NotEnoughCharacterInPasswordException extends WrongPasswordException{
        public NotEnoughCharacterInPasswordException(){}
        public NotEnoughCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public class NotLowerCaseCharacterInPasswordException extends WrongPasswordException{
        public NotLowerCaseCharacterInPasswordException(){}
        public NotLowerCaseCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public class NotUpperCaseCharacterInPasswordException extends WrongPasswordException{
        public NotUpperCaseCharacterInPasswordException(){}
        public NotUpperCaseCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public class NotNumberCharacterInPasswordException extends WrongPasswordException{
        public NotNumberCharacterInPasswordException(){}
        public NotNumberCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public class NotSpecialCharacterInPasswordException extends WrongPasswordException{
        public NotSpecialCharacterInPasswordException(){}
        public NotSpecialCharacterInPasswordException(String s) {
            super(s);
        }
    };


    private static String fileName = "dont_piszka";

    private static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList<User> result = (ArrayList<User>)ois.readObject();

        ois.close();
        fis.close();

        return result;

    }

    private static void saveUsers(ArrayList<User> users) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(users);

        oos.close();
        fos.close();
    }

    public User checkLogin(String userName, String password) throws IOException, ClassNotFoundException, UniqueException {
        ArrayList<User> list = readUsers();
        for(User u: list)
        {
            if(u.getUserName().equals(userName))
            {
                if(!u.getPassword().equals(password))
                {
                    throw new WrongPasswordException();
                }
                else
                {
                    return u;
                }
            }
        }
        throw new NotExistUserException();
    }

    public void checkRegister(User u) throws IOException, ClassNotFoundException {
        ArrayList<User> list = readUsers();

        for(User v: list) {
            if (v.getUserName().equals(u.getUserName())){
                throw new DuplicatedUserNameException();
            }
        }

        if(u.getPassword().length() < 8)
            throw new NotEnoughCharacterInPasswordException("minimum 8");

        if(!u.getPassword().contains("[a-z]") )
            throw new NotLowerCaseCharacterInPasswordException();

        if(!u.getPassword().contains("[A-Z]") )
            throw new NotUpperCaseCharacterInPasswordException();

        if(!u.getPassword().contains("[0-9]") )
            throw new NotNumberCharacterInPasswordException();

        list.add(u);
        saveUsers(list);
    }
}
