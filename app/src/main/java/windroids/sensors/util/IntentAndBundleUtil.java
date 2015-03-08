package windroids.sensors.util;

import static windroids.sensors.constants.General.*;

import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import windroids.sensors.search.BluetoothDeviceAdapter;

/**
 * Intent and bundle creation/parsing utility.
 * @author Balazs_Csernai
 */
public class IntentAndBundleUtil {

    public static final Intent createBluetoothDeviceFoundIntent() {
        return new Intent(BLUETOOTH_ACTION_DEVICE_FOUND);
    }

    public static final Intent saveBluetoothDevice(Intent intent, BluetoothDevice bluetoothDevice) {
        return intent.putExtra(BLUETOOTH_EXTRA_DEVICE, bluetoothDevice);
    }

    public static final Intent saveAdvertisementData(Intent intent, byte[] advertisementData) {
        return intent.putExtra(BLUETOOTH_EXTRA_SCANRECORD, advertisementData);
    }

    public static final boolean isBluetoothDeviceFoundIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_DEVICE_FOUND);
    }

    public static final BluetoothDevice loadBluetoothDevice(Intent intent) {
        BluetoothDevice bluetoothDevice = null;
        if (intent.hasExtra(BLUETOOTH_EXTRA_DEVICE)) {
            bluetoothDevice = intent.getParcelableExtra(BLUETOOTH_EXTRA_DEVICE);
        }
        return bluetoothDevice;
    }

    public static final byte[] loadAdvertisementData(Intent intent) {
        byte[] scanRecord = null;
        if (intent.hasExtra(BLUETOOTH_EXTRA_SCANRECORD)) {
            scanRecord = intent.getByteArrayExtra(BLUETOOTH_EXTRA_SCANRECORD);
        }
        return scanRecord;
    }

    public static final Bundle saveBluetoothDeviceAdapter(Bundle bundle, BluetoothDeviceAdapter device) {
        bundle.putParcelable(BLUETOOTH_EXTRA_DEVICE, device.getDevice());
        bundle.putByteArray(BLUETOOTH_EXTRA_SCANRECORD, device.getAdvertisementData());
        return bundle;
    }

    public static final BluetoothDeviceAdapter loadBluetoothDeviceAdapter(Bundle bundle) {
        BluetoothDeviceAdapter loaded = null;
        if (bundle.containsKey(BLUETOOTH_EXTRA_DEVICE) && bundle.containsKey(BLUETOOTH_EXTRA_SCANRECORD)) {
            loaded = new BluetoothDeviceAdapter((BluetoothDevice) bundle.getParcelable(BLUETOOTH_EXTRA_DEVICE), bundle.getByteArray(BLUETOOTH_EXTRA_SCANRECORD));
        }
        return loaded;
    }

    public static final Intent createConnectionIntent() {
        return new Intent(BLUETOOTH_ACTION_CONNECTION);
    }

    public static final boolean isConnectionIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_CONNECTION);
    }

    public static final Intent saveConnectionState(Intent intent, int connectionState) {
        return intent.putExtra(BLUETOOTH_EXTRA_CONNECTION_STATE, connectionState);
    }

    public static final int loadConnectionState(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_CONNECTION_STATE, 0 /* BluetoothProfile.STATE_DISCONNECTED */);
    }

    public static final Intent createServicesDiscoveredIntent() {
        return new Intent(BLUETOOTH_ACTION_SERVICE_DISCOVERED);
    }

    public static final boolean isServicesDiscoveredIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_SERVICE_DISCOVERED);
    }

    public static final Intent createWriteDescriptorIntent() {
        return new Intent(BLUETOOTH_ACTION_WRITE_DESCRIPTOR);
    }

    public static final boolean isWriteDescriptorIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_WRITE_DESCRIPTOR);
    }

    public static final Intent saveDescriptor(Intent intent, UUID descriptor) {
        return intent.putExtra(BLUETOOTH_EXTRA_DESCRIPTOR, descriptor.toString());
    }

    public static final UUID loadDescriptor(Intent intent) {
        UUID descriptor = null;
        if (intent.hasExtra(BLUETOOTH_EXTRA_DESCRIPTOR)) {
            descriptor = UUID.fromString(intent.getStringExtra(BLUETOOTH_EXTRA_DESCRIPTOR));
        }
        return descriptor;
    }

    public static final Intent createCharacteristicChangeIntent() {
        return new Intent(BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC);
    }

    public static final boolean isCharacteristicChangedIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC);
    }

    public static final Intent saveCharacteristic(Intent intent, UUID characteristic) {
        return intent.putExtra(BLUETOOTH_EXTRA_CHARACTERISTIC, characteristic.toString());
    }

    public static final UUID loadCharacteristic(Intent intent) {
        UUID characteristic = null;
        if (intent.hasExtra(BLUETOOTH_EXTRA_CHARACTERISTIC)) {
            characteristic = UUID.fromString(intent.getStringExtra(BLUETOOTH_EXTRA_CHARACTERISTIC));
        }
        return characteristic;
    }

    public static final Intent saveCharacteristicData(Intent intent, byte[] data) {
        return intent.putExtra(BLUETOOTH_EXTRA_CHARACTERISTIC_DATA, data);
    }

    public static final byte[] loadCharacteristicData(Intent intent) {
        byte[] data = null;
        if (intent.hasExtra(BLUETOOTH_EXTRA_CHARACTERISTIC_DATA)) {
            data = intent.getByteArrayExtra(BLUETOOTH_EXTRA_CHARACTERISTIC_DATA);
        }
        return data;
    }

    public static final Intent createHeartRateIntent() {
        return new Intent(BLUETOOTH_ACTION_HEARTRATE);
    }

    public static boolean isHeartRateIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_HEARTRATE);
    }

    public static final Intent saveHeartRate(Intent intent, int heartRate) {
        return intent.putExtra(BLUETOOTH_EXTRA_HEARTRATE, heartRate);
    }

    public static final Intent saveHeartRate2(Intent intent, int heartRate) {
        return intent.putExtra(BLUETOOTH_EXTRA_HEARTRATE2, heartRate);
    }

    public static final Intent saveHeartRate3(Intent intent, int heartRate) {
        return intent.putExtra(BLUETOOTH_EXTRA_HEARTRATE3, heartRate);
    }

    public static final int loadHeartRate(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_HEARTRATE, 0);
    }

    public static final int loadHeartRate2(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_HEARTRATE2, 0);
    }

    public static final int loadHeartRate3(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_HEARTRATE3, 0);
    }


    // bloodpressure
    public static final Intent createBloodpressureIntent() {
        return new Intent(BLUETOOTH_ACTION_BLOODPRESSURE);
    }

    public static boolean isBloodpressureIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_BLOODPRESSURE);
    }

    public static final Intent saveBloodpressure(Intent intent, float bp) {
        return intent.putExtra(BLUETOOTH_EXTRA_BLOODPRESSURE, bp);
    }

    public static final Intent saveBloodpressure2(Intent intent, float bp) {
        return intent.putExtra(BLUETOOTH_EXTRA_BLOODPRESSURE2, bp);
    }

    public static final Intent saveBloodpressure3(Intent intent, float bp) {
        return intent.putExtra(BLUETOOTH_EXTRA_BLOODPRESSURE3, bp);
    }

    public static final float loadBloodpressure(Intent intent) {
        return intent.getFloatExtra(BLUETOOTH_EXTRA_BLOODPRESSURE, 0);
    }

    public static final float loadBloodpressure2(Intent intent) {
        return intent.getFloatExtra(BLUETOOTH_EXTRA_BLOODPRESSURE2, 0);
    }

    public static final float loadBloodpressure3(Intent intent) {
        return intent.getFloatExtra(BLUETOOTH_EXTRA_BLOODPRESSURE3, 0);
    }
    // eddig


    // running
    public static final Intent createRunningSpeedIntent() {
        return new Intent(BLUETOOTH_ACTION_RUNNINGSPEED);
    }

    public static boolean isRunningSpeedIntent(Intent intent) {
        return intent.getAction().equals(BLUETOOTH_ACTION_RUNNINGSPEED);
    }

    public static final Intent saveRunningSpeed(Intent intent, int runningSpeed) {
        return intent.putExtra(BLUETOOTH_EXTRA_RUNNINGSPEED, runningSpeed);
    }

    public static final Intent saveRunningSpeed2(Intent intent, int runningSpeed) {
        return intent.putExtra(BLUETOOTH_EXTRA_RUNNINGSPEED2, runningSpeed);
    }

    public static final Intent saveRunningSpeed3(Intent intent, int runningSpeed) {
        return intent.putExtra(BLUETOOTH_EXTRA_RUNNINGSPEED3, runningSpeed);
    }

    public static final Intent saveRunningSpeed4(Intent intent, int runningSpeed) {
        return intent.putExtra(BLUETOOTH_EXTRA_RUNNINGSPEED4, runningSpeed);
    }

    public static final int loadRunningSpeed(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_RUNNINGSPEED, 0);
    }

    public static final int loadRunningSpeed2(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_RUNNINGSPEED2, 0);
    }

    public static final int loadRunningSpeed3(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_RUNNINGSPEED3, 0);
    }

    public static final int loadRunningSpeed4(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_RUNNINGSPEED4, 0);
    }
    // eddig

    public static final Intent createCyclingIntent() {
    	return new Intent(BLUETOOTH_ACTION_CYCLING);
    }

    public static final boolean isCyclingIntent(Intent intent) {
    	return intent.getAction().equals(BLUETOOTH_ACTION_CYCLING);
    }

    public static final Intent saveWheelRevolution(Intent intent, int revolutionSum) {
    	intent.putExtra(BLUETOOTH_EXTRA_WHEEL_REVOLUTION, revolutionSum);
    	return intent;
    }

    public static final int loadWheelRevolution(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_WHEEL_REVOLUTION, 0);
    }

    public static final Intent saveWheelEventTime(Intent intent, int eventTime) {
    	intent.putExtra(BLUETOOTH_EXTRA_WHEEL_EVENT_TIME, eventTime);
    	return intent;
    }

    public static final int loadWheelEventTime(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_WHEEL_EVENT_TIME, 0);
    }

    public static final Intent saveCrankRevolution(Intent intent, int revolutionSum) {
    	intent.putExtra(BLUETOOTH_EXTRA_CRANK_REVOLUTION, revolutionSum);
    	return intent;
    }

    public static final int loadCrankRevolution(Intent intent) {
        return intent.getIntExtra(BLUETOOTH_EXTRA_CRANK_REVOLUTION, 0);
    }

    public static final Intent saveCrankEventTime(Intent intent, int eventTime) {
    	intent.putExtra(BLUETOOTH_EXTRA_CRANK_EVENT_TIME, eventTime);
    	return intent;
    }

    public static final int loadCrankEventTime(Intent intent) {
    	return intent.getIntExtra(BLUETOOTH_EXTRA_CRANK_EVENT_TIME, 0);
    }

    private IntentAndBundleUtil() {
    }
}
