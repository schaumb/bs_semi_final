package windroids.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import windroids.R;
import windroids.entities.User;

public class MainActivity extends FragmentActivity {

	public static String EXTRA_USER = "EXTRA_USER";

	private User user;

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
			switch (pos) {
				case 0:
					return new NewsFragment();
				case 1:
					return new DataFragment();
				case 2:
					return new SensorFragment();
				case 3:
					return new ProfileFragment();
				case 4:
					Fragment contactFragment = new ContactFragment();
					Bundle args = new Bundle();
					args.putSerializable(EXTRA_USER, user);
					contactFragment.setArguments(args);
					return contactFragment;
				default:
					return null;
			}
		}

		@Override
		public int getCount() {
			return 5;
		}
	}
}

