package windroids.sensors.runningspeed;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

/**
 * Bluetooth communication context for heart rate service.
 * @author Balazs_Csernai
 */
public class RunningSpeedCommunicationContext extends BluetoothCommunicationContext {

    /**
     * Constructor.
     * @param context Android context
     * @param bluetoothDevice Bluetooth device
     */
    public RunningSpeedCommunicationContext(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
        BluetoothCommunicationState initialCommunicationState = new RunningSpeedConnectionState();
        initialCommunicationState.setCommunicationContext(this);
        getCommunicationReceiver().setBluetoothCommunicationCallback(initialCommunicationState);
        setInitialCommunicationState(initialCommunicationState);
    }
}
