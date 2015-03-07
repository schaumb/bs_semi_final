package windroids.sensors.communication;

import static windroids.sensors.constants.General.BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC;
import static windroids.sensors.constants.General.BLUETOOTH_ACTION_CONNECTION;
import static windroids.sensors.constants.General.BLUETOOTH_ACTION_SERVICE_DISCOVERED;
import static windroids.sensors.constants.General.BLUETOOTH_ACTION_WRITE_DESCRIPTOR;
import static windroids.sensors.util.IntentAndBundleUtil.isCharacteristicChangedIntent;
import static windroids.sensors.util.IntentAndBundleUtil.isConnectionIntent;
import static windroids.sensors.util.IntentAndBundleUtil.isServicesDiscoveredIntent;
import static windroids.sensors.util.IntentAndBundleUtil.isWriteDescriptorIntent;
import static windroids.sensors.util.IntentAndBundleUtil.loadBluetoothDevice;
import static windroids.sensors.util.IntentAndBundleUtil.loadCharacteristic;
import static windroids.sensors.util.IntentAndBundleUtil.loadCharacteristicData;
import static windroids.sensors.util.IntentAndBundleUtil.loadConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.loadDescriptor;

import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Receiver for Bluetooth Low Energy broadcast intents.
 * @author Balazs_Csernai
 */
public class BluetoothCommunicationReceiver extends BroadcastReceiver {

    private Context context;
    private boolean registered;
    private BluetoothCommunicationCallback communicationCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnectionIntent(intent)) {
            handleConnectionIntent(intent);
        } else if (isServicesDiscoveredIntent(intent)) {
            handleServicesDiscovered(intent);
        } else if (isWriteDescriptorIntent(intent)) {
            handleDescriptorWrite(intent);
        } else if (isCharacteristicChangedIntent(intent)) {
            handleCharacteristicChange(intent);
        }
    }

    /**
     * Sets the Bluetooth Low Energy broadcast intent receiver callback.
     * @param communicationCallback Callback
     */
    public void setBluetoothCommunicationCallback(BluetoothCommunicationCallback communicationCallback) {
        this.communicationCallback = communicationCallback;
    }

    /**
     * Registers the broadcast intent receiver.
     * @param context Android context
     */
    public void register(Context context) {
        if (!registered) {
            this.context = context;
            context.registerReceiver(this, createBluetoothCommunicationFilter());
            registered = true;
        }
    }

    /**
     * Unregisters the broadcast intent receiver.
     */
    public void unregister() {
        if (registered) {
            context.unregisterReceiver(this);
            registered = false;
        }
    }

    private IntentFilter createBluetoothCommunicationFilter() {
        IntentFilter bluetoothCommunicationFilter =  new IntentFilter();
        bluetoothCommunicationFilter.addAction(BLUETOOTH_ACTION_CONNECTION);
        bluetoothCommunicationFilter.addAction(BLUETOOTH_ACTION_SERVICE_DISCOVERED);
        bluetoothCommunicationFilter.addAction(BLUETOOTH_ACTION_WRITE_DESCRIPTOR);
        bluetoothCommunicationFilter.addAction(BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC);
        return bluetoothCommunicationFilter;
    }

    private void handleConnectionIntent(Intent intent) {
        if (communicationCallback != null) {
            BluetoothDevice device = loadBluetoothDevice(intent);
            switch (loadConnectionState(intent)) {
            case BluetoothProfile.STATE_CONNECTING:
                communicationCallback.onConnecting(device);
                break;
            case BluetoothProfile.STATE_CONNECTED:
                communicationCallback.onConnected(device);
                break;
            case BluetoothProfile.STATE_DISCONNECTING:
                communicationCallback.onDisconnecting(device);
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
            default:
                communicationCallback.onDisconnected(device);
                break;
            }
        }
    }

    private void handleServicesDiscovered(Intent intent) {
        if (communicationCallback != null) {
            BluetoothDevice device = loadBluetoothDevice(intent);
            communicationCallback.onServicesDiscovered(device);
        }
    }

    private void handleDescriptorWrite(Intent intent) {
        if (communicationCallback != null) {
            BluetoothDevice device = loadBluetoothDevice(intent);
            UUID characteristic = loadCharacteristic(intent);
            UUID descriptor = loadDescriptor(intent);
            communicationCallback.onDescriptorWrite(device, characteristic, descriptor);
        }
    }

    private void handleCharacteristicChange(Intent intent) {
        if (communicationCallback != null) {
            BluetoothDevice device = loadBluetoothDevice(intent);
            UUID characteristic = loadCharacteristic(intent);
            byte[] data = loadCharacteristicData(intent);
            communicationCallback.onCharacteristicChanged(device, characteristic, data);
        }
    }
}
