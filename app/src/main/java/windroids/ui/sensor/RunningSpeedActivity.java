package windroids.ui.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import windroids.R;
import windroids.entities.data.RunningSpeed;
import windroids.sensors.runningspeed.RunningSpeedBroadcastReceiver;
import windroids.sensors.runningspeed.RunningSpeedCommunicationContext;
import windroids.sensors.runningspeed.RunningSpeedReceiver;
import windroids.sensors.search.BluetoothDeviceAdapter;
import windroids.sensors.util.IntentAndBundleUtil;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_CONNECTING;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTED;
import static android.bluetooth.BluetoothProfile.STATE_DISCONNECTING;

public class RunningSpeedActivity extends Activity implements RunningSpeedReceiver {

	public final static String BUNDLE_RUNNINGSPEED = "BUNDLE_RUNNINGSPEED";

	private TextView runningspeedView;
	private RunningSpeedCommunicationContext runningspeedContext;
	private RunningSpeedBroadcastReceiver receiver;
	private Bundle arguments;
	private RunningSpeed dataCollect = new RunningSpeed(null);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heartrate);

		arguments = getIntent().getBundleExtra(BUNDLE_RUNNINGSPEED);

		runningspeedView = (TextView) findViewById(R.id.heartrateFragment_heartrateValue);
	}

	@Override
	public void onResume() {
		startRunningSpeed();
		super.onResume();
	}

	private void startRunningSpeed() {
		receiver = new RunningSpeedBroadcastReceiver();
		receiver.register(this);
		receiver.setRunningSpeedReceiver(this);
		BluetoothDeviceAdapter deviceAdapter = IntentAndBundleUtil.loadBluetoothDeviceAdapter(arguments);
		runningspeedContext = new RunningSpeedCommunicationContext(this, deviceAdapter.getDevice());
		runningspeedContext.start();
	}

	@Override
	public void onPause() {
		stopRunningSpeed();
		receiver.unregister();
		super.onPause();
	}

	private void stopRunningSpeed() {
		runningspeedContext.stop(false);
	}

	@Override
	public void onRunningSpeedReceived(int connectionState, int speed, int cadence, int stride, int total) {
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
		dataCollect.set(speed, cadence, stride, total);
		runningspeedView.setText(
				Integer.toString(speed) + " - " + Integer.toString(cadence) + "  - " + Integer.toString(stride) +
						"  - " + Integer.toString(total));
	}

	public RunningSpeed getData() {
		return dataCollect;
	}
}
