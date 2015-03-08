package windroids.sensors.bloodpressure;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static windroids.sensors.util.IntentAndBundleUtil.createBloodpressureIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure2;
import static windroids.sensors.util.IntentAndBundleUtil.saveBloodpressure3;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;


public class BloodpressureConnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_CONNECTING);
        saveBloodpressure(bloodpressureIntent, 0);
        saveBloodpressure2(bloodpressureIntent, 0);
        saveBloodpressure3(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
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
        communicationContext.getGattClient().discoverServices();
    }

    @Override
    public void onDisconnecting(BluetoothDevice device) {
    }

    @Override
    public void onDisconnected(BluetoothDevice device) {
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_CONNECTED);
        saveBloodpressure(bloodpressureIntent, 0);
        saveBloodpressure2(bloodpressureIntent, 0);
        saveBloodpressure3(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
    }

    @Override
    public void onServicesDiscovered(BluetoothDevice device) {
        Intent bloodpressureIntent = createBloodpressureIntent();
        saveConnectionState(bloodpressureIntent, STATE_CONNECTED);
        saveBloodpressure(bloodpressureIntent, 0);
        saveBloodpressure2(bloodpressureIntent, 0);
        saveBloodpressure3(bloodpressureIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(bloodpressureIntent);
        communicationContext.setNextCommunicationState(new BloodpressureNotificationState());
        communicationContext.onCommunicationStateFinished();
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

