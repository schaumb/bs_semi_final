package windroids.entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import windroids.entities.data.Data;
import windroids.storage.MessageStorage;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type{ Request, Send, Response }

    public Message(Type type, String from, String to, String message, HashMap<Data.Type, ArrayList<Data>> datas) throws IOException, ClassNotFoundException {
        this.messageId = MessageStorage.getNewMessageId();
        this.type = type;
        this.from = from;
        this.to = to;
        this.message = message;
        this.datas = datas;
        this.date = new Date();
        this.read = false;

        MessageStorage.saveNewMessage(this);
    }

    private Integer messageId;
    private Type type;
    private String from;
    private String to;
    private String message;
    private HashMap<Data.Type, ArrayList<Data>> datas;
    private Date date;
    private Boolean read;

    public boolean hasData(){
        return datas.isEmpty();
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<Data.Type, ArrayList<Data>> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<Data.Type, ArrayList<Data>> datas) {
        this.datas = datas;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public void read() throws IOException, ClassNotFoundException {
        setRead(true);
        MessageStorage.saveThisMessageChanges(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!messageId.equals(message.messageId)) return false;
        if (!from.equals(message.from)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId.hashCode();
        result = 31 * result + (from != null ? from.hashCode() : 0);
        return result;
    }
}
