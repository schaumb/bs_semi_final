package windroids.sensors.communication;

import java.util.UUID;

import android.bluetooth.BluetoothDevice;

/**
 * Callback for Bluetooth Low Energy broadcast intent receiver.
 * @author Balazs_Csernai
 */
public interface BluetoothCommunicationCallback {

    /**
     * Called when connecting to the Bluetooth device is started.
     * @param device Bluetooth device
     */
    void onConnecting(BluetoothDevice device);

    /**
     * Called when connecting to the Bluetooth device is successful.
     * @param device Bluetooth device
     */
    void onConnected(BluetoothDevice device);

    /**
     * Called when disconnection from the Bluetooth device is started.
     * @param device Bluetooth device
     */
    void onDisconnecting(BluetoothDevice device);

    /**
     * Called when disconnection from the Bluetooth device is successful.
     * @param device Bluetooth device
     */
    void onDisconnected(BluetoothDevice device);

    /**
     * Called when service discovery on the Bluetooth device is successful.
     * @param device Bluetooth device
     */
    void onServicesDiscovered(BluetoothDevice device);

    /**
     * Called when the characteristic writing on the Bluetooth device is successful.
     * @param device Bluetooth device
     * @param characteristic Characteristic UUID
     */
    void onCharacteristicWrite(BluetoothDevice device, UUID characteristic);

    /**
     * Called when the characteristic reading on the Bluetooth device is successful.
     * @param device Bluetooth device
     * @param characteristic Characteristic UUID
     */
    void onCharacteristicRead(BluetoothDevice device, UUID characteristic);

    /**
     * Called when the descriptor writing on the Bluetooth device is successful.
     * @param device Bluetooth device
     * @param characteristic Characteristic UUID
     * @param descriptor Descriptor UUID
     */
    void onDescriptorWrite(BluetoothDevice device, UUID characteristic, UUID descriptor);

    /**
     * Called when the descriptor reading on the Bluetooth device is successful.
     * @param device Bluetooth device
     * @param characteristic Characteristic UUID
     * @param descriptor Descriptor UUID
     */
    void onDescriptorRead(BluetoothDevice device, UUID characteristic, UUID descriptor);

    /**
     * Called when the observer characteristic value is changed.
     * @param device Bluetooth device
     * @param characteristic Characteristic UUID
     * @param data Change
     */
    void onCharacteristicChanged(BluetoothDevice device, UUID characteristic, byte[] data);
}
