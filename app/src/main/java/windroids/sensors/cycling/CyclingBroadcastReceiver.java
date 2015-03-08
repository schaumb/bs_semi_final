package windroids.sensors.cycling;

import static windroids.sensors.constants.General.BLUETOOTH_ACTION_CYCLING;
import static windroids.sensors.util.IntentAndBundleUtil.isCyclingIntent;
import static windroids.sensors.util.IntentAndBundleUtil.loadConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.loadCrankEventTime;
import static windroids.sensors.util.IntentAndBundleUtil.loadCrankRevolution;
import static windroids.sensors.util.IntentAndBundleUtil.loadWheelEventTime;
import static windroids.sensors.util.IntentAndBundleUtil.loadWheelRevolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Broadcast receiver for cycling intents.
 * @author Balazs_Csernai
 */
public class CyclingBroadcastReceiver extends BroadcastReceiver {

	private Context context;
	private boolean registered;
	private CyclingReceiver cyclingReceiver;

	/**
	 * Registers this broadcast receiver on an Android context.
	 * @param context Android context
	 */
	public void register(Context context) {
		if (!registered) {
			context.registerReceiver(this, createCyclingIntentFilter());
			this.context = context;
			registered = true;
		}
	}

	/**
	 * Un-registers this broadcast receiver on the Android context used for registration.
	 */
	public void unregister() {
		if (registered) {
			context.unregisterReceiver(this);
			registered = false;
		}
	}

	/**
	 * Sets the cycling data receiver.
	 * @param cyclingReceiver Cycling data receiver
	 */
	public void setCyclingReceiver(CyclingReceiver cyclingReceiver) {
		this.cyclingReceiver = cyclingReceiver;
	}

	private IntentFilter createCyclingIntentFilter() {
		IntentFilter cyclingIntentFilter = new IntentFilter();
		cyclingIntentFilter.addAction(BLUETOOTH_ACTION_CYCLING);
		return cyclingIntentFilter;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (isCyclingIntent(intent) && cyclingReceiver != null) {
			cyclingReceiver.onCyclingReceived(loadConnectionState(intent), loadWheelRevolution(intent), loadWheelEventTime(intent), loadCrankRevolution(intent), loadCrankEventTime(intent));
		}
	}

}
