package windroids.sensors.constants;

public class General {

    public static final String BLUETOOTH_ACTION_DEVICE_FOUND = "com.btlerefapp.bluetooth.action.deviceFound";
    public static final String BLUETOOTH_EXTRA_DEVICE = "com.btlerefapp.bluetooth.extra.device";
    public static final String BLUETOOTH_EXTRA_SCANRECORD = "com.btlerefapp.bluetooth.extra.scanRecord";

    public static final String BLUETOOTH_ACTION_CONNECTION = "com.btlerefapp.bluetooth.action.communication";
    public static final String BLUETOOTH_EXTRA_CONNECTION_STATE = "com.btlerefapp.bluetooth.extra.connectionState";

    public static final String BLUETOOTH_ACTION_SERVICE_DISCOVERED = "com.btlerefapp.bluetooth.action.servicesDiscovered";

    public static final String BLUETOOTH_ACTION_WRITE_CHARACTERISTIC = "com.btlerefapp.bluetooth.action.characteristicWrite";
    public static final String BLUETOOTH_ACTION_READ_CHARACTERISTIC = "com.btlerefapp.bluetooth.action.characteristicRead";
    public static final String BLUETOOTH_ACTION_CHANGE_CHARACTERISTIC = "com.btlerefapp.bluetooth.action.characteristicChange";
    public static final String BLUETOOTH_EXTRA_CHARACTERISTIC = "com.btlerefapp.bluetooth.extra.characteristic";
    public static final String BLUETOOTH_EXTRA_CHARACTERISTIC_DATA = "com.btlerefapp.extra.characteristicData";

    public static final String BLUETOOTH_ACTION_WRITE_DESCRIPTOR = "com.btlerefapp.bluetooth.action.descriptorWrite";
    public static final String BLUETOOTH_ACTION_READ_DESCRIPTOR = "com.btlerefapp.bluetooth.action.descriptorRead";
    public static final String BLUETOOTH_EXTRA_DESCRIPTOR = "com.btlerefapp.bluetooth.extra.descriptor";

    public static final String BLUETOOTH_ACTION_HEARTRATE = "com.btlerefapp.bluetooth.action.heartrate";
    public static final String BLUETOOTH_EXTRA_HEARTRATE = "com.btlerefapp.bluetooth.extra.heartrate";
    public static final String BLUETOOTH_EXTRA_HEARTRATE2 = "com.btlerefapp.bluetooth.extra.heartrate2";
    public static final String BLUETOOTH_EXTRA_HEARTRATE3 = "com.btlerefapp.bluetooth.extra.heartrate3";

    public static final String BLUETOOTH_ACTION_BLOODPRESSURE = "com.btlerefapp.bluetooth.action.bloodpressure";
    public static final String BLUETOOTH_EXTRA_BLOODPRESSURE = "com.btlerefapp.bluetooth.extra.bloodpressure";
    public static final String BLUETOOTH_EXTRA_BLOODPRESSURE2 = "com.btlerefapp.bluetooth.extra.bloodpressure2";
    public static final String BLUETOOTH_EXTRA_BLOODPRESSURE3 = "com.btlerefapp.bluetooth.extra.bloodpressure3";

    public static final String BLUETOOTH_ACTION_RUNNINGSPEED = "com.btlerefapp.bluetooth.action.runningspeed";
    public static final String BLUETOOTH_EXTRA_RUNNINGSPEED = "com.btlerefapp.bluetooth.extra.runningspeed";
    public static final String BLUETOOTH_EXTRA_RUNNINGSPEED2 = "com.btlerefapp.bluetooth.extra.runningspeed2";
    public static final String BLUETOOTH_EXTRA_RUNNINGSPEED3 = "com.btlerefapp.bluetooth.extra.runningspeed3";
    public static final String BLUETOOTH_EXTRA_RUNNINGSPEED4 = "com.btlerefapp.bluetooth.extra.runningspeed4";

    public static final String BLUETOOTH_ACTION_CYCLING = "com.btlerefapp.bluetooth.action.cycling";
    public static final String BLUETOOTH_EXTRA_WHEEL_REVOLUTION = "com.btlerefapp.bluetooth.extra.cycling.wheelRevolutionSum";
    public static final String BLUETOOTH_EXTRA_WHEEL_EVENT_TIME = "com.btlerefapp.bluetooth.extra.cycling.wheelEventTime";
    public static final String BLUETOOTH_EXTRA_CRANK_REVOLUTION = "com.btlerefapp.bluetooth.extra.cycling.crankRevolutionSum";
    public static final String BLUETOOTH_EXTRA_CRANK_EVENT_TIME = "com.btlerefapp.bluetooth.extra.cycling.crankEventTime";

    public static final int ACTION_REQUEST_ENABLE_BT = 101;

    public static final char[] HEX_CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final int BYTE_MASK = 0xFF;
    public static final int BYTE_LOW_MASK = 0x0F;
    public static final int BYTE_HALF_SHIFT = 4;
    public static final int BYTE_SHIFT = 8;

    public static final long UUID_16BIT_MASK = 0xFFFFl;
    public static final long UUID_32BIT_MASK = 0xFFFFFFFFl;
    public static final long UUID_128BIT_MASK = 0xFFFFFFFFFFFFFFFFl;

    public static final long ANIMATION_SHORT = 250l;
    public static final float UPSCALE_FACTOR = 1.25f;
    public static final float DOWNSCALE_FACTOR = 0.8f;
    public static final float FULL_ROTATION = 360.0f;

    private General() {
    }
}
