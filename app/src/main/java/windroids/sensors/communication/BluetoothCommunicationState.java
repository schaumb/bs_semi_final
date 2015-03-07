package windroids.sensors.communication;

/**
 * <p>Communication state for controlling Bluetooth Low Energy device.<p>
 * <p>
 * <em>Communication states may contain one or more coherent communication steps, e.g.
 * <ul>
 * <li>connection
 * <ul>
 * <li>start connection establishment</li>
 * <li>wait for successful connection establishment</li>
 * <li>discover services provided by the Bluetooth device</li>
 * </ul>
 * </li>
 * <li>notification
 * <ul>
 * <li>register characteristic notification</li>
 * <li>write characteristic descriptor</li>
 * <li>wait for descriptor write response</li>
 * <li>handle characteristic updates</li>
 * </ul>
 * </li>
 * </ul>
 * </em>
 * </p>
 * @author Balazs_Csernai
 *
 */
public interface BluetoothCommunicationState extends BluetoothCommunicationCallback {

    /**
     * Sets the Bluetooth communication context.
     * @param communicationContext Context
     */
    void setCommunicationContext(BluetoothCommunicationContext communicationContext);

    /**
     * Starts the state.
     */
    void startCommunication();

    /**
     * Stops the state.
     * @param forced True to force state stopping.
     */
    void stopCommunication(boolean forced);

}
