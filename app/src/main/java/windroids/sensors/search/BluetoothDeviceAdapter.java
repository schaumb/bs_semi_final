package windroids.sensors.search;

import windroids.sensors.advertisement.AdvertisementPacket;
import windroids.sensors.advertisement.BaseAdvertisementPacket;

import android.bluetooth.BluetoothDevice;

/**
 * Extended version of {@link BluetoothDevice} with {@link AdvertisementPacket}.
 * @author Balazs_Csernai
 */
public class BluetoothDeviceAdapter implements AdvertisementPacket {

    private final BluetoothDevice device;
    private final byte[] advertisementData;
    private final BaseAdvertisementPacket advertisementPacket;

    /**
     * Constructor.
     * @param device Bluetooth device
     * @param advertisementData Advertisement data
     */
    public BluetoothDeviceAdapter(BluetoothDevice device, byte[] advertisementData) {
        this.device = device;
        this.advertisementData = advertisementData;
        advertisementPacket = new BaseAdvertisementPacket(advertisementData);
    }

    /**
     * Returns the Bluetooth device.
     * @return Bluetooth device
     */
    public BluetoothDevice getDevice() {
        return device;
    }

    /**
     * Returns the Advertisement data.
     * @return Advertisement data
     */
    public byte[] getAdvertisementData() {
        return advertisementData;
    }

    /**
     * Checks if AP contains short or complete local name.
     * @return True if AP contains short or complete local name
     */
    public boolean hasName() {
        return advertisementPacket.hasName() || device.getName() != null;
    }

    /**
     * Returns complete or short local name.
     * @return Complete or short local name or null
     */
    public String getName() {
        String name = "Unknown";
        if (advertisementPacket.hasName()) {
            name = advertisementPacket.getName();
        } else if (device.getName() != null) {
            name = device.getName();
        }
        return name;
    }

    /**
     * Checks if AP contains incomplete or complete list of 16bit UUIDs.
     * @return True if AP contains incomplete or complete list of 16bit UUIDs.
     */
    public boolean has16BitUUIDs() {
        return advertisementPacket.has16BitUUIDs();
    }

    /**
     * Returns the parsed 16bit UUIDs from complete and incomplete list of 16bit UUIDs.
     * @return 16bit UUIDs
     */
    public long[] get16BitUUIDs() {
        return advertisementPacket.get16BitUUIDs();
    }

    @Override
    public boolean has16BitIncompleteUUIDs() {
        return advertisementPacket.has16BitIncompleteUUIDs();
    }

    @Override
    public boolean has16BitCompleteUUIDs() {
        return advertisementPacket.has16BitCompleteUUIDs();
    }

    @Override
    public boolean hasShortName() {
        return advertisementPacket.hasShortName();
    }

    @Override
    public boolean hasCompleteName() {
        return advertisementPacket.hasCompleteName();
    }

    @Override
    public boolean hasPowerLevel() {
        return advertisementPacket.hasPowerLevel();
    }

    @Override
    public boolean hasManufacturerSpecific() {
        return advertisementPacket.hasManufacturerSpecific();
    }

    @Override
    public byte[] get16BitIncompleteUUIDs() {
        return advertisementPacket.get16BitIncompleteUUIDs();
    }

    @Override
    public byte[] get16BitCompleteUUIDs() {
        return advertisementPacket.get16BitCompleteUUIDs();
    }

    @Override
    public byte[] getShortName() {
        return advertisementPacket.getShortName();
    }

    @Override
    public byte[] getCompleteName() {
        return advertisementPacket.getCompleteName();
    }

    @Override
    public byte[] getPowerLevel() {
        return advertisementPacket.getPowerLevel();
    }

    @Override
    public byte[] getManufacturerSpecific() {
        return advertisementPacket.getManufacturerSpecific();
    }

    @Override
    public int hashCode() {
        return device.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BluetoothDeviceAdapter) {
            return device.equals(((BluetoothDeviceAdapter) o).getDevice());
        }
        return false;
    }
}
