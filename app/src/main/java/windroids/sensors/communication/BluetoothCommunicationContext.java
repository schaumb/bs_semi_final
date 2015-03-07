package windroids.sensors.communication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.content.Context;

/**
 * Context containing everything required for Bluetooth communication.
 * @author Balazs_Csernai
 */
public abstract class BluetoothCommunicationContext {

	private Context context;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothDevice bluetoothDevice;
	private BluetoothGatt gattClient;
	private BluetoothCommunicationBroadcaster gattClientCallback;
	private BluetoothCommunicationReceiver receiver;
	private BluetoothCommunicationState communicationState;
	private BluetoothCommunicationState nextCommunicationState;

	/**
	 * Constructor.
	 * @param context Android context
	 * @param deviceAdapter Bluetooth device adapter
	 */
	public BluetoothCommunicationContext(Context context, BluetoothDevice bluetoothDevice) {
		this.context = context;
		this.bluetoothDevice = bluetoothDevice;
		this.bluetoothAdapter = ((BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
		gattClientCallback = new BluetoothCommunicationBroadcaster(context);
		receiver = new BluetoothCommunicationReceiver();
		receiver.register(context);
	}

	/**
	 * Sets the initial communication state.
	 * @param initialCommunicationState Initial communication state
	 */
	protected void setInitialCommunicationState(BluetoothCommunicationState initialCommunicationState) {
	    communicationState = initialCommunicationState;
	}

	/**
	 * Starts Bluetooth communication.
	 */
	public void start() {
		communicationState.startCommunication();
	}

	/**
	 * Stops Bluetooth communication.
	 * @param forced True to stop immediately, unregistering characteristics otherwise.
	 */
	public void stop(boolean forced) {
		communicationState.stopCommunication(forced);
	}

	/**
	 * Returns Android context.
	 * @return Android context
	 */
	public Context getAndroidContext() {
		return context;
	}

	/**
	 * Checks if the same Bluetooth device is used by the context.
	 * @param otherDevice Bluetooth device
	 * @return True if the devices match
	 */
	public boolean isDevice(BluetoothDevice otherDevice) {
	    return bluetoothDevice.getAddress().equals(otherDevice.getAddress());
	}

	/**
	 * Returns the Bluetooth device.
	 * @return Bluetooth device
	 */
	public BluetoothDevice getDevice() {
		return bluetoothDevice;
	}

	/**
	 * Returns the GATT client.
	 * @return GATT client
	 */
	public BluetoothGatt getGattClient() {
		return gattClient;
	}

	/**
	 * Sets the GATT client.
	 * @param gattClient GATT client
	 */
	public void setGattClient(BluetoothGatt gattClient) {
		this.gattClient = gattClient;
	}

	/**
	 * Sets the GATT client callback.
	 * @return GATT client callback
	 */
	public BluetoothGattCallback getGattClientCallback() {
		return gattClientCallback;
	}

	/**
	 * Returns the Bluetooth Low Energy broadcast intent receiver.
	 * @return Intent receiver
	 */
	public BluetoothCommunicationReceiver getCommunicationReceiver() {
		return receiver;
	}

	/**
	 * Returns the current communcation state.
	 * @return Communication state
	 */
	protected BluetoothCommunicationState getCommunicationState() {
		return communicationState;
	}

	/**
	 * Sets the following communcation state.
	 * @param nextCommunicationState Communication state
	 */
	public void setNextCommunicationState(BluetoothCommunicationState nextCommunicationState) {
		this.nextCommunicationState = nextCommunicationState;
	}

	/**
	 * Called by the current communication state when it has finished its job.
	 */
	public void onCommunicationStateFinished() {
		if (communicationState != null) {
			communicationState.setCommunicationContext(null);
			receiver.setBluetoothCommunicationCallback(null);
		}

		communicationState = nextCommunicationState;
		nextCommunicationState = null;

		communicationState.setCommunicationContext(this);
		receiver.setBluetoothCommunicationCallback(communicationState);

		communicationState.startCommunication();
	}

}
