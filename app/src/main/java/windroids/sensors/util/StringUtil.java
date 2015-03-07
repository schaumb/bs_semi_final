package windroids.sensors.util;

import windroids.sensors.constants.General;
import windroids.sensors.constants.BluetoothUUID;

/**
 * String formatting utility.
 * @author Balazs_Csernai
 */
public class StringUtil {

    /**
     * Returns the hexadecimal representation of a byte array.
     * @param bytes Byte array
     * @return Hexadecimal representation
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder().append("[");
        for (int index = 0; index < bytes.length; index++) {
            if (index > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append("0x");
            stringBuilder.append(General.HEX_CHARACTERS[(((int) bytes[index] >> General.BYTE_HALF_SHIFT) & General.BYTE_LOW_MASK)]);
            stringBuilder.append(General.HEX_CHARACTERS[((int) bytes[index] & General.BYTE_LOW_MASK)]);
        }
        return stringBuilder.append("]").toString();
    }

    /**
     * Returns the name of a Bluetooth standard service UUID.
     * @param serviceUUID Bluetooth standard service UUID
     * @return Name
     */
    public static final String getServiceName(long serviceUUID) {
        StringBuilder stringBuilder = new StringBuilder();
        BluetoothUUID service = BluetoothUUID.fromUUID(serviceUUID);
        return stringBuilder.append(service != null ? service.getName() : "Unknown").append(" (0x").append(Long.toHexString(serviceUUID)).append(")")
                .toString();
    }

    /**
     * Returns the names of Bluetooth standard service UUIDs.
     * @param serviceUUIDs Bluetooth standard service UUIDs
     * @return Names
     */
    public static final String getServiceNames(long[] serviceUUIDs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < serviceUUIDs.length; index++) {
            if (index > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(getServiceName(serviceUUIDs[index]));
        }
        return stringBuilder.toString();
    }

    private StringUtil() {
    }
}
