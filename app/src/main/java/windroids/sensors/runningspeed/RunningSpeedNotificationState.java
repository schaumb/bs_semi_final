package windroids.sensors.runningspeed;


import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_SFLOAT;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static windroids.sensors.constants.BluetoothUUID.ClientCharacteristicConfigurationDescriptor;
import static windroids.sensors.constants.BluetoothUUID.RunningSpeedAndCadenceService;
import static windroids.sensors.constants.BluetoothUUID.RunningSpeedAndCadenceMeasurementCharacteristic;
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

public class RunningSpeedNotificationState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        BluetoothGattService runningspeedService = communicationContext.getGattClient().getService(RunningSpeedAndCadenceService.getUUID());
        BluetoothGattCharacteristic runningspeedMeasurementCharacteristic = runningspeedService.getCharacteristic(RunningSpeedAndCadenceMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(runningspeedMeasurementCharacteristic, true);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = runningspeedMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
        if ((runningspeedMeasurementCharacteristic.getProperties() & PROPERTY_INDICATE) != 0) {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_INDICATION_VALUE);
        } else {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_NOTIFICATION_VALUE);
        }
        communicationContext.getGattClient().writeDescriptor(clientCharacteristicConfigurationDescriptor);
    }

    @Override
    public void stopCommunication(boolean forced) {
        communicationContext.setNextCommunicationState(new RunningSpeedDisconnectionState());
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
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_DISCONNECTED);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);

        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
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
        if (communicationContext.isDevice(device) && RunningSpeedAndCadenceMeasurementCharacteristic.getUUID().equals(characteristic)) {
            BluetoothGattService runningspeedService = communicationContext.getGattClient().getService(RunningSpeedAndCadenceService.getUUID());
            BluetoothGattCharacteristic runningspeedMeasurementCharacteristic = runningspeedService.getCharacteristic(RunningSpeedAndCadenceMeasurementCharacteristic.getUUID());
            int speed = 0;
            int cadence = 0;
            int stride = 0;
            int distance = 0;
            try {
                int offset = 1;
                speed = runningspeedMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
                offset += 2;
                cadence = runningspeedMeasurementCharacteristic.getIntValue(FORMAT_UINT8, offset);
                offset += 1;
                if((data[0] & 1) != 0)
                    stride = runningspeedMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
                offset += 2;
                if((data[0] & 2) != 0)
                    stride = runningspeedMeasurementCharacteristic.getIntValue(FORMAT_UINT16, offset);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            Intent runningspeedIntent = createRunningSpeedIntent();
            saveConnectionState(runningspeedIntent, STATE_CONNECTED);
            saveRunningSpeed(runningspeedIntent, speed);
            saveRunningSpeed2(runningspeedIntent, cadence);
            saveRunningSpeed3(runningspeedIntent, stride);
            saveRunningSpeed4(runningspeedIntent, distance);

            communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
        }
    }
}

