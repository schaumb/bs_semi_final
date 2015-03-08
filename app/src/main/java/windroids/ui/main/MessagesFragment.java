package windroids.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import windroids.R;
import windroids.entities.User;
import windroids.storage.MessageStorage;

public class MessagesFragment extends Fragment {

	private RecyclerView messages;
	private ImageView image;
	private TextView name;
	private TextView title;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_messages, container, false);
		messages = (RecyclerView) layout.findViewById(R.id.messages);
		image = (ImageView) layout.findViewById(R.id.image);
		name = (TextView) layout.findViewById(R.id.name);
		title = (TextView) layout.findViewById(R.id.title);

		User user = (User) getArguments().getSerializable(MainActivity.EXTRA_USER);

		String profileImageEncoded = user.getProfileImage();
		if (profileImageEncoded != null) {
			byte[] decodedString = Base64.decode(profileImageEncoded, Base64.DEFAULT);
			Bitmap profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			image.setImageBitmap(profileImage);
		}
		name.setText(user.getFullName());
		StringBuilder stringBuilder = new StringBuilder();
		if (user.getIsCoach()) {
			stringBuilder.append("Coach");
		}
		if (user.getIsDoctor()) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(" and ");
			}
			stringBuilder.append("Doctor");
		}
		if (stringBuilder.length() > 0) {
			title.setVisibility(View.VISIBLE);
			title.setText(stringBuilder.toString());
		} else {
			title.setVisibility(View.GONE);
		}

		messages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
		try {
			messages.setAdapter(new MessagesAdapter(MessageStorage.getMessagesToMe(user.getUserName())));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return layout;
	}
}
