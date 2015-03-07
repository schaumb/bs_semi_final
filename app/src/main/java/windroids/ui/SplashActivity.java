package windroids.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import windroids.R;

public class SplashActivity extends Activity {

	private long waitingTime = 2000; // TODO config

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
				// TODO shared pref alapján
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
