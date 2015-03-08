package windroids.entities.data;

import java.io.Serializable;
import java.util.ArrayList;

public class RunningSpeed extends Data implements Serializable {

    public class Rate implements  Serializable{
        private static final long serialVersionUID = 1L;

        public Rate(Integer speed, Integer cadence, Integer stride, Integer total) {
            this.speed = speed;
            this.cadence = cadence;
            this.stride = stride;
            this.total = total;
        }

        public Integer speed;
        public Integer cadence;
        public Integer stride;
        public Integer total;
    }

    public RunningSpeed(ArrayList<Rate> rates) {
        super(Type.RunningSpeed);
        this.rates = rates == null ? new ArrayList<Rate>() : rates;
    }

    private ArrayList<Rate> rates = new ArrayList<>();

    public void set(Integer speed, Integer cadence, Integer stride, Integer total) {
        rates.add(new Rate(speed, cadence, stride, total));
    }
}
