package windroids.sensors.search;

import windroids.sensors.constants.General;
import windroids.sensors.util.IntentAndBundleUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Broadcast receiver for Bluetooth Device Found intents.
 * @author Balazs_Csernai
 */
public class BluetoothSearchReceiver extends BroadcastReceiver {

    private Context context;
    private boolean registered;
    private BluetoothSearchCallback bluetoothSearchCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (IntentAndBundleUtil.isBluetoothDeviceFoundIntent(intent) && bluetoothSearchCallback != null) {
            bluetoothSearchCallback.onBluetoothDeviceFound(IntentAndBundleUtil.loadBluetoothDevice(intent), IntentAndBundleUtil.loadAdvertisementData(intent));
        }
    }

    /**
     * Registers this broadcast receiver on an Android context.
     * @param context Android context
     */
    public void register(Context context) {
        if (!registered) {
            this.context = context;
            context.registerReceiver(this, createBluetoothSearchIntentFilter());
            registered = true;
        }
    }

    /**
     * Un-registers this broadcast receiver on the Android context used for registration.
     */
    public void unregister() {
        if (registered) {
            context.unregisterReceiver(this);
            registered = false;
        }
    }

    /**
     * Sets the bluetooth device found callback.
     * @param bluetoothSearchCallback Bluetooth device found callback
     */
    public void setBluetoothSearchCallback(BluetoothSearchCallback bluetoothSearchCallback) {
        this.bluetoothSearchCallback = bluetoothSearchCallback;
    }

    private IntentFilter createBluetoothSearchIntentFilter() {
        IntentFilter bluetoothSearchIntentFilter = new IntentFilter();
        bluetoothSearchIntentFilter.addAction(General.BLUETOOTH_ACTION_DEVICE_FOUND);
        return bluetoothSearchIntentFilter;
    }
}
