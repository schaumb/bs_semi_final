package windroids.entities.data;

import java.io.Serializable;

public class BloodPressure extends Data implements Serializable {

    public BloodPressure(Float systolic, Float diastolic, Float pulse) {
        super(Type.BloodPressure);
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    private Float systolic;
    private Float diastolic;
    private Float pulse;

    public Float getSystolic() {
        return systolic;
    }

    public void setSystolic(Float systolic) {
        this.systolic = systolic;
    }

    public Float getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Float diastolic) {
        this.diastolic = diastolic;
    }

    public Float getPulse() {
        return pulse;
    }

    public void setPulse(Float pulse) {
        this.pulse = pulse;
    }
}
