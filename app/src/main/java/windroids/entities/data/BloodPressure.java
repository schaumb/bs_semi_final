package windroids.entities.data;

import java.io.Serializable;

public class BloodPressure extends Data implements Serializable {

    public BloodPressure(Integer systolic, Integer diastolic, Integer pulse) {
        super(Type.BloodPressure);
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    private Integer systolic;
    private Integer diastolic;
    private Integer pulse;

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }
}
