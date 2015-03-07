package windroids.storage;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import windroids.entities.User;

public class UserStorage {

    public static class UniqueException extends RuntimeException{
        public UniqueException(){}
        public UniqueException(String s) {
            super(s);
        }
    };
    public static class WrongPasswordException extends UniqueException{
        public WrongPasswordException(){}
        public WrongPasswordException(String s) {
            super(s);
        }
    };
    public static class NotExistUserException extends UniqueException{
        public NotExistUserException(){}
        public NotExistUserException(String s) {
            super(s);
        }
    };
    public static class DuplicatedUserNameException extends UniqueException{
        public DuplicatedUserNameException(){}
        public DuplicatedUserNameException(String s) {
            super(s);
        }
    };
    public static class NotEnoughCharacterInPasswordException extends WrongPasswordException{
        public NotEnoughCharacterInPasswordException(){}
        public NotEnoughCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public static class NotLowerCaseCharacterInPasswordException extends WrongPasswordException{
        public NotLowerCaseCharacterInPasswordException(){}
        public NotLowerCaseCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public static class NotUpperCaseCharacterInPasswordException extends WrongPasswordException{
        public NotUpperCaseCharacterInPasswordException(){}
        public NotUpperCaseCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public static class NotNumberCharacterInPasswordException extends WrongPasswordException{
        public NotNumberCharacterInPasswordException(){}
        public NotNumberCharacterInPasswordException(String s) {
            super(s);
        }
    };
    public static class NotSpecialCharacterInPasswordException extends WrongPasswordException{
        public NotSpecialCharacterInPasswordException(){}
        public NotSpecialCharacterInPasswordException(String s) {
            super(s);
        }
    };

	private static Context context;

	public static void setContext(Context contextParam) {
		context = contextParam;
	}


    private static String fileName = "user_storage";

    public static ArrayList<User> readUsers() throws IOException, ClassNotFoundException {
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + fileName);
		file.createNewFile();
        FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + fileName);

		ArrayList<User> result;
		try {
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (ArrayList<User>) ois.readObject();
			ois.close();
		} catch (EOFException e) {
			Log.i(UserStorage.class.getSimpleName(), "Üres volt a file.");
			result = new ArrayList<>();
		}

        fis.close();

        return result;

    }

    private static boolean saveUsers(ArrayList<User> users) throws IOException {
        FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(users);

        oos.close();
        fos.close();

        return true;
    }

    public static User checkLogin(String userName, String password) throws IOException, ClassNotFoundException, UniqueException {
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

    public static boolean checkRegister(User u) throws IOException, ClassNotFoundException {
        ArrayList<User> list = readUsers();

        for(User v: list) {
            if (v.getUserName().equals(u.getUserName())){
                throw new DuplicatedUserNameException();
            }
        }

        if(u.getPassword().length() < 8)
            throw new NotEnoughCharacterInPasswordException("minimum 8");

//        if(!u.getPassword().contains("[a-z]") )
//            throw new NotLowerCaseCharacterInPasswordException();
//
//        if(!u.getPassword().contains("[A-Z]") )
//            throw new NotUpperCaseCharacterInPasswordException();
//
//        if(!u.getPassword().contains("[0-9]") )
//            throw new NotNumberCharacterInPasswordException();

        list.add(u);
        return saveUsers(list);
    }

    public static ArrayList<User> getUsersFromName(ArrayList<String> connections) throws IOException, ClassNotFoundException {
        ArrayList<User> list = readUsers();
        ArrayList<User> result = new ArrayList<>();
        for(User u: list)
        {
            if(connections.contains(u.getUserName()))
            {
                result.add(u);
            }
        }
        return result;
    }

    public static void saveThisUserChanges(User user) throws IOException, ClassNotFoundException {
        ArrayList<User> list = readUsers();
        for (User m : list) {
            if (user.equals(m)) {
                list.remove(m);
                list.add(user);
                break;
            }
        }
        saveUsers(list);
    }

}
