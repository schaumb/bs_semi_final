package windroids.sensors.runningspeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import windroids.sensors.heartrate.HeartrateReceiver;

import static windroids.sensors.constants.General.BLUETOOTH_ACTION_HEARTRATE;
import static windroids.sensors.util.IntentAndBundleUtil.isRunningSpeedIntent;
import static windroids.sensors.util.IntentAndBundleUtil.loadConnectionState;
import static windroids.sensors.util.IntentAndBundleUtil.loadRunningSpeed;
import static windroids.sensors.util.IntentAndBundleUtil.loadRunningSpeed2;
import static windroids.sensors.util.IntentAndBundleUtil.loadRunningSpeed3;
import static windroids.sensors.util.IntentAndBundleUtil.loadRunningSpeed4;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
public class RunningSpeedBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private boolean registered;
    private RunningSpeedReceiver runningspeedReceiver;

    /**
     * Registers this broadcast receiver on an Android context.
     * @param context Android context.
     */
    public void register(Context context) {
        if (!registered) {
            context.registerReceiver(this, creatRunningSpeedIntentFilter());
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

    public void setRunningSpeedReceiver(RunningSpeedReceiver heartrateReceiver) {
        this.runningspeedReceiver = heartrateReceiver;
    }

    private IntentFilter creatRunningSpeedIntentFilter() {
        IntentFilter runningspeedIntentFilter = new IntentFilter();
        runningspeedIntentFilter.addAction(BLUETOOTH_ACTION_HEARTRATE);
        return runningspeedIntentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isRunningSpeedIntent(intent) && runningspeedReceiver != null) {
            runningspeedReceiver.onRunningSpeedReceived(loadConnectionState(intent), loadRunningSpeed(intent), loadRunningSpeed2(intent), loadRunningSpeed3(intent), loadRunningSpeed4(intent));
        }
    }
}
