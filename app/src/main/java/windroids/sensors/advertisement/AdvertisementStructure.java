package windroids.sensors.advertisement;

/**
 * Bluetooth Low Energy standard based Advertising Data Type (ADT).<br>
 * <em>If ADT consists of N bytes<ul>
 * <li>1st byte is N-1</li>
 * <li>2nd byte is the data type: <a href="https://www.bluetooth.org/en-us/specification/assigned-numbers/generic-access-profile">
 *      GAP Assigned Numbers</a></li>
 * <li>N-2 data bytes</ul></em>
 * @author Balazs_Csernai
 */
public class AdvertisementStructure {

    private final byte length;
    private final byte type;
    private final byte[] data;

    /**
     * Constructor.
     * @param length Data length 
     * @param type Data type
     * @param data Data
     */
    AdvertisementStructure(byte length, byte type, byte[] data) {
        this.length = length;
        this.type = type;
        this.data = data;
    }

    /**
     * Returns the length of type and data bytes.
     * @return Length of type and data bytes
     */
    public byte getLength() {
        return length;
    }

    /**
     * Returns the type of the data.
     * @return Data type
     */
    public byte getType() {
        return type;
    }

    /**
     * Returns the raw byte data.
     * @return Data
     */
    public byte[] getData() {
        return data;
    }
}
