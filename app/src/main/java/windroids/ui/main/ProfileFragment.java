package windroids.ui.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import windroids.R;
import windroids.entities.User;
import windroids.entities.data.CommonData;
import windroids.entities.data.Data;
import windroids.storage.UserStorage;

public class ProfileFragment extends Fragment {

	private Bitmap imageBitmap;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		final View layout = inflater.inflate(R.layout.fragment_profile, container, false);

		final TextView full_nameView = ((EditText) layout.findViewById(R.id.full_name));
		final TextView passwordView = ((EditText) layout.findViewById(R.id.password));
		final TextView emailView = ((EditText) layout.findViewById(R.id.email));
		final TextView addressView = ((EditText) layout.findViewById(R.id.address));
		final TextView weightView = ((EditText) layout.findViewById(R.id.weight));
		final TextView heightView = ((EditText) layout.findViewById(R.id.height));
		final TextView blood_typeView = ((EditText) layout.findViewById(R.id.blood_type));
		final TextView chronic_diseaseView = ((EditText) layout.findViewById(R.id.chronic_disease));
		final TextView allergyView = ((EditText) layout.findViewById(R.id.allergy));
		final TextView sportsView = ((EditText) layout.findViewById(R.id.sports));
		final TextView eating_habitsView = ((EditText) layout.findViewById(R.id.eating_habits));

		final User user = (User) getArguments().getSerializable(MainActivity.EXTRA_USER);

		List<Data> datas = user.getDatas().get(Data.Type.Common);
		if (datas != null) {
			for (Data data : datas) {
				CommonData commonData = (CommonData) data;
				String key = commonData.getName();
				String value = commonData.getDescription();
				switch (key) {
					case "weight":
						weightView.setText(value);
						break;
					case "height":
						heightView.setText(value);
						break;
					case "blood_type":
						blood_typeView.setText(value);
						break;
					case "chronic_disease":
						chronic_diseaseView.setText(value);
						break;
					case "allergy":
						allergyView.setText(value);
						break;
					case "sports":
						sportsView.setText(value);
						break;
					case "eating_habits":
						eating_habitsView.setText(value);
						break;
				}
			}
		}

		full_nameView.setText(user.getFullName());
		passwordView.setText(user.getPassword());
		emailView.setText(user.getEmail());
		addressView.setText(user.getCity());
		byte[] encodeByte = Base64.decode(user.getProfileImage(), Base64.DEFAULT);
		imageBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		boolean isDoctor = user.getIsDoctor();
		boolean isCoach = user.getIsCoach();

		final TextView doctor = (TextView) layout.findViewById(R.id.doctor);
		doctor.setTag(isDoctor);
		final TextView coach = (TextView) layout.findViewById(R.id.coach);
		coach.setTag(isCoach);

		if (isDoctor) {
			doctor.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_filled, 0, 0, 0);
			doctor.setTag(true);
		} else {
			doctor.setTag(false);
		}

		if (isCoach) {
			coach.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_filled, 0, 0, 0);
			coach.setTag(true);
		} else {
			coach.setTag(false);
		}

		layout.findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fullName = full_nameView.getText().toString();
				String password = passwordView.getText().toString();
				String email = emailView.getText().toString();
				if (imageBitmap == null) {
					imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_img);
				}
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
				byte[] byteArray = byteArrayOutputStream.toByteArray();
				String image = Base64.encodeToString(byteArray, Base64.DEFAULT);
				String address = addressView.getText().toString();
				boolean isDoctor = (Boolean) doctor.getTag();
				boolean isCoach = (Boolean) coach.getTag();
				user.setFullName(fullName);
				user.setPassword(password);
				user.setEmail(email);
				user.setProfileImage(image);
				user.setCity(address);
				user.setIsDoctor(isDoctor);
				user.setIsCoach(isCoach);
				String weight = weightView.getText().toString();
				String height = heightView.getText().toString();
				String blood_type = blood_typeView.getText().toString();
				String chronic_disease = chronic_diseaseView.getText().toString();
				String allergy = allergyView.getText().toString();
				String sports = sportsView.getText().toString();
				String eating_habits = eating_habitsView.getText().toString();
				user.addData(new CommonData("weight", weight));
				user.addData(new CommonData("height", height));
				user.addData(new CommonData("blood_type", blood_type));
				user.addData(new CommonData("chronic_disease", chronic_disease));
				user.addData(new CommonData("allergy", allergy));
				user.addData(new CommonData("sports", sports));
				user.addData(new CommonData("eating_habits", eating_habits));
				// TODO elmenteni a többi adatot is
				try {
					UserStorage.saveThisUserChanges(user);
				} catch (IOException | ClassNotFoundException e) {
					alert("Error while saving. Please try again!");
					e.printStackTrace();
				}
				alert("Changes seccesfully changed!");
			}
		});
		doctor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textView = (TextView) v;
				if ((Boolean) v.getTag()) {
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_empty, 0, 0, 0);
					doctor.setTag(false);
				} else {
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_filled, 0, 0, 0);
					doctor.setTag(true);
				}
			}
		});
		coach.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView textView = (TextView) v;
				if ((Boolean) v.getTag()) {
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_empty, 0, 0, 0);
					coach.setTag(false);
				} else {
					textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkbox_filled, 0, 0, 0);
					coach.setTag(true);
				}
			}
		});

		Button imageButton = (Button) layout.findViewById(R.id.image_button);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});

		return layout;
	}

	public void setImageBitmap(Bitmap imageBitmap) {
		this.imageBitmap = imageBitmap;
	}

	private void alert(String message) {
		new AlertDialog.Builder(getActivity())
				.setMessage(message)
				.show();
	}

}
