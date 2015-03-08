package windroids.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import windroids.R;
import windroids.entities.Message;
import windroids.entities.User;
import windroids.storage.UserStorage;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

	private List<Message> messages;

	public MessagesAdapter(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_message, parent, false);
		layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				View message = v.findViewById(R.id.message);
				if (message.getVisibility() == View.GONE) {
					message.setVisibility(View.VISIBLE);
				} else {
					message.setVisibility(View.GONE);
				}
			}
		});
		return new ViewHolder(layout);
	}

	@Override
	public void onBindViewHolder(MessagesAdapter.ViewHolder holder, final int position) {
		Message message = messages.get(position);
		User from = null;
		try {
			from = UserStorage.findUser(message.getFrom());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		byte[] decodedString = Base64.decode(from.getProfileImage(), Base64.DEFAULT);
		Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		holder.image.setImageBitmap(decodedImage);
		holder.name.setText(from.getFullName());
		StringBuilder stringBuilder = new StringBuilder();
		if (from.getIsCoach()) {
			stringBuilder.append("Coach");
		}
		if (from.getIsDoctor()) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(" and ");
			}
			stringBuilder.append("Doctor");
		}
		if (stringBuilder.length() > 0) {
			holder.title.setVisibility(View.VISIBLE);
			holder.title.setText(stringBuilder.toString());
		} else {
			holder.title.setVisibility(View.GONE);
		}

		ArrayAdapter<Message> adapter =
				new MessagePieceAdapter(holder.image.getContext(), R.layout.line_message_content);
		holder.messagesContent.setAdapter(adapter);

		holder.messagesContent.setVisibility(View.GONE);
	}

	@Override
	public int getItemCount() {
		return messages.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		private ImageView image;
		private TextView name;
		private TextView title;
		private ListView messagesContent;

		public ViewHolder(View layout) {
			super(layout);
			this.image = (ImageView) layout.findViewById(R.id.image);
			this.name = (TextView) layout.findViewById(R.id.name);
			this.title = (TextView) layout.findViewById(R.id.title);
			this.messagesContent = (ListView) layout.findViewById(R.id.message);
		}
	}

	private class MessagePieceAdapter extends ArrayAdapter<Message> {

		public MessagePieceAdapter(Context context, int resource) {
			super(context, resource);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View layout = convertView;
			if (layout == null) {
				LayoutInflater inflater = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = inflater.inflate(R.layout.line_message_content, parent, false);
			}

			TextView content = (TextView) layout.findViewById(R.id.content);
			Button button = (Button) layout.findViewById(R.id.button);

			Message message = messages.get(position);
			content.setText(message.getMessage());
			if (!message.hasData()) {
				button.setVisibility(View.GONE);
			} else {
				button.setVisibility(View.VISIBLE);
			}

			return layout;
		}

		@Override
		public int getCount() {
			return 1;
		}
	}
}
