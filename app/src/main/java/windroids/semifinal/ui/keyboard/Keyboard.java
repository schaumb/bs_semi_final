package windroids.semifinal.ui.keyboard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import windroids.semifinal.R;

public class Keyboard extends Fragment {

	private EventListener eventListener;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.keyboard, container, false);

		return layout;
	}

	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	public interface EventListener {
		void onText(String character);

		void onBackspace();

		void onSubmit();
	}
}
