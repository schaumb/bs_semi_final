package windroids.sensors.constants;

import java.util.UUID;

/**
 * Bluetooth standard UUID type.
 * @author Balazs_Csernai
 */
public enum BluetoothUUIDType {

    STANDARD("0000", "-0000-1000-8000-00805f9b34fb");

    private static final String UUID_16BIT_FORMATTER = "%s%04X%s";

    private String prefix;
    private String postfix;

    private BluetoothUUIDType(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    /**
     * Returns the UUID as a java standard {@link java.util.UUID}.
     * @param UUID representation
     * @return Standard UUID.
     */
    public UUID getUUIDFrom16Bit(long uuid) {
        return UUID.fromString(String.format(UUID_16BIT_FORMATTER, prefix, uuid & General.UUID_16BIT_MASK, postfix));
    }
}
