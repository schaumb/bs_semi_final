package windroids.sensors.constants;

import static windroids.sensors.constants.BluetoothUUIDType.STANDARD;

import java.util.UUID;

/**
 * Bluetooth standard Service and Characteristic UUIDs.<br>
 * <em>Incomplete, for more: <a href="https://developer.bluetooth.org/gatt/services/Pages/ServicesHome.aspx">GATT Services</a></em>
 * @author Balazs_Csernai
 */
public enum BluetoothUUID {
    AlertNotificationService("Alert Notification", 0x1811l, STANDARD),
    BatteryService("Battery", 0x180Fl, STANDARD),
    BloodPressureService("Blood Pressure", 0x1810l, STANDARD),
    BodyCompositionService("Body Composition", 0x181Bl, STANDARD),
    BondManagementService("Bond Management", 0x181El, STANDARD),
    ContinuousGlucoseMonitoringService("Continuous Glucose Monitoring", 0x181Fl, STANDARD),
    CurrentTimeService("Current Time", 0x1805l, STANDARD),
    CyclingPowerService("Cycling Power", 0x1818l, STANDARD),
    CyclingSpeedAndCadenceService("Cycling Speed and Cadence", 0x1816l, STANDARD),
    CyclingSpeedAndCadenceMeasurementCharacteristic("Cycling Speed and Cadence Measurement", 0x2A5B, STANDARD),
    DeviceInformationService("Device Information", 0x180Al, STANDARD),
    EnvironmentalSensingService("Environmental Sensing", 0x181Al, STANDARD),
    GenericAccessService("Generic Access", 0x1800l, STANDARD),
    GenericAttributeService("Generic Attribute", 0x1801l, STANDARD),
    GlucoseService("Glucose", 0x1808l, STANDARD),
    HealthThermometerService("Health Thermometer", 0x1809l, STANDARD),
    HeartRateService("Heart Rate", 0x180Dl, STANDARD),
    HeartRateMeasurementCharacteristic("Heart Rate Measurement", 0x2A37, STANDARD),
    HumanInterfaceDeviceService("Human Interface Device", 0x1812l, STANDARD),
    ImmediateAlertService("Immediate Alert", 0x1802l, STANDARD),
    InternetProtocolSupportService("Internet Protocol Support", 0x1820l, STANDARD),
    LinkLossService("Link Loss", 0x1803l, STANDARD),
    LocationAndNavigationService("Location and Navigation", 0x1819l, STANDARD),
    NextDSTChangeService("Next DST Change", 0x1807l, STANDARD),
    PhoneAlertStatusService("Phone Alert Status", 0x180El, STANDARD),
    ReferenceTimeUpdateService("Reference Time Update", 0x1806l, STANDARD),
    RunningSpeedAndCadenceService("Running Speed and Cadence", 0x1814l, STANDARD),
    ScanParametersService("Scan Parameters", 0x1813l, STANDARD),
    TxPowerService("Tx Power", 0x1804l, STANDARD),
    UserDataService("User Data", 0x181Cl, STANDARD),
    WeightScaleService("Weight Scale", 0x181Dl, STANDARD),
    ClientCharacteristicConfigurationDescriptor("Client Characteristic Configuration", 0x2902, STANDARD);

    private final String name;
    private final long uuid;
    private BluetoothUUIDType type;

    private BluetoothUUID(String name, long uuid, BluetoothUUIDType type) {
        this.name = name;
        this.uuid = uuid;
        this.type = type;
    }

    /**
     * Returns the UUID's 16bit representation.
     * @return 16bit representation
     */
    public long get16BitUUID() {
        return uuid & General.UUID_16BIT_MASK;
    }

    /**
     * Returns the UUID's 32bit representation.
     * @return 32bit representation
     */
    public long get32BitUUID() {
        return uuid & General.UUID_32BIT_MASK;
    }

    /**
     * Returns the UUID's 128bit representation.
     * @return 128bit representation
     */
    public long get128BitUUID() {
        return uuid & General.UUID_128BIT_MASK;
    }

    /**
     * Returns the UUID's name
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the UUID as a java standard {@link java.util.UUID}.
     * @return Standard UUID.
     */
    public UUID getUUID() {
        return type.getUUIDFrom16Bit(get16BitUUID());
    }

    /**
     * Creates UUID for its representation.
     * @param serviceUUID UUID representation
     * @return UUID or null
     */
    public static final BluetoothUUID fromUUID(long serviceUUID) {
        BluetoothUUID unknownService = null;
        for (BluetoothUUID service : BluetoothUUID.values()) {
            if (serviceUUID == service.uuid) {
                unknownService = service;
                break;
            }
        }
        return unknownService;
    }
}
