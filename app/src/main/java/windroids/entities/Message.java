package windroids.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import windroids.entities.data.Data;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    enum Type{ Request, Send, Response }

    public Message(Type type, String from, String to, String message, HashMap<Data.Type, ArrayList<Data>> datas) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.message = message;
        this.datas = datas;
        this.date = new Date();
    }

    private Type type;
    private String from;
    private String to;
    private String message;
    private HashMap<Data.Type, ArrayList<Data>> datas;
    private Date date;

}
