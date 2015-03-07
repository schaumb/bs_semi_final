package windroids.entities.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class HeartRate extends Data implements Serializable {

    private static final long serialVersionUID = 1L;

    public HeartRate(HashMap<Date, Integer> measures) {
        super(Type.Pulse);
        this.measures = measures;
    }

    private HashMap<Date, Integer> measures;

    public HashMap<Date, Integer> getMeasures() {
        return measures;
    }

    public void setMeasures(HashMap<Date, Integer> measures) {
        this.measures = measures;
    }

    public void addMeasure(Integer i){

        if(measures == null)
            measures = new HashMap<>();

        measures.put(new Date(), i);
    }

}
