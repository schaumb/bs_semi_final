package windroids.sensors.cycling;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static windroids.sensors.util.IntentAndBundleUtil.createCyclingIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveCrankEventTime;
import static windroids.sensors.util.IntentAndBundleUtil.saveCrankRevolution;
import static windroids.sensors.util.IntentAndBundleUtil.saveWheelEventTime;
import static windroids.sensors.util.IntentAndBundleUtil.saveWheelRevolution;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Bluetooth communication state for cycling service connection establishment.
 * @author Balazs_Csernai
 */
public class CyclingConnectionState implements BluetoothCommunicationState {

	private BluetoothCommunicationContext communicationContext;

	@Override
	public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
		this.communicationContext = communicationContext;
	}

	@Override
	public void startCommunication() {
        Intent cyclingIntent = createCyclingIntent();
        saveConnectionState(cyclingIntent, STATE_CONNECTING);
        saveWheelRevolution(cyclingIntent, 0);
        saveWheelEventTime(cyclingIntent, 0);
        saveCrankRevolution(cyclingIntent, 0);
        saveCrankEventTime(cyclingIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
		communicationContext.setGattClient(communicationContext.getDevice().connectGatt(communicationContext.getAndroidContext(), false, communicationContext.getGattClientCallback()));
	}

	@Override
	public void stopCommunication(boolean forced) {
	}

	@Override
	public void onConnecting(BluetoothDevice device) {
	}

	@Override
	public void onConnected(BluetoothDevice device) {
	    if (communicationContext.isDevice(device)) {
	        communicationContext.getGattClient().discoverServices();
	    }
	}

	@Override
	public void onDisconnecting(BluetoothDevice device) {
	}

	@Override
	public void onDisconnected(BluetoothDevice device) {
	    if (communicationContext.isDevice(device)) {
            Intent cyclingIntent = createCyclingIntent();
            saveConnectionState(cyclingIntent, STATE_DISCONNECTED);
            saveWheelRevolution(cyclingIntent, 0);
            saveWheelEventTime(cyclingIntent, 0);
            saveCrankRevolution(cyclingIntent, 0);
            saveCrankEventTime(cyclingIntent, 0);
            communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
	    }
	}

	@Override
	public void onServicesDiscovered(BluetoothDevice device) {
	    if (communicationContext.isDevice(device)) {
    	    Intent cyclingIntent = createCyclingIntent();
            saveConnectionState(cyclingIntent, STATE_CONNECTED);
            saveWheelRevolution(cyclingIntent, 0);
            saveWheelEventTime(cyclingIntent, 0);
            saveCrankRevolution(cyclingIntent, 0);
            saveCrankEventTime(cyclingIntent, 0);
            communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
    		communicationContext.setNextCommunicationState(new CyclingNotificationState());
    		communicationContext.onCommunicationStateFinished();
	    }
	}

	@Override
	public void onCharacteristicWrite(BluetoothDevice device, UUID characteristic) {
	}

	@Override
	public void onCharacteristicRead(BluetoothDevice device, UUID characteristic) {
	}

	@Override
	public void onDescriptorWrite(BluetoothDevice device, UUID characteristic, UUID descriptor) {
	}

	@Override
	public void onDescriptorRead(BluetoothDevice device, UUID characteristic, UUID descriptor) {
	}

	@Override
	public void onCharacteristicChanged(BluetoothDevice device, UUID characteristic, byte[] data) {
	}
}
