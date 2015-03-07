package windroids.sensors.util;

import windroids.sensors.constants.General;

/**
 * Byte data conversion utility.
 * @author Balazs_Csernai
 */
public class ConvertUtil {

    public static final long[] convert16BitLong(byte[] data) {
        long[] converted = new long[data.length / 2];
        for (int index = 0; index < data.length; index +=2) {
            converted[index / 2] = (long) (data[index + 1] << General.BYTE_SHIFT |  data[index]) & General.UUID_16BIT_MASK; 
        }
        return converted; 
    }

    private ConvertUtil() {
    }
}
