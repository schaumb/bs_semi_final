package windroids.sensors.advertisement;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import windroids.sensors.constants.BluetoothAPType;
import windroids.sensors.util.ConvertUtil;

import android.util.SparseArray;

/**
 * Base implementation of {@link AdvertisementPacket}.
 * @author Balazs_Csernai
 */
public class BaseAdvertisementPacket implements AdvertisementPacket {

    private final SparseArray<byte[]> advertisementData;

    /**
     * Constructor.
     * @param advertisementPacket Raw byte data
     */
    public BaseAdvertisementPacket(byte[] advertisementPacket) {
        advertisementData = new SparseArray<byte[]>();
        parseAdvertisementPacket(advertisementPacket);
    }

    private void parseAdvertisementPacket(byte[] advertisementPacket) {
        AdvertisementStructureIterator advertisementStructureIterator = new AdvertisementStructureIterator(advertisementPacket);
        while (advertisementStructureIterator.hasNext()) {
            AdvertisementStructure advertisementStructure = advertisementStructureIterator.next();
            advertisementData.put(advertisementStructure.getType(), advertisementStructure.getData());
        }
    }

    @Override
    public boolean has16BitIncompleteUUIDs() {
        return hasType(BluetoothAPType.UUID_16BIT_INCOMPLETE.getType());
    }

    @Override
    public boolean has16BitCompleteUUIDs() {
        return hasType(BluetoothAPType.UUID_16BIT_COMPlETE.getType());
    }

    @Override
    public boolean hasShortName() {
        return hasType(BluetoothAPType.NAME_SHORT.getType());
    }

    @Override
    public boolean hasCompleteName() {
        return hasType(BluetoothAPType.NAME_COMPLETE.getType());
    }

    @Override
    public boolean hasPowerLevel() {
        return hasType(BluetoothAPType.TX_POWER_LEVEL.getType());
    }

    @Override
    public boolean hasManufacturerSpecific() {
        return hasType(BluetoothAPType.MANUFACTURER_SPEC.getType());
    }

    @Override
    public byte[] get16BitIncompleteUUIDs() {
        return getData(BluetoothAPType.UUID_16BIT_INCOMPLETE.getType());
    }

    @Override
    public byte[] get16BitCompleteUUIDs() {
        return getData(BluetoothAPType.UUID_16BIT_COMPlETE.getType());
    }

    @Override
    public byte[] getShortName() {
        return getData(BluetoothAPType.NAME_SHORT.getType());
    }

    @Override
    public byte[] getCompleteName() {
        return getData(BluetoothAPType.NAME_COMPLETE.getType());
    }

    @Override
    public byte[] getPowerLevel() {
        return getData(BluetoothAPType.TX_POWER_LEVEL.getType());
    }

    @Override
    public byte[] getManufacturerSpecific() {
        return getData(BluetoothAPType.MANUFACTURER_SPEC.getType());
    }

    private boolean hasType(byte type) {
        return advertisementData.get(type) != null;
    }

    private byte[] getData(byte type) {
        return advertisementData.get(type);
    }

    /**
     * Checks if AP contains short or complete local name.
     * @return True if AP contains short or complete local name
     */
    public boolean hasName() {
        return hasShortName() || hasCompleteName();
    }

    /**
     * Returns complete or short local name.
     * @return Complete or short local name or null
     */
    public String getName() {
        String name = null;
        if (hasCompleteName()) {
            name = new String(getCompleteName(), Charset.forName("UTF-8"));
        } else if (hasShortName()) {
            name = new String(getShortName(), Charset.forName("UTF-8"));
        }
        return name;
    }

    /**
     * Checks if AP contains incomplete or complete list of 16bit UUIDs.
     * @return True if AP contains incomplete or complete list of 16bit UUIDs.
     */
    public boolean has16BitUUIDs() {
        return has16BitIncompleteUUIDs() || has16BitCompleteUUIDs();
    }

    /**
     * Returns the parsed 16bit UUIDs from complete and incomplete list of 16bit UUIDs.
     * @return 16bit UUIDs
     */
    public long[] get16BitUUIDs() {
        List<Long> UUIDs = new LinkedList<Long>();
        UUIDs.addAll(get16BitIncmpUUIDs());
        UUIDs.addAll(get16BitCmpUUIDs());
        long[] uuids = new long[UUIDs.size()];
        for (int index = 0; index < UUIDs.size(); index++) {
            uuids[index] = UUIDs.get(index);
        }
        return uuids;
    }

    private List<Long> get16BitIncmpUUIDs() {
        List<Long> UUIDs = new LinkedList<Long>(); 
        if (has16BitIncompleteUUIDs()) {
            for (long UUID : ConvertUtil.convert16BitLong(get16BitIncompleteUUIDs())) {
                UUIDs.add(UUID);
            }
        }
        return UUIDs;
    }

    private List<Long> get16BitCmpUUIDs() {
        List<Long> UUIDs = new LinkedList<Long>();
        if (has16BitCompleteUUIDs()) {
            for (long UUID : ConvertUtil.convert16BitLong(get16BitCompleteUUIDs())) {
                UUIDs.add(UUID);
            }
        }
        return UUIDs;
    }
}
