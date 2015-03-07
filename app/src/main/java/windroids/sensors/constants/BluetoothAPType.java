package windroids.sensors.constants;

/**
 * Advertising Data Types.
 * @author Balazs_Csernai
 */
public enum BluetoothAPType {

    FLAGS("Flags", (byte) 0x01),
    UUID_16BIT_INCOMPLETE("Incomplete list of 16bit UUIDs", (byte) 0x02),
    UUID_16BIT_COMPlETE("Complete list of 16bit UUIDs", (byte) 0x03),
    UUID_32BIT_INCOMPLETE("Incomplete list of 32bit UUIDs", (byte) 0x04),
    UUID_32BIT_COMPLETE("Cncomplete list of 32bit UUIDs", (byte) 0x05),
    UUID_128BIT_INCOMPLETE("Incomplete list of 128bit UUIDs", (byte) 0x06),
    UUID_128BIT_COMPLETE("Complete list of 128bit UUIDs", (byte) 0x07),
    NAME_SHORT("Short local name", (byte) 0x08),
    NAME_COMPLETE("Complete local name", (byte) 0x09),
    TX_POWER_LEVEL("Tx power level", (byte) 0x0A),
    DEVICE_CLASS("Class of device", (byte) 0x0D),
    PAIRING_HASH("Simple pairing hash C", (byte) 0x0E),
    PAIRING_RANDOMIZER("Simple pairing randomizer R", (byte) 0x0F),
    SECMAN_TK_VALUE("Security manager TK value", (byte) 0x10),
    SECMAN_OOB_FLAG("Security manager Out of Band flag", (byte) 0x11),
    SLAVE_INTERVAL("Slave connection interval range", (byte) 0x12),
    UUID_16BIT_SOLICITATION("List of 16bit service solicitation UUIDs", (byte) 0x14),
    UUID_128BIT_SOLICITATION("List of 128bit service solicitation UUIDs", (byte) 0x15),
    SERVICE_DATA_16BIT("Service data 16bit", (byte) 0x16),
    TARGET_ADDRESS_PUBLIC("Public target address", (byte) 0x17),
    TARGET_ADDRESS_RANDOM("Random target address", (byte) 0x18),
    APPEARANCE("Appearance", (byte) 0x19),
    ADVERTISING_INTERVAL("Advertising interval", (byte) 0x1A),
    DEVICE_ADDRESS("LE Bluetooth device address", (byte) 0x1B),
    ROLE("LE role", (byte) 0x1C),
    PAIRING_HASH_256("Simple pairing hash C-256", (byte) 0x1D),
    PAIRING_RANDOMIZER_256("Simple pairing randomizer R-256", (byte) 0x1E),
    UUID_32BIT_SOLICITATION("List of 32bit service solicitation UUIDs", (byte) 0x1F),
    SERVICE_DATA_32BIT("Service data 32bit", (byte) 0x20),
    SERVICE_DATA_1286BIT("Service data 128bit", (byte) 0x21),
    LESEC_CONFIRM("LE secure connections confirmation value", (byte) 0x22),
    LESEC_RANDOM("LE secure connections random value", (byte) 0x23),
    INFORMATION_3D("3D information data", (byte) 0x3D),
    MANUFACTURER_SPEC("Manufacturer specific data", (byte) 0xFF);
    
    private final String name;
    private final byte type;

    private BluetoothAPType(String name, byte type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Returns the name of the ADT.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the byte value of the ADT.
     * @return Byte value
     */
    public byte getType() {
        return type;
    }

    /**
     * Creates ADT from its byte value.
     * @param type Byte value
     * @return ADT or null
     */
    public static final BluetoothAPType fromType(byte type) {
        BluetoothAPType unknownType = null;
        for (BluetoothAPType apType : BluetoothAPType.values()) {
            if (type == apType.getType()) {
                unknownType = apType;
                break;
            }
        }
        return unknownType;
    }
}
