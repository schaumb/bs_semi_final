package windroids.sensors.search;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;
import java.util.List;

import windroids.sensors.BluetoothDeviceAdapterView;

/**
 * View adapter for Bluetooth devices.
 * @author Balazs_Csernai
 */
public class BluetoothDeviceAdapterListAdapter extends BaseAdapter implements BluetoothSearchCallback {

    private final Context context;
    private final List<BluetoothDeviceAdapter> bluetoothDevices;

    public BluetoothDeviceAdapterListAdapter(Context context) {
        this.context = context;
        bluetoothDevices = new LinkedList<BluetoothDeviceAdapter>();
    }

    @Override
    public int getCount() {
        return bluetoothDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return bluetoothDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bluetoothDevices.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDeviceAdapterView adapterView;
        if (convertView == null) {
            adapterView = new BluetoothDeviceAdapterView(context); 
        } else {
            adapterView = (BluetoothDeviceAdapterView) convertView;
        }
        populateView(adapterView, bluetoothDevices.get(position));
        return adapterView;
    }

    @Override
    public void onBluetoothDeviceFound(BluetoothDevice bluetoothDevice, byte[] scanRecord) {
        BluetoothDeviceAdapter extendedBluetoothDevice = new BluetoothDeviceAdapter(bluetoothDevice, scanRecord);
        if (!bluetoothDevices.contains(extendedBluetoothDevice)) {
            bluetoothDevices.add(extendedBluetoothDevice);
            notifyDataSetChanged();
        }
    }

    private void populateView(BluetoothDeviceAdapterView adapterView, BluetoothDeviceAdapter deviceAdapter) {
        adapterView.setName(deviceAdapter.getName());
        adapterView.setAddress(deviceAdapter.getDevice().getAddress());
    }
}
