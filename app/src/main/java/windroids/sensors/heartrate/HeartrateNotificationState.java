package windroids.sensors.heartrate;

import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static windroids.sensors.constants.BluetoothUUID.ClientCharacteristicConfigurationDescriptor;
import static windroids.sensors.constants.BluetoothUUID.HeartRateMeasurementCharacteristic;
import static windroids.sensors.constants.BluetoothUUID.HeartRateService;
import static windroids.sensors.util.IntentAndBundleUtil.createHeartRateIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveHeartRate;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;

/**
 * Bluetooth communication state for heart rate service updates.
 * @author Balazs_Csernai
 */
public class HeartrateNotificationState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        BluetoothGattService heartrateService = communicationContext.getGattClient().getService(HeartRateService.getUUID());
        BluetoothGattCharacteristic heartrateMeasurementCharacteristic = heartrateService.getCharacteristic(HeartRateMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(heartrateMeasurementCharacteristic, true);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = heartrateMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
        if ((heartrateMeasurementCharacteristic.getProperties() & PROPERTY_INDICATE) != 0) {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_INDICATION_VALUE);    
        } else {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_NOTIFICATION_VALUE);
        }
        communicationContext.getGattClient().writeDescriptor(clientCharacteristicConfigurationDescriptor);
    }

    @Override
    public void stopCommunication(boolean forced) {
        communicationContext.setNextCommunicationState(new HeartrateDisconnectionState());
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
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_DISCONNECTED);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
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

    @Override
    public void onCharacteristicChanged(BluetoothDevice device, UUID characteristic, byte[] data) {
        if (communicationContext.isDevice(device) && HeartRateMeasurementCharacteristic.getUUID().equals(characteristic)) {
            BluetoothGattService heartrateService = communicationContext.getGattClient().getService(HeartRateService.getUUID());
            BluetoothGattCharacteristic heartrateMeasurementCharacteristic = heartrateService.getCharacteristic(HeartRateMeasurementCharacteristic.getUUID());
            int heartrate = 0;
            int offset = 1;
            if ((data[0] & 1) != 0) {
                heartrate = heartrateMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
                offset += 2;
            } else {
                heartrate = heartrateMeasurementCharacteristic.getIntValue(FORMAT_UINT8, offset);
                offset += 1;
            }
            Intent heartrateIntent = createHeartRateIntent();
            saveConnectionState(heartrateIntent, STATE_CONNECTED);
            saveHeartRate(heartrateIntent, heartrate);
            communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
        }
    }
}
