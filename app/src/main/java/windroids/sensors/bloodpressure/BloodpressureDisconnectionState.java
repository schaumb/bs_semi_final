package windroids.sensors.bloodpressure;

import static android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;
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

public class BloodpressureDisconnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_DISCONNECTING);
        saveBloodpressure(bloodpressureIntent, 0);
        saveBloodpressure2(bloodpressureIntent, 0);
        saveBloodpressure3(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
        BluetoothGattService bloodpressureService = communicationContext.getGattClient().getService(BloodPressureService.getUUID());
        BluetoothGattCharacteristic bloodpressureMeasurementCharacteristic = bloodpressureService.getCharacteristic(BloodPressureMeasurementCharacteristic.getUUID());
        communicationContext.getGattClient().setCharacteristicNotification(bloodpressureMeasurementCharacteristic, false);
        BluetoothGattDescriptor clientCharacteristicConfigurationDescriptor = bloodpressureMeasurementCharacteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.getUUID());
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
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_DISCONNECTED);
        saveBloodpressure(bloodpressureIntent, 0);
        saveBloodpressure2(bloodpressureIntent, 0);
        saveBloodpressure3(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
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
        if (communicationContext.isDevice(device) && BloodPressureMeasurementCharacteristic.getUUID().equals(characteristic)) {
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
