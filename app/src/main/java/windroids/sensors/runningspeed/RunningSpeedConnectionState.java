package windroids.sensors.runningspeed;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
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
import android.content.Intent;


public class RunningSpeedConnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_CONNECTING);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
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
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_CONNECTED);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
    }

    @Override
    public void onServicesDiscovered(BluetoothDevice device) {
        Intent runningspeedIntent = createRunningSpeedIntent();
        saveConnectionState(runningspeedIntent, STATE_CONNECTED);
        saveRunningSpeed(runningspeedIntent, 0);
        saveRunningSpeed2(runningspeedIntent, 0);
        saveRunningSpeed3(runningspeedIntent, 0);
        saveRunningSpeed4(runningspeedIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(runningspeedIntent);
        communicationContext.setNextCommunicationState(new RunningSpeedNotificationState());
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

