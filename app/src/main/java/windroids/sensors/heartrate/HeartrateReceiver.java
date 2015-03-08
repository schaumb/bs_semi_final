package windroids.sensors.heartrate;

/**
 * Heart rate data receiver.
 * @author Balazs_Csernai
 */
public interface HeartrateReceiver {

    /**
     * Called on heart rate data receive.
     * @param connectionState Sensor connection state
     * @param heartrate Heart rate (BPM)
     */
    void onHeartrateReceived(int connectionState, int heartrate, int energy, int rri);
}
