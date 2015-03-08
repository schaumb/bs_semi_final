package windroids.entities.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class HeartRate extends Data implements Serializable {

    private static final long serialVersionUID = 1L;

    public class Rate implements  Serializable{
        private static final long serialVersionUID = 1L;

        public Rate(Integer heartRate, Integer energy, Integer rri) {
            this.heartRate = heartRate;
            this.energy = energy;
            this.rri = rri;
        }

        public Integer heartRate;
        public Integer energy;
        public Integer rri;
    }

    public HeartRate(HashMap<Date, Rate> measures) {
        super(Type.Pulse);
        this.measures = measures == null ? new HashMap<Date, Rate>() : measures;
    }

    private HashMap<Date, Rate> measures;

    public HashMap<Date, Rate> getMeasures() {
        return measures;
    }

    public void setMeasures(HashMap<Date, Rate> measures) {
        this.measures = measures;
    }

    public void addMeasure(Integer i, Integer j, Integer k){
        measures.put(new Date(), new Rate(i,j,k));
    }

}
