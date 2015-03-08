package windroids.ui.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import windroids.R;
import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.search.BluetoothDeviceAdapterListAdapter;
import windroids.sensors.search.BluetoothSearchBroadcaster;
import windroids.sensors.search.BluetoothSearchReceiver;
import windroids.sensors.util.IntentAndBundleUtil;
import windroids.ui.sensor.BloodpressureActivity;
import windroids.ui.sensor.HeartrateActivity;
import windroids.ui.sensor.RunningSpeedActivity;

import static windroids.sensors.constants.BluetoothUUID.RunningSpeedAndCadenceService;
import static windroids.sensors.constants.BluetoothUUID.BloodPressureService;
import static windroids.sensors.constants.BluetoothUUID.HeartRateService;


public class SensorFragment extends Fragment implements OnItemClickListener {

    private ListView listView;
    private BluetoothSearchReceiver receiver;
    private BluetoothSearchBroadcaster broadcaster;
    private BluetoothDeviceAdapterListAdapter viewAdapter;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        setupViews(rootView);
        return rootView;
    }

    private void setupViews(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.searchFragment_foundBluetoothDevice_list);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        startSearch();
        super.onResume();
    }

    private void startSearch() {
        getActivity().setProgressBarIndeterminateVisibility(true);
        receiver = new BluetoothSearchReceiver();
        receiver.register(getActivity());
        viewAdapter = new BluetoothDeviceAdapterListAdapter(getActivity());
        receiver.setBluetoothSearchCallback(viewAdapter);
        listView.setAdapter(viewAdapter);
        broadcaster = new BluetoothSearchBroadcaster(getActivity());
        bluetoothAdapter = ((BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        bluetoothAdapter.startLeScan(broadcaster);

    }

    @Override
    public void onPause() {
        stopSearch();
        super.onPause();
    }

    private void stopSearch() {
        getActivity().setProgressBarIndeterminateVisibility(false);
        receiver.unregister();
        bluetoothAdapter.stopLeScan(broadcaster);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        stopSearch();
        BluetoothDeviceAdapter deviceAdapter = (BluetoothDeviceAdapter) viewAdapter.getItem(position);
        Bundle params = IntentAndBundleUtil.saveBluetoothDeviceAdapter(new Bundle(), deviceAdapter);
        for (long uuid : deviceAdapter.get16BitUUIDs()) {
            if (uuid == HeartRateService.get16BitUUID()) {
                Intent intent = new Intent(getActivity(), HeartrateActivity.class);
                intent.putExtra(HeartrateActivity.BUNDLE_HEARTRATE,params);
                startActivity(intent);
            }
            if(uuid == BloodPressureService.get16BitUUID()){
                Intent intent = new Intent(getActivity(), BloodpressureActivity.class);
                intent.putExtra(BloodpressureActivity.BUNDLE_BLOODPRESSURE,params);
                startActivity(intent);
            }
            if(uuid == RunningSpeedAndCadenceService.get16BitUUID()){
                Intent intent = new Intent(getActivity(), RunningSpeedActivity.class);
                intent.putExtra(RunningSpeedActivity.BUNDLE_RUNNINGSPEED,params);
                startActivity(intent);
            }
        }
    }
}
