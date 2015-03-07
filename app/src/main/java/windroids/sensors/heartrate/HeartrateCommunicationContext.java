package windroids.sensors.heartrate;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

/**
 * Bluetooth communication context for heart rate service.
 * @author Balazs_Csernai
 */
public class HeartrateCommunicationContext extends BluetoothCommunicationContext {

    /**
     * Constructor.
     * @param context Android context
     * @param bluetoothDevice Bluetooth device
     */
    public HeartrateCommunicationContext(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
        BluetoothCommunicationState initialCommunicationState = new HeartrateConnectionState();
        initialCommunicationState.setCommunicationContext(this);
        getCommunicationReceiver().setBluetoothCommunicationCallback(initialCommunicationState);
        setInitialCommunicationState(initialCommunicationState);
	}
}
