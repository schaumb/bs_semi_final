package windroids.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import windroids.entities.data.Data;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
   
    public User() {
    }
    
    public User(String userName, String password, String profileImage, String fullName, Date birthDate, String city,
                Boolean isDoctor, String doctorType, Boolean isCoach, String coachType, HashMap<Data.Type, ArrayList<Data>> datas) {
        this.userName = userName;
        this.password = password;
        this.profileImage = profileImage;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.city = city;
        this.isDoctor = isDoctor;
        this.doctorType = doctorType;
        this.isCoach = isCoach;
        this.coachType = coachType;
        this.datas = datas;
    }

    public User(String userName, String email, String password, String fullName) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
    }


    private String userName;
	private String password;
	private String profileImage;
	private String fullName;
	private Date birthDate;
	private String city;
	private Boolean isDoctor;
	private String doctorType;
	private Boolean isCoach;
	private String coachType;
    private HashMap<Data.Type, ArrayList<Data>> datas;

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
		return isDoctor;
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
		return isCoach;
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
}
