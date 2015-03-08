package windroids.sensors.cycling;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT32;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
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
import android.content.Intent;

/**
 * Bluetooth communication state for cycling service updates.
 * @author Balazs_Csernai
 */
public class CyclingNotificationState implements BluetoothCommunicationState {

	private BluetoothCommunicationContext communicationContext;

	@Override
	public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
		this.communicationContext = communicationContext;
	}

	@Override
	public void startCommunication() {
		BluetoothGattService cyclingService = communicationContext.getGattClient().getService(CyclingSpeedAndCadenceService.getUUID());
		BluetoothGattCharacteristic cyclingMeasurementCharacteristic = cyclingService.getCharacteristic(CyclingSpeedAndCadenceMeasurementCharacteristic.getUUID());
		communicationContext.getGattClient().setCharacteristicNotification(cyclingMeasurementCharacteristic, true);
		BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = cyclingMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
		if ((cyclingMeasurementCharacteristic.getProperties() & PROPERTY_INDICATE) != 0) {
			clientCharacteristicConfigurationDescriptor.setValue(ENABLE_INDICATION_VALUE);
		} else {
			clientCharacteristicConfigurationDescriptor.setValue(ENABLE_NOTIFICATION_VALUE);
		}
		communicationContext.getGattClient().writeDescriptor(clientCharacteristicConfigurationDescriptor);
	}

	@Override
	public void stopCommunication(boolean forced) {
		communicationContext.setNextCommunicationState(new CyclingDisconnectionState());
		communicationContext.onCommunicationStateFinished();
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

	/**
	 * Parsing of the received updates is based on: <a href="https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.csc_measurement.xml">Cycling Speed and Cadence Characteristic</a>
	 */
	@Override
	public void onCharacteristicChanged(BluetoothDevice device, UUID characteristic, byte[] data) {
	    if (communicationContext.isDevice(device) && CyclingSpeedAndCadenceMeasurementCharacteristic.getUUID().equals(characteristic)) {
    		BluetoothGattService cyclingService = communicationContext.getGattClient().getService(CyclingSpeedAndCadenceService.getUUID());
    		BluetoothGattCharacteristic cyclingMeasurementCharacteristic = cyclingService.getCharacteristic(CyclingSpeedAndCadenceMeasurementCharacteristic.getUUID());
    		int cummulativeWheelRevolutions = 0;
    		int cummulativeCrankRevolutions = 0;
    		int lastWheelEventTime = 0;
    		int lastCrankEventTime = 0;
    		int offset = 1;
    		if ((data[0] & 1) != 0) {
    			cummulativeWheelRevolutions = cyclingMeasurementCharacteristic.getIntValue(FORMAT_UINT32, offset);
    			offset += 4;
    			lastWheelEventTime = cyclingMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
    			offset += 2;
    		}
    		if ((data[0] & 2) != 0) {
    			cummulativeCrankRevolutions = cyclingMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
    			offset += 2;
    			lastCrankEventTime = cyclingMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
    			offset += 2;
    		}
    		Intent cyclingIntent = createCyclingIntent();
    		saveConnectionState(cyclingIntent, STATE_CONNECTED);
    		saveWheelRevolution(cyclingIntent, cummulativeWheelRevolutions);
    		saveWheelEventTime(cyclingIntent, lastWheelEventTime);
    		saveCrankRevolution(cyclingIntent, cummulativeCrankRevolutions);
    		saveCrankEventTime(cyclingIntent, lastCrankEventTime);
    		communicationContext.getAndroidContext().sendBroadcast(cyclingIntent);
	    }
	}
}
