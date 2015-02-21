package windroids.semifinal.ui.keyboard;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import windroids.semifinal.R;
import windroids.semifinal.logic.pattern.KeyEvent;
import windroids.semifinal.logic.pattern.Pattern;
import windroids.semifinal.util.KeyCode;
import windroids.semifinal.util.KeyCodeParser;

public class Keyboard extends Fragment {

	private static final String TAG = Keyboard.class.getSimpleName();

	private EventListener eventListener = new EventListener() {
		@Override
		public void onTextInput(String character) {

		}

		@Override
		public void onBackspace() {

		}

		@Override
		public void onSubmit(Pattern pattern) {
			List<KeyEvent> keyEvents = pattern.getEvents();
			for (KeyEvent event : keyEvents) {
				Log.d(getClass().getSimpleName(), event.toString());
			}
		}
	}; // TODO delete

	private List<Button> normalButtons = new ArrayList<>(40);
	private Button shift;
	private Button backspace;
	private Button change;
	private Button enter;

	private boolean isShift;

	private List<KeyEvent> events = new ArrayList<>(20); // TODO: mekkora legyen?
	private KeyEvent downEventCache;
	private KeyEvent upEventCache;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.keyboard, container, false);
		findButtonsOnLayout(layout);
		addTagsForSpecialCharacters();
		addListenersToButtons();
		// TODO
		return layout;
	}

	private void findButtonsOnLayout(View layout) {
		normalButtons.add((Button) layout.findViewById(R.id.btn_1));
		normalButtons.add((Button) layout.findViewById(R.id.btn_2));
		normalButtons.add((Button) layout.findViewById(R.id.btn_3));
		normalButtons.add((Button) layout.findViewById(R.id.btn_4));
		normalButtons.add((Button) layout.findViewById(R.id.btn_5));
		normalButtons.add((Button) layout.findViewById(R.id.btn_6));
		normalButtons.add((Button) layout.findViewById(R.id.btn_7));
		normalButtons.add((Button) layout.findViewById(R.id.btn_8));
		normalButtons.add((Button) layout.findViewById(R.id.btn_9));
		normalButtons.add((Button) layout.findViewById(R.id.btn_0));
		normalButtons.add((Button) layout.findViewById(R.id.btn_q));
		normalButtons.add((Button) layout.findViewById(R.id.btn_w));
		normalButtons.add((Button) layout.findViewById(R.id.btn_e));
		normalButtons.add((Button) layout.findViewById(R.id.btn_r));
		normalButtons.add((Button) layout.findViewById(R.id.btn_t));
		normalButtons.add((Button) layout.findViewById(R.id.btn_z));
		normalButtons.add((Button) layout.findViewById(R.id.btn_u));
		normalButtons.add((Button) layout.findViewById(R.id.btn_i));
		normalButtons.add((Button) layout.findViewById(R.id.btn_o));
		normalButtons.add((Button) layout.findViewById(R.id.btn_p));
		normalButtons.add((Button) layout.findViewById(R.id.btn_a));
		normalButtons.add((Button) layout.findViewById(R.id.btn_s));
		normalButtons.add((Button) layout.findViewById(R.id.btn_d));
		normalButtons.add((Button) layout.findViewById(R.id.btn_f));
		normalButtons.add((Button) layout.findViewById(R.id.btn_g));
		normalButtons.add((Button) layout.findViewById(R.id.btn_h));
		normalButtons.add((Button) layout.findViewById(R.id.btn_j));
		normalButtons.add((Button) layout.findViewById(R.id.btn_k));
		normalButtons.add((Button) layout.findViewById(R.id.btn_l));
		normalButtons.add((Button) layout.findViewById(R.id.btn_y));
		normalButtons.add((Button) layout.findViewById(R.id.btn_x));
		normalButtons.add((Button) layout.findViewById(R.id.btn_c));
		normalButtons.add((Button) layout.findViewById(R.id.btn_v));
		normalButtons.add((Button) layout.findViewById(R.id.btn_b));
		normalButtons.add((Button) layout.findViewById(R.id.btn_n));
		normalButtons.add((Button) layout.findViewById(R.id.btn_m));
		normalButtons.add((Button) layout.findViewById(R.id.btn_comma));
		normalButtons.add((Button) layout.findViewById(R.id.btn_space));
		normalButtons.add((Button) layout.findViewById(R.id.btn_dot));
		shift = (Button) layout.findViewById(R.id.btn_shift);
		backspace = (Button) layout.findViewById(R.id.btn_backspace);
		change = (Button) layout.findViewById(R.id.btn_change);
		enter = (Button) layout.findViewById(R.id.btn_enter);
	}

	private void addListenersToButtons() {
		//
		//	Set View.OnClickListener
		//
		for (Button normalButton : normalButtons) {
			normalButton.setOnClickListener(onNormalButtonClickListener);
		}
		shift.setOnClickListener(onShiftClickListener);
		backspace.setOnClickListener(onBackspaceClickListener);
		enter.setOnClickListener(onEnterClickListener);

		//
		//	Set View.OnTouchListener
		//
		for (Button normalButton : normalButtons) {
			normalButton.setOnTouchListener(onTouchListener);
		}
		shift.setOnTouchListener(onTouchListener);
		backspace.setOnTouchListener(onTouchListener);
		change.setOnTouchListener(onTouchListener);
		enter.setOnTouchListener(onTouchListener);
	}

	private void addTagsForSpecialCharacters() {
		shift.setTag("shift");
		backspace.setTag("backspace");
		change.setTag("change");
		enter.setTag("enter");
	}

	private View.OnClickListener onNormalButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			events.add(downEventCache);
			events.add(upEventCache);
			updateCaseIfNecessary();
			Button button = (Button) view;
			eventListener.onTextInput(button.getText().toString());
		}
	};

	private View.OnClickListener onShiftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (view != null) {
				events.add(downEventCache);
				events.add(upEventCache);
				isShift = !isShift;
			}
			if (isShift) {
				for (Button normalButton : normalButtons) {
					normalButton.setText(normalButton.getText().toString().toUpperCase());
				}
			} else {
				for (Button normalButton : normalButtons) {
					normalButton.setText(normalButton.getText().toString().toLowerCase());
				}
			}
		}
	};

	private View.OnClickListener onBackspaceClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			events.add(downEventCache);
			events.add(upEventCache);
			updateCaseIfNecessary();
			eventListener.onBackspace();
		}
	};

	private View.OnClickListener onEnterClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			events.add(downEventCache);
			events.add(upEventCache);
			updateCaseIfNecessary();
			eventListener.onSubmit(new Pattern(events));
			events = new ArrayList<>(20);
		}
	};

	private void updateCaseIfNecessary() {
		if (isShift) {
			isShift = false;
			onShiftClickListener.onClick(null);
		}
	}

	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TYPE
			KeyEvent.Type type;
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					type = KeyEvent.Type.DOWN;
					Log.d(TAG, "Type: KeyEvent.Type.DOWN");
					break;
				case MotionEvent.ACTION_UP:
					type = KeyEvent.Type.UP;
					Log.d(TAG, "Type: KeyEvent.Type.UP");
					break;
				default:
					Log.d(TAG, "Another type: " + event.getAction());
					return false;
			}

			// TIME
			long time = new Date().getTime();
			Log.d(TAG, "Time: " + time);

			// CODE 
			// TODO a shift-es logikával baj van
			Button button = (Button) view;
			CharSequence rawValue = button.getText();
			if (rawValue == null || rawValue.length() == 0) {
				rawValue = button.getTag().toString();
			}
			KeyCode keyCode = KeyCodeParser.parse(rawValue.toString().toLowerCase());
			Log.d(TAG, "KeyCode: " + keyCode);

			// POSX
			double posX = event.getX() / view.getWidth();
			Log.d(TAG, "PosX: " + posX);

			// POSY
			double posY = event.getY() / view.getHeight();
			Log.d(TAG, "PosY: " + posY);

			switch (type) {
				case DOWN:
					downEventCache = new KeyEvent(type, time, keyCode, posX, posY);
					break;
				case UP:
					upEventCache = new KeyEvent(type, time, keyCode, posX, posY);
					break;
			}

			return false;
		}
	};

	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	public interface EventListener {
		void onTextInput(String character);

		void onBackspace();

		void onSubmit(Pattern pattern);
	}
}
