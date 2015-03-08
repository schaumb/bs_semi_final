package windroids.sensors.runningspeed;

import static android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;
import static windroids.sensors.constants.BluetoothUUID.ClientCharacteristicConfigurationDescriptor;
import static windroids.sensors.constants.BluetoothUUID.RunningSpeedAndCadenceMeasurementCharacteristic;
import static windroids.sensors.constants.BluetoothUUID.RunningSpeedAndCadenceService;
import static windroids.sensors.util.IntentAndBundleUtil.createRunningSpeedIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveRunningSpeed;
import static windroids.sensors.util.IntentAndBundleUtil.saveRunningSpeed2;
import static windroids.sensors.util.IntentAndBundleUtil.saveRunningSpeed3;
import static windroids.sensors.util.IntentAndBundleUtil.saveRunningSpeed4;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;

public class RunningSpeedDisconnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_DISCONNECTING);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
        BluetoothGattService runningspeedService = communicationContext.getGattClient().getService(RunningSpeedAndCadenceService.getUUID());
        BluetoothGattCharacteristic runningspeedMeasurementCharacteristic = runningspeedService.getCharacteristic(RunningSpeedAndCadenceMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(runningspeedMeasurementCharacteristic, false);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = runningspeedMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
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
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_DISCONNECTED);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
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
        if (communicationContext.isDevice(device) && RunningSpeedAndCadenceMeasurementCharacteristic.getUUID().equals(characteristic)) {
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
