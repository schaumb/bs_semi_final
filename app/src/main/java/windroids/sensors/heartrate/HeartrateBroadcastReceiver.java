package windroids.sensors.heartrate;

import static windroids.sensors.constants.General.BLUETOOTH_ACTION_HEARTRATE;
import static windroids.sensors.util.IntentAndBundleUtil.isHeartRateIntent;
import static windroids.sensors.util.IntentAndBundleUtil.loadConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.loadHeartRate;
import static windroids.sensors.util.IntentAndBundleUtil.loadHeartRate2;
import static windroids.sensors.util.IntentAndBundleUtil.loadHeartRate3;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Broadcast receiver for heart rate intents.
 * @author Balazs_Csernai
 */
public class HeartrateBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private boolean registered;
    private HeartrateReceiver heartrateReceiver;

    /**
     * Registers this broadcast receiver on an Android context.
     * @param context Android context.
     */
    public void register(Context context) {
        if (!registered) {
            context.registerReceiver(this, createHeartrateIntentFilter());
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
     * Sets the heart rate data receiver. 
     * @param heartrateReceiver Heart rate data receiver
     */
    public void setHeartrateReceiver(HeartrateReceiver heartrateReceiver) {
        this.heartrateReceiver = heartrateReceiver;
    }

    private IntentFilter createHeartrateIntentFilter() {
        IntentFilter heartrateFilter = new IntentFilter();
        heartrateFilter.addAction(BLUETOOTH_ACTION_HEARTRATE);
        return heartrateFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isHeartRateIntent(intent) && heartrateReceiver != null) {
            heartrateReceiver.onHeartrateReceived(loadConnectionState(intent), loadHeartRate(intent), loadHeartRate2(intent), loadHeartRate3(intent));
        }
    }
}
