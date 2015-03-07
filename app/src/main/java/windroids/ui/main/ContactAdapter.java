package windroids.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import windroids.R;
import windroids.entities.User;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

	private List<User> contacts;
	private OnItemClickListener clickListener;

	public ContactAdapter(List<User> contacts, OnItemClickListener clickListener) {
		this.contacts = contacts;
		this.clickListener = clickListener;
	}

	@Override
	public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ImageView profileImage =
				(ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_profile_image, parent, false);
		return new ViewHolder(profileImage);
	}

	@Override
	public void onBindViewHolder(ContactAdapter.ViewHolder holder, final int position) {
		holder.profileImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clickListener.onClick(position);
			}
		});
		User user = contacts.get(position);
		String profileImageEncoded = user.getProfileImage();
		if (profileImageEncoded != null) {
			byte[] decodedString = Base64.decode(profileImageEncoded, Base64.DEFAULT);
			Bitmap profileImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			holder.profileImage.setImageBitmap(profileImage);
		}
	}

	@Override
	public int getItemCount() {
		return contacts.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private ImageView profileImage;

		public ViewHolder(ImageView profileImage) {
			super(profileImage);
		}

	}

	public interface OnItemClickListener {
		void onClick(int position);
	}
}
