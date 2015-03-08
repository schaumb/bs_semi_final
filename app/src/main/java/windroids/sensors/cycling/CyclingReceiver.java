package windroids.sensors.cycling;

/**
 * Cycling data receiver.
 * @author Balazs_Csernai
 */
public interface CyclingReceiver {

    /**
     * Called on cycling data receive.
     * @param connectionState Sensor connection state
     * @param wheelRevolution Wheel revolution counter
     * @param wheelEventTime Last wheel revolution time
     * @param crankRevolution Crank revolution counter
     * @param crankEventTime Last crank revolution time
     */
	void onCyclingReceived(int connectionState, int wheelRevolution, int wheelEventTime, int crankRevolution, int crankEventTime);
}
