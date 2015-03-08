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
import windroids.entities.data.RunningSpeed;
import windroids.sensors.cycling.CyclingBroadcastReceiver;
import windroids.sensors.cycling.CyclingCommunicationContext;
import windroids.sensors.cycling.CyclingReceiver;
import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.util.IntentAndBundleUtil;

public class CyclingActivity extends Activity implements CyclingReceiver {

    public final static String BUNDLE_CYCLING = "BUNDLE_CYCLING";

    private ImageView heartImage;
    private TextView cyclingView;
    private CyclingCommunicationContext cyclingContext;
    private CyclingBroadcastReceiver receiver;
    private Bundle arguments;
    private RunningSpeed dataCollect = new RunningSpeed(null);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartrate);

        arguments = getIntent().getBundleExtra(BUNDLE_CYCLING);

        cyclingView = (TextView) findViewById(R.id.heartrateFragment_heartrateValue);
        heartImage = (ImageView) findViewById(R.id.heartrateFragment_heartImage);
    }

    @Override
    public void onResume() {
        startRunningSpeed();
        super.onResume();
    }

    private void startRunningSpeed() {
        receiver = new CyclingBroadcastReceiver();
        receiver.register(this);
        receiver.setCyclingReceiver(this);
        BluetoothDeviceAdapter deviceAdapter = IntentAndBundleUtil.loadBluetoothDeviceAdapter(arguments);
        cyclingContext = new CyclingCommunicationContext(this, deviceAdapter.getDevice());
        cyclingContext.start();
    }

    @Override
    public void onPause() {
        stopRunningSpeed();
        receiver.unregister();
        super.onPause();
    }

    private void stopRunningSpeed() {
        cyclingContext.stop(false);
    }

    @Override
    public void onCyclingReceived(int connectionState, int wheelRevolution, int wheelEventTime, int crankRevolution, int crankEventTime) {
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
        dataCollect.set(wheelRevolution, wheelEventTime, crankRevolution, crankEventTime);
        cyclingView.setText(Integer.toString(wheelRevolution) + " - " + Integer.toString(wheelEventTime) + " - "
                + Integer.toString(crankRevolution)+ "  - " + Integer.toString(crankEventTime));
    }

    public RunningSpeed getData(){
        return dataCollect;
    }
}
