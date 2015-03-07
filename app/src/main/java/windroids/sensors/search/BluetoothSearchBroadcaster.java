package windroids.sensors.search;

import windroids.sensors.util.IntentAndBundleUtil;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

/**
 * Broadcasts Bluetooth Device Found intents.
 * @author Balazs_Csernai
 */
public class BluetoothSearchBroadcaster implements LeScanCallback {

    private final Context context;

    /**
     * Constructor.
     * @param context Android context
     */
    public BluetoothSearchBroadcaster(Context context) {
        this.context = context;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        context.sendBroadcast(IntentAndBundleUtil.saveAdvertisementData(IntentAndBundleUtil.saveBluetoothDevice(IntentAndBundleUtil.createBluetoothDeviceFoundIntent(), device), scanRecord));
    }

}
