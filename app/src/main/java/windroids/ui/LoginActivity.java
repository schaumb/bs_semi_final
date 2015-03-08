package windroids.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import windroids.R;
import windroids.entities.User;
import windroids.storage.UserStorage;
import windroids.ui.main.MainActivity;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final Button loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				User user = getLoginDatas();
				if (user != null) {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra(MainActivity.EXTRA_USER, user);
					startActivity(intent);
				}
			}
		});

		Button signinButton = (Button) findViewById(R.id.signin_button);
		signinButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
			}
		});
	}

	public User getLoginDatas() {
		String userName = ((EditText) findViewById(R.id.account_name)).getText().toString();
		String password = ((EditText) findViewById(R.id.password)).getText().toString();
		User user = null;
		try {
			user = UserStorage.checkLogin(userName, password);
		} catch (UserStorage.WrongPasswordException e) {
			alertDialog("Wrong username/password!");
		} catch (UserStorage.NotExistUserException e) {
			alertDialog("User not exists!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private void alertDialog(String message) {
		new AlertDialog.Builder(this)
				.setMessage(message)
				.show();
	}
}
