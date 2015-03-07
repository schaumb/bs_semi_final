package windroids.sensors.advertisement;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Iterates through the raw byte data of Advertisement Packet parsing it to Advertising Data Types.
 * @author Balazs_Csernai
 */
public class AdvertisementStructureIterator implements Iterator<AdvertisementStructure> {

    private final byte[] advertisementPacket;
    private int index;

    /**
     * Constructor.
     * @param advertisementPacket Raw byte data
     */
    AdvertisementStructureIterator(byte[] advertisementPacket) {
        this.advertisementPacket = advertisementPacket;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < advertisementPacket.length && advertisementPacket[index] > 0 && index + advertisementPacket[index] < advertisementPacket.length;
    }

    @Override
    public AdvertisementStructure next() {
        AdvertisementStructure advertisementStructure = null;
        if (hasNext()) {
            advertisementStructure = new AdvertisementStructure(advertisementPacket[index], advertisementPacket[index + 1], Arrays.copyOfRange(advertisementPacket, index + 2, index + advertisementPacket[index] + 1));
            index += advertisementPacket[index] + 1;
        }
        return advertisementStructure;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
