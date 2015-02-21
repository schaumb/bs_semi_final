package windroids.semifinal.ui.keyboard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import windroids.semifinal.R;

public class Keyboard extends Fragment {

	private EventListener eventListener = new EventListener() {
		@Override
		public void onTextInput(String character) {

		}

		@Override
		public void onBackspace() {

		}

		@Override
		public void onSubmit() {

		}
	}; // TODO delete
	
	private List<Button> normalButtons = new ArrayList<>(39);
	private Button shift;
	private Button backspace;
	private Button change;
	private Button enter;
	
	private boolean isShift;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.keyboard, container, false);
		findButtonsOnLayout(layout);
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

	// TODO
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
		//	TODO
		//
	}
	
	private View.OnClickListener onNormalButtonClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			updateCaseIfNecessary();
			Button button = (Button) view;
			eventListener.onTextInput(button.getText().toString());
		}
	};

	private View.OnClickListener onShiftClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			if (view != null) {
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
			updateCaseIfNecessary();
			eventListener.onBackspace();
		}
	};
	
	private View.OnClickListener onEnterClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			updateCaseIfNecessary();
			eventListener.onSubmit();
		}
	};
	
	private void updateCaseIfNecessary() {
		if (isShift) {
			isShift = false;
			onShiftClickListener.onClick(null);
		}
	}

	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}

	public interface EventListener {
		void onTextInput(String character);

		void onBackspace();

		void onSubmit();
	}
}
