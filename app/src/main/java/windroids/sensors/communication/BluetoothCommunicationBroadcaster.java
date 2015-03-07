package windroids.sensors.communication;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Context;
import android.content.Intent;

import windroids.sensors.constants.General;
import windroids.sensors.util.IntentAndBundleUtil;

/**
 * <p>
 * Broadcasts intents on Bluetooth Low Energy events.<br>
 * <em>Implementation in insufficient!</em>
 * </p>
 * <p>
 * <ul>
 * <li>{@link General#BLUETOOTH_ACTION_CONNECTION} on {@link BluetoothGattCallback#onConnectionStateChange(BluetoothGatt, int, int)} containing
 * <ul>
 * <li>Bluetooth device</li>
 * <li>connection state</li>
 * </ul>
 * </li>
 * <li>{@link General#BLUETOOTH_ACTION_SERVICE_DISCOVERED} on {@link BluetoothGattCallback#onServicesDiscovered(BluetoothGatt, int)} containing
 * <ul>
 * <li>Bluetooth device</li>
 * </ul>
 * </li>
 * <li>{@link General#BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC} on {@link BluetoothGattCallback#onCharacteristicChanged(BluetoothGatt, BluetoothGattCharacteristic)} containing
 * <ul>
 * <li>Bluetooth device</li>
 * <li>changed characteristic UUID</li>
 * <li>change</li>
 * </ul>
 * </li>
 * <li>{@link General#BLUETOOTH_ACTION_WRITE_DESCRIPTOR} on {@link BluetoothGattCallback#onDescriptorWrite(BluetoothGatt, BluetoothGattDescriptor, int)}
 * <ul>
 * <li>Bluetooth device</li>
 * <li>written characteristic UUID</li>
 * <li>written descriptor UUID</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * @author Balazs_Csernai
 */
public class BluetoothCommunicationBroadcaster extends BluetoothGattCallback {

    private final Context context;

    public BluetoothCommunicationBroadcaster(Context context) {
        this.context = context;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gattClient, int status, int newState) {
        Intent intent = IntentAndBundleUtil.createConnectionIntent();
        IntentAndBundleUtil.saveBluetoothDevice(intent, gattClient.getDevice());
        IntentAndBundleUtil.saveConnectionState(intent, newState);
        context.sendBroadcast(intent);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gattClient, int status) {
        Intent intent = IntentAndBundleUtil.createServicesDiscoveredIntent();
        IntentAndBundleUtil.saveBluetoothDevice(intent, gattClient.getDevice());
        context.sendBroadcast(intent);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gattClient, BluetoothGattCharacteristic characteristic, int status) {

    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gattClient, BluetoothGattCharacteristic characteristic, int status) {
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gattClient, BluetoothGattCharacteristic characteristic) {
        Intent intent = IntentAndBundleUtil.createCharacteristicChangeIntent();
        IntentAndBundleUtil.saveBluetoothDevice(intent, gattClient.getDevice());
        IntentAndBundleUtil.saveCharacteristic(intent, characteristic.getUuid());
        IntentAndBundleUtil.saveCharacteristicData(intent, characteristic.getValue());
        context.sendBroadcast(intent);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gattClient, BluetoothGattDescriptor descriptor, int status) {
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gattClient, BluetoothGattDescriptor descriptor, int status) {
        Intent intent = IntentAndBundleUtil.createWriteDescriptorIntent();
        IntentAndBundleUtil.saveBluetoothDevice(intent, gattClient.getDevice());
        IntentAndBundleUtil.saveCharacteristic(intent, descriptor.getCharacteristic().getUuid());
        IntentAndBundleUtil.saveDescriptor(intent, descriptor.getUuid());
        context.sendBroadcast(intent);
    }

}
