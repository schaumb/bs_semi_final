package windroids.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import windroids.R;
import windroids.entities.User;

public class MainActivity extends FragmentActivity {

	public static String EXTRA_USER = "EXTRA_USER";

	private User user;
	private Fragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (User) getIntent().getSerializableExtra(EXTRA_USER);

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			Bundle args = new Bundle();
			args.putSerializable(EXTRA_USER, user);
			switch (pos) {
				case 0:
					fragment = new MessagesFragment();
					fragment.setArguments(args);
					return fragment;
				case 1:
					fragment = new ContactFragment();
					fragment.setArguments(args);
					return fragment;
				case 2:
					fragment = new ProfileFragment();
					fragment.setArguments(args);
					return fragment;
				case 3:
					return new SensorFragment();
				case 4:
					return new DataFragment();
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return 5;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
			Toast.makeText(this, "Image successfully saved", Toast.LENGTH_SHORT).show();
			((ProfileFragment) fragment).setImageBitmap(imageBitmap);
		} catch (Exception e) {
			Log.i(ProfileFragment.class.getSimpleName(), "Nem sikerült betölteni a képet.");
		}
	}
}

