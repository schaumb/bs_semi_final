package windroids.sensors.search;

import android.bluetooth.BluetoothDevice;

/**
 * Callback for Bluetooth device found events.
 * @author Balazs_Csernai
 */
public interface BluetoothSearchCallback {

    /**
     * Called on Bluetooth device find.
     * @param bluetoothDevice Bluetooth device
     * @param scanRecord Advertisement data
     */
    void onBluetoothDeviceFound(BluetoothDevice bluetoothDevice, byte[] scanRecord);
}
