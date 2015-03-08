package windroids.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import windroids.R;
import windroids.entities.data.BloodPressure;
import windroids.sensors.bloodpressure.BloodpressureBroadcastReceiver;
import windroids.sensors.bloodpressure.BloodpressureCommunicationContext;
import windroids.sensors.bloodpressure.BloodpressureReceiver;
import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.util.IntentAndBundleUtil;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;

public class BloodpressureActivity extends Activity implements BloodpressureReceiver {

    public final static String BUNDLE_BLOODPRESSURE = "BUNDLE_BLOODPRESSURE";

    private ImageView heartImage;
    private TextView heartrateView;
    private BloodpressureCommunicationContext heartrateContext;
    private BloodpressureBroadcastReceiver receiver;
    private Bundle arguments;
    private BloodPressure dataCollect = new BloodPressure(null, null, null);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);

        arguments = getIntent().getBundleExtra(BUNDLE_BLOODPRESSURE);

        heartrateView = (TextView) findViewById(R.id.heartrateFragment_heartrateValue);
        heartImage = (ImageView) findViewById(R.id.heartrateFragment_heartImage);
    }

    @Override
    public void onResume() {
        startBloodPressure();
        super.onResume();
    }

    private void startBloodPressure() {
        receiver = new BloodpressureBroadcastReceiver();
        receiver.register(this);
        receiver.setBloodpressureReceiver(this);
        BluetoothDeviceAdapter deviceAdapter = IntentAndBundleUtil.loadBluetoothDeviceAdapter(arguments);
        heartrateContext = new BloodpressureCommunicationContext(this, deviceAdapter.getDevice());
        heartrateContext.start();
    }

    @Override
    public void onPause() {
        stopHeartrate();
        receiver.unregister();
        super.onPause();
    }

    private void stopHeartrate() {
        heartrateContext.stop(false);
    }

    @Override
    public void onBloodpressureReceived(int connectionState, float sys, float dia, float map) {
        switch (connectionState) {
            case STATE_CONNECTING:
            case STATE_DISCONNECTING:
                setProgressBarIndeterminateVisibility(true);
                break;
            case STATE_CONNECTED:
                setProgressBarIndeterminateVisibility(false);
                break;
            case STATE_DISCONNECTED:
                setProgressBarIndeterminateVisibility(false);
                break;
        }
        dataCollect.setSystolic(sys);
        dataCollect.setDiastolic(dia);
        dataCollect.setPulse(map);
        heartrateView.setText(Float.toString(sys) + " - " + Float.toString(dia) + "  - " + Float.toString(map));
    }

    public BloodPressure getData(){
        return dataCollect;
    }

}
