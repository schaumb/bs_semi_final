package windroids.entities.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Cycling extends Data implements Serializable {

    public class Rate implements  Serializable{
        private static final long serialVersionUID = 1L;

        public Rate(Integer wheelRevolution, Integer wheelEventTime, Integer crankRevolution, Integer crankEventTime) {
            this.wheelRevolution = wheelRevolution;
            this.wheelEventTime = wheelEventTime;
            this.crankRevolution = crankRevolution;
            this.crankEventTime = crankEventTime;
        }

        public Integer wheelRevolution;
        public Integer wheelEventTime;
        public Integer crankRevolution;
        public Integer crankEventTime;
    }

    public Cycling(ArrayList<Rate> rates) {
        super(Type.Cycling);
        this.rates = rates == null ? new ArrayList<Rate>() : rates;
    }

    private ArrayList<Rate> rates = new ArrayList<>();

    public void set(Integer speed, Integer cadence, Integer stride, Integer total) {
        rates.add(new Rate(speed, cadence, stride, total));
    }
}
