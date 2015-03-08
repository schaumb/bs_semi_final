package windroids.sensors.bloodpressure;


import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_SFLOAT;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16;
import static android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8;
import static android.bluetooth.BluetoothGattCharacteristic.PROPERTY_INDICATE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
import static android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static windroids.sensors.constants.BluetoothUUID.ClientCharacteristicConfigurationDescriptor;
import static windroids.sensors.constants.BluetoothUUID.BloodPressureMeasurementCharacteristic;
import static windroids.sensors.constants.BluetoothUUID.BloodPressureService;
import static windroids.sensors.util.IntentAndBundleUtil.createBloodpressureIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure2;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure3;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;

public class BloodpressureNotificationState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        BluetoothGattService bloodpressureService = communicationContext.getGattClient().getService(BloodPressureService.getUUID());
        BluetoothGattCharacteristic bloodpressureMeasurementCharacteristic = bloodpressureService.getCharacteristic(BloodPressureMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(bloodpressureMeasurementCharacteristic, true);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = bloodpressureMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
        if ((bloodpressureMeasurementCharacteristic.getProperties() & PROPERTY_INDICATE) != 0) {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_INDICATION_VALUE);
        } else {
            clientCharacteristicConfigurationDescriptor.setValue(ENABLE_NOTIFICATION_VALUE);
        }
        communicationContext.getGattClient().writeDescriptor(clientCharacteristicConfigurationDescriptor);
    }

    @Override
    public void stopCommunication(boolean forced) {
        communicationContext.setNextCommunicationState(new BloodpressureDisconnectionState());
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
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_DISCONNECTED);
        saveBloodpressure(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
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
        if (communicationContext.isDevice(device) && BloodPressureMeasurementCharacteristic.getUUID().equals(characteristic)) {
            BluetoothGattService bloodpressureService = communicationContext.getGattClient().getService(BloodPressureService.getUUID());
            BluetoothGattCharacteristic bloodpressureMeasurementCharacteristic = bloodpressureService.getCharacteristic(BloodPressureMeasurementCharacteristic.getUUID());
            float sys = 0;
            float dia = 0;
            float map = 0;
            try {
                int offset = 1;
                if ((data[0] & 1) != 0) {
                    offset += 2 * 3;
                }

                sys = bloodpressureMeasurementCharacteristic.getFloatValue(FORMAT_SFLOAT, offset);
                offset += 2;
                dia = bloodpressureMeasurementCharacteristic.getFloatValue(FORMAT_SFLOAT, offset);
                offset += 2;
                map = bloodpressureMeasurementCharacteristic.getFloatValue(FORMAT_SFLOAT, offset);
                offset += 2;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            Intent bloodpressureIntent = createBloodpressureIntent();
            saveConnectionState(bloodpressureIntent, STATE_CONNECTED);
            saveBloodpressure(bloodpressureIntent, sys);
            saveBloodpressure2(bloodpressureIntent, dia);
            saveBloodpressure3(bloodpressureIntent, map);

            communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
        }
    }
}

