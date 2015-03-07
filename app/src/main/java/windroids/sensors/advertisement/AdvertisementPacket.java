package windroids.sensors.advertisement;

/**
 * Bluetooth Low Energy standard based Advertisement Packet (AP) data interface.<br>
 * <em>Incomplete interface, for more: <a href="https://www.bluetooth.org/en-us/specification/assigned-numbers/generic-access-profile">GAP Assigned Numbers</a></em>
 * @author Balazs_Csernai
 */
public interface AdvertisementPacket {

    /**
     * Checks if AP contains incomplete list of 16bit UUIDs.
     * @return True if AP contains incomplete list of 16bit UUIDs.
     */
    boolean has16BitIncompleteUUIDs();

    /**
     * Checks if AP contains complete list of 16bit UUIDs.
     * @return True if AP contains complete list of 16bit UUIDs
     */
    boolean has16BitCompleteUUIDs();

    /**
     * Checks if AP contains short local name.
     * @return True if AP contains short local name
     */
    boolean hasShortName();

    /**
     * Checks if AP contains complete local name.
     * @return True if AP contains complete local name
     */
    boolean hasCompleteName();

    /**
     * Checks if AP contains power level.
     * @return True if AP contains power level
     */
    boolean hasPowerLevel();

    /**
     * Checks if AP contains manufacturer specific data.
     * @return True if AP contains manufacturer specific data
     */
    boolean hasManufacturerSpecific();

    /**
     * Returns the raw byte data for incomplete list of 16bit UUIDs.
     * @return Incomplete list of 16bit UUIDs or null
     */
    byte[] get16BitIncompleteUUIDs();

    /**
     * Returns raw byte data for complete list of 16bit UUIDs.
     * @return Complete list of 16bit UUIDs or null
     */
    byte[] get16BitCompleteUUIDs();

    /**
     * Returns raw byte data for short local name.
     * @return Short local name or null
     */
    byte[] getShortName();

    /**
     * Returns raw byte data for complete local name.
     * @return Complete local name or null
     */
    byte[] getCompleteName();

    /**
     * Returns raw byte data for power level.
     * @return Power level or null
     */
    byte[] getPowerLevel();

    /**
     * Returns raw byte data for manufacturer specific data.
     * @return Manufacturer specific data or null
     */
    byte[] getManufacturerSpecific();
}
