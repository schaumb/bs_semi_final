package windroids.sensors.cycling;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

/**
 * Bluetooth communication context for cycling service.
 * @author Balazs_Csernai
 */
public class CyclingCommunicationContext extends BluetoothCommunicationContext {

    /**
     * Constructor.
     * @param context Android context
     * @param bluetoothDevice Bluetooth device
     */
	public CyclingCommunicationContext(Context context, BluetoothDevice bluetoothDevice) {
		super(context, bluetoothDevice);
		BluetoothCommunicationState initialCommunicationState = new CyclingConnectionState();
		initialCommunicationState.setCommunicationContext(this);
		getCommunicationReceiver().setBluetoothCommunicationCallback(initialCommunicationState);
		setInitialCommunicationState(initialCommunicationState);
	}
}
