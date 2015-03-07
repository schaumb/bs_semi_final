package windroids.sensors.heartrate;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static windroids.sensors.util.IntentAndBundleUtil.createHeartRateIntent;
import static windroids.sensors.util.IntentAndBundleUtil.saveConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.saveHeartRate;

import java.util.UUID;

import windroids.sensors.communication.BluetoothCommunicationContext;
import windroids.sensors.communication.BluetoothCommunicationState;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;

/**
 * Bluetooth communication state for heart rate service connection establishment.
 * @author Balazs_Csernai
 */
public class HeartrateConnectionState implements BluetoothCommunicationState {

    private BluetoothCommunicationContext communicationContext;

    @Override
    public void setCommunicationContext(BluetoothCommunicationContext communicationContext) {
        this.communicationContext = communicationContext;
    }

    @Override
    public void startCommunication() {
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_CONNECTING);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
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
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_CONNECTED);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
    }

    @Override
    public void onServicesDiscovered(BluetoothDevice device) {
        Intent heartrateIntent = createHeartRateIntent();
        saveConnectionState(heartrateIntent, STATE_CONNECTED);
        saveHeartRate(heartrateIntent, 0);
        communicationContext.getAndroidContext().sendBroadcast(heartrateIntent);
        communicationContext.setNextCommunicationState(new HeartrateNotificationState());
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
