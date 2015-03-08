package windroids.sensors.bloodpressure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import windroids.sensors.heartrate.HeartrateReceiver;

import static windroids.sensors.constants.General.BLUETOOTH_ACTION_HEARTRATE;
import static windroids.sensors.util.IntentAndBundleUtil.isBloodpressureIntent;
import static windroids.sensors.util.IntentAndBundleUtil.loadConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.loadBloodpressure;
import static windroids.sensors.util.IntentAndBundleUtil.loadBloodpressure2;
import static windroids.sensors.util.IntentAndBundleUtil.loadBloodpressure3;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
public class BloodpressureBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private boolean registered;
    private BloodpressureReceiver bloodpressureReceiver;

    /**
     * Registers this broadcast receiver on an Android context.
     * @param context Android context.
     */
    public void register(Context context) {
        if (!registered) {
            context.registerReceiver(this, creatBloodpressureIntentFilter());
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

    public void setBloodpressureReceiver(BloodpressureReceiver heartrateReceiver) {
        this.bloodpressureReceiver = heartrateReceiver;
    }

    private IntentFilter creatBloodpressureIntentFilter() {
        IntentFilter bloodpressureIntentFilter = new IntentFilter();
        bloodpressureIntentFilter.addAction(BLUETOOTH_ACTION_HEARTRATE);
        return bloodpressureIntentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isBloodpressureIntent(intent) && bloodpressureReceiver != null) {
            bloodpressureReceiver.onBloodpressureReceived(loadConnectionState(intent), loadBloodpressure(intent), loadBloodpressure2(intent), loadBloodpressure3(intent));
        }
    }
}
