package windroids.sensors.cycling;

import static android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
import static windroids.sensors.constants.BluetoothUUID.ClientCharacteristicConfigurationDescriptor;
import static windroids.sensors.constants.BluetoothUUID.CyclingSpeedAndCadenceMeasurementCharacteristic;
import static windroids.sensors.constants.BluetoothUUID.CyclingSpeedAndCadenceService;
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
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;

/**
 * Bluetooth communication state for cycling service disconnection.
 * @author Balazs_Csernai
 */
public class CyclingDisconnectionState implements BluetoothCommunicationState {

	private BluetoothCommunicationContext communicationContext;

	@Override
	public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
		this.communicationContext = communicationContext;
	}

	@Override
	public void startCommunication() {
	    Intent cyclingIntent = createCyclingIntent();
	    saveConnectionState(cyclingIntent, BluetoothProfile.STATE_DISCONNECTING);
	    saveWheelRevolution(cyclingIntent, 0);
	    saveWheelEventTime(cyclingIntent, 0);
	    saveCrankRevolution(cyclingIntent, 0);
	    saveCrankEventTime(cyclingIntent, 0);
	    communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
		BluetoothGattService cyclingService = communicationContext.getGattClient().getService(CyclingSpeedAndCadenceService.getUUID());
		BluetoothGattCharacteristic cyclingMeasurementCharacteristic = cyclingService.getCharacteristic(CyclingSpeedAndCadenceMeasurementCharacteristic.getUUID());
		communicationContext.getGattClient().setCharacteristicNotification(cyclingMeasurementCharacteristic, false);
		BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = cyclingMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
		clientCharacteristicConfigurationDescriptor.setValue(DISABLE_NOTIFICATION_VALUE);
		communicationContext.getGattClient().writeDescriptor(clientCharacteristicConfigurationDescriptor);
	}

	@Override
	public void stopCommunication(boolean forced) {
	}

	@Override
	public void onConnecting(BluetoothDevice device) {
	}

	@Override
	public void onConnected(BluetoothDevice device) {
	}

	@Override
	public void onDisconnecting(BluetoothDevice device) {
	}

	@Override
	public void onDisconnected(BluetoothDevice device) {
	    if (communicationContext.isDevice(device)) {
    	    Intent cyclingIntent = createCyclingIntent();
    	    saveConnectionState(cyclingIntent, BluetoothProfile.STATE_DISCONNECTED);
    	    saveWheelRevolution(cyclingIntent, 0);
    	    saveWheelEventTime(cyclingIntent, 0);
    	    saveCrankRevolution(cyclingIntent, 0);
    	    saveCrankEventTime(cyclingIntent, 0);
    	    communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
    		communicationContext.getGattClient().close();
    		communicationContext.getCommunicationReceiver().unregister();
	    }
	}

	@Override
	public void onServicesDiscovered(BluetoothDevice device) {
	}

	@Override
	public void onCharacteristicWrite(BluetoothDevice device, UUID characteristic) {
	}

	@Override
	public void onCharacteristicRead(BluetoothDevice device, UUID characteristic) {
	}

	@Override
	public void onDescriptorWrite(BluetoothDevice device, UUID characteristic, UUID descriptor) {
	    if (communicationContext.isDevice(device) && CyclingSpeedAndCadenceMeasurementCharacteristic.getUUID().equals(characteristic)) {
	        communicationContext.getGattClient().disconnect();
	    }
	}

	@Override
	public void onDescriptorRead(BluetoothDevice device, UUID characteristic, UUID descriptor) {
	}

	@Override
	public void onCharacteristicChanged(BluetoothDevice device, UUID characteristic, byte[] data) {
	}
}
