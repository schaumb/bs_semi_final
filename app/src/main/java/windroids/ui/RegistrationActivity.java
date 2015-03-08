package windroids.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import windroids.R;
import windroids.config.Constants;
import windroids.entities.User;
import windroids.storage.UserStorage;

public class RegistrationActivity extends Activity {

    Button submitButton;
	Button imageButton;
    SharedPreferences sharedpreferences;
	Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sharedpreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);

        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = false;
                try {
				    success = UserStorage.checkRegister(getRegistrationDatas());
                } catch (UserStorage.NotEnoughCharacterInPasswordException e) {
                    alertError("Too low character number for password, please use at least 8!");
                } catch (UserStorage.NotLowerCaseCharacterInPasswordException e) {
                    alertError("Please use lower character too in password!");
                } catch (UserStorage.NotUpperCaseCharacterInPasswordException e) {
                    alertError("Please use upper character too in password!");
                } catch (UserStorage.NotNumberCharacterInPasswordException e) {
                    alertError("Please use numbers too in password!");
                } catch (UserStorage.DuplicatedUserNameException e) {
                    alertError("User already exists!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (success) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Constants.USER_REGISTERED_STATE, true);
                    editor.commit();
                    finish();
                }
            }
        });

		imageButton = (Button) findViewById(R.id.image_button);
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			imageBitmap = (Bitmap) data.getExtras().get("data");
			Toast.makeText(this, "Image successfully saved", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.i(RegistrationActivity.class.getSimpleName(), "Nem sikerült betölteni a képet.");
		}
	}

    public User getRegistrationDatas() {
        String userName = ((EditText) findViewById(R.id.account_name)).getText().toString();
        String fullName = ((EditText) findViewById(R.id.full_name)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
		String image;
		if (imageBitmap == null) {
			imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_img);
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();
		image = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return new User(userName, password, image, fullName, email, null, null, null, null, null, null, null, null);
    }

    private void alertError(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .show();
    }


}
