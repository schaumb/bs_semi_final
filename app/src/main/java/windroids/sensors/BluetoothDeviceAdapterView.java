package windroids.sensors;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import windroids.R;

/**
 * @author Balazs_Csernai
 */
public class BluetoothDeviceAdapterView extends LinearLayout {

    private TextView nameView;
    private TextView addressView;

    public BluetoothDeviceAdapterView(Context context) {
        super(context);
        inflate(context, R.layout.bluetoothdeviceadapter_view, this);
        if (!isInEditMode()) {
            setupViews();
        }
    }

    private void setupViews() {
        nameView = (TextView) findViewById(R.id.name);
        addressView = (TextView) findViewById(R.id.title);
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
		if (address != null) {
			addressView.setText(address);
		}
    }
}
