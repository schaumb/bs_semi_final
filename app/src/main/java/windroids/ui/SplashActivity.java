package windroids.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import windroids.R;
import windroids.config.Constants;

public class SplashActivity extends Activity {

	private long waitingTime = getResources().getInteger(R.integer.splash_screen_time_ms);

	private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		timer = new CountDownTimer(waitingTime, waitingTime) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				//store if the user regisered or not
                SharedPreferences sharedpreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
                sharedpreferences.getBoolean(Constants.USER_REGISTERED_STATE, false);

				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
			}
		};
    }

	@Override
	protected void onResume() {
		super.onResume();
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		timer.cancel();
	}
}
