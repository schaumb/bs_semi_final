package windroids.sensors.bloodpressure;

/**
 * Created by ebelsch on 3/8/2015.
 */
public interface BloodpressureReceiver {

    void onBloodpressureReceived(int connectionState, float sys, float dia, float map);
}
