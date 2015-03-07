package windroids.entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import windroids.entities.data.Data;
import windroids.storage.MessageStorage;
import windroids.storage.UserStorage;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
   
    public User() {
    }
    
    public User(String userName, String password, String profileImage, String fullName, String email, Date birthDate, String city,
                Boolean isDoctor, String doctorType, Boolean isCoach, String coachType, HashMap<Data.Type, ArrayList<Data>> datas, ArrayList<String> connections) {
        this.userName = userName;
        this.password = password;
        this.profileImage = profileImage;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.city = city;
        this.isDoctor = isDoctor;
        this.doctorType = doctorType;
        this.isCoach = isCoach;
        this.coachType = coachType;
        this.datas = datas;
        this.connections = connections;
    }

    private String userName;
	private String password;
	private String profileImage;
	private String fullName;
    private String email;
	private Date birthDate;
	private String city;
	private Boolean isDoctor;
	private String doctorType;
	private Boolean isCoach;
	private String coachType;
    private HashMap<Data.Type, ArrayList<Data>> datas;
    private ArrayList<String> connections;

    public ArrayList<User> getContacts() throws IOException, ClassNotFoundException {
		if (connections == null) {
			this.connections = new ArrayList<>();
		}
        return UserStorage.getUsersFromName(connections);
    }

    public ArrayList<Message> getReceivedDatasFrom(String userName) throws IOException, ClassNotFoundException {
        return MessageFilter.filterMessagesToMeFrom(MessageStorage.getMessagesToMe(this.userName), userName);
    }

    public ArrayList<Message> getSentDatasTo(String userName) throws IOException, ClassNotFoundException {
        return MessageFilter.filterMessagesFromMeTo(MessageStorage.getMessagesFromMe(this.userName), userName);
    }

    public ArrayList<Message> getUserMessages(String userName) throws IOException, ClassNotFoundException {
        ArrayList<Message> result = getReceivedDatasFrom(userName);
        result.addAll(getSentDatasTo(userName));

        Comparator<Message> comparator = new Comparator<Message>() {
            public int compare(Message c1, Message c2) {
                return c1.getDate().compareTo(c1.getDate());
            }
        };

        Collections.sort(result, comparator);
        return result;
    }

    public void addContact(User u) throws IOException, ClassNotFoundException {
        connections.add(u.getUserName());
        UserStorage.saveThisUserChanges(this);
        u.connections.add(this.getUserName());
        UserStorage.saveThisUserChanges(u);
    }

    public HashMap<Data.Type, ArrayList<Data>> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<Data.Type, ArrayList<Data>> datas) {
        this.datas = datas;
    }

    public void addData(Data d){
        if(datas == null)
            datas = new HashMap<>();

        ArrayList<Data> vec = datas.get(d.getType());
        if(vec == null){
            vec = new ArrayList<>();
        }
        vec.add(d);

        datas.put(d.getType(), vec);
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getIsDoctor() {
		return isDoctor == null ? false : isDoctor;
	}

	public void setIsDoctor(Boolean isDoctor) {
		this.isDoctor = isDoctor;
	}

	public String getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(String doctorType) {
		this.doctorType = doctorType;
	}

	public Boolean getIsCoach() {
		return isCoach == null ? false : isCoach;
	}

	public void setIsCoach(Boolean isCoach) {
		this.isCoach = isCoach;
	}

	public String getCoachType() {
		return coachType;
	}

	public void setCoachType(String coachType) {
		this.coachType = coachType;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<String> connections) {
        this.connections = connections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }
}
