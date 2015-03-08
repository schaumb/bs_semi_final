package windroids.sensors;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import windroids.R;
import windroids.sensors.search.BluetoothDeviceAdapter;

/**
 * @author Balazs_Csernai
 */
public class BluetoothDeviceAdapterView extends LinearLayout {

    private TextView nameView;
    private TextView addressView;
    private TextView additionalView;

    public BluetoothDeviceAdapterView(Context context) {
        super(context);
        inflate(context, R.layout.bluetoothdeviceadapter_view, this);
        if (!isInEditMode()) {
            setupViews();
        }
    }

    private void setupViews() {
        nameView = (TextView) findViewById(R.id.foundBluetoothDeviceView_name);
        addressView = (TextView) findViewById(R.id.foundBluetoothDeviceView_address);
        additionalView = (TextView) findViewById(R.id.foundBluetoothDeviceView_additional);
    }

    /**
     * Sets the device name.
     * @param name Device name
     */
    public void setName(CharSequence name) {
        nameView.setText(name);
    }

    /**
     * Sets the device address.
     * @param address Device address
     */
    public void setAddress(CharSequence address) {
        addressView.setText(address);
    }

    /**
     * Sets additional information.
     * @param additional Aditional information
     */
    public void setAdditional(CharSequence additional) {
        additionalView.setText(additional);
    }
}
