package windroids.ui.main;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import windroids.R;
import windroids.sensors.heartrate.HeartrateBroadcastReceiver;
import windroids.sensors.heartrate.HeartrateCommunicationContext;
import windroids.sensors.heartrate.HeartrateReceiver;
import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.util.IntentAndBundleUtil;

public class HeartrateActivity extends Activity implements HeartrateReceiver {

    public final static String BUNDLE_HEARTRATE = "BUNDLE_HEARTRATE";

    private ImageView heartImage;
    private TextView heartrateView;
    private HeartrateCommunicationContext heartrateContext;
    private HeartrateBroadcastReceiver receiver;
    private Bundle arguments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arguments = getIntent().getBundleExtra(BUNDLE_HEARTRATE);

        heartrateView = (TextView) findViewById(R.id.heartrateFragment_heartrateValue);
        heartImage = (ImageView) findViewById(R.id.heartrateFragment_heartImage);
    }

    @Override
    public void onResume() {
        startHeartrate();
        super.onResume();
    }

    private void startHeartrate() {
        receiver = new HeartrateBroadcastReceiver();
        receiver.register(this);
        receiver.setHeartrateReceiver(this);
        BluetoothDeviceAdapter deviceAdapter = IntentAndBundleUtil.loadBluetoothDeviceAdapter(arguments);
        heartrateContext = new HeartrateCommunicationContext(this, deviceAdapter.getDevice());
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
    public void onHeartrateReceived(int connectionState, int heartrate) {
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
        heartrateView.setText(Integer.toString(heartrate) + " bpm");
    }
}
