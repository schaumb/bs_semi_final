package windroids.sensors.heartrate;

import static android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;
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
 * Bluetooth communication state for heart rate service disconnection.
 * @author Balazs_Csernai
 */
public class HeartrateDisconnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_DISCONNECTING);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
        BluetoothGattService heartrateService = communicationContext.getGattClient().getService(HeartRateService.getUUID());
        BluetoothGattCharacteristic heartrateMeasurementCharacteristic = heartrateService.getCharacteristic(HeartRateMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(heartrateMeasurementCharacteristic, false);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = heartrateMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
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
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_DISCONNECTED);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
        communicationContext.getGattClient().close();
        communicationContext.getCommunicationReceiver().unregister();
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
        if (communicationContext.isDevice(device) && HeartRateMeasurementCharacteristic.getUUID().equals(characteristic)) {
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
