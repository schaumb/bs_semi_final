package windroids.entities.data;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {Common, Pulse};

    public Data(Type type) {
        this.type = type;
    }

    private Type type;

    public Type getType() {
        return type;
    }

}
