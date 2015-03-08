package windroids.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import windroids.R;
import windroids.entities.User;
import windroids.search.SearchUser;

public class ContactFragment extends Fragment implements ContactAdapter.OnItemClickListener {

	private ArrayList<User> contacts;
	private ArrayList<User> results;

	private RecyclerView contactsView;
	private RecyclerView resultsView;
	private View detailsView;
	private ImageView imageView;
	private TextView nameView;
	private TextView titleView;
	private TextView birthDateView;
	private TextView addressView;
	//private View cancelView;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_contact, container, false);
		contactsView = (RecyclerView) layout.findViewById(R.id.contacts);
		resultsView = (RecyclerView) layout.findViewById(R.id.results);
		detailsView = layout.findViewById(R.id.details);
		imageView = (ImageView) layout.findViewById(R.id.image);
		nameView = (TextView) layout.findViewById(R.id.name);
		titleView = (TextView) layout.findViewById(R.id.title);
		birthDateView = (TextView) layout.findViewById(R.id.birth_date);
		addressView = (TextView) layout.findViewById(R.id.address);
		//cancelView = layout.findViewById(R.id.cancel);

//		cancelView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				detailsView.setVisibility(View.GONE);
//				resultsView.setVisibility(View.GONE);
//			}
//		});

		User user = (User) getArguments().getSerializable(MainActivity.EXTRA_USER);
		try {
			contacts = user.getContacts();
		} catch (IOException | ClassNotFoundException e) {
			Log.e(ContactFragment.class.getSimpleName(), "A user.getContacts() exceptiont dobott.");
			e.printStackTrace();
		}

		contactsView.setHasFixedSize(true);
		contactsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
		contactsView.setAdapter(new ContactAdapter(contacts, this, ContactAdapter.Type.Contact));

		resultsView.setHasFixedSize(true);
		resultsView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

		detailsView.setVisibility(View.GONE);
		resultsView.setVisibility(View.GONE);

		final EditText searchView = (EditText) layout.findViewById(R.id.search_view);
		searchView.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					String searchText = searchView.getText().toString();
					try {
						detailsView.setVisibility(View.GONE);
						resultsView.setVisibility(View.VISIBLE);
						results = SearchUser.searchByName(searchText);
						resultsView.setAdapter(
								new ContactAdapter(results, ContactFragment.this, ContactAdapter.Type.Result));
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});

		return layout;
	}

	@Override
	public void onClick(int position, ContactAdapter.Type type) {
		resultsView.setVisibility(View.GONE);
		detailsView.setVisibility(View.VISIBLE);
		User user = null;
		switch (type) {
			case Contact:
				user = contacts.get(position);
				break;
			case Result:
				user = results.get(position);
				break;
		}
		byte[] decodedString = Base64.decode(user.getProfileImage(), Base64.DEFAULT);
		Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		imageView.setImageBitmap(decodedImage);
		nameView.setText(user.getFullName());
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
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(stringBuilder.toString());
		} else {
			titleView.setVisibility(View.GONE);
		}
		Date date = user.getBirthDate();
		if (date != null) {
			birthDateView.setText(date.toString());
		}
		addressView.setText(user.getCity());
	}
}
