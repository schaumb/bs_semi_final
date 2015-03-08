package windroids.entities.data;

import java.io.Serializable;
import java.util.Date;

public class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {Common, Pulse, BloodPressure, RunningSpeed, Cycling};

    public Data(Type type) {
        this.type = type;
        this.date = new Date();
    }

    private Type type;
    private Date date;

    public Type getType() {
        return type;
    }

}
