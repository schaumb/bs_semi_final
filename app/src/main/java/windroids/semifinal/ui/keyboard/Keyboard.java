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
import windroids.semifinal.logic.pattern.KeyCode;
import windroids.semifinal.logic.pattern.KeyEvent;
import windroids.semifinal.logic.pattern.Pattern;

public class Keyboard extends Fragment {

	private static final String TAG = Keyboard.class.getSimpleName();

	private EventListener eventListener;

	private List<Button> normalButtons = new ArrayList<>(40);
	private Button shift;
	private Button backspace;
	private Button change;
	private Button enter;

	private boolean isShift;
	private boolean isChange;

	private List<KeyEvent> events = new ArrayList<>(30);
	private KeyEvent downEventCache;
	private KeyEvent upEventCache;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.keyboard, container, false);
		findButtonsOnLayout(layout);
		addListenersToButtons();
		return layout;
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
		change.setOnClickListener(onChangeClickListener);

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

	private View.OnClickListener onNormalButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			events.add(downEventCache);
			events.add(upEventCache);
			Button button = (Button) view;
			eventListener.onTextInput(button.getText().toString());
			checkShift();
			checkChange();
		}
	};

	private View.OnClickListener onShiftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			checkChange();
			if (view != null) {
				events.add(downEventCache);
				events.add(upEventCache);
				isShift = true;
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
			eventListener.onBackspace();
			checkShift();
			checkChange();
		}
	};

	private View.OnClickListener onEnterClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			events.add(downEventCache);
			events.add(upEventCache);
			checkShift();
			checkChange();
			eventListener.onSubmit(new Pattern(events));
			events = new ArrayList<>(20);
		}
	};

	private View.OnClickListener onChangeClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			checkShift();
			if (view != null) {
				events.add(downEventCache);
				events.add(upEventCache);
				isChange = true;
			}
			if (isChange) {
				for (Button normalButton : normalButtons) {
					CharSequence rawValue = normalButton.getText();
					if (rawValue.equals(" ") || rawValue.equals(",") || (rawValue.equals("."))) {
						continue;
					}
					Object rawTag = normalButton.getTag();
					if (rawTag == null) {
						continue;
					}
					String tag = rawTag.toString();
					String specialKey = String.valueOf(tag.charAt(tag.length() - 1));
					if (specialKey.equals("0")) {
						continue;
					}
					normalButton.setText(specialKey);
				}
			} else {
				for (Button normalButton : normalButtons) {
					CharSequence rawValue = normalButton.getText();
					if (rawValue.equals(" ") || rawValue.equals(",") || (rawValue.equals("."))) {
						continue;
					}
					Object rawTag = normalButton.getTag();
					if (rawTag == null){
						continue;
					}
					String tag = rawTag.toString();
					String letter = String.valueOf(tag.charAt(tag.length() - 2));
					if (letter.equals("0")) {
						continue;
					}
					normalButton.setText(letter);
				}
			}
		}
	};

	private void checkShift() {
		if (isShift) {
			isShift = false;
			onShiftClickListener.onClick(null);
		}
	}

	private void checkChange() {
		if (isChange) {
			isChange = false;
			onChangeClickListener.onClick(null);
		}
	}

	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
		
		private double lastValidPosX;
		private double lastValidPosY;
		
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
				case MotionEvent.ACTION_MOVE:
					double posX = event.getX();
					double posY = event.getY();
					if (posX >= 0 && posX <= view.getWidth()) lastValidPosX = posX;
					if (posY >= 0 && posY <= view.getHeight()) lastValidPosY = posY;
				default:
					Log.d(TAG, "Another type: " + event.getAction());
					return false;
			}

			// TIME
			long time = new Date().getTime();
			Log.d(TAG, "Time: " + time);

			// CODE
			KeyCode keyCode;
			Object rawTag = view.getTag();
			String tag = rawTag.toString();
			String reference = String.valueOf(tag.charAt(tag.length() - 2));
			String buttonText = ((Button) view).getText().toString().toLowerCase();
			if (buttonText.equals(reference)) {
				keyCode = KeyCode.getKeyCode(reference.toUpperCase());
			} else {
				keyCode = KeyCode.getKeyCode(tag.substring(0, tag.length() - 2));
			}
			Log.d(TAG, "KeyCode: " + keyCode);
			System.out.println(keyCode.getName() + " <- name + char:" + (char)keyCode.impl_getCode());

			// POSX
			double posX = event.getX();
			if (posX <= 0 || posX >= view.getWidth()) posX = lastValidPosX;
			posX /= view.getWidth();
			Log.d(TAG, "PosX: " + posX);

			// POSY
			double posY = event.getY();
			if (posY <= 0 || posY >= view.getHeight()) posY = lastValidPosY;
			posY /= view.getHeight();
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
}
