package windroids.sensors.runningspeed;

/**
 * Created by ebelsch on 3/8/2015.
 */
public interface RunningSpeedReceiver {

    void onRunningSpeedReceived(int connectionState, int speed, int cadence, int stride, int total);
}
